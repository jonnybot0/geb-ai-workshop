package workshop.pages

import geb.Page

/**
 * Page object for the Geb manual page
 */
class ManualPage extends Page {
    
    static at = { 
        title.contains("The Book of Geb") || title.contains("Manual") || title.contains("Geb")
    }
    
    static content = {
        // Main content
        mainContent { $("main") }
        contentDiv { $(".content") }
        bodyElement { $("body") }
    }
    
    /**
     * Check if the manual content is properly loaded
     * @return boolean
     */
    boolean hasContent() {
        mainContent.size() > 0 || contentDiv.size() > 0 || bodyElement.size() > 0
    }
}