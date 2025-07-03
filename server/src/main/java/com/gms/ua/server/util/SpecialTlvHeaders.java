package com.gms.ua.server.util;

import com.gms.ua.server.constants.CustomTlv;
import org.jsmpp.bean.OptionalParameter;
import org.jsmpp.bean.SubmitSm;
import org.jsmpp.util.OctetUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.gms.ua.server.constants.Constants.*;
import static java.lang.String.format;
import static org.jsmpp.bean.OptionalParameter.*;

public final class SpecialTlvHeaders {

    private SpecialTlvHeaders() {
    }

    public static Map<String, Object> readAllTlvHeaders(OptionalParameter[] optionalParameters) {
        if (optionalParameters == null || optionalParameters.length == 0) {
            return null;
        } else {
            Map<String, Object> headers = new HashMap<>();
            for (OptionalParameter op : optionalParameters) {
                Tag tag = Tag.valueOf(op.tag);
                if (tag != null) {
                    readTlv(op, headers);
                }
            }
            return headers;
        }
    }

    private static void readTlv(OptionalParameter parameter, Map<String, Object> headers) {
        Tag tag = Tag.valueOf(parameter.tag);
        if (tag == null) {
            headers.put(format("%s%s_%s", TLV_PREFIX, "unknown", parameter.tag), new String(parameter.serialize(), StandardCharsets.UTF_8));
            return;
        }

        if (CustomTlv.TLVS.contains(tag.code())){
            OctetString tlvParameter = (OctetString) parameter;
            headers.put(TLV_PREFIX + tag.name(), tlvParameter.getValueAsString());
        }

    }

    public static byte getMessagePartsFromTlv(SubmitSm submitSm) {
        Sar_total_segments optional = submitSm.getOptionalParameter(Sar_total_segments.class);
        return optional != null ? optional.getValue() : 1;
    }

    public static byte getMessagePartFromTlv(SubmitSm submitSm) {
        Sar_segment_seqnum optional = submitSm.getOptionalParameter(Sar_segment_seqnum.class);
        return optional != null ? optional.getValue() : 1;
    }

    public static int getMessageRefNum(SubmitSm submitSm) {
        if (submitSm.isUdhi()) {
            byte[] shortMessage = submitSm.getShortMessage();
            if (shortMessage[0] == LENGTH_UDH_8) {
                return shortMessage[3];
            }
            if (shortMessage[0] == LENGTH_UDH_16) {
                return OctetUtil.bytesToInt(new byte[]{shortMessage[3], shortMessage[4]});
            }
        } else {
            Sar_msg_ref_num optional = submitSm.getOptionalParameter(Sar_msg_ref_num.class);
            if (optional != null) {
                return optional.getValue();
            }
        }
        return 1;
    }

}
