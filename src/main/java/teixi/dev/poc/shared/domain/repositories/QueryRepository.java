package teixi.dev.poc.shared.domain.repositories;

import teixi.dev.poc.shared.domain.models.Query;

import java.io.IOException;

public interface QueryRepository {
    public Query load(String queryName) throws IOException;
}
