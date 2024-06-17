package org.plivo;

import org.apache.catalina.LifecycleException;

public class Main {
    private static final int TOMCAT_DEFAULT_PORT = 8989;

    public static void main(String[] args) throws LifecycleException {
        var tomcat = TomcatServer.create(TOMCAT_DEFAULT_PORT);
        tomcat.start();
        tomcat.getServer().await();
    }
}