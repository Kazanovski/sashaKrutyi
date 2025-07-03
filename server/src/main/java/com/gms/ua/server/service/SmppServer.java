package com.gms.ua.server.service;

import com.gms.ua.server.listeners.MessageReceiverListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsmpp.session.SMPPServerSession;
import org.jsmpp.session.SMPPServerSessionListener;
import com.gms.ua.server.listeners.ServerResponseDeliveryListenerImpl;
import org.jsmpp.session.SessionStateListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmppServer extends Thread {

    @Value("${smpp.server.port}")
    private Integer port;

    @Value("${smpp.default.processor.degree}")
    private int processorDegree;

    private final WaitBindService waitBindService;
    private final SessionStateListener sessionStateListener;
    private final MessageReceiverListener messageReceiverListener;
    @Qualifier("valueTest") private final ServerResponseDeliveryListenerImpl serverResponseDeliveryListenerImpl;

    @Override
    public void run() {
        try (SMPPServerSessionListener sessionListener = new SMPPServerSessionListener(port)) {
            sessionListener.setSessionStateListener(sessionStateListener);
            sessionListener.setPduProcessorDegree(processorDegree);
            log.info("Smpp server started and listening on port {}", port);

            while (!Thread.currentThread().isInterrupted()) {
                SMPPServerSession serverSession = sessionListener.accept();
                log.info("Accepting connection for session {}", serverSession.getSessionId());
                serverSession.setMessageReceiverListener(messageReceiverListener);
                waitBindService.waitBind(serverSession);
                serverSession.setResponseDeliveryListener(serverResponseDeliveryListenerImpl);
//              serverSession.setStatisticService(statisticService);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
