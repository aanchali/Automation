package pages;
//All import statement

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import utils.Waits;

public class UserRegister 
{
	public WebDriver driver;
    public WebElement userFirstName;
    public WebElement userLastName;
    public WebElement companyname1;
    public WebElement Email1;
    public WebElement Mobilenumber1;
    public WebElement password1;
    public WebElement ve1;
    public WebElement bicycle1;
    public WebElement days;
    public WebElement time;
    public WebElement Pan1;
    public FileInputStream fi;
	public static String FilePath = System.getProperty("user.dir") + "\\data123.xls";
	public static String Sheetname= "signup";
	public static HSSFSheet Sheet;
	public static HSSFRow Row;
	public static HSSFCell Cell;

	 	   
	 public WebElement randomgenerator(List<WebElement> rto) 
        {
	       Random rt2= new Random();
	       int roo= rt2.nextInt(rto.size());
	       rto.get(roo).click(); 	
	       return (rto.get(roo));
		}		   
	   
	public UserRegister(WebDriver driver)
	{
			this.driver = driver;
			PageFactory.initElements(this.driver, this);
	}

	public String getTitle() 
	{
		   System.out.println(driver.getTitle());
		   return driver.getTitle();
	}
  
    public void Userfirstname() throws Exception
	 {
    	 Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
    	 String firstname1= Sheet.getRow(1).getCell(0).getStringCellValue();
    	 userFirstName= driver.findElement(By.xpath(".//*[@id='first_name']"));
		 userFirstName.sendKeys(firstname1);  
	 }
	   
        public void Userlastname() throws Exception 
	  {	  
	    Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
	    String lastname1= Sheet.getRow(1).getCell(1).getStringCellValue();
        userLastName = driver.findElement(By.xpath(".//*[@id='last_name']"));   //Enter the last name
        userLastName.sendKeys(lastname1);     
	  }  
   
      public void Handlename() throws Exception
    {    
    	Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(10000);	  
    	WebElement handlename = driver.findElement(By.xpath(".//*[@id='handle_name']")); //Enter Handle name
    	String handle1name= Sheet.getRow(1).getCell(2).getStringCellValue();
    	handlename.sendKeys(randomInt+handle1name);
    }
		
	   public void Companyname() throws Exception
	   {
		  Random randomGenerator = new Random();
		  int randomInt1 = randomGenerator.nextInt(10000);	   
		  Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
		String Companyname= Sheet.getRow(1).getCell(2).getStringCellValue();
		companyname1=driver.findElement(By.xpath("//*[@id='company_name']"));   //Enter the company name
		companyname1.sendKeys(randomInt1+Companyname);
	   }
	   
	   public void Email() throws Exception 
	   {
	    Random randomGenerator = new Random();
	    int randomInt = randomGenerator.nextInt(10000);	   
	    Sheet = ReadExcel.DataSheet(FilePath, Sheetname);  
		String Email= Sheet.getRow(1).getCell(3).getStringCellValue();
		Email1 = driver.findElement(By.xpath("//*[@id='email']"));            //Enter the Email of user
		Email1.sendKeys("he"+randomInt+Email);
	   }
	   
	   public void Mobileno() throws Exception
	   {
		 Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
		String mobileno= Sheet.getRow(1).getCell(4).getStringCellValue();
		Mobilenumber1=driver.findElement(By.xpath("//*[@id='mobile_phone']"));  //Enter the mobile phone of user
	    Mobilenumber1.sendKeys(mobileno);
	   }
	    
	   public void Password() throws Exception
	   {
		Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
	    String password= Sheet.getRow(1).getCell(5).getStringCellValue();
	    password1=driver.findElement(By.id("password"));                    //Enter the password of user
	    password1.sendKeys(password);
	    scroll1();
	    Thread.sleep(15000);
	   }
	   
	   public void scroll1() 
	   {
		JavascriptExecutor jse = (JavascriptExecutor) driver;  
	    jse.executeScript("window.scrollBy(0,250)", ""); 
	   }
	   
	    public void signup() throws Exception
	    {
	    WebElement Save=driver.findElement(By.xpath("//*[@id='signUpButton']/input"));    //click signup
	    Save.click();
	    }
	    
	    public void closepopup() throws Exception
	    {    
	    driver.findElement(By.xpath(".//*[@id='modalDivOtp']/div/div/button")).click();  //Close Pop-up on seller settings
	    }
	    
	    public void vehicles() throws Exception
	    {
	    	//Select vehicles
	    ve1 = driver.findElement(By.xpath("//*[@id='div_asset']/label"));   
	    ve1.click();
	    Thread.sleep(5000);
	    }
	    	        
        public void vehiclecategory() throws Exception
       {	          
        	//Enter vehicles category
       List<WebElement> li= driver.findElements(By.xpath(".//*[@id='asset_child']/div/label"));  //to catch all Web elements
	   randomgenerator(li);
       }	 	 
  
	    public void days() throws Exception
	 {
	    	//Select days available 
	    scroll1();	
	    Thread.sleep(5000);
        List<WebElement> ds = driver.findElements(By.xpath(".//*[@id='typ_1211']/div/div/label")); 
        randomgenerator(ds);
	 }

	    public void timesavailable() throws Exception
	 {
	   //Enter different timings
	   List<WebElement> lis = driver.findElements(By.xpath(".//*[@id='typ_1281']/div/div/label"));    //Enter Times available   
       randomgenerator(lis); 	   
	   scroll1();
	 }
	        
	    public void timeslot() throws Exception
	    {
	    	  // Enter time slots
	    List<WebElement> tim = driver.findElements(By.xpath(".//*[@id='typ_2891']/div/div/label"));   
	    WebElement t=randomgenerator(tim);
	     if(t.getText().equals("Yes"))
	     {
	    Select day = new Select(driver.findElement(By.xpath(".//*[@id='time_slot_day']"))); //Select day
	    	   List<WebElement> day1=day.getOptions();
	    	   Random rm= new Random();
	    //	   Thread.sleep(5000);
	    	   int i=rm.nextInt(day1.size());  
	    	   if (i==0)                       //skipping first element
	    	   {
	    		   i++;
	    		   System.out.println("Yes, we got Day i.e first element");
	    	   }
	    	   day.selectByIndex(i);
	    	   
	    	   System.out.println(rm.nextInt((day1.size())));
	 Thread.sleep(2000);  	   
	     Select time= new Select(driver.findElement(By.xpath(".//*[@id='time_slot_time']"))); //Select time
	    	   List<WebElement> times = day.getOptions();	    	            	 
	    	   System.out.println(rm.nextInt(times.size()));
	    	   int j=rm.nextInt(times.size());
	    	   if (j==0)                       //skipping first element
	    	   {
	    		   j++;
	    		   System.out.println("Yes, we got time i.e first element");
	    	   }
	    	   time.selectByIndex(j);
	    	   
	     }
	         else if( t.getText().equals("No"))
	          {
	    	   System.out.println("No Time slot");
	           }  
	    scroll1();
	    }
	      
	    public void viewingoptions() throws Exception
	    {
            // Enter viewing options
	    List<WebElement> view = driver.findElements(By.xpath(".//*[@id='typ_2921']/div/div/label")); 
		randomgenerator(view);
	    scroll1();
	    }
	    	   
	    public void shippingoptions() throws Exception
 {
	   // Enter shipping options
	   List<WebElement> ship= driver.findElements(By.xpath(".//*[@id='typ_2931']/div/div/label"));     	   
	   Random rtservice= new Random();
       int shipo= rtservice.nextInt(ship.size());
       
       System.out.println(ship.get(0).getText());	       
       System.out.println(ship.get(shipo));
       
       ship.get(shipo).click();       
       if((ship.get(shipo)).equals(ship.get(0)))
       {
    	 WebElement s= driver.findElement(By.xpath(".//*[@id='shipping_cost']")); 
         s.sendKeys("1223"); 
       }
       else if(ship.get(shipo).equals(ship.get(1)))
       {
    	   System.out.println("HI");
       }	      
 }

	   public void returnpolicy() throws Exception
 {
		//do u offer return	 
		Thread.sleep(5000);
	    List<WebElement> ship= driver.findElements(By.xpath(".//*[@id='typ_2951']/div/div/label")); 
	    System.out.println(ship.get(0));
	   
	    Random rp= new Random();
	    int rp1= rp.nextInt(ship.size());	     
	    ship.get(rp1).click();
	    
	    if(((ship.get(rp1)).equals(ship.get(0))) || (ship.get(rp1).equals(ship.get(1))) )	   
	   {
	    	WebElement d1= driver.findElement(By.xpath(".//*[@id='return_policy_option']"));
	    	//d1.click();           
	    	Thread.sleep(5000);	    	
	    	
	    	Select se= new Select(d1);
	    	List<WebElement> days = se.getOptions();	    	
	    	System.out.println(days.size()); 			 
	    	
	    	int i= rp.nextInt(days.size());
	    	if(i==0)
	    	{
	    		i++;
	    		System.out.println("Yes, we got Select Days : zeroth index");
	    	}   	
	    	se.selectByIndex(i);
	   }
	    else if (ship.get(rp1).equals(ship.get(2)))
	   {
	    	System.out.println("NO, No offer");
	    //	 driver.findElement(By.xpath(".//*[@id='return_policy_option']")).sendKeys("Under 5 Days");	
	   }

	        scroll1();	        
}        
	   
	    public void testdrive() throws Exception                  
	 {  
	   //   do u offer test drive
	    List<WebElement> td= driver.findElements(By.xpath(".//*[@id='typ_2971']/div/div/label"));
	    WebElement td1  = randomgenerator(td);
	    if(td1.getText().equals("Yes"))
	    {
		         System.out.println("Yes, test drive");	    
		         Thread.sleep(70000);
		         driver.findElement(By.xpath(".//*[@id='test_drive_price']")).click();
		         Random test= new Random();	    
	         //count options
		            Select sel2= new Select(driver.findElement(By.xpath(".//*[@id='test_drive_price']")));
				    List<WebElement> l= sel2.getOptions();
			        System.out.println(l.size());     
			        sel2.selectByIndex(test.nextInt(l.size()));
		 }
	    else if (td1.getText().equals("No"))
	    {
	    	
	    	   System.out.println("No Test drive");	
	    }
	 }	        	    
	    
	    public void RTO() throws Exception                       
	 {
	    // do u offer RTO
	    	Waits w= new Waits();
	 	     w.waitForVisibilityOfElement(driver, "xpath", ".//*[@id='typ_2991']/div/div/label");
	 	     
	    List<WebElement> rto= driver.findElements(By.xpath(".//*[@id='typ_2991']/div/div/label"));
	    WebElement rt= randomgenerator(rto);
	    
	    if(rt.getText().equals("Yes"))
	    {
		         System.out.println("Yes, RTO");	    	
	         
		         Waits w1= new Waits();
		 	     w1.waitForVisibilityOfElement(driver, "xpath", ".//*[@id='rto_service_type']");
//Enter RTO Service Type			 	  
		 	    driver.findElement(By.xpath(".//*[@id='rto_service_type']")).click();
		 	    
		         Random services= new Random();	    
		         Select sel2= new Select(driver.findElement(By.xpath(".//*[@id='rto_service_type']")));
				 List<WebElement> l= sel2.getOptions();
			     System.out.println("RTO options"+l.size());        
			     
				 int j= services.nextInt(l.size());
			     if(j==0)
			       {
			    		j++;
			    		System.out.println("Yes, we got Select Days : zeroth index");
			    	}   	
			     sel2.selectByIndex(j); 			    	
			 System.out.println(l.get(services.nextInt(l.size())).getText());
			 
			    if((l.get(services.nextInt(l.size())).getText()).equals("Paid"))
			     {			    	
			        	System.out.println("Paid");
			        	driver.findElement(By.xpath(".//*[@id='rto_service_price']")).sendKeys("23");
			     }			        			    
		 }
	    else if (rt.getText().equals("No"))
	    {   	
	    	   System.out.println("No, RTO ");	
	    }
	//    Thread.sleep(5000);
	 }
	    
	    public void Automobile() throws Exception
	    {
	    driver.findElement(By.id("type_services")).click();    //automobile services
	    }
	    	   
	    public void inspection() throws Exception
	    {
	      // choose options ( inspection,  RSA, RS, warranty )

	     Thread.sleep(7000);
	     List<WebElement> rto= driver.findElements(By.xpath(".//*[@id='services_child']/div/label"));
	     randomgenerator(rto);
	     scroll1();
	    } 
	    
	     public void Isitservicable() throws Exception
{     
	    	 // Is it servicable across India?
	    	Thread.sleep(5000); 
	       List<WebElement> service= driver.findElements(By.xpath(".//*[@id='typ_3361']/div/div/label"));	
	       System.out.println(service.size());	  
	       
	       Random rtservice= new Random();
	       int rservice= rtservice.nextInt(service.size());

	       System.out.println(service.get(0).getText());	       	       
	       service.get(rservice).click();	              
	       
	      if((service.get(rservice)).equals(service.get(0)))
	      {
	         System.out.println(" No, it isn't serviceable");	    
	         Thread.sleep(10000);
	    // select location
	         driver.findElement(By.xpath(".//*[@id='multiSelectDropdown3371']/div/label")).click();
	         Thread.sleep(5000);
	         List<WebElement> searchLocations=driver.findElements(By.xpath("//*[@id='multiSelectDropdown3371']/div/ul/li"));
	         Random sl= new Random();	    
        // count options
	         driver.findElement(By.xpath(".//*[@id='multiSelectDropdown3371']/div/ul/li["+sl.nextInt(searchLocations.size())+"]")).click();  	       
	    // Excluded pin codes
	         WebElement exe= driver.findElement(By.xpath(".//*[@id='excluded_pincodes']"));
	         exe.sendKeys("abcd");	      
	       }
	        else if(service.get(rservice).equals(service.get(1)))
	         {
	    	   System.out.println("Yes, it is servicable");
	         }        	    
	      Thread.sleep(5000);
}

	    public void serviceAvailability() throws Exception
	    {
	    	//Service Availability
	       Thread.sleep(5000);
	       List<WebElement> sa= driver.findElements(By.xpath(".//*[@id='typ_3031']/div/div/label")); 
	       randomgenerator(sa);   
	    }
	    
	    public void timesAvailability() throws Exception
	    {
	        //Times Availability
	    	 List<WebElement> ta= driver.findElements(By.xpath(".//*[@id='typ_3041']/div/div/label")); 
		       randomgenerator(ta);      
	    }
	      
	    public void methoddelivery() throws Exception
	    {
	        //Method of delivery
	        List<WebElement> md= driver.findElements(By.xpath(".//*[@id='typ_3091']/div/div/label")); 
	        randomgenerator(md);	    
	    }
	    
	    public void Itemsdetails() throws Exception
	    {
	     // Items/Details reqd from buyer
	    	List<WebElement> Id= driver.findElements(By.xpath(".//*[@id='typ_3111']/div/div/label")); 
		    randomgenerator(Id);	   
	    }
	     
	    public void service() throws Exception
	     {
	      //Does ur service require departmental approval?
	      List<WebElement> dA= driver.findElements(By.xpath(".//*[@id='typ_3131']/div/div/label")); 
		  randomgenerator(dA);
		  Thread.sleep(5000);  
	     }
	    
	    public void savecontinue() throws Exception
	    {
	    //click save andcontinue
	      Thread.sleep(5000);  
	      driver.findElement(By.xpath(".//*[@id='settingButton']/input")).click();  //save and continue
	    //scroll();   
	    }

	    public void pannumber() throws Exception
	    {
	    	 // Enter Pannumber on bussiness info page
	    Thread.sleep(5000);  
	    Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
	    String str = Sheet.getRow(1).getCell(6).getStringCellValue();
        Pan1=driver.findElement(By.id("pan_number"));       //Enter Pan number on Bussiness info page
	    Pan1.sendKeys(str); 
	    
	    }
	    
	   public void gst(String p) throws Exception
	 {
	    //Select GST Radio button	
	    Waits w= new Waits();
	 	w.waitForVisibilityOfElement(driver, "xpath", ".//*[@id='businessInfoForm']/div/div/div[3]/div/label");
	 	     
	    List<WebElement> g= driver.findElements(By.xpath(".//*[@id='businessInfoForm']/div/div/div[3]/div/label"));
	    System.out.println(g.get(0).getText());
	    
	    Random r= new Random();
	    
	    int ro= r.nextInt(g.size());
	    g.get(ro).click();
	    
	    if ((g.get(ro)).equals(g.get(0)))
	    {
	      System.out.println("Less"); 	         
	    }
	    
	    else if((g.get(ro)).equals(g.get(1)))
	    {
		  System.out.println("More");
	      gstnumber(p); 	      
	    }
	    
	    scroll1();
	//    Thread.sleep(5000);
	 }
	    
	    public void gstnumber(String p) throws Exception
	       {
	      //Enter GST number
	    	Waits w= new Waits();
		 	w.waitForVisibilityOfElement(driver, "xpath", ".//*[@id='gst_number']");
	    	
	    	WebElement GSTnumber= driver.findElement(By.xpath(".//*[@id='gst_number']"));
	    	
	    	if(p.equalsIgnoreCase("140401"))
	    	{
	  	      GSTnumber.sendKeys("03BLTPA7232DhZt");
	    	}
	    	else if(p.equalsIgnoreCase("122001"))
	    	{
	    		GSTnumber.sendKeys("06BLTPA7232DhZt");
	    	}  	
	    	else if(p.equalsIgnoreCase("147001"))
	    	{
	    		GSTnumber.sendKeys("03BLTPA7232DhZt");
	    	}
	    	
     //     scroll1();
	//      Thread.sleep(5000);  
	      } 
 
	    public String pincode() throws Exception
	 {
	    	 //Enter Pin code    	
	    ArrayList<Integer> a = new ArrayList<Integer>();
	    a.add(140401);
	    a.add(122001);
	    a.add(147001);
	   
		Waits w= new Waits();
	 	w.waitForVisibilityOfElement(driver, "xpath", ".//*[@id='zip']");
    	
	    WebElement element= driver.findElement(By.xpath(".//*[@id='zip']"));
	    
	    Random rand= new Random(); 	    
	    rand.nextInt(a.size());
	    int al= a.get(rand.nextInt(a.size()));
	    System.out.println(al);
	  
	    element.sendKeys(String.valueOf(al));
	    
	    return String.valueOf(al);
    
	 }
	    
        public void location(String p) throws Exception
    {
        	 //Enter Location
        
    	//    Thread.sleep(5000);
        	Waits w= new Waits();
    	 	w.waitForVisibilityOfElement(driver, "xpath", ".//*[@id='location']");
        		       	
    	    WebElement loc1= driver.findElement(By.xpath(".//*[@id='location']"));
            loc1.click();
              	    
    	    if(p.equalsIgnoreCase("140401"))
	    	{
    	    	loc1.sendKeys("Rajpura");
	    	}
	    	else if(p.equalsIgnoreCase("122001"))
	    	{
	    		loc1.sendKeys("Gurgaon");
	    	}  	
	    	else if(p.equalsIgnoreCase("147001"))
	    	{
	    		loc1.sendKeys("Patiala");
	    	}
    /*    Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
	    String loc= Sheet.getRow(1).getCell(9).getStringCellValue(); */ 	   	 
	   
      //  Thread.sleep(5000);
  }
        	
	    public void address() throws Exception
	    {
	    	//Enter address
	    Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
	    String address= Sheet.getRow(1).getCell(10).getStringCellValue(); 	    
	    WebElement baddress= driver.findElement(By.xpath(".//*[@id='address1']"));
        baddress.sendKeys(address);
   //     Thread.sleep(5000);
	    }
	   	    
	    public void bussinnessproof() throws Exception
	    {
	      //Enter Bussiness proof              	
	//    	Thread.sleep(5000);	
	    	Waits w= new Waits();
    	 	w.waitForVisibilityOfElement(driver, "xpath", ".//*[@id='address_proof']");
    	 	
		    WebElement element2= driver.findElement(By.xpath(".//*[@id='address_proof']"));
		    element2.click();
	                           //edit this
		    Select sel2= new Select(element2);
		    List<WebElement> l= sel2.getOptions();
	        System.out.println(l.size());
	        
	       Random bp= new Random(); 
          Thread.sleep(3000);
          System.out.println("select pls");
	        sel2.selectByIndex(bp.nextInt(l.size()));
//	        Thread.sleep(5000);
	    }
	   
	    public void accounttype() throws Exception
	    {
	   //Select your account type
	  //  Thread.sleep(5000);   
	    System.out.println("Click on account type");
	    
	    Waits w= new Waits();
	 	w.waitForVisibilityOfElement(driver, "id", "ac_type");
	 	
	    WebElement ele8= driver.findElement(By.id("ac_type"));      
	    ele8.click();  
	    ele8.sendKeys("Saving");
	    }
	    
	    public void accountholder() throws Exception
	    {
	    //Select account holder name
	    Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
	    String str5= Sheet.getRow(1).getCell(12).getStringCellValue();    
	    WebElement el9= driver.findElement(By.id("ac_holder_name" )); 
	    el9.sendKeys(str5);
	    }
	    
	    public void bank() throws Exception
	    {
	    	//Select bank name
	    Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
	    String str6=Sheet.getRow(1).getCell(13).getStringCellValue();      
	    driver.findElement(By.id("bank_name")).sendKeys(str6);
	    }
	    
	    public void Accountno() throws Exception
	    {
	        //Select Account no
	    Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
	    String str7=Sheet.getRow(1).getCell(14).getStringCellValue();    
	    driver.findElement(By.id("account_number")).sendKeys(str7);
	    }
	    
	    public void Ifsccode() throws Exception
	    {
	    //Select IFSC code 
	    Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
	    String str8= Sheet.getRow(1).getCell(15).getStringCellValue();       
	    driver.findElement(By.id("bank_ifsc_code")).sendKeys(str8);
	    Thread.sleep(3000);  
	    }
	    
	    public void category() throws Exception       
	    {	  
	    	//Select primary category
	    WebElement el11= driver.findElement(By.xpath(".//*[@id='primary_category']"));  //Select Bicycle category
        el11.click(); 
   //     Sheet = ReadExcel.DataSheet(FilePath, Sheetname);
	//    String str9 = Sheet.getRow(1).getCell(16).getStringCellValue();
	    Select select1= new Select(el11);
	    select1.selectByIndex(1);
	    
	    }
	    
	    public void accept() throws Exception
	    {	
	    	WebElement el17= driver.findElement(By.xpath(".//*[@id='gst_declartion_signup']"));
	    	el17.click();
	    	Thread.sleep(6000); 
	    }
	    
	    //Select Save and continue
	    public void Saveandcontinue() throws Exception
	    {
	    WebElement el16=  driver.findElement(By.xpath(".//*[@id='infoButton']/input"));
	    el16.click();
	    Thread.sleep(5000); 
	    } 
	
	    
     public void userRegister(WebDriver driver2) throws Exception 
     {
	 Userfirstname();
	 
	 Userlastname();
	 
	 Companyname();
     
	 Email();
     
	 Mobileno();
     
	 Password();
    
     signup();
     
     closepopup();
    
     vehicles();
     
     Thread.sleep(5000);
     
     vehiclecategory();
   
     days();
   
     Thread.sleep(5000);
     scroll1();
     timesavailable();
 
	 timeslot();
		 
	 viewingoptions();
	 
	 shippingoptions();

	 returnpolicy();

	 testdrive();
	 
	 RTO();
	 
	    JavascriptExecutor jse = (JavascriptExecutor) driver;  
	   jse.executeScript("window.scrollBy(0,-250)");
	   
	 Automobile();
	 
	 inspection();
	 
	 Isitservicable();
	 
	 serviceAvailability();
	 
	 timesAvailability();
	 
	 methoddelivery();
	 
	 Itemsdetails();
	 
	 service();
	    
	  JavascriptExecutor jse11 = (JavascriptExecutor) driver;  
	   jse11.executeScript("window.scrollBy(0,-250)");
	    
	   savecontinue();
	 
	   pannumber();   //click it
	      
	   String p=  pincode(); 	     
	   
       gst(p);

       /*  fileupload f1=new fileupload();
	  f1.fileupload1(driver); //upload a file 
      */
  
	    location(p);    
	    address();	    
	   
	 //  Thread.sleep(5000);
	    bussinnessproof();
	  
	    Thread.sleep(5000);
	    
	    fileupload.fileupload2(driver);  //Upload Bussiness document

	    accounttype();
	    
	    accountholder();
	  
	    bank();
	    
	    Accountno();
	  
	    Ifsccode();
   
	    category();
	    
	    JavascriptExecutor jse1 = (JavascriptExecutor) driver;  
	    jse1.executeScript("window.scrollBy(0,-250)", "");
	    
	    Thread.sleep(3000); 
	    Saveandcontinue();
	  
        }

   /*    public void CasualUser()  throws Exception
       {
    		 Userfirstname();
    		 Userlastname();
    		 Handlename();
    		 Email();
    		 Mobileno();
    		 pincode();
    	
    		 location();         //issue here
    		 
    		 Thread.sleep(5000);
    		 scroll1();
    		 
    		 Password();
    		 accept();
    	  		 
    		 signup(); 		  		 
       }
*/
}

