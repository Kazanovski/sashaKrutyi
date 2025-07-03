package com.gms.ua.server.domain;

import com.gms.ua.server.domain.enums.LatinEncodingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class SmppClient {

    private String sessionId;
    private String systemId;
    private String password;
    private Boolean clientActive;
    private Set<String> allowedIpAddresses = new HashSet<>();
    private Byte latinEncodingType = LatinEncodingType.GSM_0338.getCode();

    public void setAllowedIpAddresses(String allowedIpAddresses) {
        this.allowedIpAddresses = Arrays.stream(allowedIpAddresses.split(","))
                .collect(Collectors.toSet());
    }

}
