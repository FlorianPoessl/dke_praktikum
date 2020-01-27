package com.kino.metaservice.services;

import com.kino.metaservice.fuseki.FusekiDBServer;
import org.apache.jena.fuseki.main.FusekiServer;

@Deprecated
// TODO Implementierungen von FusekiServiceImpl & anschließend Klasse löschen: Ist irrelevant geworden, da der Fuseki-Server jetzt extern über Konsole gestartet und ausgeführt wird
public class FusekiServiceImpl implements FusekiService {
    FusekiDBServer fusekiDBServer = new FusekiDBServer();

    @Override
    public void checkFusekiServer() {
        if(!isFusekiServerRunning()) {
            startFusekiServer();
            System.out.println("Server is running");
        } else {
            System.out.println("Server is already running");
        }
    }

    @Override
    public void startFusekiServer() {
        fusekiDBServer.run();
    }

    @Override
    public void stopFusekiServer() {
        fusekiDBServer.stop();
    }

    @Override
    public FusekiServer getFusekiServer() {
        return fusekiDBServer.getServer();
    }

    @Override
    public boolean isFusekiServerRunning() {
        return fusekiDBServer.isRunning();
    }

    @Override
    public int getFusekiServerPort() {
        return fusekiDBServer.getPort();
    }
}
