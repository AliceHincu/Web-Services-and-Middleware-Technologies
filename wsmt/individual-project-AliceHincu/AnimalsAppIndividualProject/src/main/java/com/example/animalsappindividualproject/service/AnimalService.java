package com.example.animalsappindividualproject.service;

import com.example.animalsappindividualproject.dtos.AnimalDTO;
import com.example.animalsappindividualproject.exceptions.ResourceNotFoundException;
import com.example.animalsappindividualproject.exceptions.ValidationException;
import com.example.animalsappindividualproject.models.Animal;
import com.example.animalsappindividualproject.repository.AnimalRepository;
import com.example.animalsappindividualproject.validator.AnimalValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository repository;

    public List<Animal> findAll() {
        return repository.findAll();
    }

    public Animal findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Animal not found"));
    }

    public Animal save(Animal animal) {
        if (!AnimalValidator.isValidAnimal(animal)) {
            throw new ValidationException("Invalid animal data");
        }
        return repository.save(animal);
    }

    public void delete(Long id) {

        repository.deleteById(id);
    }

    public Animal update(Animal animal, Long id) {
        if (!AnimalValidator.isValidAnimal(animal)) {
            throw new ValidationException("Invalid animal data");
        }
        return repository.findById(id)
                         .map(existingAnimal -> {
                             existingAnimal.setName(animal.getName());
                             existingAnimal.setAge(animal.getAge());
                             existingAnimal.setSpecies(animal.getSpecies());
                             existingAnimal.setStatus(animal.getStatus());
                             return repository.save(existingAnimal);
                         }).orElseThrow(() ->
                new ResourceNotFoundException("Animal with ID " + id + " not found")
            );
    }

    public AnimalDTO convertToDTO(Animal animal) {
        AnimalDTO dto = new AnimalDTO();
        dto.setId(animal.getId());
        dto.setAge(animal.getAge());
        dto.setName(animal.getName());
        dto.setSpecies(animal.getSpecies());
        dto.setStatus(animal.getStatus());
        return dto;
    }

    public Animal convertToEntity(AnimalDTO dto) {
        Animal animal = new Animal();
        animal.setId(dto.getId());
        animal.setAge(dto.getAge());
        animal.setName(dto.getName());
        animal.setSpecies(dto.getSpecies());
        animal.setStatus(dto.getStatus());
        return animal;
    }

}

