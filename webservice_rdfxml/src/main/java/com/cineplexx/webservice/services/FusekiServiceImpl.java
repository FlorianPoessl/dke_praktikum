package com.cineplexx.webservice.services;

import com.cineplexx.webservice.fuseki.FusekiDBServer;
import com.cineplexx.webservice.fuseki.FusekiQueryExecution;
import org.apache.jena.fuseki.main.*;
import org.apache.jena.graph.Graph;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.system.Txn;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FusekiServiceImpl implements FusekiService {


    @Override
    public void checkFusekiServer() throws FileNotFoundException {
        if(!isFusekiServerRunning()) {
            System.out.println("Server is running");
            File inputFile = new File("exampleRDFXML/Rdf_test.xml");
            InputStream in = new FileInputStream(inputFile);
            Model model = ModelFactory.createDefaultModel();
            model.read(in, null);
            System.out.println("Dataset:");
            Dataset ds = DatasetFactory.wrap(model);
            System.out.println(ds.asDatasetGraph());
            startFusekiServer(ds);
        } else {
            System.out.println("Server is already running");
        }
    }

    @Override
    public void startFusekiServer(Dataset ds) {
        FusekiDBServer.run(ds);
    }

    @Override
    public void stopFusekiServer() {
        FusekiDBServer.stop();
    }

    @Override
    public FusekiServer getFusekiServer() {
        return FusekiDBServer.getServer();
    }

    @Override
    public boolean isFusekiServerRunning() {
        return FusekiDBServer.isRunning();
    }

    @Override
    public int getFusekiServerPort() {
        return FusekiDBServer.getPort();
    }

    @Override
    public String getMovieDataset() {
        ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
        Map<String, String> prefixMap = new HashMap<>();
        prefixMap.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        prefixMap.put("movie", "http://localhost:3030//movie/");
        queryStr.setNsPrefixes(prefixMap);
        queryStr.append("CONSTRUCT { ");
        queryStr.append("?x movie:cinema ?cinema.\n");
        queryStr.append("?x movie:title ?title.\n");
        queryStr.append("?x movie:date ?date.\n");
        queryStr.append("?x movie:price ?price.\n");
        queryStr.append("?x movie:timestart ?timestart.\n");
        queryStr.append("?x movie:timeend ?timeend\n");
        queryStr.append("}");
        queryStr.append("WHERE");
        queryStr.append("{ ");
        queryStr.append("?x movie:cinema ?cinema.\n");
        queryStr.append("?x movie:title ?title.\n");
        queryStr.append("?x movie:date ?date.\n");
        queryStr.append("?x movie:price ?price.\n");
        queryStr.append("?x movie:timestart ?timestart.\n");
        queryStr.append("?x movie:timeend ?timeend\n");
        queryStr.append("}");
        System.out.println(queryStr.toString());

        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds", queryStr.toString());
        Model results = qe.execConstruct();
        Writer writer = new StringWriter();
        results.write(writer, "RDF/XML");
        return writer.toString();
    }

    @Override
    public String getSnackDataset() {
        ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
        Map<String, String> prefixMap = new HashMap<>();
        prefixMap.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        prefixMap.put("snack", "http://localhost:3030//snack/");
        queryStr.setNsPrefixes(prefixMap);
        queryStr.append("CONSTRUCT { ");
        queryStr.append("?x snack:cinema ?cinema.\n");
        queryStr.append("?x snack:name ?name.\n");
        queryStr.append("?x snack:price ?price.\n");
        queryStr.append("?x snack:size ?size.\n");
        queryStr.append("}");
        queryStr.append("WHERE");
        queryStr.append("{ ");
        queryStr.append("?x snack:cinema ?cinema.\n");
        queryStr.append("?x snack:name ?name.\n");
        queryStr.append("?x snack:price ?price.\n");
        queryStr.append("?x snack:size ?size.\n");
        queryStr.append("}");
        System.out.println(queryStr.toString());

        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds", queryStr.toString());
        Model results = qe.execConstruct();
        Writer writer = new StringWriter();
        results.write(writer, "RDF/XML");
        return writer.toString();
    }
}
