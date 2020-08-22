package com.vinay.imagesearch.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * local unit tests for Conveter util class.
 *
 * @author vinay
 */
class ConverterUnitTest {

    // Test for convertToDateString util function is correct
    @Test
    fun convertToDateString_isCorrect() {
        val testTimeMillis = 1471300214792L
        assertEquals(testTimeMillis.convertToDateString("hh:mm dd-MM-yyyy"), "04:00 16-08-2016")
    }

    // Test for convertToDateString util function is wrong
    @Test
    fun convertToDateString_isWrong() {
        val testTimeMillis = 1471300214792L
        assertNotEquals(testTimeMillis.convertToDateString("hh:mm dd-MM-yyyy"), "04:00 16-08-2020")
    }

    // Test for toTitleCase util function is correct
    @Test
    fun toTitleCase_isCorrect() {
        val testString = "hello World!"
        assertEquals(testString.toTitleCase(), "Hello World!")
    }

    // Test for toTitleCase util function is wrong - when first letter isn't capitalised
    @Test
    fun toTitleCase_isWrong() {
        val testString = "hello World!"
        assertNotEquals(testString.toTitleCase(), "hello World!")
    }

    // Test for toTitleCase util function is wrong - when first letter is capitalised but second word first letter is changed to lowered
    @Test
    fun toTitleCase_isWrong2() {
        val testString = "hello World!"
        assertNotEquals(testString.toTitleCase(), "Hello world!")
    }

}