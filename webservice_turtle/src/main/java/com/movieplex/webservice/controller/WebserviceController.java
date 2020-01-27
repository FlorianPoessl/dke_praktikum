package com.movieplex.webservice.controller;

import com.movieplex.webservice.services.FusekiService;
import com.movieplex.webservice.services.FusekiServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
public class WebserviceController {

    FusekiService fusekiService = new FusekiServiceImpl();

    @GetMapping("/movieWebservice")
    public String getMovieData() throws FileNotFoundException {
        fusekiService.checkFusekiServer();
        String rdfxml = fusekiService.getMovieDataset();
        System.out.println(rdfxml);
        return rdfxml;
    }

    @GetMapping("/snackWebservice")
    public String getSnackData() throws FileNotFoundException {
        fusekiService.checkFusekiServer();
        String rdfxml = fusekiService.getSnackDataset();
        System.out.println(rdfxml);
        return rdfxml;
    }

}
