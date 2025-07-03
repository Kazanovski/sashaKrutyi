package com.gms.ua.server.service;

import com.gms.ua.server.domain.SmppClient;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ClientService {

    @Getter
    private Map<String, SmppClient> clients = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        SmppClient smppClient = new SmppClient();
        smppClient.setSessionId("1");
        smppClient.setSystemId("login");
        smppClient.setPassword("pass");
        smppClient.setClientActive(true);
        smppClient.setAllowedIpAddresses("127.0.0.1, 127.0.0.2");
        clients.put("login", smppClient);
        log.info("filling client units completed");
    }

    @PreDestroy
    public void destroy() {
        clients.clear();
        log.info("all routes were stopped and all sessions unbind and closed before shutdown");
    }

    public SmppClient find(String clientId) {
        return clientId != null ? clients.get(clientId) : null;
    }

    public Collection<SmppClient> findAll() {
        return clients.values();
    }

}
