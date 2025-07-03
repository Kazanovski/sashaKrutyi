package com.gms.ua.server.listeners;

import lombok.extern.slf4j.Slf4j;
import org.jsmpp.extra.SessionState;
import org.jsmpp.session.SMPPServerSession;
import org.jsmpp.session.Session;
import org.jsmpp.session.SessionStateListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SessionChangeStateListener implements SessionStateListener {

    @Override
    public void onStateChange(SessionState newState, SessionState oldState, Session sourceSession) {
        SMPPServerSession session = (SMPPServerSession) sourceSession;
        log.info("New state of " + session.getSessionId() + " is " + newState + " bindType: " + session.getSessionState().isBound());

        if (newState == SessionState.CLOSED) {
            log.warn("Closed");
        }
    }

}
