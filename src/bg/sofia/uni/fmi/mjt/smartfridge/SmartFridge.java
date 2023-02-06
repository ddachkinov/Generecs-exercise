package bg.sofia.uni.fmi.mjt.smartfridge;

import bg.sofia.uni.fmi.mjt.smartfridge.exception.FridgeCapacityExceededException;
import bg.sofia.uni.fmi.mjt.smartfridge.exception.InsufficientQuantityException;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.Ingredient;
import bg.sofia.uni.fmi.mjt.smartfridge.recipe.Recipe;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.StorableByExpirationComparator;

import java.util.*;

public class SmartFridge implements SmartFridgeAPI{

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
        for (int i=0; i < quantity; i++){
            storage.get(item.getName()).add(item);
        }

        currentCapacity += quantity;
    }

    @Override
    public List<? extends Storable> retrieve(String itemName) {
        return null;
    }

    @Override
    public List<? extends Storable> retrieve(String itemName, int quantity) throws InsufficientQuantityException {
        return null;
    }

    @Override
    public int getQuantityOfItem(String itemName) {
        return 0;
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
