package workshop

import geb.spock.GebSpec
import spock.lang.Unroll
import workshop.pages.HomePage
import workshop.pages.ManualPage

/**
 * Demonstrates parallel test execution with Geb
 * These tests are designed to run in parallel to showcase performance benefits
 */
class ParallelTestsSpec extends GebSpec {

    @Unroll("Test parallel execution #version")
    def "demonstrate parallel execution capabilities"() {
        when: "I navigate to the homepage"
        def homePage = to HomePage
        homePage.manualLink.click()
        homePage.manualsMenu.clickVersion(version)

        then:
        def page = at ManualPage
        page.versionNumber.text().contains(version)

        where:
        version << ['8.0.0', '7.0', '6.0', '5.1', '5.0', '4.1', '4.0']
    }
}
