import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;

public class GlobalMouseListenerExample implements NativeMouseInputListener
{
	//write to file using bufferedWriter
	private BufferedWriter getBufferedWriter()
	{
		BufferedWriter bwriter;
		try
		{
			File f = new File("log.txt");
			//create file if it doesn't exist
			if(!f.exists())
			{
				f.createNewFile();
			}
			
			FileWriter writer = new FileWriter(f.getName(), true);	//append
			//FileWriter writer = new FileWriter(f.getName());
			bwriter = new BufferedWriter(writer);
			return bwriter;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void nativeMouseClicked(NativeMouseEvent e)
	{
		try
		{
			BufferedWriter bwriter = this.getBufferedWriter();
			bwriter.write("Mosue Clicked: " + e.getClickCount() + "at: " + e.getWhen() + "\n");
			bwriter.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public void nativeMousePressed(NativeMouseEvent e)
	{
		try
		{
			BufferedWriter bwriter = this.getBufferedWriter();
			bwriter.write("Mosue Pressed: " + e.getButton() + "at: " + e.getWhen() + "\n");
			bwriter.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public void nativeMouseReleased(NativeMouseEvent e)
	{
		try
		{
			BufferedWriter bwriter = this.getBufferedWriter();
			bwriter.write("Mosue Released: " + e.getButton() + "at: " + e.getWhen() + "\n");
			bwriter.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public void nativeMouseMoved(NativeMouseEvent e)
	{
//		try
//		{
//			BufferedWriter bwriter = this.getBufferedWriter();
//			bwriter.write("Mosue Moved: " + e.getX() + ", " + e.getY() + "at: " + e.getWhen() + "\n");
//			bwriter.close();
//		}
//		catch (IOException e1)
//		{
//			e1.printStackTrace();
//		}
	}

	public void nativeMouseDragged(NativeMouseEvent e)
	{
//		try
//		{
//			BufferedWriter bwriter = this.getBufferedWriter();
//			bwriter.write("Mosue Dragged: " + e.getX() + ", " + e.getY() + "at: " + e.getWhen() + "\n");
//			bwriter.close();
//		}
//		catch (IOException e1)
//		{
//			e1.printStackTrace();
//		}
	}
	
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e)
	{
		try
		{
			BufferedWriter bwriter = this.getBufferedWriter();
			bwriter.write("Mosue Wheel Moved: " + e.getX() + ", " + e.getY() + " " + e.getWheelRotation() + " " + e.getScrollAmount() + " " + e.getScrollType() + "at: " + e.getWhen() + "\n");
			bwriter.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		// Clear previous logging configurations.
		LogManager.getLogManager().reset();

		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		
		try
		{
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex)
		{
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		//Construct the example object.
		GlobalMouseListenerExample example = new GlobalMouseListenerExample();

		//Add the appropriate listeners for the example object.
		GlobalScreen.addNativeMouseListener(example);
		GlobalScreen.addNativeMouseMotionListener(example);
	}
}
