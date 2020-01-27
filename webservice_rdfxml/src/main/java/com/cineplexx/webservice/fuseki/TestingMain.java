package com.cineplexx.webservice.fuseki;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.QuerySolution;

import java.util.Iterator;
import java.util.List;

public class TestingMain {

    private static final String UPDATE_TEMPLATE =
            "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
                    + "INSERT DATA"
                    + "{ <http://movieTheater>    dc:Name    \"Webservice Kino\" ;"
                    + "                         dc:Owner  \"Webservice\" ." + "}   ";

    public static void main(String[] args){
        /*Dataset ds = null;
        FusekiDBServer.run(ds);
        if(FusekiDBServer.isRunning()) {
            FusekiQueryExecution.executeUpdate(FusekiDBServer.getServer(), UPDATE_TEMPLATE);
            List<QuerySolution> l = FusekiQueryExecution.executeQuery(FusekiDBServer.getServer(), "SELECT * WHERE {?x ?r ?y}");
            Iterator<QuerySolution> i = l.iterator();
            while (i.hasNext()) {
                System.out.println(i.next().toString());
            }
        }*/
    }
}
