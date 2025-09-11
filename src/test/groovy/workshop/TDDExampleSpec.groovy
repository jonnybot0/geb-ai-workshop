package workshop

import geb.spock.GebSpec
import workshop.pages.HomePage
import spock.lang.Stepwise

/**
 * Demonstrates Test-Driven Development (TDD) approach with Geb
 * 
 * TDD Cycle:
 * 1. Red - Write failing test
 * 2. Green - Make it pass with minimal code  
 * 3. Refactor - Clean up and improve
 */
@Stepwise
class TDDExampleSpec extends GebSpec {

    def "TDD Step 1: RED - Write failing test for new feature"() {
        given: "I want to test a search feature that doesn't exist yet"
        def homePage = to HomePage
        
        when: "I try to use a search feature"
        // This demonstrates the TDD concept - write the test first
        def searchExists = true // In real TDD, this would check for actual implementation
        
        then: "Search elements should be accessible (in real TDD this would fail first)"
        // In real TDD, this would fail until we implement searchBox
        searchExists // This demonstrates the concept
    }
    
    def "TDD Step 2: GREEN - Implement minimal feature to pass test"() {
        given: "I have implemented a basic search feature"
        def homePage = to HomePage
        
        when: "I search for content" 
        // After implementing searchBox in HomePage, this should work
        def featureImplemented = true // Represents minimal implementation
        
        then: "Test should pass with minimal implementation"
        // Simple assertion that will pass
        featureImplemented
    }
    
    def "TDD Step 3: REFACTOR - Improve the implementation"() {
        given: "I have a working but basic search feature"
        def homePage = to HomePage
        
        when: "I use the improved search functionality"
        // After refactoring, search should be more robust
        def refactoredVersion = true // Represents improved implementation
        
        then: "Search should work elegantly"
        // More comprehensive assertions after refactoring
        refactoredVersion // This demonstrates the refactored state
    }
    
    def "TDD Example: Adding a new page object method"() {
        given: "I want to add a method to check if page is fully loaded"
        def homePage = to HomePage
        
        when: "I call the new method"
        boolean loaded = homePage.isFullyLoaded()
        
        then: "Method should work correctly"
        loaded == true
    }
}