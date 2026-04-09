package Items;

import Entities.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private ArrayList<Item> items;
    private Map<Item, Integer> quantityMap;
    private int totalQuantity;

    public Inventory() {
        items = new ArrayList<>();
        quantityMap = new HashMap<>();
        totalQuantity = 0;
    }

    // Add a single item
    public void addItem(Item item) {
        addItem(item, 1);
    }

    // Add multiple copies of an item
    public void addItem(Item item, int quantity) {
        if (quantity <= 0) return;

        if (quantityMap.containsKey(item)) {
            quantityMap.put(item, quantityMap.get(item) + quantity);
        } else {
            items.add(item);
            quantityMap.put(item, quantity);
        }
        totalQuantity += quantity;
        item.quantity = quantityMap.get(item);
    }

    // Remove multiple copies of an item
    public void removeItem(Item item, int quantity) {
        if (quantity <= 0) return;

        if (quantityMap.containsKey(item)) {
            int currentQty = quantityMap.get(item);
            if (currentQty <= quantity) {
                // Remove the item completely
                quantityMap.remove(item);
                items.remove(item);
                totalQuantity -= currentQty;
                item.quantity = 0;
            } else {
                // Reduce quantity
                quantityMap.put(item, currentQty - quantity);
                totalQuantity -= quantity;
                item.quantity = currentQty - quantity;
            }
        }
    }

    // Remove a single item (legacy method)
    public void remove(Item item) {
        removeItem(item, 1);
    }

    // Check if inventory contains at least one of the item
    public boolean hasItem(Item item) {
        return quantityMap.containsKey(item) && quantityMap.get(item) > 0;
    }

    // Get quantity of a specific item
    public int getQuantity(Item item) {
        return quantityMap.getOrDefault(item, 0);
    }

    // Use an item on a target entity
    public void useItem(Item item, Entity target) {
        if (!hasItem(item)) {
            System.out.println("You don't have any " + item.getName() + "!");
            return;
        }

        // Apply item effect
        item.useItem(target);

        // Display use message
        if (item.getUseMessage() != null) {
            System.out.println(item.getUseMessage());
        }

        // Remove one copy after use
        removeItem(item, 1);
    }

    // Get all items (returns list of unique items, not copies)
    public ArrayList<Item> getItems() {
        return items;
    }

    // Get total number of items (counting stacks)
    public int getTotalQuantity() {
        return totalQuantity;
    }

    // Clear all items from inventory
    public void clear() {
        items.clear();
        quantityMap.clear();
        totalQuantity = 0;
    }

    // Check if inventory is empty
    public boolean isEmpty() {
        return totalQuantity == 0;
    }
}