package com.kino.metaservice.names;

import java.util.HashMap;
import java.util.Map;

public enum MetadataTypes {
    GENERAL("general"), MOVIE("movie"), SNACK("snack");

    private String value;

    MetadataTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<String, MetadataTypes> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static
    {
        for(MetadataTypes meta : MetadataTypes.values())
        {
            lookup.put(meta.getValue(), meta);
        }
    }

    //This method can be used for reverse lookup purpose
    public static MetadataTypes get(String value)
    {
        return lookup.get(value);
    }
}
