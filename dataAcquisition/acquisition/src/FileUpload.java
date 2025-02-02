import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPHTTPClient;
/**
* A program that demonstrates how to upload files from local computer* to a remote FTP server using Apache Commons Net API.
* @author www.codejava.net*/

public class FileUpload
{
	public void uploadFile(String fileLocation, String proxyHost, int proxyPort)
	{
//		String server = "203.110.246.230";
//		String server = "10.109.11.91";
		String server = "www.filegenie.com";
		int port = 21;
		String user = "soumya";
		String pass = "1253-JWTI";
		FTPClient ftpClient;
		if(proxyHost !=null)
		{
			System.out.println("Using HTTP proxy server: " + proxyHost);
			ftpClient = new FTPHTTPClient(proxyHost, proxyPort);
		}
		else
		{
			ftpClient = new FTPClient();
		}
		try
		{
			System.out.println("Starting...");
			ftpClient.connect(server, port);
			System.out.println("Starting 1...");
			ftpClient.login(user, pass);
			System.out.println("Starting 2...");
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
			// APPROACH #1: uploads first file using an InputStream
			File firstLocalFile = new File(fileLocation);
			String firstRemoteFile = "log.zip";
			InputStream inputStream = new FileInputStream(firstLocalFile);
			System.out.println("Start uploading first file");
			boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
			inputStream.close();
			if(done)
			{
				System.out.println("The first file is uploaded successfully.");
			}
			// APPROACH #2: uploads second file using an OutputStream
//			File secondLocalFile = new File("E:/Test/Report.doc");
//			String secondRemoteFile = "test/Report.doc";
//			inputStream = new FileInputStream(secondLocalFile);
//			System.out.println("Start uploading second file");
//			OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
//			byte[] bytesIn = new byte[4096];
//			int read = 0;
//			while((read = inputStream.read(bytesIn)) != -1)
//			{
//				outputStream.write(bytesIn, 0, read);
//			}
//			inputStream.close();
//			outputStream.close();
//			boolean completed = ftpClient.completePendingCommand();
//			if(completed)
//			{
//				System.out.println("The second file is uploaded successfully.");
//			}
		}
		catch(IOException ex)
		{
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if(ftpClient.isConnected())
				{
					ftpClient.logout();
					ftpClient.disconnect();
				}
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}