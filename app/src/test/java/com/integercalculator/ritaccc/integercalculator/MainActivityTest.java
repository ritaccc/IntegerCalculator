package com.integercalculator.ritaccc.integercalculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainActivityTest {
    MainActivity mainActivity = new MainActivity();
    @Before
    public void setUp(){

    }
    @Test
    public void addition_isCorrect() throws Exception {

        assertEquals(4, 2 + 2);
    }
}