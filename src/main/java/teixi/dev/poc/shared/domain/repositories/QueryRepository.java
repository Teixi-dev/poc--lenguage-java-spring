package teixi.dev.poc.shared.domain.repositories;

import teixi.dev.poc.shared.domain.exceptions.QueryNotFoundException;
import teixi.dev.poc.shared.domain.models.Query;
import teixi.dev.poc.shared.domain.models.QueryCode;

public interface QueryRepository {
    public Query load(QueryCode code) throws QueryNotFoundException;
}
