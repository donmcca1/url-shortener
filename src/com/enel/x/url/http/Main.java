package com.enel.x.url.http;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.enel.x.url.http.resource.ResolveHandler;
import com.enel.x.url.http.resource.UrlHandler;
import com.enel.x.url.service.UrlService;
import com.sun.net.httpserver.HttpServer;

/**
 * Application entry point. Starts a http server.
 */
public class Main {
    private static final int NUM_HTTP_THREADS = 10;
    private static final int SERVER_PORT = 8080;
    private static final String BIND_ADDRESS = "localhost";
    private static final String URL_RESOURCE = "/url";
    private static final String RESOLVE_RESOURCE = "/";
    private static final ExecutorService HTTP_EXECUTOR = Executors.newFixedThreadPool(NUM_HTTP_THREADS);

    public static void main(String[] args) throws Exception {
        Thread shutdownHook = new Thread(() -> {
            System.out.println("JVM is shutting down.");
            try {
                HTTP_EXECUTOR.shutdown();
                var shutdown = HTTP_EXECUTOR.awaitTermination(3, TimeUnit.SECONDS);
                System.out.println("Http executor shutdown: %s".formatted(shutdown));
            } catch (InterruptedException e) {
                System.out.println("Caught interrupted exception while exiting jvm: exception = " + e);
                Thread.currentThread().interrupt();
            }
        });
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        var server = HttpServer.create(new InetSocketAddress(BIND_ADDRESS, SERVER_PORT), 0);
        server.createContext(URL_RESOURCE, new UrlHandler(UrlService.instance()));
        server.createContext(RESOLVE_RESOURCE, new ResolveHandler(UrlService.instance()));
        server.setExecutor(HTTP_EXECUTOR);
        server.start();
    }
}