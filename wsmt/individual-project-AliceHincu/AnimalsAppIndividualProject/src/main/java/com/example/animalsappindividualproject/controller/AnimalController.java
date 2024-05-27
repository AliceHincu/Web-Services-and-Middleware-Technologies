package com.example.animalsappindividualproject.controller;

import com.example.animalsappindividualproject.dtos.AnimalDTO;
import com.example.animalsappindividualproject.exceptions.ResourceNotFoundException;
import com.example.animalsappindividualproject.exceptions.ValidationException;
import com.example.animalsappindividualproject.models.Animal;
import com.example.animalsappindividualproject.service.AnimalService;
import com.example.animalsappindividualproject.validator.AnimalValidator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private AnimalService service;

    @GetMapping
    public ResponseEntity<?> getAllAnimals() {
        try {
            List<Animal> animals = service.findAll();
            List<AnimalDTO> animalDTOs = animals.stream()
                                                .map(service::convertToDTO)
                                                .toList();
            return ResponseEntity.ok(animalDTOs);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while getting all the animals.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnimalById(@PathVariable Long id) {
        try {
            Animal animal = service.findById(id);
            AnimalDTO animalDTO = service.convertToDTO(animal);
            return ResponseEntity.ok(animalDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while getting the animal.");
        }
    }

    @PostMapping
    public ResponseEntity<?> createAnimal(@RequestBody AnimalDTO animalDTO) {
        try {
            Animal animal = service.convertToEntity(animalDTO);
            Animal savedAnimal = service.save(animal);
            AnimalDTO savedAnimalDTO = service.convertToDTO(savedAnimal);
            return new ResponseEntity<>(savedAnimalDTO, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while creating the animal.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnimal(@RequestBody AnimalDTO animalDTO, @PathVariable Long id) {
        try {
            Animal animal = service.convertToEntity(animalDTO);
            Animal updatedAnimal = service.update(animal, id);
            AnimalDTO updatedAnimalDTO = service.convertToDTO(updatedAnimal);
            return ResponseEntity.ok(updatedAnimalDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while updating the animal.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnimal(@PathVariable Long id) {
        try {
            service.findById(id);
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while deleting the animal.");
        }
    }
}
