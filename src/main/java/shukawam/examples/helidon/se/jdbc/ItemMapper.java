package shukawam.examples.helidon.se.jdbc;

import io.helidon.dbclient.DbColumn;
import io.helidon.dbclient.DbMapper;
import io.helidon.dbclient.DbRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemMapper implements DbMapper<Item> {

    @Override
    public Item read(DbRow dbRow) {
        DbColumn id = dbRow.column("ID");
        DbColumn name = dbRow.column("NAME");
        DbColumn price = dbRow.column("PRICE");
        DbColumn itemTypeCode = dbRow.column("ITEM_TYPE_CODE");
        return new Item(id.as(Integer.class), name.as(String.class), price.as(Integer.class), itemTypeCode.as(Integer.class));
    }

    @Override
    public Map<String, Object> toNamedParameters(Item item) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", item.getId());
        map.put("name", item.getName());
        map.put("price", item.getPrice());
        map.put("itemTypeCode", item.getItemTypeCode());
        return map;
    }

    @Override
    public List<Object> toIndexedParameters(Item item) {
        List<Object> list = new ArrayList<>();
        list.add(item.getId());
        list.add(item.getName());
        list.add(item.getPrice());
        list.add(item.getItemTypeCode());
        return list;
    }
}
