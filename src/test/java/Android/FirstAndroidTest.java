package Android;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class FirstAndroidTest {

    private AppiumDriver driver;
    private AndroidTouchAction actions;

    private static final By backupBtn = By.id("auto_backup_switch");
    private static final By touchOutsideBtn = By.id("touch_outside");
    private static final By keepOffBtn = By.id("//*[@text='KEEP OFF']");
    private static final By photo = By.xpath("//android.viewGroup[@content-desc='Photo']");

    File classpath, imgDir, img;


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

    private void scrollDown() {
        Dimension dimension = driver.manage().window().getSize();
        int scrollStart = (int) (dimension.getHeight() * 0.8);
        int scrollEnd = (int) (dimension.getHeight() * 0.1);

        actions = new AndroidTouchAction(driver)
                .press(PointOption.point(0, scrollStart))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                .moveTo(PointOption.point(0, scrollEnd))
                .release().perform();
    }

    @Test
    public void scroll_test() {
        AndroidElement viewsElement =
                (AndroidElement) driver.findElementByAccessibilityId("Views");

        //Tap
        actions = new AndroidTouchAction(driver);
        actions.tap(ElementOption.element(viewsElement)).perform();

        //Scroll down
        scrollDown();
        AndroidElement lists = (AndroidElement) driver.findElementByAccessibilityId("Lists");
        actions.tap(ElementOption.element(lists)).perform();

    }

    @Test
    public void drag_drop() {
        AndroidElement viewsElement =
                (AndroidElement) driver.findElementByAccessibilityId("Views");

        actions = new AndroidTouchAction(driver);
        actions.tap(ElementOption.element(viewsElement)).perform();

        AndroidElement dragDropElement =
                (AndroidElement) driver.findElementByAccessibilityId("Drag and Drop");
        actions.tap(ElementOption.element(dragDropElement)).perform();

        AndroidElement drag = (AndroidElement) driver.findElement(By.id("drag_dot_1"));
        AndroidElement drop = (AndroidElement) driver.findElement(By.id("drag_dot_2"));

        actions.longPress(ElementOption.element(drag))
                .waitAction().moveTo(ElementOption.element(drop))
                .release()
                .perform();
    }

    @Test
    public void swipe() {
        AndroidElement viewsElement =
                (AndroidElement) driver.findElementByAccessibilityId("Views");

        actions = new AndroidTouchAction(driver);
        actions.tap(ElementOption.element(viewsElement)).perform();

        AndroidElement gallery =
                (AndroidElement) driver.findElementByAccessibilityId("Gallery");
        actions.tap(ElementOption.element(gallery)).perform();

        AndroidElement photo =
                (AndroidElement) driver.findElementByAccessibilityId("1. Photos");
        actions.tap(ElementOption.element(photo)).perform();

        AndroidElement pic1 =
                (AndroidElement) driver.findElements(By.className("androidImageView")).get(0);
        actions.press(ElementOption.element(pic1))
                .waitAction()
                .moveTo(PointOption.point(-20, 210))
                .release()
                .perform();
    }

    @Test
    public void send_photo() throws IOException {
        classpath = new File(System.getProperty("user.dir"));
        imgDir = new File(classpath, "/resources/images");
        img = new File(imgDir.getCanonicalFile(), "Logo.img");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(backupBtn)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(touchOutsideBtn)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(keepOffBtn)).click();

        String Android_Photo_Path = "mnt/sdcard/Pictures";
        driver.pullFile(Android_Photo_Path + "/" + img.getName());

        ExpectedCondition<List<WebElement>> condition = ExpectedConditions.numberOfElementsToBe(photo, 1);
        wait.until(condition);

    }

    @Test
    public void clipboard() {
        String text = "Hello World";
//        driver.setClipboardText(text);
        MobileElement nameTxt = (MobileElement) driver.findElementsByAccessibilityId("my_text_fieldCD");
        nameTxt.clear();
//        nameTxt.sendKeys(driver.getClipboardText());
        Assert.assertEquals(text, nameTxt.getText());

    }


    @AfterTest
    public void tearDown() {
        if (null != driver) {
            driver.quit();
        }
    }
}
