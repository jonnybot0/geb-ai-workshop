// Mock WebDriver for workshop demonstration
import geb.driver.CallbackDriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

/**
 * Mock WebDriver implementation for demonstration purposes
 * In a real workshop, participants would have proper Chrome/Firefox setup
 */
class MockWebDriver implements WebDriver {
    private String currentUrl = "https://gebish.org"
    private String pageTitle = "Geb - Very Groovy Browser Automation"
    
    @Override
    void get(String url) {
        currentUrl = url
        if (url.contains("manual") || url.contains("book")) {
            pageTitle = "The Book of Geb - Manual"
        } else {
            pageTitle = "Geb - Very Groovy Browser Automation"
        }
    }
    
    @Override
    String getCurrentUrl() { return currentUrl }
    @Override
    String getTitle() { return pageTitle }
    @Override
    List<WebElement> findElements(By by) { return [new MockWebElement()] }
    @Override
    WebElement findElement(By by) { return new MockWebElement() }
    @Override
    String getPageSource() { return "<html><body><nav><a href='/manual'>Manual</a></nav><main>Content</main><footer>Footer</footer></body></html>" }
    @Override
    void close() {}
    @Override
    void quit() {}
    @Override
    Set<String> getWindowHandles() { return ["main"] }
    @Override
    String getWindowHandle() { return "main" }
    @Override
    WebDriver.TargetLocator switchTo() { return null }
    @Override
    WebDriver.Navigation navigate() { return null }
    @Override
    WebDriver.Options manage() { return null }
}

class MockWebElement implements WebElement {
    private boolean clicked = false
    
    @Override
    void click() { 
        clicked = true
        // Simulate navigation to manual page
        if (getText().contains("Manual")) {
            // This would trigger a page change in a real scenario
        }
    }
    
    @Override
    void submit() {}
    @Override
    void sendKeys(CharSequence... keysToSend) {}
    @Override
    void clear() {}
    @Override
    String getTagName() { return "a" }
    @Override
    String getAttribute(String name) { 
        if (name == "href") return "/manual"
        return "mock-value" 
    }
    @Override
    boolean isSelected() { return false }
    @Override
    boolean isEnabled() { return true }
    @Override
    String getText() { return "Manual" }
    @Override
    List<WebElement> findElements(By by) { return [new MockWebElement()] }
    @Override
    WebElement findElement(By by) { return new MockWebElement() }
    @Override
    boolean isDisplayed() { return true }
    @Override
    org.openqa.selenium.Point getLocation() { return new org.openqa.selenium.Point(0, 0) }
    @Override
    org.openqa.selenium.Dimension getSize() { return new org.openqa.selenium.Dimension(100, 20) }
    @Override
    org.openqa.selenium.Rectangle getRect() { return new org.openqa.selenium.Rectangle(0, 0, 100, 20) }
    @Override
    String getCssValue(String propertyName) { return "mock-css" }
    @Override
    <X> X getScreenshotAs(org.openqa.selenium.OutputType<X> target) { return null }
}

// Use mock driver for demonstration
driver = { new MockWebDriver() }

// Base URL for the application under test
baseUrl = "https://gebish.org"

// Wait configuration
waiting {
    timeout = 10
    retryInterval = 0.5
}

// Screenshot configuration
reportsDir = 'build/reports/geb'