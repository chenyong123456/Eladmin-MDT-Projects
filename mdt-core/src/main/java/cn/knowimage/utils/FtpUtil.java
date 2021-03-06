package cn.knowimage.utils;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * ftp上传下载工具类
 * <p>Title: FtpUtil</p>
 * @author	yong.Mr
 * @date	2019年7月29日下午8:11:51
 * @version 1.0
 */
public class FtpUtil {

	/** 
	 * Description: 向FTP服务器上传文件 
	 * @param host FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param basePath FTP服务器基础目录  ----->/usr/local/nginx/html
	 * @param filePath FTP服务器文件存放路径。例如分日期存放：/2019/01/01。文件的路径为basePath+filePath
	 * @param filename 上传到FTP服务器上的文件名 
	 * @param input 输入流 
	 * @return 成功返回true，否则返回false 
	 */  
	public static boolean uploadFile(String host, int port, String username, String password, String basePath,
		String filePath, String filename, InputStream input) throws IOException {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		//ftp.enterLocalPassiveMode();
		try {
			int reply;
			ftp.connect(host, port);// 连接FTP服务器
			//ftp.enterLocalActiveMode();
			ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
			ftp.enterLocalPassiveMode();  //
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			System.out.println("连接图片服务器服务器响应编号:--->" + reply);
			System.out.println("查看连接服务器的状态:--->" + FTPReply.isPositiveCompletion(reply));
			//ftp.setFileType(FTP.BINARY_FILE_TYPE);
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			//切换到上传目录
			if (!ftp.changeWorkingDirectory(basePath+filePath)) {
				//如果目录不存在创建目录
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir)) continue;
					tempPath += "/" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						if (!ftp.makeDirectory(tempPath)) {
							return result;
						} else {
							ftp.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			//设置上传文件的类型为二进制类型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			//上传文件
			if (!ftp.storeFile(filename, input)) {
				return result;
			}
			input.close();
			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			//}
		}
		return result;
	}
	
	/** 
	 * Description: 从FTP服务器下载文件 
	 * @param host FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param remotePath FTP服务器上的相对路径 
	 * @param fileName 要下载的文件名 
	 * @param localPath 下载后保存到本地的路径 
	 * @return  返回boolean
	 */  
	public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
			String fileName, String localPath) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

	/**
	 * Description: 从FTP服务器下载单个文件
	 * @param host FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 * @return  返回File
	 */
	public static File downloadSingleFile(String host, int port, String username, String password, String remotePath,
									   String fileName, String localPath) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		File localFile = null;
		try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				//return result;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return localFile;
	}
	/**
	 * 删除文件
	 * @param hostname FTP服务器地址
	 * @param port   FTP服务器端口号
	 * @param username   FTP登录帐号
	 * @param password   FTP登录密码
	 * @param pathname   FTP服务器保存目录
	 * @param filename   要删除的文件名称
	 * @return
	 */
	public static boolean deleteFile(String hostname, int port, String username, String password, String pathname, String filename){
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		try {
			//连接FTP服务器
			ftpClient.connect(hostname, port);
			//登录FTP服务器
			ftpClient.login(username, password);
			//验证FTP服务器是否登录成功
			int replyCode = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(replyCode)){
				return flag;
			}
			//切换FTP目录
			ftpClient.changeWorkingDirectory(pathname);
			ftpClient.dele(filename);
			ftpClient.logout();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(ftpClient.isConnected()){
				try {
					ftpClient.logout();
				} catch (IOException e) {

				}
			}
		}
		return flag;
	}
	/**
	 * 主方法
	 * @param args 主方法的数组参数
	 */
	public static void main(String[] args) {
	        //FileInputStream in=new FileInputStream(new File("D:\\temp\\image\\gaigeming.jpg"));
	        //File getFile = downloadSingleFile("192.168.50.4", 21, "ftpuser", "ftpuser", "/usr/local/nginx/html/image","01201627528722@IMG@_1.png", "C:\\Users\\wh123\\Desktop\\image");
			//deleteFile("192.168.50.4", 21, "ftpuser", "ftpuser", "/image/MDT","20191221142440,4");
	        //System.out.println(getFile.toString());

	}
}
