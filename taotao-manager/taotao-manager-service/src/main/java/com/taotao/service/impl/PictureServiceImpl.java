package com.taotao.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FastDFSClient;
import com.taotao.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

	@Value("${IMAGE_SERVER_BASE_URL}")
	private String IMAGE_SERVER_BASE_URL;

	@Override
	public PictureResult uploadPic(MultipartFile picFile) {

		PictureResult result = new PictureResult();

		if (picFile.isEmpty()) {
			result.setError(1);
			result.setMessage("图片为空");
			return result;
		}

		try {

			// 取图片的扩展名
			String originalFilename = picFile.getOriginalFilename();
			// 扩展名不要"."
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			// 图片不为空，上传到图片服务器
			FastDFSClient client = new FastDFSClient("classpath:properties/fdfs_client.conf");
			String url = client.uploadFile(picFile.getBytes(), extName);
			// 拼接图片服务器的ip地址
			url= IMAGE_SERVER_BASE_URL + url;
			// 把url返回给客户端
			result.setError(0);
			result.setUrl(url);
		} catch (Exception e) {

			e.printStackTrace();
			result.setError(1);
			result.setMessage("上传失败");
		}

		return result;
	}

}
