import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class FirstAndroidTest {

    AppiumDriver driver;

    @BeforeTest
    public void setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UIAutomator2");
        capabilities.setCapability("platformVersion", "9.0");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("app", System.getProperty("user.dir") + "/apps/ApiDemo.apk");

        driver = new AndroidDriver(new URL("https://localhost:4723/wd/hub"), capabilities);
    }

    @Test
    public void click_app_button() {
        driver.findElement(By.id("button")).click();
    }

    @AfterTest
    public void tearDown() {
        if (null != driver) {
            driver.quit();
        }
    }
}
