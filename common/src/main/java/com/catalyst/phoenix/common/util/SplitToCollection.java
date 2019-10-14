package com.catalyst.phoenix.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SplitToCollection {
    private String tokenizer = ";";
    private List<String> row;

    public SplitToCollection(String line) {
        this.row = new ArrayList<>(Arrays.asList(line.split(tokenizer)));
    }

    public Map<?, ?> toMap(List<String> keys) {
        List<List<?>> attributes = this.zip(keys);

        return attributes.stream()
                .collect(Collectors.toMap(el -> el.get(0), el -> el.get(1)));
    }

    private List<List<?>> zip(List<String> keys) {
        List<List<?>> els = new ArrayList<>();

        for(int i = 0; i < keys.size(); i++) {
            els.add(new ArrayList<>(Arrays.asList(keys.get(i), row.get(i))));
        }

        return els;
    }
}
