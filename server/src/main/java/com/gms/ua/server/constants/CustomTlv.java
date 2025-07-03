package com.gms.ua.server.constants;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
public class CustomTlv {

    public static final Set<Short> TLVS = new HashSet<>(Arrays.asList(
            (short) 0x0010, //SOURCE_TELEMATICS_ID
            (short) 0x1403, //CUSTOM_TLV_1403
            (short) 0x1776, //MTS_TRANSACTION_ID
            (short) 0x2001, //CUSTOM_TLV_2001
            (short) 0x2010, //ASTELIT_SERVICE_ID
            (short) 0x1410, //TRANSIT_DR_CHANNEL_TYPE
            (short) 0x1411, //TRANSIT_DR_HR_CODE
            (short) 0x1775 //MTS_SERVICE_ID
    ));

}
