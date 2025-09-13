package workshop

import geb.spock.GebReportingSpec
import spock.lang.Shared
import workshop.pages.GebHomePage

/**
 * Clean tests using page objects and following Geb best practices
 */
class RoughTestsSpec extends GebReportingSpec {

    @Shared
    GebHomePage gebHomePage

    def setup() {
        gebHomePage = to GebHomePage
    }

    def "navigate to Geb homepage and verify title"() {
        expect: "I should see the correct title"
        gebHomePage.hasCorrectTitle()
    }

    def "check navigation menu exists"() {
        expect: "I should see navigation elements"
        gebHomePage.hasNavigation()
    }

    def "navigate to manual page"() {
        when: "I navigate to the manual"
        def manualPage = gebHomePage.navigateToManual()

        then: "I should be on the manual page"
        manualPage.isManualPage()
    }

    def "check main content area exists"() {
        expect: "I should see main content"
        gebHomePage.hasMainContent()
    }

    def "verify footer exists"() {
        expect: "I should see a footer"
        gebHomePage.hasFooter()
    }
}
