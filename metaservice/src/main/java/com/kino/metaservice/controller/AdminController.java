package com.kino.metaservice.controller;

import com.kino.metaservice.names.MetadataTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class AdminController extends AbstractController {

    public static final String GENERAL_METADATA_PATH = "json/generalMetadata.json";
    public static final String MOVIE_METADATA_PATH = "json/movieMetadata.json";
    public static final String SNACK_METADATA_PATH = "json/snackMetadata.json";

    @GetMapping("/admin")
    public ModelAndView getSnackView() {
        ModelAndView modelAndView = new ModelAndView("admin");
        return modelAndView;
    }

    @GetMapping("/loadMetadata")
    public String getGeneralMetadata(@RequestParam("type") String type) throws IOException {
        Path path = null;
        MetadataTypes metaType = MetadataTypes.get(type);
        switch(metaType) {
            case GENERAL:
                path = Paths.get(GENERAL_METADATA_PATH);
                break;
            case MOVIE:
                path = Paths.get(MOVIE_METADATA_PATH);
                break;
            case SNACK:
                path = Paths.get(SNACK_METADATA_PATH);
                break;
        }
        String content = Files.readString(path, StandardCharsets.UTF_8);
        return content;
    }

    @PostMapping("/saveMetadata")
    public void saveGeneralMetadata(@RequestParam("metadata") String metadata, @RequestParam("type") String type) throws IOException {
        MetadataTypes metaType = MetadataTypes.get(type);
        BufferedWriter writer = null;
        switch(metaType) {
            case GENERAL:
                writer = new BufferedWriter(new FileWriter(GENERAL_METADATA_PATH));
                break;
            case MOVIE:
                writer = new BufferedWriter(new FileWriter(MOVIE_METADATA_PATH));
                break;
            case SNACK:
                writer = new BufferedWriter(new FileWriter(SNACK_METADATA_PATH));
                break;
        }
        writer.write(metadata);
        writer.close();
    }
}