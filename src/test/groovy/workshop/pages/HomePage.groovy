package workshop.pages

import geb.Page

/**
 * Page object for the Geb homepage
 * This demonstrates the organized approach to test automation
 */
class HomePage extends Page {

    static url = "https://groovy.apache.org/geb"

    static at = {
        title.contains("Geb")
    }

    static content = {
        // Navigation elements - using flexible selectors since nav element may not exist
        navigation { $("header, .menu, .navigation") }
        navigationLinks { $("header a, .menu a, .navigation a, a") }
        manualLink { $("a", text: "Manual") }
        manualsMenu { $(id: 'manuals-menu') }

        // Main content areas
        contentArea { $(".content") }

        // Footer
        footer { $("footer, .footer") }
    }

    /**
     * Navigate to the manual page
     * @return ManualPage instance
     */
    ManualPage goToManual(String version = "") {
        manualLink.click()
        def versionLinks = manualsMenu.children('.ui.container').children('.item')
        def versionLink = version ? versionLinks.find { (it.text() - 'current\n') == version } : versionLinks.first()
        versionLink.click()
        browser.at(ManualPage)
    }

    /**
     * Check if navigation is properly displayed
     * @return boolean
     */
    boolean hasNavigation() {
        navigation.size() > 0 && navigationLinks.size() > 0
    }

    /**
     * Check if main content is displayed
     * @return boolean
     */
    boolean hasMainContent() {
        contentArea.size() > 0
    }


    /**
     * TDD Example: Check if page is fully loaded
     * @return boolean
     */
    boolean isFullyLoaded() {
        // Implementation after TDD cycle
        navigation.size() > 0 && (contentArea.size() > 0)
    }
}
