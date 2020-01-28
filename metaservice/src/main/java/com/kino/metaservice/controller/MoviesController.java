package com.kino.metaservice.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kino.metaservice.fuseki.FusekiQueryExecution;
import com.kino.metaservice.services.MetadataService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFReader;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

@RestController
public class MoviesController extends AbstractController {

    private final String MOVIE_URL = "http://localhost:3330/movie/";
    private final String INPUT_DATE = "yyyy-mm-dd";
    private final String OUTPUT_DATE = "dd-mm-yyyy";

    @GetMapping("/movies")
    public ModelAndView getMovieView() throws IOException {
        metadataService.parseMovieMetadata();
        metadataService.parseGeneralMetadata();
        this.readFirstWebservice();
        this.readSecondWebservice();
        ModelAndView modelAndView = new ModelAndView("movies");
        return modelAndView;
    }

    private void readSecondWebservice() throws IOException {
        final URL url = new URL("http://localhost:8082/movieWebservice");
        final URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        urlConnection.connect();
        final InputStream inputStream = urlConnection.getInputStream();

        Model model = ModelFactory.createDefaultModel();
        //RDFReader arp = model.getReader();
        //arp.setProperty("WARN_UNQUALIFIED_RDF_ATTRIBUTE", "EM_IGNORE");
        //arp.read(model, inputStream, null);
        model.read(inputStream, null, "TTL");
        inputStream.close();

        Graph graph = model.getGraph();
        ExtendedIterator<Triple> tripleIterator = graph.find();

        ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
        Map<String, String> prefixMap = new HashMap<>();
        prefixMap.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        queryStr.setNsPrefixes(prefixMap);
        queryStr.append("INSERT DATA\n");
        queryStr.append("{\n");
        for (ExtendedIterator<Triple> it = tripleIterator; it.hasNext(); ) {
            Triple triple = it.next();
            String movie = metadataService.getGeneralizedMovie(triple.getSubject().getLocalName());
            String attribute = metadataService.getGeneralizedAttribute(triple.getPredicate().getLocalName());
            queryStr.append("<" + MOVIE_URL + movie + ">" + "<" + GENERAL_URL + attribute + ">" + triple.getObject().toString() + " ." + "\n");
        }
        queryStr.append("}");
        System.out.println("Query for FusekiServer:\n" + queryStr.toString());
        System.out.println("Query End-------------------------------");
        FusekiQueryExecution.executeUpdateWebserviceTwo(queryStr.toString());
    }

    private void readFirstWebservice() throws IOException {
        final URL url = new URL("http://localhost:8083/movieWebservice");
        final URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        urlConnection.connect();
        final InputStream inputStream = urlConnection.getInputStream();

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
        prefixMap.put("movie", "http://localhost:3030//movie/");
        queryStr.setNsPrefixes(prefixMap);
        queryStr.append("INSERT DATA\n");
        queryStr.append("{\n");
        for (ExtendedIterator<Triple> it = tripleIterator; it.hasNext(); ) {
            Triple triple = it.next();
            String movie = metadataService.getGeneralizedMovie(triple.getSubject().getLocalName());
            String attribute = metadataService.getGeneralizedAttribute(triple.getPredicate().getLocalName());
            queryStr.append("<" + MOVIE_URL + movie + ">" + "<" + GENERAL_URL + attribute + ">" + triple.getObject().toString() + " ." + "\n");
        }

        queryStr.append("}");
        System.out.println("Query for FusekiServer:\n" + queryStr.toString());
        System.out.println("Query End-------------------------------");
        FusekiQueryExecution.executeUpdateWebserviceOne(queryStr.toString());
    }

    @GetMapping("/searchMovies")
    public String getSearchedMovies(@RequestParam("movieName") String movieName, @RequestParam("movieDate") String movieDate, @RequestParam("movieTime") String movieTime) throws ParseException {
        String metaName = "";
        if(StringUtils.isNotEmpty(movieName)) {
            metaName = metadataService.getGeneralizedMovie(movieName);
        }
        ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
        Map<String, String> prefixMap = new HashMap<>();
        prefixMap.put("movie", "http://localhost:3330//");
        queryStr.setNsPrefixes(prefixMap);
        queryStr.append("SELECT ?title ?price ?date ?timestart ?cinema ?timeend\n");
        queryStr.append("WHERE {\n");
        queryStr.append("?movie movie:Titel ?title.");
        queryStr.append("?movie movie:Preis ?price.");
        queryStr.append("?movie movie:Datum ?date.");
        queryStr.append("?movie movie:Startzeit ?timestart.");
        queryStr.append("?movie movie:Endzeit ?timeend.");
        queryStr.append("?movie movie:Kino ?cinema.");
        if(StringUtils.isNotEmpty(movieTime)) {
            queryStr.append("FILTER (?timestart >= \"" + movieTime + "\").");
        }
        if(StringUtils.isNotEmpty(movieDate)) {
            Date date = new SimpleDateFormat(INPUT_DATE).parse(movieDate);
            String movieDateFormatted = new SimpleDateFormat(OUTPUT_DATE).format(date);
            queryStr.append("FILTER (?date >= \"" + movieDateFormatted + "\").");
        }
        queryStr.append("FILTER (contains(str(?movie), \"" + metaName + "\")).");

        queryStr.append("}");

        String query = queryStr.toString();
        System.out.println(query);
        List<QuerySolution> solutionsOne = FusekiQueryExecution.executeQueryWebserviceOne(query);
        List<QuerySolution> solutionsTwo = FusekiQueryExecution.executeQueryWebserviceTwo(query);
        JsonArray jsonArray = new JsonArray();
        for(QuerySolution qs : solutionsOne) {
            JsonObject jsonObject = new JsonObject();
            Iterator<String> iterator = qs.varNames();
            while(iterator.hasNext()) {
                String var = iterator.next();
                jsonObject.addProperty(var, qs.getLiteral(var).getString());
            }
            jsonArray.add(jsonObject);
        }
        for(QuerySolution qs : solutionsTwo) {
            JsonObject jsonObject = new JsonObject();
            Iterator<String> iterator = qs.varNames();
            while(iterator.hasNext()) {
                String var = iterator.next();
                jsonObject.addProperty(var, qs.getLiteral(var).getString());
            }
            jsonArray.add(jsonObject);
        }
        System.out.println(jsonArray.toString());
        return jsonArray.toString();
    }

}