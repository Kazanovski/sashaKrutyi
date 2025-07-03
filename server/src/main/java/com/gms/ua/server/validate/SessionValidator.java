package com.gms.ua.server.validate;

import org.jsmpp.session.BindRequest;
import org.jsmpp.session.SMPPServerSession;
import org.springframework.stereotype.Component;

@Component
public interface SessionValidator<T> {
    String ERROR_TEXT = "error bind session code";

    int validate(BindRequest br, SMPPServerSession session, T client);

}
