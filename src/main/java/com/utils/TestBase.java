package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class TestBase {

	public static String log4jPropertyFilePath = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\log4j.properties";
	public static String g_strDownloadPath = System.getProperty("user.dir") + "\\Resources\\Downloads";
	public final static Logger logger = Logger.getLogger(TestBase.class.getName());
	public static ExtentReports extentReport;
	public static ExtentTest eTest;
	public static Properties prop;
	public FileInputStream fis = null;
	static String chromeDriverPath = System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";
	static String gerkoDriverPath = System.getProperty("user.dir") + "\\drivers\\geckodriver.exe";
	static String extentReportPath = System.getProperty("user.dir") + "\\extentReports";
	
	public static WebDriver driver = null;
	// static String extentConfigFilePath = System.getProperty("user.dir") +
	// "\\src\\main\\resources\\extent-config.xml";

    static String localURL = "http://127.0.0.1:4723/wd/hub";
    File androidApp = new File("D:\\Users\\Alok\\NewWorkspace\\AndroidAutomation\\src\\main\\resources\\apks\\flipkart.apk");
    File iOSApp = new File("src/resources/appname.app");
	static Log log;



	static {

		extentReport = new ExtentReports(System.getProperty("user.dir") + "\\extentReports\\"
				+ new SimpleDateFormat("dd_mm_yyyy_hh_mm_ss").format(Calendar.getInstance().getTime()) + ".html",
				false);
		// extentReport.loadConfig(new File(extentConfigFilePath));
		extentReport.addSystemInfo(" App env", "Dev2");
		extentReport.addSystemInfo("Tester", "Alok");

	}

	public TestBase() {

		prop = new Properties();
		try {
			fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties");
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@BeforeSuite

	public AndroidDriver getAndroidDriver() throws MalformedURLException {

    	AppiumServerJava.startServer();
        AndroidDriver driver = null;

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName","Mi");
        caps.setCapability("platformName","ANDROID");
        caps.setCapability("platformVersion", "4.4.4");
        caps.setCapability("app", androidApp);

        driver = new AndroidDriver(new URL(localURL),caps);

        return driver;
    }

    public AndroidDriver getAndroidDriver(String URL) throws MalformedURLException {

        AndroidDriver driver = null;

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName","Android Emulator");
        caps.setCapability("platformName","ANDROID");
        caps.setCapability("app", androidApp);

        driver = new AndroidDriver(new URL(URL),caps);

        return driver;
    }

    public IOSDriver getIOSDriver() throws MalformedURLException {

        IOSDriver driver = null;

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName","iPhone 5");
        caps.setCapability("platformName","IOS");
        caps.setCapability("platformVersion", "8.0");
        caps.setCapability("app", iOSApp);

        driver = new IOSDriver(new URL(localURL),caps);

        return driver;
    }

    public IOSDriver getIOSDriver(String URL) throws MalformedURLException {

        IOSDriver driver = null;

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName","iPhone 5");
        caps.setCapability("platformName","IOS");
        caps.setCapability("platformVersion", "8.0");
        caps.setCapability("app", iOSApp);

        driver = new IOSDriver(new URL(URL),caps);

        return driver;
    }



	@BeforeMethod()
	public void driverAndExtentReportSetup(Method method) throws Exception {

		eTest = extentReport.startTest(method.getName());
		logger.info(method.getName() + " test started");

		Utils.customAlertByJS(method.getName() + " test started");
	}

	@AfterMethod()
	public void afterMethod(ITestResult result) throws InterruptedException {
		getResult(result);

		if (result.getStatus() == ITestResult.SUCCESS) {

			String screenshot = Utils.takeScreenShot(result.getName());

			String image = eTest.addScreenCapture(screenshot);
			eTest.log(LogStatus.PASS, "title verification", image);

		}

		else if (result.getStatus() == ITestResult.FAILURE) {

			Thread.sleep(2000);
			String screenshot = Utils.takeScreenShot(result.getName());

			String image = eTest.addScreenCapture(screenshot);
			eTest.log(LogStatus.FAIL, image);
		}
		// log.info("Quitting driver and closing browser");
		// driver.quit();
	}

	@BeforeClass

	public void thisClassTestStarted() {
		logger.info("***** Test case execution of Class " + getClass().getName() + "started******");
	}

	@AfterClass(alwaysRun = true)
	public void endTest() {
		logger.info("***************All test cases of Class " + getClass().getName() + " executed******");

	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		// to delete directory
		FileUtils.deleteDirectory(extentReportPath);
		FileUtils.mkdir(extentReportPath);
		// extent report related code below
		extentReport.endTest(eTest);
		extentReport.flush();
		Thread.sleep(1000);

		// to send extent report in email.
		/*
		 * try {
		 * SendMailSSLWithAttachment.sendReportByEmail(prop.getProperty("fromEmail"),
		 * prop.getProperty("fromEmailPassword"), prop.getProperty("toEmail")); }
		 * 
		 * catch(Exception e) {
		 * 
		 * log.error(" Emailing of report failed :  "+e); }
		 */

		logger.info("*********** All test classes run. Extent report generated and put in : " + extentReportPath
				+ " .Quitting browser**********");

	}

	// extent report related
	public void getResult(ITestResult result) {

		if (result.getStatus() == ITestResult.SUCCESS) {

			// eTest.log(LogStatus.PASS, result.getName() + " test is pass");

		} else if (result.getStatus() == ITestResult.SKIP) {

			eTest.log(LogStatus.SKIP, result.getName() + " Test is skipped and reason is : " + result.getThrowable());

		} else if (result.getStatus() == ITestResult.FAILURE) {

			// eTest.log(LogStatus.FAIL, result.getName() + " Test is failed and reason is :
			// " + result.getThrowable());
		}

		else if (result.getStatus() == ITestResult.STARTED) {

			eTest.log(LogStatus.INFO, result.getName() + " - Test is started");
		}

	}
	
	
}