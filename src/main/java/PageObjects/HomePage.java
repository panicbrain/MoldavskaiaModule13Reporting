package PageObjects;

import BusinessObjects.Users;
import Loggers.MyLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends AbstractPage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    private static final By LOGIN_INPUT_LOCATOR = By.name("login");
    private static final By PASSWORD_INPUT_LOCATOR = By.cssSelector(".input[type=password]");
    private static final By SIGN_IN_BUTTON = By.cssSelector(".o-control[type=submit]");

    public HomePage clearLoginInput() {
        driver.findElement(LOGIN_INPUT_LOCATOR).clear();
        MyLogger.info("Login field is cleaned");
        return this;
    }

    public HomePage open() {
        driver.navigate().to("https://mail.ru/");
        MyLogger.info("Mail.ru site is opened");
        return this;
    }

    public IncomingMailsPage logIn(Users user) {
        String login = user.getLogin();
        String password = user.getPassword();
        driver.findElement(LOGIN_INPUT_LOCATOR).sendKeys(login);
        driver.findElement(PASSWORD_INPUT_LOCATOR).sendKeys(password);
        driver.findElement(SIGN_IN_BUTTON).click();
        MyLogger.info("User was logged in with their credentials");
        return new IncomingMailsPage(driver);
    }

}
