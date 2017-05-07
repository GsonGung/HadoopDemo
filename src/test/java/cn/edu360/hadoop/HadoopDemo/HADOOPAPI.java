package cn.edu360.hadoop.HadoopDemo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HADOOPAPI {
	
	FileSystem fileSystem = null;
	
	@Before
	public void init() throws IOException{
		//1.更改有写权限的用户
		System.setProperty("HADOOP_USER_NAME","root");
		//2.hdfs建立连接,知道namenode地址
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoop.edu360.cn:9000");
		fileSystem = FileSystem.get(conf);
	}
	
	@After
	public void destory() throws IOException{
		//关闭fileSystem
		fileSystem.close();
	}
	
	@Test
	public void testDownload() throws IllegalArgumentException, IOException{
		//3.打开hdfs的输入流
		FSDataInputStream in = fileSystem.open(new Path("/anaconda-ks.cfg"));
		//4.打开本地文件系统的输出流
		OutputStream out = new FileOutputStream("d://anaconda-ks-out.cfg");
		//5.cope:in->out 关闭流
		IOUtils.copyBytes(in, out, 1024, true);
	}
	
	@Test
	public void testUpload() throws IllegalArgumentException, IOException{
		//3.打开本地文件系统的输入流
		InputStream in = new FileInputStream("d://anaconda-ks-out.cfg");
		//4.打开hdfs的输出流
		OutputStream out = fileSystem.create(new Path("/anaconda-ks-out-in.cfg"));
		//5.cope:out->in 关闭流
		IOUtils.copyBytes(in, out, 1024, true);
	}
	
	@Test
	public void testMkdir() throws IllegalArgumentException, IOException{
		//3.创建hdfs目录
		fileSystem.mkdirs(new Path("/a"));
	}

	@Test
	public void testDelete() throws IllegalArgumentException, IOException{
		//3.删除hdfs文件(夹)，第二个参数为是否递归删除
		fileSystem.delete(new Path("/a"), true);
		
		boolean isExists = fileSystem.exists(new Path("/a"));
		
		Assert.assertFalse(isExists);
	}
}
