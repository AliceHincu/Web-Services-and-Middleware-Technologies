package com.example.animalsappindividualproject.validator;

import com.example.animalsappindividualproject.models.Animal;

public class AnimalValidator {

    public static boolean isValidAnimal(Animal animal) {
        return isValidInput(animal.getName()) && animal.getAge() >= 0 && isValidInput(animal.getSpecies())
            && isValidInput(animal.getStatus());
    }

    private static boolean isValidInput(String text) {
        return text != null && !text.trim().isEmpty();
    }
}
