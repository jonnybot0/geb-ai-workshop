package workshop.pages

import geb.Page

class GebHomePage extends Page {
    static url = "https://groovy.apache.org/geb/"

    static at = {
        title.contains("Geb")
    }

    static content = {
        navigationLinks { $("header a, .menu a, .navigation a, a") }
        mainContent { $("body") }
        footer { $("footer, .footer") }
        manualDropdown { $(".manuals", text: "Manual") }
        manualMenu { $("#manuals-menu .ui.container") }
    }

    boolean hasCorrectTitle() {
        title.contains("Geb")
    }

    boolean hasNavigation() {
        navigationLinks.size() > 0
    }

    boolean hasMainContent() {
        mainContent.displayed
    }

    boolean hasFooter() {
        footer.displayed
    }

    GebManualPage navigateToManual() {
        manualDropdown.click()

        waitFor {
            manualMenu.displayed && manualMenu.children('.item').size() > 2
        }

        manualMenu.children('.item').first().click()

        browser.at GebManualPage
    }
}
