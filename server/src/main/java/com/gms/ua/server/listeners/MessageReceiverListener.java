package com.gms.ua.server.listeners;

import com.gms.ua.server.domain.enums.LatinEncodingType;
import com.gms.ua.server.domain.smpp.SubmitSmMessage;
import com.gms.ua.server.encoding.CharsetEncoding;
import com.gms.ua.server.encoding.Gsm0338Charset;
import com.gms.ua.server.encoding.LatinIso8859Charset;
import com.gms.ua.server.encoding.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsmpp.PDUStringException;
import org.jsmpp.bean.*;
import org.jsmpp.session.*;
import org.jsmpp.util.MessageId;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static com.gms.ua.server.constants.Constants.*;
import static com.gms.ua.server.domain.enums.LatinEncodingType.GSM_0338;
import static com.gms.ua.server.util.SpecialTlvHeaders.*;

@Slf4j
@Component
public class MessageReceiverListener implements ServerMessageReceiverListener {

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public SubmitSmResult onAcceptSubmitSm(SubmitSm submitSm, SMPPServerSession smppServerSession) {
        try {
            SubmitSmMessage submitSmMessage = new SubmitSmMessage();
            submitSmMessage.setSubmitSm(submitSm);

            LatinEncodingType latinEncodingType = LatinEncodingType.getTypeByCode(LatinEncodingType.GSM_0338.getCode()); //TODO find client

            if (hasUdh(submitSm)) {
                byte[] shortMessage = submitSm.getShortMessage();
                byte udhLength = shortMessage[0];
                submitSmMessage.setPart(shortMessage[udhLength]);
                submitSmMessage.setParts(shortMessage[udhLength - 1]);
                submitSmMessage.setRefNum(getMessageRefNum(submitSm));
                submitSmMessage.getSubmitSm().setShortMessage(Arrays.copyOfRange(shortMessage, udhLength + 1, shortMessage.length));
            } else {
                submitSmMessage.setPart(getMessagePartFromTlv(submitSm));
                submitSmMessage.setParts(getMessagePartsFromTlv(submitSm));
                submitSmMessage.setRefNum(getMessageRefNum(submitSm));
            }
            submitSmMessage.setText(TextUtils.convertByteToString(submitSm.getShortMessage(), submitSm.getDataCoding(), latinEncodingType));
            System.out.println(submitSmMessage);
            return new SubmitSmResult(new MessageId("1"), submitSm.getOptionalParameters());
        } catch (UnsupportedEncodingException | PDUStringException e) {
            throw new RuntimeException(e);
        }

//            Long msgId = smppMessagesRepository.save(new DbSmppMessages(
//                            submitSm.getRegisteredDelivery() == SMSCDeliveryReceipt.SUCCESS_FAILURE.value(),
//                            submitSm.getDataCoding(),
//                            submitSm.getText(),
//                            submitSm.getSourceAddr(),
//                            LocalDateTime.now()))
//                    .getId();
//
//            jmsSender.send(IncomeMessage.builder()
//                    .id(msgId)
//                    .clientId(serverSession.getClientUnitId())
//                    .source(submitSm.getSourceAddr())
//                    .dest(submitSm.getDestAddress())
//                    .text(submitSm.getText())
//                    .part(submitSm.getPart())
//                    .parts(submitSm.getParts())
//                    .refNum(submitSm.getRefNum())
//                    .optionalParameters(submitSm.getOptionalParameters())
//                    .build());
//
//            statisticService.incAccepted(serverSession.getClientUnitId());
//
//            logger.info(msgOf()
//                    .smppId(msgId)
//                    .unitId(serverSession.getClientUnitId())
//                    .sessionId(serverSession.getSessionId())
//                    .msg("submitSm: %s", submitSm.getText()));
//
//            return new MessageId(msgId.toString());
//        } catch (Exception e) {
//            logger.error(msgOf()
//                    .unitId(serverSession.getClientUnitId())
//                    .sessionId(serverSession.getSessionId())
//                    .msg(e.getMessage()), e);
//
//            statisticService.incRejected(serverSession.getClientUnitId());
//            throw new ProcessRequestException(e.getMessage(), SMPPConstant.STAT_ESME_RSYSERR);
//        }

    }

    private boolean hasUdh(SubmitSm submitSm) {
        if (submitSm.isUdhi()) {
            byte first = submitSm.getShortMessage()[0];
            return first == LENGTH_UDH_8 || first == LENGTH_UDH_16;
        }
        return false;
    }

    @Override
    public QuerySmResult onAcceptQuerySm(QuerySm querySm, SMPPServerSession smppServerSession) {
        log.info(smppServerSession.getSessionId() + " QuerySm received");
        return null;
    }

    @Override
    public SubmitMultiResult onAcceptSubmitMulti(SubmitMulti submitMulti, SMPPServerSession smppServerSession) {
        log.info(smppServerSession.getSessionId() + " SubmitMulti received");
        return null;
    }

    @Override
    public DataSmResult onAcceptDataSm(DataSm dataSm, Session session) {
        log.info(session.getSessionId() + " DataSm received");
        return null;
    }

    @Override
    public void onAcceptCancelSm(CancelSm cancelSm, SMPPServerSession smppServerSession) {
        log.info(smppServerSession.getSessionId() + " CancelSm received");
    }

    @Override
    public BroadcastSmResult onAcceptBroadcastSm(BroadcastSm broadcastSm, SMPPServerSession smppServerSession) {
        log.info(smppServerSession.getSessionId() + " BroadcastSm received");
        return null;
    }

    @Override
    public void onAcceptCancelBroadcastSm(CancelBroadcastSm cancelBroadcastSm, SMPPServerSession smppServerSession) {
        log.info(smppServerSession.getSessionId() + " CancelBroadcastSm received");
    }

    @Override
    public QueryBroadcastSmResult onAcceptQueryBroadcastSm(QueryBroadcastSm queryBroadcastSm, SMPPServerSession smppServerSession) {
        log.info(smppServerSession.getSessionId() + " QueryBroadcastSm received");
        return null;
    }

    @Override
    public void onAcceptReplaceSm(ReplaceSm replaceSm, SMPPServerSession smppServerSession) {
        log.info(smppServerSession.getSessionId() + " ReplaceSm received");
    }

}
