package shukawam.examples.helidon.se.jdbc;

public class Item {

    private Integer id;
    private String name;
    private Integer price;

    /**
     * Default constructor.
     */
    public Item() {
    }

    /**
     * Create item with id, name and price.
     *
     * @param id    id of the item
     * @param name  name of the item
     * @param price price of the item
     */
    public Item(Integer id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
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
}
