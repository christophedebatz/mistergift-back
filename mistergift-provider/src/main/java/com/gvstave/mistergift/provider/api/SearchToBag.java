package com.gvstave.mistergift.provider.api;

import com.gvstave.mistergift.provider.common.ParameterBag;

import java.util.function.Function;

/**
 * Created by christophedebatz on 24/04/2016.
 */
public class SearchToBag implements Function<SearchBuilder.Search, ParameterBag> {


    /**
     * Applies this function to the given argument.
     *
     * @param search the function argument
     * @return the function result
     */
    @Override
    public ParameterBag apply(SearchBuilder.Search search) {
        return null;
    }
}
