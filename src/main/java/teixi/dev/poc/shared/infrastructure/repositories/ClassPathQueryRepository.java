package teixi.dev.poc.shared.infrastructure.repositories;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import teixi.dev.poc.shared.domain.exceptions.QueryNotFoundException;
import teixi.dev.poc.shared.domain.models.Query;
import teixi.dev.poc.shared.domain.models.QueryCode;
import teixi.dev.poc.shared.domain.repositories.QueryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Repository
public class ClassPathQueryRepository implements QueryRepository {
    private static final String CLASS_PATH = "classpath:database/queries/";
    private static final String FILE_EXTENSION = ".sql";

    private final ResourceLoader resourceLoader;

    public ClassPathQueryRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Query load(QueryCode code) throws QueryNotFoundException {
        try {
            String queryPath = CLASS_PATH + code.getValue() + FILE_EXTENSION;

            return Query.builder()
                    .value(new String(Files.readAllBytes(Paths.get(
                            resourceLoader.getResource(queryPath).getURI()
                    ))))
                    .build();
        } catch (IOException e) {
            throw new QueryNotFoundException(code);
        }
    }
}
