package utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SetUp 
{	
	public SetUp() 
    {			
		
    }	
	
	public WebDriver serverAuth() 
	{
        WebDriver  driver = new FirefoxDriver();
		String baseurl1 = "http://qa6.droom.in/";
		driver.get(baseurl1);		
		driver.manage().window().maximize();		
		return driver;
    }
	
/*	public WebDriver login() 
	{
		WebDriver driver = new FirefoxDriver();
		String baseurl="https://secure.droom.in/user/login";
		driver.get(baseurl);
		driver.manage().window().maximize();
		return driver;
	}	
	public WebDriver Addtocart()
	{
		WebDriver driver= new FirefoxDriver();
		driver.get("http://qa6.droom.in/product/maruti-suzuki-alto-lxi-2011-586c85251b");
		driver.manage().window().maximize();
		return driver;
	}
	*/	
}
