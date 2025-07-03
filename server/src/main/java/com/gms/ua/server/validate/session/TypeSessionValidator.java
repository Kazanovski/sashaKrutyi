package com.gms.ua.server.validate.session;

import com.gms.ua.server.domain.SmppClient;
import com.gms.ua.server.validate.SessionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsmpp.bean.InterfaceVersion;
import org.jsmpp.session.BindRequest;
import org.jsmpp.session.SMPPServerSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.jsmpp.SMPPConstant.*;

@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
public class TypeSessionValidator implements SessionValidator<SmppClient> {

    //TODO
    @Override
    public int validate(BindRequest br, SMPPServerSession session, SmppClient smppClient) {
/*        if ((br.getBindType() == BIND_RX) && client.getReceiversMap().size() >= client.getMaxReceivers()) {
            log.error(String.format("%s: %s (%s %s)",
                    ERROR_TEXT, STAT_ESME_RALYBND, "limit BIND_RX reached", session.getSessionId()));
            return STAT_ESME_RALYBND;
        }

        if ((br.getBindType() == BIND_TX) && client.getTransmittersMap().size() >= client.getMaxTransmitters()) {
            log.error(String.format("%s: %s (%s %s)",
                    ERROR_TEXT, STAT_ESME_RALYBND, "limit BIND_TX reached", session.getSessionId()));
            return STAT_ESME_RALYBND;
        }

        if ((br.getBindType() == BIND_TRX) && client.getTransceiversMap().size() >= client.getMaxTransceivers()) {
            log.error(String.format("%s: %s (%s %s)",
                    ERROR_TEXT, STAT_ESME_RALYBND, "limit BIND_TRX reached", session.getSessionId()));
            return STAT_ESME_RALYBND;
        }*/

        if (br.getInterfaceVersion() != InterfaceVersion.IF_34) {
            log.error(String.format("%s: %s (%s %s)",
                    ERROR_TEXT, STAT_ESME_RALYBND, "Not correct protocol version IF_34", session.getSessionId()));

            return STAT_ESME_RBINDFAIL;
        }
        return STAT_ESME_ROK;
    }

}
