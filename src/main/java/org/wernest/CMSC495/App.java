/*
MIT License

Copyright (c) 2016 William Ernest

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package org.wernest.CMSC495;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import org.glassfish.grizzly.http.server.HttpServer;
import org.wernest.CMSC495.api.RestEventsResource;
import org.wernest.CMSC495.api.SwotResource;
import org.wernest.CMSC495.authentication.AuthenticationFilter;
import org.wernest.CMSC495.authentication.LoginResource;
import org.wernest.CMSC495.authentication.LogoutResource;
import org.wernest.CMSC495.authentication.RegisterResource;

/**
 * CMSC 495 Final Project
 *
 * @author wernest
 */
public class App {

    private static final URI BASE_URI = URI.create("http://localhost:8080/api/");
    public static final String ROOT_PATH = "";

    public static void main(String[] args) {
        try {

            System.out.println("\"CMSC 495 Final Project");

            final ResourceConfig resourceConfig = new ResourceConfig(AuthenticationFilter.class,
                    RestEventsResource.class,
                    SwotResource.class,
                    LoginResource.class,
                    RegisterResource.class,
                    LogoutResource.class,
                    JacksonFeature.class);

            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    server.shutdownNow();
                }
            }));

            server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("src/main/resources/WEB-INF/"));

            server.start();

            System.out.println(String.format("Application started.\nTry out %s%s\nStop the application using CTRL+C",
                    BASE_URI, ROOT_PATH));

            Thread.currentThread().join();


        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

