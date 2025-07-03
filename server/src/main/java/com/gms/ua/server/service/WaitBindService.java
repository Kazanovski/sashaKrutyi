package com.gms.ua.server.service;

import com.gms.ua.server.domain.SmppClient;
import com.gms.ua.server.validate.SessionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsmpp.PDUStringException;
import org.jsmpp.bean.InterfaceVersion;
import org.jsmpp.session.BindRequest;
import org.jsmpp.session.SMPPServerSession;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.gms.ua.server.constants.Constants.ENQUIRE_LINK_TIMER_TIMEOUT_MILLIS;
import static org.jsmpp.SMPPConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaitBindService {
    public static final int WAIT_FOR_BIND_SECONDS = 30;

    private final ClientService clientService;
    private final List<SessionValidator<SmppClient>> bindSessionValidator;

    @Async("waitBindExecutor")
    public void waitBind(SMPPServerSession serverSession) {
        try {
            BindRequest bindRequest = serverSession.waitForBind(TimeUnit.SECONDS.toMillis(WAIT_FOR_BIND_SECONDS));
            log.debug("Accepting bind for session {}", serverSession.getSessionId());

            try {
                SmppClient smppClient = clientService.find(bindRequest.getSystemId());

                Optional<Integer> validationCode = Optional.of(STAT_ESME_RINVSYSID);
                if (smppClient != null && smppClient.getClientActive()) {
                    /* Validate initiate session */
                    validationCode = bindSessionValidator.stream()
                            .map(validator -> validator.validate(bindRequest, serverSession, smppClient))
                            .findFirst();
                }
                if (validationCode.isPresent()) {
                    Integer resultCode = validationCode.get();
                    if (resultCode == STAT_ESME_ROK) {
                        bindRequest.accept(bindRequest.getSystemId(), InterfaceVersion.IF_34);
                        serverSession.setEnquireLinkTimer(ENQUIRE_LINK_TIMER_TIMEOUT_MILLIS);

//                    clientService.addSession(unit, serverSession, bindRequest.getBindType());
//                    if (serverSession.getBindType().isReceiveable()) {
//                        drRouteBuilder.addRoute(serverSession, unit);
//                    }
                        log.info(String.format("Accept session login: %s, pass: %s, ip: %s",
                                bindRequest.getSystemId(),
                                bindRequest.getPassword(),
                                serverSession.getInetAddress().getHostAddress()));
                    } else {
                        log.warn(String.format("Response code: %s, ip: %s", resultCode, serverSession.getInetAddress().getHostAddress()));
                        bindRequest.reject(resultCode);
//                    statisticService.incConnectionError();
//                    if (validationCode == STAT_ESME_RALYBND) {
//                        statisticService.incAchievedConnectionLimit(unit.getUnitId());
//                    }
                    }
                }
            } catch (PDUStringException e) {
                log.error("Invalid system id %s", e);
                bindRequest.reject(STAT_ESME_RSYSERR);
            }
        } catch (IllegalStateException e) {
            log.error("System error %s", e);
        } catch (TimeoutException e) {
            log.warn("Wait for bind has reach timeout %s", e);
        } catch (IOException e) {
            log.error(String.format("Failed accepting bind request for session %s %s", serverSession.getSessionId(), e));
        }
    }

}
