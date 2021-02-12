package shukawam.examples.helidon.se.jdbc;

import io.helidon.common.GenericType;
import io.helidon.dbclient.DbMapper;
import io.helidon.dbclient.spi.DbMapperProvider;

import java.util.Optional;

public class ItemMapperProvider implements DbMapperProvider {

    private static final ItemMapper MAPPER = new ItemMapper();

    @Override
    public <T> Optional<DbMapper<T>> mapper(Class<T> aClass) {
        return aClass.equals(Item.class) ? Optional.of((DbMapper<T>) MAPPER) : Optional.empty();
    }
}
