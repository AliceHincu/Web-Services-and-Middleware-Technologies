package com.example.animalsappindividualproject.dtos;

import lombok.Data;

@Data
public class AnimalDTO {
    private Long id;
    private String name;
    private Integer age;
    private String species;
    private String status;
}
