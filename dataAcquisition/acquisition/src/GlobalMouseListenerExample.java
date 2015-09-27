import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class GlobalMouseListenerExample implements NativeMouseInputListener, NativeMouseWheelListener
{
	
	//name of the file where the current log is saved
	private String filename;
	//time in milliseconds of the last event
	private long lastEventTime;
	
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the lastEventTime
	 */
	public long getLastEventTime() {
		return lastEventTime;
	}

	/**
	 * @param lastEventTime the lastEventTime to set
	 */
	public void setLastEventTime(long lastEventTime) {
		this.lastEventTime = lastEventTime;
	}
	
	//call to this function when some error occurs and the log file data is corrupted
	//delete the current log file
	private void deleteFile(int err)
	{
		switch(err)
		{
			case 0:
				JOptionPane.showMessageDialog(null, "Error in getting file buffer. Please restart.",
						"ERROR", JOptionPane.ERROR_MESSAGE);
				break;
			case 1:
				JOptionPane.showMessageDialog(null, "Error in registering mouse click event. Please restart.",
						"ERROR", JOptionPane.ERROR_MESSAGE);
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Error in registering mouse press event. Please restart.",
						"ERROR", JOptionPane.ERROR_MESSAGE);
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "Error in registering mouse release event. Please restart.",
						"ERROR", JOptionPane.ERROR_MESSAGE);
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "Error in registering mouse move event. Please restart.",
						"ERROR", JOptionPane.ERROR_MESSAGE);
				break;
			case 5:
				JOptionPane.showMessageDialog(null, "Error in registering mouse drag event. Please restart.",
						"ERROR", JOptionPane.ERROR_MESSAGE);
				break;
			case 6:
				JOptionPane.showMessageDialog(null, "Error in registering mouse wheel move event. Please restart.",
						"ERROR", JOptionPane.ERROR_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(null, "Error in registering mouse wheel move event. Please restart.",
						"ERROR", JOptionPane.ERROR_MESSAGE);
		}
		File f = new File(getFilename());
		f.delete();
		System.exit(-1);
	}

	//write to file using bufferedWriter
	private BufferedWriter getBufferedWriter()
	{
		BufferedWriter bwriter;
		try
		{
			File f = new File(this.getFilename());
			FileWriter writer = new FileWriter(this.getFilename(), true);	//append
			bwriter = new BufferedWriter(writer);
			return bwriter;
		}
		catch(IOException e)
		{
			this.deleteFile(0);
		}
		return null;
	}
	
	public void nativeMouseClicked(NativeMouseEvent e)
	{
		try
		{
			BufferedWriter bwriter = this.getBufferedWriter();
			long currentTime = e.getWhen();
			long relativeTime = currentTime - this.getLastEventTime();
			this.setLastEventTime(currentTime);
			bwriter.write("MC, " + e.getClickCount() + ", " + relativeTime + "\n");
			bwriter.close();
		}
		catch (IOException e1)
		{
			this.deleteFile(1);
		}
	}

	public void nativeMousePressed(NativeMouseEvent e)
	{
		try
		{
			BufferedWriter bwriter = this.getBufferedWriter();
			long currentTime = e.getWhen();
			long relativeTime = currentTime - this.getLastEventTime();
			this.setLastEventTime(currentTime);
			bwriter.write("MP, " + e.getButton() + ", " + relativeTime + "\n");
			bwriter.close();
		}
		catch (IOException e1)
		{
			this.deleteFile(2);
		}
	}

	public void nativeMouseReleased(NativeMouseEvent e)
	{
		try
		{
			BufferedWriter bwriter = this.getBufferedWriter();
			long currentTime = e.getWhen();
			long relativeTime = currentTime - this.getLastEventTime();
			this.setLastEventTime(currentTime);
			bwriter.write("MR, " + e.getButton() + ", " + relativeTime + "\n");
			bwriter.close();
		}
		catch (IOException e1)
		{
			this.deleteFile(3);
		}
	}

	public void nativeMouseMoved(NativeMouseEvent e)
	{
		try
		{
			BufferedWriter bwriter = this.getBufferedWriter();
			long currentTime = e.getWhen();
			long relativeTime = currentTime - this.getLastEventTime();
			this.setLastEventTime(currentTime);
			bwriter.write("MM, " + e.getX() + ", " + e.getY() + ", " + relativeTime + "\n");
			bwriter.close();
		}
		catch (IOException e1)
		{
			this.deleteFile(4);
		}
	}

	public void nativeMouseDragged(NativeMouseEvent e)
	{
		try
		{
			BufferedWriter bwriter = this.getBufferedWriter();
			long currentTime = e.getWhen();
			long relativeTime = currentTime - this.getLastEventTime();
			this.setLastEventTime(currentTime);
			bwriter.write("MD, " + e.getX() + ", " + e.getY() + ", " + relativeTime + "\n");
			bwriter.close();
		}
		catch (IOException e1)
		{
			this.deleteFile(5);
		}
	}
	
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e)
	{
		try
		{
			BufferedWriter bwriter = this.getBufferedWriter();
			long currentTime = e.getWhen();
			long relativeTime = currentTime - this.getLastEventTime();
			this.setLastEventTime(currentTime);
			bwriter.write("MWM, " + e.getX() + ", " + e.getY() + ", " + e.getWheelRotation() + ", " + e.getScrollAmount() + ", " + e.getScrollType() + ", " + relativeTime + "\n");
			bwriter.close();
		}
		catch (IOException e1)
		{
			this.deleteFile(6);
		}
	}

	public static void main(String[] args)
	{
		// Clear previous logging configurations.
		LogManager.getLogManager().reset();

		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		
		String tempFileName = "";
		
		try
		{
			//register mouse hook
			GlobalScreen.registerNativeHook();
			
			//get client related various informations
			String ipAddr = InetAddress.getLocalHost().getHostAddress();
			String osName = System.getProperty("os.name");
			String osArch = System.getProperty("os.arch");
			String osVersion = System.getProperty("os.version");
			String username = System.getProperty("user.name");
			//get screen resolution
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			double width = screenSize.getWidth();
			double height = screenSize.getHeight();
			
			//get user home directory
			String homeDir = System.getProperty("user.home");
			File newDir = new File(homeDir + "/MouseLogDir");
			//create directory if it doesn't exist
			if(!newDir.exists())
			{
				boolean created = newDir.mkdir();
				if(!created)
				{
					//directory could not be created so show error
					throw new IOException();
				}
			}
			//directory already exists(or got created) so create file with current time-stamp
			
			//get current time stamp
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			
			//create new file for logging
			int n = newDir.listFiles().length;	//number of current files in the directory
			tempFileName = homeDir + "/MouseLogDir/log" + (n+1) + ".txt";
			File f = new File(tempFileName);
			//create the file
			f.createNewFile();
			FileWriter writer = new FileWriter(tempFileName, true);	//append
			//FileWriter writer = new FileWriter(f.getName());
			BufferedWriter bwriter = new BufferedWriter(writer);
			
			//write initial user stuffs and time of logging
			bwriter.write("********************************************************************************\n");
			bwriter.write("LOGGING TIME: " + timeStamp + "\n");
			bwriter.write("CLIENT IP: " + ipAddr + "\n");
			bwriter.write("USERNAME: " + username + "\n");
			bwriter.write("OS: " + osName + "\n");
			bwriter.write("ARCHITECTURE: " + osArch + "\n");
			bwriter.write("VERSION: " + osVersion + "\n");
			bwriter.write("RESOLUTION: " + width + " " + height + "\n");
			bwriter.write("********************************************************************************\n");
			bwriter.close();
		}
		catch (NativeHookException | IOException ex)
		{
			System.err.println("There was a problem registering the native hook.");
			//delete the file made
			File f = new File(tempFileName);
			f.delete();
			System.exit(1);
		}
		
		//Construct the example object.
		GlobalMouseListenerExample example = new GlobalMouseListenerExample();
		
		//set last event time to system time in milliseconds
		example.setLastEventTime((int)System.currentTimeMillis());
		//set file name for global use
		example.setFilename(tempFileName);
		
		//upload the text file to server
//		FileUpload upload = new FileUpload();
//		upload.uploadFile("../log.txt", "10.3.100.207", 8080);
		
		//Add the appropriate listeners for the example object.
		GlobalScreen.addNativeMouseListener(example);
		GlobalScreen.addNativeMouseMotionListener(example);
		GlobalScreen.addNativeMouseWheelListener(example);
	}
}
