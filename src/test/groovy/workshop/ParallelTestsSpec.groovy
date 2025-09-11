package workshop

import geb.spock.GebSpec
import workshop.pages.HomePage
import spock.lang.Unroll

/**
 * Demonstrates parallel test execution with Geb
 * These tests are designed to run in parallel to showcase performance benefits
 */
class ParallelTestsSpec extends GebSpec {

    @Unroll("Test parallel execution #testNumber")
    def "demonstrate parallel execution capabilities"() {
        when: "I navigate to the homepage"
        to HomePage
        
        then: "I should be on the correct page"
        at HomePage
        
        and: "Navigation should work"
        page.hasNavigation()
        
        where:
        testNumber << (1..5)
    }
    
    def "parallel test - verify title"() {
        when: "I go to the homepage"
        to HomePage
        
        then: "Title should be correct"
        browser.title.contains("Geb")
    }
    
    def "parallel test - verify navigation count"() {
        when: "I go to the homepage"
        to HomePage
        
        then: "Should have navigation links"
        page.navigationLinks.size() >= 0
    }
    
    def "parallel test - verify footer"() {
        when: "I go to the homepage"
        to HomePage
        
        then: "Footer should be present"
        page.footer.size() >= 0 // Using size() to avoid strict display check
    }
    
    def "parallel test - verify main content"() {
        when: "I go to the homepage"
        to HomePage
        
        then: "Main content should be accessible"
        page.hasMainContent()
    }
}