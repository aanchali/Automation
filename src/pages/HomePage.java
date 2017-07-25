package pages;
import utils.Waits;

import org.openqa.selenium.*;
//import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;


public class HomePage 
{
	public WebDriver driver;
	private WebElement popUpClose;
	
	public HomePage(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public String getTitle() 
	{
		System.out.println(driver.getTitle());
		return driver.getTitle();
	}

	public void closePopUp()
	{
		try {
			Waits waits =new Waits();
			waits.waitForPresenceOfElement(driver,"id","page-load-close");
			popUpClose = driver.findElement(By.id("page-load-close"));
			popUpClose.click();
		    } 
		catch (NoSuchElementException e) {
			System.out.println("Exception message" + e.getMessage());
			Assert.fail("Element is not found");
		}
	}

	public void CreateProsellerAccount()                               //createproseller accnt
	{
		  //click on Mydroom	
		 try
		{
			 WebElement e=driver.findElement(By.xpath("//*[@id='user_details']")); 	 
		e.click();
		driver.findElement(By.xpath("//*[@id='user_loggedout']/a[3]")).click();  //click on proseller button
	//  new UserRegister(driver);
		
		}	
		  catch(NoSuchElementException e1)
		{
			System.out.println("Exception message" + e1.getMessage());
		}				
	}
		
	public void login()
	{
		try
		 {
			
		  }
		catch(NoSuchElementException e2)
		  {
			
		    }
		
	}	
}




