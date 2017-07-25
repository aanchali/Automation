package pages;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;

import utils.Waits;

//import com.mysql.jdbc.Driver;

public class fileupload 
{
	public void fileupload1(WebDriver driver) throws Exception
		// TODO Auto-generated method stub
		{
		System.out.println("Upload");
		 Robot robot = new Robot();
		 
		 Thread.sleep(5000);
/*		 Waits w= new Waits();
 	     w.waitForVisibilityOfElement(driver, "id", "uploadtempGST");*/
 	     
         driver.findElement(By.id("uploadtempGST")).click();
         robot.setAutoDelay(2000);   //is same as thread.sleep
         
	     StringSelection stringSelection = new StringSelection("userregister.png");
	     Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	     
	     robot.keyPress(KeyEvent.VK_CONTROL);
	     robot.keyPress(KeyEvent.VK_V);

	     robot.keyRelease(KeyEvent.VK_CONTROL);
	     robot.keyRelease(KeyEvent.VK_V);
	     
	     robot.setAutoDelay(2000);
	     
	     robot.keyPress(KeyEvent.VK_ENTER);
	     robot.keyRelease(KeyEvent.VK_ENTER);
	     }
	
	public static void fileupload2(WebDriver driver) throws AWTException, InterruptedException
	{
		 Robot robot = new Robot();
			
		 Waits w= new Waits();
 	     w.waitForVisibilityOfElement(driver, "xpath", ".//*[@id='upload-box']/label");
		 
		         driver.findElement(By.xpath(".//*[@id='upload-box']/label")).click();
		         robot.setAutoDelay(2000);   //is same as thread.sleep
		         
			     StringSelection stringSelection = new StringSelection("userregister.png");
			     Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			     
			     robot.keyPress(KeyEvent.VK_CONTROL);
			     robot.keyPress(KeyEvent.VK_V);

			     robot.keyRelease(KeyEvent.VK_CONTROL);
			     robot.keyRelease(KeyEvent.VK_V);
			     
			     robot.setAutoDelay(2000);
			     
			     robot.keyPress(KeyEvent.VK_ENTER);
			     robot.keyRelease(KeyEvent.VK_ENTER);
			     
			     UserRegister u= new UserRegister(driver);
			     u.scroll1();
				  
			//	    Thread.sleep(3000);
	}
}
