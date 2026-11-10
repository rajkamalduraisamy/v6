package stepdefs;

import base.BaseTest;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;
import pages.ReportPage;
import utils.ConfigReader;

public class ReportSteps {

    private final LoginPage loginPage   = new LoginPage(BaseTest.getDriver());
    private final ReportPage reportPage = new ReportPage(BaseTest.getDriver());

    private static final String DOWNLOAD_DIR = System.getProperty("user.home") + "/Downloads";

    @Given("the user is logged in using config credentials")
    public void loginWithConfigCredentials() {
        loginPage.navigateTo(ConfigReader.getAppUrl());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        Assert.assertTrue(loginPage.isDashboardDisplayed(), "Login failed in background step");
    }

    @Given("the user is logged in as {string} with password {string}")
    public void loginAsUser(String username, String password) {
        loginPage.navigateTo(ConfigReader.getAppUrl());
        loginPage.login(username, password);
        Assert.assertTrue(loginPage.isDashboardDisplayed(), "Login failed in background step");
    }

    @When("the user navigates to the Reports section")
    public void navigateToReports() {
        reportPage.navigateToReports();
    }

    @When("selects report type {string}")
    public void selectReportType(String type) {
        reportPage.selectReportType(type);
    }

    @When("clicks the Download button")
    public void clickDownload() {
        reportPage.clickDownload();
    }

    @Then("a download success message should be displayed")
    public void verifySuccessToast() {
        Assert.assertTrue(reportPage.isDownloadSuccessDisplayed(), "Download success toast not shown");
    }

    @Then("the report file should exist in the downloads folder")
    public void verifyFileDownloaded() {
        Assert.assertTrue(reportPage.isFileDownloaded(DOWNLOAD_DIR, "report"), "Report file not found in downloads");
    }
}
