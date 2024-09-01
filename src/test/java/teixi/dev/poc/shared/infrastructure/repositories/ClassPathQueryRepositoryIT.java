package teixi.dev.poc.shared.infrastructure.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import teixi.dev.poc.shared.domain.exceptions.QueryNotFoundException;
import teixi.dev.poc.shared.domain.models.Query;
import teixi.dev.poc.shared.domain.models.QueryCode;

public class ClassPathQueryRepositoryIT {
    private static final QueryCode SOME_QUERY_CODE = QueryCode.builder()
            .value("someQueryDemo")
            .build();
    private static final QueryCode SOME_NOT_EXIST_QUERY_CODE = QueryCode.builder()
            .value("someQueryNotExist")
            .build();
    private static final String SOME_EXPECTED_QUERY_VALUE = """
            SELECT *
            FROM some_table;""";

    private final ClassPathQueryRepository repository = new ClassPathQueryRepository(new DefaultResourceLoader());

    @Test
    public void whenLoadExistingQueryThenReturnQuery() throws QueryNotFoundException {
        Query result = this.repository.load(SOME_QUERY_CODE);

        Assertions.assertFalse(result.getValue().isEmpty());
        Assertions.assertEquals(SOME_QUERY_CODE, result.getCode());
        Assertions.assertEquals(SOME_EXPECTED_QUERY_VALUE, result.getValue());
    }

    @Test
    public void whenTryLoadQueryAndNotExistThenThrowQueryNotFoundException() {
        Assertions.assertThrows(
                QueryNotFoundException.class,
                () -> this.repository.load(SOME_NOT_EXIST_QUERY_CODE)
        );
    }
}
