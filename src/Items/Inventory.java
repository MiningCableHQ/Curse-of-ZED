package Items;

import java.util.ArrayList;

public class Inventory {
    ArrayList<Item> items;
    int amountOfItems;

    public Inventory(){
        items = new ArrayList<>();
        amountOfItems = 0;
    }
}
