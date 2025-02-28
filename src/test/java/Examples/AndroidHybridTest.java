package Examples;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class AndroidHybridTest {

    private AppiumDriver driver;
    private static By chromeBtn = MobileBy.AccessibilityId("buttonStartWebview");
    private static By goToHomeBtn = By.id("goBack");

    @BeforeTest
    public void setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UIAutomator1");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("app", System.getProperty("user.dir")
                + "/apps/selendroid.apk");

        driver = new AndroidDriver(new URL("https://localhost:4723/wd/hub"), capabilities);
    }

    public void switchToWebView() {
        Set<String> contexts = driver.getContextHandles();
        for (String context : contexts) {
            if(context.contains("WEBVIEW")) {
                driver.context(context);
                break;
            }
        }
    }

    @Test
    public void hybridTest() {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.presenceOfElementLocated(chromeBtn)).click();

        switchToWebView();
        WebElement nameInput = driver.findElement(By.id("name_input"));
        nameInput.clear();
        String name = "Stefan";
        nameInput.sendKeys(name);
        driver.context("WEBVIEW");
        driver.findElement(goToHomeBtn).click();
    }

    @AfterTest
    public void tearDown() {
        if (null != driver) {
            driver.quit();
        }
    }
}
