package bg.sofia.uni.fmi.mjt.smartfridge;

import bg.sofia.uni.fmi.mjt.smartfridge.exception.FridgeCapacityExceededException;
import bg.sofia.uni.fmi.mjt.smartfridge.exception.InsufficientQuantityException;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.Ingredient;
import bg.sofia.uni.fmi.mjt.smartfridge.recipe.Recipe;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.StorableByExpirationComparator;

import java.util.*;

public class SmartFridge implements SmartFridgeAPI {

    private final int totalCapacity;
    private int currentCapacity;

    private final Map<String, Queue<Storable>> storage = new HashMap<>();

    public SmartFridge(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    @Override
    public <E extends Storable> void store(E item, int quantity) throws FridgeCapacityExceededException {

        if (item == null || quantity < 0) {
            throw new IllegalArgumentException("the quantity is not positive");
        }

        if (currentCapacity + quantity > totalCapacity) {
            throw new FridgeCapacityExceededException("There is no room in the fridge left");
        }

        storage.putIfAbsent(item.getName(), new PriorityQueue<>(new StorableByExpirationComparator()));
        for (int i = 0; i < quantity; i++) {
            storage.get(item.getName()).add(item);
        }

        currentCapacity += quantity;
    }

    @Override
    public List<? extends Storable> retrieve(String itemName) {

        if ((itemName == null) || itemName.isEmpty() || itemName.isBlank()) {
            throw new IllegalArgumentException("Not an item");
        }

        if (!storage.containsKey(itemName)) {
            return new LinkedList<>();

        }
        List<Storable> retrieved = new LinkedList<>(storage.get(itemName));
        currentCapacity -= retrieved.size();
        storage.remove(itemName);
        return retrieved;

    }

    @Override
    public List<? extends Storable> retrieve(String itemName, int quantity) throws InsufficientQuantityException {

        if ((itemName == null) || itemName.isEmpty() || itemName.isBlank()) {
            throw new IllegalArgumentException("Not an item");
        }

        if (!storage.containsKey(itemName) || storage.get(itemName).size() < quantity)
            throw new InsufficientQuantityException("Not enough " + itemName + " in the fridge.");

        List<Storable> retrievedWithQuantity = new LinkedList<>();
        Queue<Storable> foods = storage.get(itemName);

        for (int i = 0; i < quantity; i++) {
            retrievedWithQuantity.add(foods.poll());
        }
        currentCapacity -= quantity;

        return retrievedWithQuantity;

    }

    @Override
    public int getQuantityOfItem(String itemName) {
        if(itemName == null || itemName.isEmpty() || itemName.isBlank()){
            throw new IllegalArgumentException("Not enough " + itemName + " in the Fridge");
        }

        Queue<Storable> foods = storage.get(itemName);

        return foods == null ? 0 : foods.size();
    }

    @Override
    public Iterator<Ingredient<? extends Storable>> getMissingIngredientsFromRecipe(Recipe recipe) {
        return null;
    }

    @Override
    public List<? extends Storable> removeExpired() {
        return null;
    }
}
