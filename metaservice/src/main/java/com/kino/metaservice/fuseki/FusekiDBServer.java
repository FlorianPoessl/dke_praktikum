package com.kino.metaservice.fuseki;

import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.TDBFactory;

@Deprecated
// TODO Klasse löschen, sobald FusekiService & FusekiServiceImpl gelöscht wurden.
public class FusekiDBServer {
    private static Dataset ds;
    private static FusekiServer server;
    private static int port = 3330;

    /**
     * Starts the Fuseki Server with the default port 3330
     */
    public static void run(){
        /*
        ds = TDBFactory.createDataset("DIRECTORY_DATASET");
        server = FusekiServer.create()
                .add("/ds", ds)
                .build() ;
        server.start() ;

         */
    }

    /**
     * checks if the server is still running
     * @return true if still running, false otherwise
     */
    public static boolean isRunning(){
        /*
        try {
            return port == server.getPort();
        }catch (NullPointerException e){
            System.out.println("Server is not running.");
            return false;
        }

         */
        return true;
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

    /**
     * DO NOT USE THIS METHOD YET
     * Synchronises the database of the Web-Services and the Meta-Service
     * @return  True if successful, false otherwise
     */
    //TODO: Lösung von Florian in MovieController abändern und hier übernehmen
    public static boolean synchronise(){
        return false;
    }

}
