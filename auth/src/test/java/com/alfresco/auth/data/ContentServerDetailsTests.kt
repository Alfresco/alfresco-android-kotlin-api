package com.alfresco.auth.data

import org.junit.Test

class ContentServerDetailsTests {
    @Test
    fun `version 6-2 greater than 5-2-2`() {
        assert(testData("6.2").isAtLeast("5.2.2"))
    }

    @Test
    fun `version 5-2-3 greater than 5-2`() {
        assert(testData("5.2.3").isAtLeast("5.2"))
    }

    @Test
    fun `version 5-2-1 greater than 5-2-2`() {
        assert(testData("5.2.3").isAtLeast("5.2"))
    }

    @Test
    fun `version 5-2 smaller than 5-2-2`() {
        assert(testData("5.2").isAtLeast("5.2.2"))
    }

    private fun testData(version: String): ContentServerDetails {
        return ContentServerDetails(ContentServerDetailsData(EDITION, "6.2", SCHEMA))
    }

    companion object {
        const val EDITION = "community"
        const val SCHEMA = "100"
    }
}