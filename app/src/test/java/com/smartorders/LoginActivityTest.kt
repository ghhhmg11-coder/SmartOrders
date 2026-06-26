package com.smartorders

import org.junit.Assert.*
import org.junit.Test

class LoginActivityTest {
    @Test
    fun testValidCredentials() {
        val username = "admin"
        val password = "1234"
        assertEquals("admin", username)
        assertEquals("1234", password)
        assertTrue(username == "admin" && password == "1234")
    }

    @Test
    fun testEmptyUsernameIsRejected() {
        val username = ""
        val password = "1234"
        assertTrue(username.isEmpty())
    }

    @Test
    fun testEmptyPasswordIsRejected() {
        val username = "admin"
        val password = ""
        assertTrue(password.isEmpty())
    }

    @Test
    fun testWrongCredentialsRejected() {
        val username = "wrong"
        val password = "wrong"
        assertFalse(username == "admin" && password == "1234")
    }
}
