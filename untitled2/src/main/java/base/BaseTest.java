package base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

public class BaseTest {

    // ── Driver access ─────────────────────────────────────────────────────────

    public static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    // ── Element retrieval ─────────────────────────────────────────────────────

    protected WebElement find(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> findAll(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // ── Click actions ─────────────────────────────────────────────────────────

    protected void click(By locator) {
        getWait().until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void jsClick(By locator) {
        WebElement element = find(locator);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    protected void doubleClick(By locator) {
        new Actions(getDriver()).doubleClick(find(locator)).perform();
    }

    protected void rightClick(By locator) {
        new Actions(getDriver()).contextClick(find(locator)).perform();
    }

    // ── Input actions ─────────────────────────────────────────────────────────

    protected void type(By locator, String text) {
        WebElement el = find(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected void typeAndEnter(By locator, String text) {
        WebElement el = find(locator);
        el.clear();
        el.sendKeys(text, Keys.ENTER);
    }

    protected void clearField(By locator) {
        find(locator).clear();
    }

    protected String getValue(By locator) {
        return find(locator).getAttribute("value");
    }

    protected String getText(By locator) {
        return find(locator).getText();
    }

    // ── Dropdown ──────────────────────────────────────────────────────────────

    protected void selectByVisibleText(By locator, String text) {
        new Select(find(locator)).selectByVisibleText(text);
    }

    protected void selectByValue(By locator, String value) {
        new Select(find(locator)).selectByValue(value);
    }

    protected void selectByIndex(By locator, int index) {
        new Select(find(locator)).selectByIndex(index);
    }

    protected String getSelectedOption(By locator) {
        return new Select(find(locator)).getFirstSelectedOption().getText();
    }

    // ── Checkbox & Radio ──────────────────────────────────────────────────────

    protected void check(By locator) {
        WebElement el = find(locator);
        if (!el.isSelected()) el.click();
    }

    protected void uncheck(By locator) {
        WebElement el = find(locator);
        if (el.isSelected()) el.click();
    }

    protected boolean isChecked(By locator) {
        return find(locator).isSelected();
    }

    // ── Visibility & State checks ─────────────────────────────────────────────

    protected boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isEnabled(By locator) {
        return find(locator).isEnabled();
    }

    protected void waitForVisible(By locator) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForInvisible(By locator) {
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitForClickable(By locator) {
        getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForText(By locator, String text) {
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // ── Navigation ────────────────────────────────────────────────────────────

    protected void navigateTo(String url) {
        getDriver().get(url);
    }

    protected void goBack() {
        getDriver().navigate().back();
    }

    protected void refresh() {
        getDriver().navigate().refresh();
    }

    protected String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    protected String getTitle() {
        return getDriver().getTitle();
    }

    // ── Scroll ────────────────────────────────────────────────────────────────

    protected void scrollToElement(By locator) {
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", find(locator));
    }

    protected void scrollToTop() {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, 0);");
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // ── Hover ─────────────────────────────────────────────────────────────────

    protected void hoverOver(By locator) {
        new Actions(getDriver()).moveToElement(find(locator)).perform();
    }

    // ── Alerts ────────────────────────────────────────────────────────────────

    protected void acceptAlert() {
        getWait().until(ExpectedConditions.alertIsPresent()).accept();
    }

    protected void dismissAlert() {
        getWait().until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    protected String getAlertText() {
        return getWait().until(ExpectedConditions.alertIsPresent()).getText();
    }

    // ── Frames ────────────────────────────────────────────────────────────────

    protected void switchToFrame(By locator) {
        getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    protected void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }

    // ── Windows / Tabs ────────────────────────────────────────────────────────

    protected void switchToNewTab() {
        String current = getDriver().getWindowHandle();
        for (String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(current)) {
                getDriver().switchTo().window(handle);
                break;
            }
        }
    }

    protected void closeCurrentTab() {
        getDriver().close();
    }

    // ── Table helpers ─────────────────────────────────────────────────────────

    protected int getRowCount(By tableLocator) {
        return getDriver().findElement(tableLocator).findElements(By.tagName("tr")).size();
    }

    protected String getCellText(By tableLocator, int row, int col) {
        List<WebElement> rows = getDriver().findElement(tableLocator).findElements(By.tagName("tr"));
        List<WebElement> cells = rows.get(row).findElements(By.tagName("td"));
        return cells.get(col).getText();
    }

    // ── Attribute & CSS ───────────────────────────────────────────────────────

    protected String getAttribute(By locator, String attribute) {
        return find(locator).getAttribute(attribute);
    }

    protected String getCssValue(By locator, String property) {
        return find(locator).getCssValue(property);
    }
}
