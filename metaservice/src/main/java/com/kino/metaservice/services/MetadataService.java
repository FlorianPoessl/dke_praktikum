package com.kino.metaservice.services;

import java.io.IOException;

public interface MetadataService {

    public void parseMovieMetadata() throws IOException;

    public void parseSnackMetadata() throws IOException;

    public void parseGeneralMetadata() throws IOException;

    public void printMovieMetadata();

    public String getGeneralizedSnack(String value);

    public String getGeneralizedMovie(String value);

    public String getGeneralizedAttribute(String value);
}
