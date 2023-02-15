package bg.sofia.uni.fmi.mjt.smartfridge;

import bg.sofia.uni.fmi.mjt.smartfridge.storable.Food;

import java.time.LocalDate;

public class TestSmartFridge {
    public static void main(String[] args) {
        Food yogurt = new Food("Bozhentsi", LocalDate.of(2023, 03, 03));
    }
}
