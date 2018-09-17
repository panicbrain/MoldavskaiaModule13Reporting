package Utils;

import DriverManager.ChromeWebDriverSingleton;
import Loggers.MyLogger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Screenshoter {
    private static final String SCREENSHOTS_NAME_TPL = "screenshots/scr";

    public static void takeScreenshot() {
        WebDriver driver = ChromeWebDriverSingleton.getWebDriverInstance();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotName = SCREENSHOTS_NAME_TPL + System.nanoTime();
            File copy = new File(screenshotName + ".png");
            FileUtils.copyFile(screenshot, copy);
            System.out.println("Saved screenshot: " + screenshotName);
        } catch (IOException e) {
            MyLogger.error("Failed to make screenshot");
        }
    }
}
