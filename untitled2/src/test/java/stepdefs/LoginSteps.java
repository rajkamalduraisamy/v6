import base.BaseTest;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginSteps {

    private final LoginPage loginPage = new LoginPage(BaseTest.getDriver());

    @Given("the user is on the login page")
    public void navigateToLoginPage() {
        loginPage.navigateTo(ConfigReader.getAppUrl());
    }

    @When("the user enters username from config")
    public void enterCredentialsFromConfig() {
        loginPage.enterUsername(ConfigReader.getUsername());
        loginPage.enterPassword(ConfigReader.getPassword());
    }

    @When("the user enters username {string} and password {string}")
    public void enterCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("the user clicks the login button")
    public void clickLogin() {
        loginPage.clickLogin();
    }

    @Then("the dashboard should be displayed")
    public void verifyDashboard() {
        Assert.assertTrue(loginPage.isDashboardDisplayed(), "Dashboard not visible after login");
    }

    @Then("an error message {string} should be displayed")
    public void verifyErrorMessage(String expectedMsg) {
        Assert.assertEquals(loginPage.getErrorMessage(), expectedMsg);
    }
}
