package com.kino.metaservice.controller;

import com.kino.metaservice.services.MetadataService;
import com.kino.metaservice.services.MetadataServiceImpl;

public class AbstractController {

    public final String GENERAL_URL = "http://localhost:3330//";

    MetadataService metadataService = new MetadataServiceImpl();

}


