package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.io.File;
import java.time.Duration;

public class ReportPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By reportsMenu        = By.id("reportsMenu");
    private final By downloadButton     = By.id("downloadReportBtn");
    private final By reportTypeDropdown = By.id("reportType");
    private final By successToast       = By.id("downloadSuccess");

    public ReportPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    public void navigateToReports() {
        wait.until(ExpectedConditions.elementToBeClickable(reportsMenu)).click();
    }

    public void selectReportType(String type) {
        wait.until(ExpectedConditions.elementToBeClickable(reportTypeDropdown)).click();
        driver.findElement(By.xpath("//option[text()='" + type + "']")).click();
    }

    public void clickDownload() {
        wait.until(ExpectedConditions.elementToBeClickable(downloadButton)).click();
    }

    public boolean isDownloadSuccessDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successToast)).isDisplayed();
    }

    public boolean isFileDownloaded(String downloadDir, String fileNameContains) {
        File dir = new File(downloadDir);
        File[] files = dir.listFiles((d, name) -> name.contains(fileNameContains));
        return files != null && files.length > 0;
    }
}
