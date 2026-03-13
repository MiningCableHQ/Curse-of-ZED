package Items;

public abstract class Item {
    protected String name;
    protected double tier;
    protected String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract <T> void useItem(T Entity);

    //getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
