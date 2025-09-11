package workshop

import spock.lang.Specification

/**
 * Simple test to verify Spock is working correctly
 */
class BasicSpec extends Specification {

    def "basic spock test"() {
        expect:
        true
    }
    
    def "simple arithmetic test"() {
        given:
        def a = 2
        def b = 3
        
        when:
        def result = a + b
        
        then:
        result == 5
    }
}