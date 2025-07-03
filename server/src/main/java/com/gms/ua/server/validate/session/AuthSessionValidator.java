package com.gms.ua.server.validate.session;

import com.gms.ua.server.domain.SmppClient;
import com.gms.ua.server.validate.SessionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsmpp.session.BindRequest;
import org.jsmpp.session.SMPPServerSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.jsmpp.SMPPConstant.*;

@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class AuthSessionValidator implements SessionValidator<SmppClient> {

    @Override
    public int validate(BindRequest br, SMPPServerSession session, SmppClient smppClient) {
        if (!smppClient.getAllowedIpAddresses().contains(session.getInetAddress().getHostAddress())) {
            log.error(String.format("%s: %s (%s %s)", ERROR_TEXT, STAT_ESME_RBINDFAIL, "Not allowed ip for client", session.getSessionId()));
            return STAT_ESME_RBINDFAIL;
        }
        if (!smppClient.getPassword().equals(br.getPassword())) {
            log.error(String.format("%s: %s (%s %s)", ERROR_TEXT, STAT_ESME_RINVPASWD, "Not correct password", br.getPassword()));
            return STAT_ESME_RINVPASWD;
        }
        return STAT_ESME_ROK;
    }

}
