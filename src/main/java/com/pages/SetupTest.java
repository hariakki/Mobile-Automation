package com.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.utils.AppiumServerJava;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by viet.ch on 9/4/2015.
 */

public class SetupTest {

    static String localURL = "http://127.0.0.1:4723/wd/hub";
    File androidApp = new File("D:\\Users\\Alok\\NewWorkspace\\AndroidAutomation\\src\\main\\resources\\apks\\flipkart.apk");
    File iOSApp = new File("src/resources/appname.app");

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


}
