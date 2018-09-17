import DriverManager.ChromeWebDriverSingleton;
import org.testng.annotations.BeforeClass;


public class BasePage {

    @BeforeClass(description = "init browser")
    public void init() {
        ChromeWebDriverSingleton.getWebDriverInstance();
    }
}


