package com.austin.neoviewerjava;

import static org.junit.Assert.assertEquals;

import com.austin.neoviewerjava.database.Neo;

import org.junit.Test;

public class NeoTest {

    @Test
    public void neoEqualsChecksAllValues() {
        Neo neo1 = Util.getSampleNeo();
        Neo neo2 = Util.getSampleNeo();
        assertEquals(neo1, neo2);
    }
}
