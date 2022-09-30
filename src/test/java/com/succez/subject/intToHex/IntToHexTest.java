package com.orrsrosx.subject.intToHex;

import org.junit.Assert;
import org.junit.Test;

public class IntToHexTest {

    @Test
    public void intToHex() {
        int i=16;
        String str=IntToHex.intToHex(i);
        Assert.assertNotNull(str);
    }
}