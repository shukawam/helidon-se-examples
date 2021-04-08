package shukawam.examples.helidon.se.jdbc;

public class ItemType {
    public Integer code;
    public String name;

    public ItemType() {
        // default constructor.
    }

    public ItemType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
