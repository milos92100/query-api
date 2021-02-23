package org.queryapi.request;

public class Query {
    private String query;

    public String getQuery() {
        return query.replace("\n", "").replace("\r", "");
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
