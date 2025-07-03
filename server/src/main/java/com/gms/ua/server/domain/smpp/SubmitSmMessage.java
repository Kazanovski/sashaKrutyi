package com.gms.ua.server.domain.smpp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jsmpp.bean.SubmitSm;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SubmitSmMessage {

    protected byte part;
    protected byte parts;
    protected int refNum;
    protected String text;
    protected SubmitSm submitSm;

}
