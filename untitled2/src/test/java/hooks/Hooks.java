package hooks;

import base.DriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.ScreenshotUtil;

public class Hooks {

    private static ExtentReports extent;
    private ExtentTest scenarioTest;

    // ── Runs ONCE before all scenarios ───────────────────────────────────────
    @BeforeAll
    public static void initReport() {
        extent = ExtentReportManager.getInstance();
    }

    // ── Runs before EACH scenario → launches browser ─────────────────────────
    @Before
    public void launchBrowser(Scenario scenario) {
        DriverManager.initDriver();
        scenarioTest = extent.createTest(scenario.getName());
        scenarioTest.log(Status.INFO, "Browser launched: " + ConfigReader.getBrowser());
        scenarioTest.log(Status.INFO, "Scenario started: " + scenario.getName());
    }

    // ── Runs after EACH scenario → screenshot on fail + quit browser ──────────
    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Attach screenshot to Cucumber HTML report
            byte[] screenshot = ScreenshotUtil.capture(DriverManager.getDriver());
            scenario.attach(screenshot, "image/png", "Failure Screenshot");

            // Embed screenshot in Extent report
            String base64 = ScreenshotUtil.captureBase64(DriverManager.getDriver());
            scenarioTest.fail("Scenario FAILED: " + scenario.getName())
                        .addScreenCaptureFromBase64String(base64, "Failure Screenshot");
        } else {
            scenarioTest.pass("Scenario PASSED: " + scenario.getName());
        }

        DriverManager.quitDriver();   // quit browser after every scenario
    }

    // ── Runs ONCE after all scenarios → flush Extent report ──────────────────
    @AfterAll
    public static void flushReport() {
        if (extent != null) extent.flush();
    }
}
