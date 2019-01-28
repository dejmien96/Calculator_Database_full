package com.janicki.damian.calc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    private MainActivity mainActivity = null;

    @Before
    public void setUp() {
        mainActivity = new MainActivity();
    }

    @Test
    public void testDeleteLastCharWhenStringIsNull() {
        final String actual = null;
        final String expected = null;

        assertSame(mainActivity.deleteLastChar(actual), expected);
    }

    @Test
    public void testDeleteLastCharWhenStringIsEmpty() {
        final String actual = "";
        final String expected = "";

        assertEquals(mainActivity.deleteLastChar(actual), expected);
    }

    @Test
    public void testDeleteLastCharWhenStringIsNotEmpty() {
        final String actual = "abcd";
        final String expected = "abc";

        assertEquals(mainActivity.deleteLastChar(actual), expected);
    }

    @Test
    public void testConcatWithEquealitySign() {
        final String string = "xyz";
        final Double value = 3.22;
        final String expected = "xyz = 3.22";

        assertEquals(mainActivity.concatWithEquealitySign(string, value), expected);
    }

    @Test
    public void testFormatDoubleToStringWhenDecimal() {
        final double value = 3;
        final String expected = "3";
        assertEquals(mainActivity.formatDoubleToString(value), expected);
    }

    @Test
    public void testFormatDoubleToStringWhenLong() {
        final double value = 3.20000000;
        final String expected = "3.2";
        assertEquals(mainActivity.formatDoubleToString(value), expected);
    }


}