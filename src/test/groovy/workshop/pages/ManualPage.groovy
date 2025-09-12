package workshop.pages

import geb.Page

/**
 * Page object for the Geb manual page
 */
class ManualPage extends Page {

    static at = {
        title.contains("The Book Of Geb")
    }

    static content = {
        // Main content
        mainContent { $("main") }
        contentDiv { $(".content") }
        bodyElement { $("body") }
        versionNumber { $(id: 'revnumber') }
    }

    /**
     * Check if the manual content is properly loaded
     * @return boolean
     */
    boolean hasContent() {
        mainContent.size() > 0 || contentDiv.size() > 0 || bodyElement.size() > 0
    }
}
