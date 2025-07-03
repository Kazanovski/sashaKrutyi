package com.gms.ua.server.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Constants {

    public static final int ENQUIRE_LINK_TIMER_TIMEOUT_MILLIS = 50000;

    public static final byte LENGTH_UDH_8 = 5;
    public static final byte LENGTH_UDH_16 = 6;

    public static final byte GSM_7_BIT = 0x00;
    public static final byte ALPHA_UCS2 = 0x08;
    public static final byte LATIN_8859_1 = 0x03;
    public static final String UCS2_ENCODING = "UTF-16BE";

    public static final String TLV_PREFIX = "tlv_";

}
