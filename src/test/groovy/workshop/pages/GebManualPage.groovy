package workshop.pages

import geb.Page

class GebManualPage extends Page {
    static at = {
        title.contains("The Book Of Geb")
    }

    boolean isManualPage() {
        title.contains("The Book Of Geb")
    }
}
