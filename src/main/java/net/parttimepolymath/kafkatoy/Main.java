package net.parttimepolymath.kafkatoy;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello world from %s, version %s (%s)%n",
                ApplicationProperties.getAppName(),
                ApplicationProperties.getAppVersion(),
                ApplicationProperties.getBuildDate()
        );
    }
}