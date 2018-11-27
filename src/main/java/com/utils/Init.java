package com.utils;
/*package com.cctv.utils;



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.http.ExceptionLogger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.*;



public class Init {
	
	
	public  static WebDriver driver;
	public static Result result = Result.getInstance();
	public static FluentWait<WebDriver> fluentWait;
	
	static 
	{
		
		
	
	}
	
	public static void cleanup()
	{
		try{
			driver.close();
			driver.quit();	
			try {Thread.sleep(3000);} catch (InterruptedException e) {}	
		}
		catch(Exception e)
		{
		//	result.print(e.getLocalizedMessage(),"Fail");
		}
	}
	
	public static boolean OpenUrl(String Url)
	{
		
		try
		{		
		String Btype=input.ReadGlobal("BrowserType"); //MobileEmulation
		System.out.println("Browser selected is ::"+Btype);
		String Dtype=input.ReadGlobal("DeviceName"); //MobileEmulation
		System.out.println("DeviceName selected is ::"+Dtype);
		if(Dtype.equalsIgnoreCase("Google Nexus 5") || Dtype.equalsIgnoreCase("Apple iPhone 6") || Dtype.equalsIgnoreCase("Apple iPhone 5"))
		{
			MobileEmulation(Dtype);
		}
		
		else
		{
			if(Btype.equalsIgnoreCase("Chrome") || Btype.equalsIgnoreCase("Google chrome"))
			{
				
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver_win32\\chromedriver.exe");
				File file = new File("D:\\Automation_IMS\\IMS\\chromedriver_win32\\chromedriver.exe");
				System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
				ChromeOptions options= new ChromeOptions();
				options.addArguments("--disable-extensions");
				driver = new ChromeDriver(options);
				
				
				fluentWait = new FluentWait<WebDriver>(driver)
			            .withTimeout(10, TimeUnit.SECONDS)
			            .pollingEvery(500, TimeUnit.MILLISECONDS)
			            .ignoring(NoSuchElementException.class);
				
			}
			else if(Btype.equalsIgnoreCase("IE") || Btype.equalsIgnoreCase("Internet Explorer"))
			{
				//System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\IE_Driver\\IEDriverServer.exe");
				
				System.setProperty("webdriver.ie.driver",System.getProperty("user.dir") + "\\IE_Driver\\IEDriverServer.exe" );
				DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
		        cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		        cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS,false);
		        //cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		        //cap.setJavascriptEnabled(true);
		       
		        driver = new InternetExplorerDriver(cap);
				// WebDriver driver = new InternetExplorerDriver();
				//driver.manage().window().maximize();
				//driver.get(URL);
			}
			else
			{
				result.print("Couldnt Open browser", "Fail");
			}
		}
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//String url = input.ReadGlobal("URL");
			//driver.manage().window().maximize();
			
			driver.navigate().to(Url);
			//commented
			try
			{
				String url_new=driver.getCurrentUrl();
				int statusCode=ApplicationLibrary.getHTTPResponseStatusCode(url_new);
				Thread.sleep(3000);
				
				if(statusCode == 200)
				{
				
					result.print("URL is opened and have a http STATUS ::"+statusCode, "Pass");
				}
				else
				{
				
					result.print("URL is opened and have a http Error ::"+statusCode, "Fail");
					driver.close();
				}
			}
			catch(Exception e)
			{
				  result.print("URL is not opened and have a http Error:", "Fail");
				  driver.close();
			}
			try
			{
			   driver.navigate().to(Url);
			   Thread.sleep(2000);
			}
			catch(Exception e)
			{
				
			}
			
			try
			{
			  driver.findElement(By.id("HoneywellEUCookie-CookieDismiss")).click();
			}
			catch(Exception e)
			{				
			 result.print("Couldnt find any Cookie button for Do NOT show pop up");
			}
			
		}	
		catch(Exception e)
		{
			result.print("Couldnt traverse to the specified URL due to driver Exception", "Fail");
			driver.close();
		}
		
		return true;
		
	}
	
	
	    public static void MobileEmulation( String deviceName)
	    {
	    	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver_win32\\chromedriver.exe");
			ChromeOptions options= new ChromeOptions();
			options.addArguments("--disable-extensions");
			driver = new ChromeDriver(options);
			
			
			fluentWait = new FluentWait<WebDriver>(driver)
		            .withTimeout(10, TimeUnit.SECONDS)
		            .pollingEvery(500, TimeUnit.MILLISECONDS)
		            .ignoring(NoSuchElementException.class);
	    	   Map<String, Object> deviceMetrics = new HashMap<String, Object>();
	    	   deviceMetrics.put("width", 360);
	    	   deviceMetrics.put("height", 640);
	    	   deviceMetrics.put("pixelRatio", 3.0);
	    	   
	    	  HashMap<String, String> deviceEmulator =new HashMap<>();
	    	  deviceEmulator.put("deviceName",deviceName);
	    	  //deviceEmulator.put("userAgent", "Safari/535.19");
	    	  Map<String, Object> chromeOptions = new HashMap<String, Object>();
	    	  chromeOptions.put("mobileEmulation", deviceEmulator);
	    	 //chromeOptions.put("deviceMetrics", deviceMetrics);
	    	  
	    	  DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	    	  capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
	    	  //capabilities.setCapability(MobileCapabilityType.PLATFORM, );
	    	  driver = new ChromeDriver(capabilities);
	    	  fluentWait = new FluentWait<WebDriver>(driver)
			            .withTimeout(10, TimeUnit.SECONDS)
			            .pollingEvery(500, TimeUnit.MILLISECONDS)
			            .ignoring(NoSuchElementException.class);
	    		    	
	    }
	public static boolean Login(String user, String password)
	{
		//Setting Chrome Webdriver executable path
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver_win32\\chromedriver.exe");
		
		//Create a driver
		driver = new ChromeDriver();
		
		fluentWait = new FluentWait<WebDriver>(driver)
	            .withTimeout(10, TimeUnit.SECONDS)
	            .pollingEvery(500, TimeUnit.MILLISECONDS)
	            .ignoring(NoSuchElementException.class);
		
		//Puts an Implicit wait, Will wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Launch website 
		String uri = input.ReadGlobal("URL");
		driver.navigate().to(uri);
		
//		// Store the current window handle
//		String winHandleBefore = driver.getWindowHandle();
//
//		// Perform the click operation that opens new window
//
//		// Switch to new window opened
//		for(String winHandle : driver.getWindowHandles()){
//			
//			if(winHandle.compareToIgnoreCase(winHandleBefore)!=0)
//			{
//				driver.switchTo().window(winHandle);
//				break;
//			}
//			
//		}
//
//		driver.switchTo().frame("fmsearchSE");
//		
//		WebElement oWE = driver.findElement(By.xpath(".//textarea[contains(@id,'txt_mn_symptom_text')]"));
//		oWE.sendKeys("asfsdfasdfasdf");
//		
//		JavascriptExecutor jse = (JavascriptExecutor)driver;
//		jse.executeScript("argument[0].setAttribute('value', 'text')", oWE);
////		
////		
////		selenium.type("//input[contains(@id,'startDateTimeInputDate')]", "2011-05-22 05:33:00"); 
//		
//		oWE.sendKeys("asfsdfasdfasdf");
//		// Switch back to original browser (first window)
//		driver.switchTo().window(winHandleBefore);
		
		
		//login
		Process p = null;
		try {
			String[] cmd = { System.getProperty("user.dir") + "\\TravelToolExActions\\TravelToolExActions.exe","AuthenticationDialog",user, password};
		       
			//String[] cmd = { System.getProperty("user.dir") + "\\TravelToolExActions\\TravelToolExActions.exe","AuthenticationDialog",user, password};
	        p = Runtime.getRuntime().exec(cmd);
	        try {
				p.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(p.exitValue()==0) 
	        	return false;
	       
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
		}
		
		
		//------------------------------------------------------------
		
		
		
	    //Maximize the browser
	    driver.manage().window().maximize();
	
		return true;
	}
	
	
	public static void TestCaseFunction(String s)
	{
		String[] args = {};
		
		Class<?> clazz;
		try {
			
			clazz = Class.forName("ims.IMSTestCases");
			Method[] methods = clazz.getMethods();
			for (Method m : methods) {
			    if (s.equals(m.getName())) {
			        // for static methods we can use null as instance of class
			    	m.invoke(null, new Object[] {});
			    	//.invoke(null, null);
					break;
			    }
			}
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		switch(s)
//		{
//		case "TC_HTSHUB_International_Travel_1": 
//			TestCases.TC_HTSHUB_International_Travel_1();
//			break;
//		
//		}
	}
	
	
	
	
	
}
*/