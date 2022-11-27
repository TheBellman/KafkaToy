package net.parttimepolymath.kafkatoy;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class Producer implements Runnable {
    private boolean debugMode = false;

    @Override
    public void run() {
        log.info("starting");
        System.out.printf("Hello world from %s producer, version %s (%s)%n",
                ApplicationProperties.getAppName(),
                ApplicationProperties.getAppVersion(),
                ApplicationProperties.getBuildDate()
        );
        log.info("ending");
    }
}
