package com.gms.ua.server;

import com.gms.ua.server.service.SmppServer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Server {

    private final SmppServer smppServer;

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @PostConstruct
    public void initServer() {
        try {
            smppServer.start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void destroyServer() {
        try {
            smppServer.interrupt();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
