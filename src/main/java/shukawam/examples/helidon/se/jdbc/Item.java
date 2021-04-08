package shukawam.examples.helidon.se.jdbc;

public class Item {
    private Integer id;
    private String name;
    private Integer price;
    private Integer itemTypeCode;

    public Item() {
        // default constructor.
    }

    public Item(Integer id, String name, Integer price, Integer itemTypeCode) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.itemTypeCode = itemTypeCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getItemTypeCode() {
        return itemTypeCode;
    }

    public void setItemTypeCode(Integer itemTypeCode) {
        this.itemTypeCode = itemTypeCode;
    }
}
