package com.taotao.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.taotao.common.utils.FastDFSClient;

public class FastdfsTest {

	/*1、把FastDFS提供的jar包添加到工程中
	2、初始化全局配置。加载一个配置文件。
	3、创建一个TrackerClient对象。
	4、创建一个TrackerServer对象。
	5、声明一个StorageServer对象，null。
	6、获得StorageClient对象。
	7、直接调用StorageClient对象方法上传文件即可。
*/
	@Test
	public void testUpload() throws FileNotFoundException, IOException, MyException{
		ClientGlobal.init("C:\\Users\\jessyon\\TaoTao\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\properties\\fdfs_client.conf");
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer,storageServer);
		String [] strings = storageClient.upload_file("C:\\Users\\jessyon\\Pictures\\kuniklo.jpg", "jpg", null);
		for(String string:strings){
			System.out.println(string);
		}
		
	}
	

	
}
