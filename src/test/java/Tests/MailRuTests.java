package Tests;

import BusinessObjects.Users;
import DriverManager.ChromeWebDriverSingleton;
import PageObjects.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import static PageObjects.BaseAreasPage.MAIL_SUBJECT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MailRuTests {

    private WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void beforeClass() {
        driver = ChromeWebDriverSingleton.getWebDriverInstance();
        wait = new WebDriverWait(driver, 15);
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        ChromeWebDriverSingleton.kill();
    }

    @Test(description = "user can log in using their email and password")
    public void loginTest() {
        // Open mail.ru page
        HomePage homepage = new HomePage(driver);
        homepage.open();
        String expectedHomepageTitle = new String("Mail.Ru: почта, поиск в интернете, новости, игры");
        assertEquals(homepage.driver.getTitle(), expectedHomepageTitle);

        // clear the email address field
        homepage.clearLoginInput();

        //login to the mail box
        IncomingMailsPage incomingMailsPage = homepage.logIn(new Users());

        // assert that the login was successful
        String expectedTitle = new String("Входящие - Почта Mail.Ru");
        wait.until(ExpectedConditions.titleContains(expectedTitle));
        assertTrue(incomingMailsPage.driver.getTitle().contains(expectedTitle));
        //incomingMailsPage.logOff();
    }

    @Test(description = "user can save the letter as draft")
    public void saveAsDraft() {
        // Open mail.ru page
        HomePage homepage = new HomePage(driver);
        homepage.open();

        // clear the email address field
        homepage.clearLoginInput();

        //login to the mail box
        IncomingMailsPage incomingMailsPage = homepage.logIn(new Users());

        // assert that the login was successful
        String expectedTitle = new String("Входящие - Почта Mail.Ru");
        wait.until(ExpectedConditions.titleContains(expectedTitle));

        //Create a new mail
        NewLetterPage newLetterPage = incomingMailsPage.createNewLetter();
        newLetterPage.fillAllFieldsOfNewLetter();

        // save the mail as draft
        newLetterPage.saveAsDraft();

        //open drafts folder
        DraftMailsPage draftMailsPage = newLetterPage.openDraftFolder();
        String expectedDraftSavedTitle = new String("Новое письмо - Почта Mail.Ru");
        assertEquals(draftMailsPage.driver.getTitle(), expectedDraftSavedTitle);

        // assert that draft presents in the Draft folder
        assertTrue(draftMailsPage.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The draft of test email is absent in the folder");

        //Open saved draft
        newLetterPage = draftMailsPage.openLastSavedDraft();
        assertEquals(newLetterPage.driver.getTitle(), expectedDraftSavedTitle);

        // assert that all field contain the same information that before saving as draft
        assertEquals(newLetterPage.getMailAddress(), "ekaterinamoldavskaia18@gmail.com");
        assertEquals(newLetterPage.getMailSubject(), MAIL_SUBJECT);
        assertTrue(newLetterPage.getBodyText().contains("Text"), "Mail text is absent");
        //newLetterPage.logOff();
    }

    @Test(description = "user can send the mail from draft folder")
    public void sendEmailTest() throws InterruptedException {
        // Open mail.ru page
        HomePage homepage = new HomePage(driver);
        homepage.open();

        // clear the email address field
        homepage.clearLoginInput();

        //login to the mail box
        IncomingMailsPage incomingMailsPage = homepage.logIn(new Users());

        // assert that the login was successful
        String expectedTitle = new String("Входящие - Почта Mail.Ru");
        wait.until(ExpectedConditions.titleContains(expectedTitle));

        //Create a new mail
        NewLetterPage newLetterPage = incomingMailsPage.createNewLetter();
        newLetterPage.fillAllFieldsOfNewLetter();

        // save the mail as draft
        newLetterPage.saveAsDraft();

        //open drafts folder
        DraftMailsPage draftMailsPage = newLetterPage.openDraftFolder();

        //Open saved draft
        newLetterPage = draftMailsPage.openLastSavedDraft();

        // send email
        newLetterPage.sendMail();

        // assert that the draft disappeared from draft folder
        draftMailsPage = newLetterPage.openDraftFolder();
        assertFalse(draftMailsPage.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The draft stays in the folder");

        //assert the sent mail presents in Sent folder
        SentMailsPage sentMailsPage = draftMailsPage.openSentFolder();
        assertTrue(sentMailsPage.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The sent email is absent in the folder");
        //sentMailsPage.logOff();
    }

    @Test(description = "user can log out")
    public void logOutTest() throws InterruptedException {
        // Open mail.ru page
        HomePage homepage = new HomePage(driver);
        homepage.open();

        // clear the email address field
        homepage.clearLoginInput();

        //login to the mail box
        IncomingMailsPage incomingMailsPage = homepage.logIn(new Users());

        // assert that the login was successful
        String expectedTitle = new String("Входящие - Почта Mail.Ru");
        wait.until(ExpectedConditions.titleContains(expectedTitle));

        //Create a new mail
        NewLetterPage newLetterPage = incomingMailsPage.createNewLetter();
        newLetterPage.fillAllFieldsOfNewLetter();

        // save the mail as draft
        newLetterPage.saveAsDraft();

        //open drafts folder
        DraftMailsPage draftMailsPage = newLetterPage.openDraftFolder();

        //Open saved draft
        newLetterPage = draftMailsPage.openLastSavedDraft();

        // send email
        newLetterPage.sendMail();

        // assert that the draft disappeared from draft folder
        draftMailsPage = newLetterPage.openDraftFolder();
        assertFalse(draftMailsPage.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The draft stays in the folder");

        //assert the sent mail presents in Sent folder
        SentMailsPage sentMailsPage = draftMailsPage.openSentFolder();
        assertTrue(sentMailsPage.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The sent email is absent in the folder");
        //Log off
        homepage = sentMailsPage.logOff();
        String expectedMainPageTitle = new String("Mail.Ru: почта, поиск в интернете, новости, игры");
        assertEquals(homepage.driver.getTitle(), expectedMainPageTitle);
    }
}
