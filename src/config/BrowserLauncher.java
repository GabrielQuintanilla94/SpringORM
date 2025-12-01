package com.universidad.demo_orm.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BrowserLauncher {

    @EventListener(ApplicationReadyEvent.class)
    public void abrirNavegador() {
        try {
            Runtime.getRuntime().exec("cmd /c start http://localhost:9090");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}