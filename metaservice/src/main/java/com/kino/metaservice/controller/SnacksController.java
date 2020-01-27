package com.kino.metaservice.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kino.metaservice.fuseki.FusekiQueryExecution;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFReader;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class SnacksController extends AbstractController {

    private final String SNACK_URL = "http://localhost:3330/snack/";

    @GetMapping("/snacks")
    public ModelAndView getSnackView() throws IOException {
        metadataService.parseSnackMetadata();
        metadataService.parseGeneralMetadata();

        final URL url = new URL("http://localhost:8083/snackWebservice");
        final URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        urlConnection.connect();

        final InputStream inputStream = urlConnection.getInputStream();

        boolean output = false;

        if(output) {
            //creating an InputStreamReader object
            InputStreamReader isReader = new InputStreamReader(inputStream);
            //Creating a BufferedReader object
            BufferedReader reader = new BufferedReader(isReader);
            StringBuffer sb = new StringBuffer();
            String str;
            while((str = reader.readLine())!= null){
                sb.append(str);
            }
            System.out.println(sb.toString());
        } else {
            Model model = ModelFactory.createDefaultModel();
            RDFReader arp = model.getReader();
            arp.setProperty("WARN_UNQUALIFIED_RDF_ATTRIBUTE", "EM_IGNORE");
            arp.read(model, inputStream, null);
            inputStream.close();

            Graph graph = model.getGraph();
            ExtendedIterator<Triple> tripleIterator = graph.find();

            ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
            Map<String, String> prefixMap = new HashMap<>();
            prefixMap.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
            prefixMap.put("snack", "http://localhost:3030//snack");
            queryStr.setNsPrefixes(prefixMap);
            queryStr.append("INSERT DATA\n");
            queryStr.append("{\n");
            for (ExtendedIterator<Triple> it = tripleIterator; it.hasNext(); ) {
                Triple triple = it.next();
                System.out.println(triple.getSubject().getLocalName());
                System.out.println(triple.getPredicate().getLocalName());
                String snack = metadataService.getGeneralizedSnack(triple.getSubject().getLocalName());
                String attribute = metadataService.getGeneralizedAttribute(triple.getPredicate().getLocalName());
                queryStr.append("<" + SNACK_URL + snack + ">" + "<" + GENERAL_URL + attribute + ">" + triple.getObject().toString() + " ." + "\n");
            }
            queryStr.append("}");
            System.out.println("Query for FusekiServer:\n" + queryStr.toString());
            System.out.println("Query End-------------------------------");
            FusekiQueryExecution.executeUpdate(queryStr.toString());
        }

        ModelAndView modelAndView = new ModelAndView("snacks");
        return modelAndView;
    }

    @GetMapping("/searchSnacks")
    public String getSearchedMovies(@RequestParam("snackName") String snackName) {
        String metaName = "";
        if(StringUtils.isNotEmpty(snackName)) {
            metaName = metadataService.getGeneralizedSnack(snackName);
        }

        ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
        Map<String, String> prefixMap = new HashMap<>();
        prefixMap.put("snack", "http://localhost:3330//");
        queryStr.setNsPrefixes(prefixMap);
        queryStr.append("SELECT ?name ?preis ?groesse ?cinema\n");
        queryStr.append("WHERE {\n");
        queryStr.append("?snack snack:Snackname ?name.");
        queryStr.append("?snack snack:Snackpreis ?preis.");
        queryStr.append("?snack snack:Snackgroesse ?groesse.");
        queryStr.append("?snack snack:snackcinema ?cinema.");
        queryStr.append("FILTER (contains(str(?snack), \"" + metaName + "\")).");
        queryStr.append("}");

        String query = queryStr.toString();
        System.out.println(query);
        List<QuerySolution> solutions = FusekiQueryExecution.executeQuery(query);
        JsonArray jsonArray = new JsonArray();
        for(QuerySolution qs : solutions) {
            JsonObject jsonObject = new JsonObject();
            Iterator<String> iterator = qs.varNames();
            while(iterator.hasNext()) {
                String var = iterator.next();
                System.out.println(var);
                System.out.println(qs.getLiteral(var).getString());
                jsonObject.addProperty(var, qs.getLiteral(var).getString());
            }
            System.out.println(jsonObject.toString());
            jsonArray.add(jsonObject);
            System.out.println(jsonArray.toString());
        }
        System.out.println(jsonArray.toString());
        return jsonArray.toString();
    }
}