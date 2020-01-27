package com.cineplexx.webservice.fuseki;

import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.tdb.TDBFactory;

public class FusekiDBServer {
    private static Dataset ds;
    private static FusekiServer server;
    private static int port = 3030;
    /**
     * Starts the Fuseki Server with the default port 3030
     */
    public static void run(Dataset ds){
        server = FusekiServer.create()
                .port(port)
                .add("/ds", ds)
                .build() ;
        server.start();
    }

    /**
     * checks if the server is still running
     * @return true if still running, false otherwise
     */
    public static boolean isRunning(){
        try {
            return port == server.getPort();
        }catch (NullPointerException e){
            System.out.println("Server is not running.");
            return false;
        }
    }

    /**
     * Saves all changes and shuts down the Fuseki Server
     */
    public static void stop(){
        server.stop();
    }

    /**
     * Returns the FusekiServer object
     * @return FusekiServer object
     */
    public static FusekiServer getServer(){
        return server;
    }

    /**
     *
     * @return Port of the Fuseki Server
     */
    public static int getPort(){
        return server.getPort();
    }
}
