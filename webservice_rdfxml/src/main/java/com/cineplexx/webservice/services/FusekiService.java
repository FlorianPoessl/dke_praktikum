package com.cineplexx.webservice.services;

import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.query.Dataset;

import java.io.FileNotFoundException;

public interface FusekiService {

    public void checkFusekiServer() throws FileNotFoundException;
    public void startFusekiServer(Dataset ds);
    public void stopFusekiServer();
    public FusekiServer getFusekiServer();
    public boolean isFusekiServerRunning();
    public int getFusekiServerPort();
    public String getMovieDataset();
    public String getSnackDataset();
}
