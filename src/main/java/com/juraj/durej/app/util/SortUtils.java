package com.juraj.durej.app.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SortUtils {

    public static Pageable getPage(@Value("{}") String filterStr, @Value("[0,9]") String rangeStr, @Value("[\"id\",\"ASC\"]") String sortStr) {

        Pageable sorted = null;

        try {
            JSONArray sortParams = new JSONArray(sortStr);
            JSONArray rangeParams = new JSONArray(rangeStr);

            if(sortParams.getString(1).equals("ASC")) {
                sorted = PageRequest.of(rangeParams.getInt(0), rangeParams.getInt(1),
                        Sort.by(sortParams.getString(0)));
            } else {
                sorted = PageRequest.of(rangeParams.getInt(0), rangeParams.getInt(1),
                        Sort.by(sortParams.getString(0)).descending());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return sorted;
    }
}
