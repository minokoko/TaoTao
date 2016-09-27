package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

/**
 * 内容管理Service
 * 
 * @author jessyon
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentList(Long parentId) {

		// 按条件查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();

		// 查询条件为parentId
		criteria.andParentIdEqualTo(parentId);

		// 执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

		// 转换成EasyUITreeNode列表
		List<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();

		for (TbContentCategory tbContentCategory : list) {

			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(tbContentCategory.getId());
			treeNode.setText(tbContentCategory.getName());
			// 如果没有子节点为open,如果有子节点为closed
			treeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			resultList.add(treeNode);

		}

		return resultList;
	}

	/**
	 * 插入内容管理
	 */
	@Override
	public TaotaoResult insertCategory(Long parentId, String name) {

		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		// 添加状态，1为正常，2删除
		contentCategory.setStatus(1);
		contentCategory.setIsParent(false);
		// 设置排列序号,表示同级目录的展示优先级,取值为大于零的整数
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());

		// 插入数据库
		contentCategoryMapper.insert(contentCategory);
		Long id = contentCategory.getId();
		// 判断父节点的isParent属性
		// 查询父节点
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			// 更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}

		// 返回主键
		return TaotaoResult.ok(id);
	}

	/**
	 * 重命名
	 */
	@Override
	public void updateCategoryName(Long id, String name) {
		TbContentCategory node = contentCategoryMapper.selectByPrimaryKey(id);
		node.setName(name);
		contentCategoryMapper.updateByPrimaryKey(node);
	}

	/**
	 * 根据Id删除，包括级联删除
	 */
	@Override
	public void deleteById(Long parentId, Long id) {

		// 根据id查询要删除的node
		TbContentCategory node = contentCategoryMapper.selectByPrimaryKey(id);

		// 该节点是否为叶子节点
		Boolean isParent = node.getIsParent();

		// 保存父节点的ID
		// 如果是第一次调用，参数中的parentId为空，需要将parentId查询出来，从第二次开始，这里的parentId与参数中的一样
		parentId = node.getParentId();

		// 根据node的parentId找到父节点
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);

		// 如果是叶子节点
		if (!isParent) {
			// 根据ID删除节点
			int deleteByPrimaryKey = contentCategoryMapper.deleteByPrimaryKey(id);

			// 查询是否还有父节点为parentId的节点(node节点是否还有兄弟节点)
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();

			// 查询条件为parentId
			criteria.andParentIdEqualTo(parentId);

			// 执行查询
			List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

			// 如果list不为空，说明还有node的父节点还有其他子节点,则不作其他操作
			// 如果list为空，则将node的父节点更新为子节点
			if (list == null) {
				parentNode.setIsParent(false);
				contentCategoryMapper.updateByPrimaryKey(parentNode);
			}
		} else {
			// 不是叶子节点，而是一个有子节点的根节点,要使用递归删除
			// 根据parentId查询所有子节点，先删除子节点，再删除自己
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();

			// 查询条件为parentId
			criteria.andParentIdEqualTo(id);

			// 执行查询
			List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

			// 先把自己的所有子节点删除
			for (TbContentCategory tbContentCategory : list) {
				deleteById(id, tbContentCategory.getId());
			}
			// 删除自己
			contentCategoryMapper.deleteByPrimaryKey(id);
		}

	}

}
