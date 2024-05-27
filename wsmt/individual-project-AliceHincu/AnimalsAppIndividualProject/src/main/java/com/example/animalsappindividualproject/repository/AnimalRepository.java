package com.example.animalsappindividualproject.repository;

import com.example.animalsappindividualproject.models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {}
