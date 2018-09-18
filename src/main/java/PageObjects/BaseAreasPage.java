package PageObjects;

import Loggers.MyLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static Utils.Screenshoter.takeScreenshot;

public abstract class BaseAreasPage extends AbstractPage {
    public BaseAreasPage(WebDriver driver) {
        super(driver);
    }

    private final static By NEW_LETTER_BUTTON = By.cssSelector(".b-toolbar__btn_with-foldings");
    private final static By DRAFT_LETTERS_FOLDER_LOCATOR = By.cssSelector("[data-mnemo=\"drafts\"]");
    private final static By SENT_LETTERS_FOLDER_LOCATOR = By.cssSelector("[href='/messages/sent/']");
    private final static By LOG_OFF_BUTTON_LOCATOR = By.cssSelector("#PH_logoutLink");
    private final static By MAIL_SUBJECTS_LOCATOR = By.cssSelector(".b-datalist__item__subj");
    private final static By SUBJECTS_WITH_BODY = By.cssSelector(".b-datalist__item__subj__snippet");

    static final UUID SUBJECT_TO_MAIL = UUID.randomUUID();
    public static final String MAIL_SUBJECT = SUBJECT_TO_MAIL.toString();

    public NewLetterPage createNewLetter() {
        waitForElementEnabled(NEW_LETTER_BUTTON);
        highlightElement(NEW_LETTER_BUTTON);
        takeScreenshot();
        unHighlightElement(NEW_LETTER_BUTTON);
        driver.findElement(NEW_LETTER_BUTTON).click();
        MyLogger.info("New letter page is opened");
        return new NewLetterPage(driver);
    }

    public DraftMailsPage openDraftFolder() {
        waitForElementEnabled(DRAFT_LETTERS_FOLDER_LOCATOR);
        highlightElement(DRAFT_LETTERS_FOLDER_LOCATOR);
        takeScreenshot();
        unHighlightElement(DRAFT_LETTERS_FOLDER_LOCATOR);
        driver.findElement(DRAFT_LETTERS_FOLDER_LOCATOR).click();
        waitForLoad(driver);
        MyLogger.info("Draft folder is opened");
        return new DraftMailsPage(driver);
    }

    public SentMailsPage openSentFolder() {
        waitForElementEnabled(SENT_LETTERS_FOLDER_LOCATOR);
        highlightElement(SENT_LETTERS_FOLDER_LOCATOR);
        takeScreenshot();
        unHighlightElement(SENT_LETTERS_FOLDER_LOCATOR);
        driver.findElement(SENT_LETTERS_FOLDER_LOCATOR).click();
        MyLogger.info("Sent folder is opened");
        return new SentMailsPage(driver);
    }

    public HomePage logOff() {
        waitForElementEnabled(LOG_OFF_BUTTON_LOCATOR);
        highlightElement(LOG_OFF_BUTTON_LOCATOR);
        takeScreenshot();
        unHighlightElement(LOG_OFF_BUTTON_LOCATOR);
        driver.findElement(LOG_OFF_BUTTON_LOCATOR).click();
        MyLogger.info("Log off button was clicked");
        return new HomePage(driver);
    }

    public List<String> getSubjectTextsOfMails() {
        driver.navigate().refresh();
        waitForElementPresent(MAIL_SUBJECTS_LOCATOR);
        List<WebElement> subjectsOfMailForTest = driver.findElements(MAIL_SUBJECTS_LOCATOR);
        List<String> textOfSubjects = new ArrayList<String>();
        for (WebElement subject : subjectsOfMailForTest) {
            String bodyOfMail = subject.findElement(SUBJECTS_WITH_BODY).getText();
            textOfSubjects.add(subject.getText().replace(bodyOfMail, "").trim());
        }
        return textOfSubjects;
    }
}
