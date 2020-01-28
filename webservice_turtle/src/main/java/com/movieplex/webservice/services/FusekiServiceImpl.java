package com.movieplex.webservice.services;

import com.movieplex.webservice.fuseki.FusekiDBServer;
import org.apache.jena.fuseki.main.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.resultset.RDFOutput;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FusekiServiceImpl implements FusekiService {


    @Override
    public void checkFusekiServer() throws FileNotFoundException {
        if(!isFusekiServerRunning()) {
            System.out.println("Server is running");
            File inputFile = new File("exampleTurtle/turtle.ttl");
            InputStream in = new FileInputStream(inputFile);
            Model model = ModelFactory.createDefaultModel();
            model.read(in, null, "TURTLE");
            System.out.print("Dataset:");
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
        prefixMap.put("film", "http://localhost:8082/");
        queryStr.setNsPrefixes(prefixMap);
        queryStr.append("CONSTRUCT { ");
        queryStr.append("?x film:name ?name.\n");
        queryStr.append("?x film:datum ?datum.\n");
        queryStr.append("?x film:beginn ?beginn.\n");
        queryStr.append("?x film:ende ?ende.\n");
        queryStr.append("?x film:ticketpreis ?ticketpreis.\n");
        queryStr.append("?x film:kino ?kino\n");
        queryStr.append("}");
        queryStr.append("WHERE");
        queryStr.append("{ ");
        queryStr.append("?x film:name ?name.\n");
        queryStr.append("?x film:datum ?datum.\n");
        queryStr.append("?x film:beginn ?beginn.\n");
        queryStr.append("?x film:ende ?ende.\n");
        queryStr.append("?x film:ticketpreis ?ticketpreis.\n");
        queryStr.append("?x film:kino ?kino\n");
        queryStr.append("}");
        System.out.println(queryStr.toString());

        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3032/ds", queryStr.toString());
        Model results = qe.execConstruct();
        Writer writer = new StringWriter();
        results.write(writer, "TURTLE");
        return writer.toString();
    }

    @Override
    public String getSnackDataset() {
        ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
        Map<String, String> prefixMap = new HashMap<>();
        prefixMap.put("verpflegung", "http://localhost:8082/");
        queryStr.setNsPrefixes(prefixMap);
        queryStr.append("CONSTRUCT { ");
        queryStr.append("?x verpflegung:snack ?snack.\n");
        queryStr.append("?x verpflegung:kosten ?kosten.\n");
        queryStr.append("?x verpflegung:portion ?portion.\n");
        queryStr.append("?x verpflegung:kino ?kino.\n");
        queryStr.append("}");
        queryStr.append("WHERE");
        queryStr.append("{ ");
        queryStr.append("?x verpflegung:snack ?snack.\n");
        queryStr.append("?x verpflegung:kosten ?kosten.\n");
        queryStr.append("?x verpflegung:portion ?portion.\n");
        queryStr.append("?x verpflegung:kino ?kino.\n");
        queryStr.append("}");
        System.out.println(queryStr.toString());

        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3032/ds", queryStr.toString());
        Model results = qe.execConstruct();
        Writer writer = new StringWriter();
        results.write(writer, "TURTLE");
        return writer.toString();
    }
}
