---
title: Url Query Parser, Java
author: Kenny Cason
tags: url, query, java
---

This is just a light-weight Url query parser written in Java. My main goal was to just have a cleaner, easier to use interface for working with Url query parameters.

UrlQuery.java
```{.java .numberLines startFrom="1"}
package web.url;

import ch.lambdaj.Lambda;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONWriter;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kenny on 4/21/14.
 */
public class UrlQuery {

    private final static Logger LOGGER = Logger.getLogger(UrlQuery.class);

    private final Map<String, List<String>> query = new HashMap<>();

    public void put(String key, String value) {
        if(!containsKey(key)) {
            query.put(key, new LinkedList<String>());
        }
        query.get(key).add(value);
    }

    public void put(String key, List<String> values) {
        if(!containsKey(key)) {
            query.put(key, new LinkedList<String>());
        }
        query.get(key).addAll(values);
    }

    public boolean isEmpty() {
        return query.isEmpty();
    }

    public boolean containsKey(String key) {
        return query.containsKey(key);
    }

    public List<String> getValues(String key) {
        return query.get(key);
    }

    public String getValue(String key) {
        if(containsKey(key)) {
            return query.get(key).get(0);
        }
        return null;
    }

    public Set<Map.Entry<String, List<String>>> entrySet() {
        return query.entrySet();
    }

    @Override
    public String toString() {
        return UrlQuery.toString(this);
    }

    public String toJson() {
        return UrlQuery.toJson(this);
    }

    public static UrlQuery fromUrl(final URL url) {
        return fromQuery(url.getQuery(), "UTF-8");
    }

    public static UrlQuery fromUri(final URI url) {
        return fromQuery(url.getQuery(), "UTF-8");
    }

    public static UrlQuery fromQuery(final String query, final String encoding) {
        final UrlQuery urlQuery = new UrlQuery();
        if(StringUtils.isBlank(query)) { return urlQuery; }
        try {
            final String trimmedQuery = query.replaceAll("^\\?", "").trim();
            for (String param : trimmedQuery.split("&")) {
                String pair[] = param.split("=");
                if (pair.length > 1) {
                    String key = URLDecoder.decode(pair[0], encoding);
                    String value = URLDecoder.decode(pair[1], encoding);
                    urlQuery.put(key, value);
                }
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return urlQuery;
    }

    public static String toJson(final UrlQuery urlQuery) throws JSONException {
        final StringWriter sw = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(sw).object();

        for(Map.Entry<String, List<String>> entrySet : urlQuery.entrySet()) {
            if(entrySet.getValue().isEmpty()) { continue; }

            jsonWriter.key(entrySet.getKey());
            if(entrySet.getValue().size() == 1) {
                jsonWriter.value(entrySet.getValue().get(0));
            } else {
                jsonWriter.value(entrySet.getValue());
            }
        }
        jsonWriter .endObject();
        return sw.toString();
    }

    public static String toString(final UrlQuery params) {
        if(params == null || params.isEmpty()) { return ""; }

        final List<String> paramPairs = new LinkedList<>();
        for(Map.Entry<String, List<String>> entry : params.entrySet()) {
            final List<String> values = entry.getValue();
            if(values.size() == 1) {
                paramPairs.add(entry.getKey() + "=" + values.get(0));
            } else {
                for(String value : values) {
                    paramPairs.add(entry.getKey() + "[]=" + value);
                }
            }
        }
        return "?" + Lambda.join(paramPairs, "&");
    }

}

```

TestUrlQuery.java
```{.java .numberLines startFrom="1"}
package com.datarank.krunch.web.url;

import com.datarank.krunch.lib.web.url.UrlQuery;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class TestUrlQuery {

    @Test
    public void buildQueryStringTest() {
        UrlQuery urlQuery = new UrlQuery();

        assertEquals("", urlQuery.toString());

        urlQuery.put("a", "1");
        urlQuery.put("b", "2");

        boolean match = "?a=1&b=2".equals(urlQuery.toString());
        boolean match2 = "?b=2&a=1".equals(urlQuery.toString());
        Assert.assertTrue(match | match2);
    }

    @Test
    public void buildJsonAndQueryString() {
        UrlQuery urlQuery = new UrlQuery();
        urlQuery.put("foo", "bar");
        urlQuery.put("animal", "dog");
        urlQuery.put("animal", "cat");

        assertEquals("?foo=bar&animal[]=dog&animal[]=cat", urlQuery.toString());
        assertEquals("{\"foo\":\"bar\",\"animal\":[\"dog\",\"cat\"]}", urlQuery.toJson());
    }

    /**
     * http://stackoverflow.com/questions/2632175/java-decoding-uri-query-string
     * URIs treat the + symbol as it is, whereas spaces are encoded into %20.
     * URLDecoder is not compatible with URI encoded strings as it will decode both + and %20 into a space.
     */
    @Test
    public void urlVsUriEncodingTest() throws URISyntaxException, MalformedURLException {
        UrlQuery urlQuery = UrlQuery.fromUri(new URI("resource://path?query=A%2BB"));
        assertEquals("A B", urlQuery.getValue("query"));

        urlQuery = UrlQuery.fromUri(new URI("resource://path?query=A+B"));
        assertEquals("A B", urlQuery.getValue("query"));

        urlQuery = UrlQuery.fromUrl(new URL("http://google.com?query=A%2BB"));
        assertEquals("A+B", urlQuery.getValue("query"));

        urlQuery = UrlQuery.fromUrl(new URL("http://google.com?query=A+B"));
        assertEquals("A B", urlQuery.getValue("query"));
    }

}

```