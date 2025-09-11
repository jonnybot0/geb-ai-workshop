import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver

// Environment configuration for workshop participants
environments {

    // Development environment - use local Chrome (default)
    chrome {
        def options = new ChromeOptions()
        // Enable headless mode for CI/sandbox environments
        if (System.getProperty('geb.headless') == 'true') {
            options.addArguments('--headless')
        }
        // Standard options for workshop environment
        options.addArguments('--no-sandbox', '--disable-dev-shm-usage', '--disable-gpu')
        driver = { new ChromeDriver(options) }
    }

    // Firefox option for workshop participants
    firefox {
        def options = new FirefoxOptions()
        if (System.getProperty('geb.headless') == 'true') {
            options.addArguments('--headless')
        }
        driver = { new FirefoxDriver(options) }
    }

    // Selenium Grid environment for advanced participants
    grid {
        driver = {
            new RemoteWebDriver(
                new URI("http://localhost:4444/wd/hub").toURL(),
                new ChromeOptions()
            )
        }
    }
}

// Wait configuration - reasonable defaults for workshop
waiting {
    timeout = 10
    retryInterval = 0.5
}

// Screenshot configuration - store in standard location
reportsDir = 'build/reports/geb'
