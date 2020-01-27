package com.kino.metaservice.fuseki;

import org.apache.jena.query.*;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;

import java.util.List;
import java.util.UUID;

public class FusekiQueryExecution {

    /**
     * Executes the given Update
     * @param query The given SPARQL query
     * @return true if Update was successful, false otherwise
     */
    public static boolean executeUpdate(String query){
        try {
            String id = UUID.randomUUID().toString();
            System.out.println(String.format("Adding %s", id));
            UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                    UpdateFactory.create(String.format(query, id)),
                    "http://localhost:3330/ds/update");
            upp.execute();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Executes the given Query and returns the Results
     * @param query The given SPARQL query
     * @return  List of Solutions for the query
     */
    public static List<QuerySolution> executeQuery(String query){
        try {
            QueryExecution qe = QueryExecutionFactory.sparqlService(
                    "http://localhost:3330/ds/query", query);
            org.apache.jena.query.ResultSet results = qe.execSelect();
            List<QuerySolution> l = ResultSetFormatter.toList(results);
            qe.close();
            return l;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
