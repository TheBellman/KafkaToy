package net.parttimepolymath.kafkatoy;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * The consumer just reads from the specified topic and writes what it sees to stdout.
 */
@Builder
@Slf4j
public class Consumer implements Runnable {
    private String topic;
    private String bootstrap;

    @Override
    public void run() {

    }
}
