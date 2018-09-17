package Utils;

import DriverManager.ChromeWebDriverSingleton;
import Loggers.MyLogger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;

public class ScreenshotListener extends TestListenerAdapter {
    private static final String SCREENSHOTS_NAME_TPL = "screenshots/scr";
    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getName();
        if (!result.isSuccess()) {
            WebDriver driver = ChromeWebDriverSingleton.getWebDriverInstance();
            MyLogger.debug("Test failed");
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                String screenshotName = SCREENSHOTS_NAME_TPL + System.nanoTime();
                File copy = new File(screenshotName + ".png");
                FileUtils.copyFile(screenshot, copy);
                MyLogger.debug("Test failed, saved screenshot: "+ screenshotName);
            } catch (IOException e) {
                MyLogger.error("Failed to make screenshot");
            }
        }
    }
}