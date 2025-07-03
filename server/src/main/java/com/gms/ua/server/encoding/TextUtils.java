package com.gms.ua.server.encoding;

import com.gms.ua.server.domain.enums.LatinEncodingType;

import java.io.UnsupportedEncodingException;

import static com.gms.ua.server.constants.Constants.*;
import static com.gms.ua.server.domain.enums.LatinEncodingType.GSM_0338;

public record TextUtils() {

    public static String convertByteToString(byte[] array, byte dataCoding, LatinEncodingType latinEncodingType) throws UnsupportedEncodingException {
        if (dataCoding == ALPHA_UCS2) {
            return new String(array, UCS2_ENCODING);
        } else {
            return getCharsetEncoding(latinEncodingType).convertBytesToString(array);
        }
    }

    private static CharsetEncoding getCharsetEncoding(LatinEncodingType latinEncodingType) {
        return latinEncodingType.getCode() == GSM_0338.getCode()
                ? new Gsm0338Charset() : new LatinIso8859Charset();
    }

    public static byte determineEncodingStatus(String text, LatinEncodingType latinEncodingType) {
        if (getCharsetEncoding(latinEncodingType).isLatinEncoding(text)) {
            return GSM_7_BIT;
        } else {
            return ALPHA_UCS2;
        }
    }

    public static byte[] convertStringToByte(String text, byte dataCoding, LatinEncodingType latinEncodingType) throws UnsupportedEncodingException {
        if (dataCoding == GSM_7_BIT || dataCoding == LATIN_8859_1) {
            return getCharsetEncoding(latinEncodingType).convertStringToBytes(text);
        } else {
            //cyrillic ALPHA_UCS2
            return text.getBytes(UCS2_ENCODING);
        }
    }

}
