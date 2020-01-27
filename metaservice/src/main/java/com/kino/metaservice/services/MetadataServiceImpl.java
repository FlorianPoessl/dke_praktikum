package com.kino.metaservice.services;

import com.google.gson.Gson;
import com.kino.metaservice.controller.AdminController;
import com.kino.metaservice.objects.Metadata;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class MetadataServiceImpl implements MetadataService{

    List<Metadata> movieMetadata;
    List<Metadata> snackMetadata;
    List<Metadata> generalMetadata;
    final Gson gson = new Gson();

    @Override
    public void parseMovieMetadata() throws IOException {
        Path path = Paths.get(AdminController.MOVIE_METADATA_PATH);
        String content = Files.readString(path, StandardCharsets.UTF_8);
        this.movieMetadata = Arrays.asList(gson.fromJson(content, Metadata[].class));
    }

    @Override
    public void parseSnackMetadata() throws IOException {
        Path path = Paths.get(AdminController.SNACK_METADATA_PATH);
        String content = Files.readString(path, StandardCharsets.UTF_8);
        this.snackMetadata = Arrays.asList(gson.fromJson(content, Metadata[].class));
    }

    @Override
    public void parseGeneralMetadata() throws IOException {
        Path path = Paths.get(AdminController.GENERAL_METADATA_PATH);
        String content = Files.readString(path, StandardCharsets.UTF_8);
        this.generalMetadata = Arrays.asList(gson.fromJson(content, Metadata[].class));
    }

    @Override
    public void printMovieMetadata() {
        for(Metadata m : movieMetadata) {
            System.out.println("Attribut: " + m.getAttribute());
            for(String s : m.getValues()) {
                System.out.println("Values: " + s);
            }
            for(String k : m.getSynonyms()) {
                System.out.println("Synonyms: " + k);
            }
        }
    }

    @Override
    public String getGeneralizedSnack(String value) {
        for (Metadata m : this.snackMetadata) {
            if (m.synonymContains(value)) {
                return m.getAttribute();
            }
        }
        return value;
    }

    @Override
    public String getGeneralizedMovie(String value) {
        for (Metadata m : this.movieMetadata) {
            if (m.synonymContains(value)) {
                return m.getAttribute();
            }
        }
        return value;
    }

    @Override
    public String getGeneralizedAttribute(String value) {
        for (Metadata m : this.generalMetadata) {
            if (m.synonymContains(value)) {
                return m.getAttribute();
            }
        }
        return value;
    }
}
