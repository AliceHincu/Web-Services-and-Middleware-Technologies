package org.example.service.interfaces;


import org.example.domain.dto.CarDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICarService {
    List<CarDTO> findAll();
    void saveCar(CarDTO carDTO);
    void deleteCar(Long id);
    CarDTO getCarById(Long id);
    CarDTO updateCar(CarDTO carDTO);
}
