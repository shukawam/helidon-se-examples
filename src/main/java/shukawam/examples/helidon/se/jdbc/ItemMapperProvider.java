package shukawam.examples.helidon.se.jdbc;

import io.helidon.dbclient.DbMapper;
import io.helidon.dbclient.spi.DbMapperProvider;

import javax.annotation.Priority;
import java.util.Optional;

@Priority(1000)
public class ItemMapperProvider implements DbMapperProvider { // ... 1
    private static final ItemMapper MAPPER = new ItemMapper();

    @Override
    public <T> Optional<DbMapper<T>> mapper(Class<T> aClass) {
        return aClass.equals(Item.class) ? Optional.of((DbMapper<T>) MAPPER) : Optional.empty(); // ... 2
    }
}
