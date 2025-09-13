package workshop.modules

import geb.Module

class ManualsMenuModule extends Module {

    static content = {
        // Main container
        container { $(".ui.container") }

        // All version links
        versionLinks { container.find("a.item") }

        // Current version link (with green label)
        currentVersionLink { container.find("a.item", 0) }
        currentVersionLabel { currentVersionLink.find(".ui.green.horizontal.label") }
        currentVersionNumber { currentVersionLink.text().replaceAll(/current\s*/, "").trim() }

        // Snapshot version link (with yellow label)
        snapshotVersionLink { container.find("a.item").has(".ui.yellow.horizontal.label") }
        snapshotVersionLabel { snapshotVersionLink.find(".ui.yellow.horizontal.label") }
        snapshotVersionNumber { snapshotVersionLink.text().replaceAll(/snapshot\s*/, "").trim() }

        // Historical version links (excluding current and snapshot)
        historicalVersionLinks {
            container.find("a.item").not(has(".ui.horizontal.label"))
        }
    }

    /**
     * Click on a specific version link by version number
     * @param version The version number to click (e.g., "7.0", "6.0")
     */
    void clickVersion(String version) {
        def versionLink = version ? versionLinks.find { (it.text() - 'current\n') == version } : versionLinks.first()
        if (versionLink) {
            versionLink.click()
        } else {
            throw new IllegalArgumentException("Version $version not found in manuals menu")
        }
    }

    /**
     * Click on the current version link
     */
    void clickCurrentVersion() {
        currentVersionLink.click()
    }

    /**
     * Click on the snapshot version link
     */
    void clickSnapshotVersion() {
        snapshotVersionLink.click()
    }

    /**
     * Get all available version numbers
     * @return List of version strings
     */
    List<String> getAvailableVersions() {
        versionLinks.collect {
            it.text().replaceAll(/(current|snapshot)\s*/, "").trim()
        }
    }

    /**
     * Check if a specific version is available
     * @param version The version to check
     * @return true if version exists, false otherwise
     */
    Boolean hasVersion(String version) {
        versionLinks.any { it.text().contains(version) }
    }
}
