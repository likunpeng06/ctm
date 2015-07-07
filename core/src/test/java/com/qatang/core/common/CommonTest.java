package com.qatang.core.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.springframework.validation.BindingResult;

/**
 * @author qatang
 * @since 2014-12-24 10:55
 */
public class CommonTest {

    @Test
    public void testBase64() throws Exception {
        String str = "窝窝";

        System.out.println(Base64.isBase64(str));
        String encodeStr = Base64.encodeBase64String(str.getBytes());
        System.out.println(encodeStr);
        System.out.println(Base64.isBase64(encodeStr));

        String decodeStr = new String(Base64.decodeBase64(encodeStr));
        System.out.println(decodeStr);
        System.out.println(Base64.isBase64(decodeStr));
        System.out.println();
    }

    @Test
    public void testHex() throws Exception {
        String str = "窝窝";

        String encodeStr = Hex.encodeHexString(str.getBytes());
        System.out.println(encodeStr);

        String decodeStr = new String(Hex.decodeHex(encodeStr.toCharArray()));
        System.out.println(decodeStr);

        System.out.println(BindingResult.class.getName());
    }
}
