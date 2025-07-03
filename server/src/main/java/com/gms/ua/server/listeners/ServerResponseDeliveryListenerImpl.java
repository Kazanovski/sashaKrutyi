package com.gms.ua.server.listeners;

import lombok.extern.slf4j.Slf4j;
import org.jsmpp.session.SMPPServerSession;
import org.jsmpp.session.ServerResponseDeliveryListener;
import org.jsmpp.session.SubmitMultiResult;
import org.jsmpp.session.SubmitSmResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service("valueTest")
public class ServerResponseDeliveryListenerImpl implements ServerResponseDeliveryListener {
    @Override
    public void onSubmitSmRespSent(SubmitSmResult submitSmResult, SMPPServerSession source) {
        log.info("SMRESULT " + submitSmResult);
    }

    @Override
    public void onSubmitSmRespError(SubmitSmResult submitSmResult, Exception cause, SMPPServerSession source) {

    }

    @Override
    public void onSubmitMultiRespSent(SubmitMultiResult submitMultiResult, SMPPServerSession source) {

    }

    @Override
    public void onSubmitMultiRespError(SubmitMultiResult submitMultiResult, Exception cause, SMPPServerSession source) {

    }
}
