package IOS;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class FirstIOSTest {
    private AppiumDriver driver;

    @BeforeTest
    public void setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("platformVersion", "14.4");
        capabilities.setCapability("deviceName", "IPhone 12");
        capabilities.setCapability("appPackage", "com.android.calculator2");
        capabilities.setCapability("appActivity", ".Calculator");
        driver = new IOSDriver(new URL("https://localhost:4723/wd/hub"), capabilities);
    }

    @Test
    public void click_test() {
        driver.findElement(By.name("button")).click();
    }

    @Test
    public void scroll_test() {
        HashMap<String, Object> scrollObject = new HashMap<>();
        scrollObject.put("direction", "down");
        scrollObject.put("value", "Web View");
        driver.executeScript("mobile:scroll", scrollObject);
        driver.findElementByAccessibilityId("Web View").click();
    }

    @Test
    public void testPickerView() {
        driver.findElementByAccessibilityId("Picker View").click();
        driver.findElementByAccessibilityId("Red color component value").sendKeys("30");
        driver.findElementByAccessibilityId("Green color component value").sendKeys("200");
        IOSElement blue = (IOSElement) driver.findElementByAccessibilityId("Blue color component value");
        blue.sendKeys("100");
        System.out.println(blue.getText());

    }

    @Test
    public void sliderTest() {
        driver.findElementByAccessibilityId("Sliders").click();
        IOSElement slider = (IOSElement) driver.findElement(By.xpath("//XCUIElementTypeSlider"));
        slider.setValue("0");
        slider.setValue("1%"); //100%
        slider.setValue("0.5"); //50%
        Assert.assertEquals(slider.getAttribute("value"), "50%");
    }

    @Test
    public void alertSimpleTest() {
        driver.findElementByAccessibilityId("Alert Views").click();
        driver.findElementByAccessibilityId("Simple").click();
        driver.switchTo().alert().accept();
    }

    @Test
    public void alertOkayCancelTest() {
        driver.findElementByAccessibilityId("Alert Views").click();
        driver.findElementByAccessibilityId("Okay / Cancel").click();
        driver.switchTo().alert().dismiss();
    }

    @Test
    public void alertSendTextTest() {
        driver.findElementByAccessibilityId("Alert Views").click();
        driver.findElementByAccessibilityId("Text Entry").click();
        driver.switchTo().alert().sendKeys("Hello");
        driver.switchTo().alert().accept();
    }

    @AfterTest
    public void tearDown() {
        if (null != driver) {
            driver.quit();
        }
    }

}
