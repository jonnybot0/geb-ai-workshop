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
        
        // Main content areas  
        mainContent { $("main") }
        contentArea { $(".content") }
        
        // Footer
        footer { $("footer, .footer") }
    }
    
    /**
     * Navigate to the manual page
     * @return ManualPage instance
     */
    ManualPage goToManual() {
        manualLink.click()
        browser.page(ManualPage)
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
        mainContent.size() > 0 || contentArea.size() > 0
    }
    
    /**
     * TDD Example: Search functionality (implement after writing tests)
     */
    def searchBox = { $("input[type='search'], .search input") }
    def searchResults = { $(".search-results, #search-results") }
    
    /**
     * Perform a search operation
     * @param query The search term
     */
    void performSearch(String query) {
        if (searchBox.displayed) {
            searchBox.value(query)
            searchBox << "\n"
        }
    }
    
    /**
     * Check if search results are displayed
     * @return boolean
     */
    boolean hasSearchResults() {
        searchResults.displayed
    }
    
    /**
     * TDD Example: Check if page is fully loaded
     * @return boolean
     */
    boolean isFullyLoaded() {
        // Implementation after TDD cycle
        navigation.size() > 0 && (mainContent.size() > 0 || contentArea.size() > 0)
    }
}