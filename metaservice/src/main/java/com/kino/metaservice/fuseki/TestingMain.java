package com.kino.metaservice.fuseki;

import org.apache.jena.query.QuerySolution;
import java.util.Iterator;
import java.util.List;

public class TestingMain {

    private static final String UPDATE_TEMPLATE =
            "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
                    + "INSERT DATA"
                    + "{ <http://movieTheater>    dc:Name    \"Das Neue Kino\" ;"
                    + "                         dc:Owner  \"Ein bekannter Man\" ." + "}   ";
    private static final String DELETE_TEMPLATE =
            "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
                    + "DELETE DATA"
                    + "{ <http://movieTheater>    dc:Name \"Movie Star\";"
                    + "                         dc:Owner \"Reiner\" ." + "}    ";

    public static void main(String[] args){
        if(FusekiQueryExecution.executeUpdate(UPDATE_TEMPLATE))
            System.out.println("Zugriff auf Fuseki Server funktioniert.");
        else{
            System.out.println("Zugriff auf Fuseki Server funktioniert nicht.");
        }

        List<QuerySolution> l = FusekiQueryExecution.executeQuery("SELECT * WHERE {?x ?r ?y}");
        Iterator<QuerySolution> i = l.iterator();
        while (i.hasNext()) {
            System.out.println(i.next().toString());
        }

    }
}
