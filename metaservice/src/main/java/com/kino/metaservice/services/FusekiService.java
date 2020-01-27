package com.kino.metaservice.services;

import org.apache.jena.fuseki.main.FusekiServer;

@Deprecated
//TODO Implementierungen von Interface &Interface löschen: Interface irrelevant siehe FusekiServiceImpl Class
public interface FusekiService {

    public void checkFusekiServer();
    public void startFusekiServer();
    public void stopFusekiServer();
    public FusekiServer getFusekiServer();
    public boolean isFusekiServerRunning();
    public int getFusekiServerPort();
}
