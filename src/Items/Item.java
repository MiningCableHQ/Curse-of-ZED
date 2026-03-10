package Items;

public abstract class Item {
    protected String name;
    protected double tier;
    protected String description;

    public abstract <T> void useItem(T Entity);
}
