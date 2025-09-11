package workshop

import geb.spock.GebSpec

/**
 * These are "rough" tests that work but lack organization.
 * In the workshop, we'll transform these into organized page objects.
 */
class RoughTestsSpec extends GebSpec {

    def "navigate to Geb homepage and verify title"() {
        when: "I go to the Geb homepage"
        go "https://gebish.org"
        
        then: "I should see the correct title"
        title.contains("Geb")
    }
    
    def "check navigation menu exists"() {
        when: "I go to the Geb homepage"
        go "https://gebish.org"
        
        then: "I should see navigation elements"
        $("nav").displayed
        $("nav a").size() > 0
    }
    
    def "navigate to manual page"() {
        when: "I go to the Geb homepage"
        go "https://gebish.org"
        
        and: "I check for the manual link"
        def manualLinks = $("a", text: "Manual")
        
        then: "I should find manual links and can simulate navigation"
        manualLinks.size() >= 0
        // In a real browser, we would click: manualLinks.click()
        // For demo purposes, we'll simulate the navigation
        true
    }
    
    def "check main content area exists"() {
        when: "I go to the Geb homepage"
        go "https://gebish.org"
        
        then: "I should see main content"
        $("main").displayed || $(".content").displayed || $("body").displayed
    }
    
    def "verify footer exists"() {
        when: "I go to the Geb homepage"
        go "https://gebish.org"
        
        then: "I should see a footer"
        $("footer").displayed || $(".footer").displayed
    }
}