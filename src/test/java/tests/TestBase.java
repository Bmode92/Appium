package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class TestBase extends AbstractTestNGCucumberTests {

    public static AppiumDriver driver;

    //appium -p 10000(replace with port)
    //appium -p 10001(replace with port)
    public static void iOS_setUp(String port, String deviceName, String platformVersion, String udid, String wdaLocalPort) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("app",
                System.getProperty("user.dir") + "/apps/ToDo.apk");
        capabilities.setCapability("wdaLocalPort", wdaLocalPort);
        capabilities.setCapability("udid", udid);
        driver = new IOSDriver(new URL("https://localhost:" + port + "/wd/hub"), capabilities);
    }

    public static void Android_setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "9.0");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("app",
                System.getProperty("user.dir") + "/apps/ToDo.apk");

        driver = new AndroidDriver(new URL("https://localhost:4723/wd/hub"), capabilities);
    }

    public static void tearDown() {
        if(null != driver) {
            driver.quit();
        }
    }
}
