package tests;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.HomePage;
//import pages.HomePage;
import pages.UserRegister;
import utils.SetUp;

public class RegAsProSellerSignUpPage 
{
  public WebDriver driver;
  
    @BeforeTest
    public void beforeTest() throws InterruptedException 
      {
        SetUp setup = new SetUp();
        System.out.println("hello");
        driver = setup.serverAuth();             // Opens up Droom url 
      }
    
    @Test
	public void ResgisterAsProseller() throws Exception
     {
   // 	Close pop-up on home page
        HomePage homePage = new HomePage(driver);             
        homePage.closePopUp(); 
        homePage.CreateProsellerAccount();
       
   //   Creates a new pro-seller account
		UserRegister registerLogin = new UserRegister(driver);  
        registerLogin.userRegister(driver); 	 
   	  }
    
/* public void RegisterAsCasualseller() throws Exception
    {
       UserRegister registerLogin1 = new UserRegister(driver);  
     //registerLogin1.userRegister();
       registerLogin1.CasualUser(); 
*/   
 }

    
/*    @AfterTest
    public void afterTest()
    {
    	driver.quit();
    }*/

    


