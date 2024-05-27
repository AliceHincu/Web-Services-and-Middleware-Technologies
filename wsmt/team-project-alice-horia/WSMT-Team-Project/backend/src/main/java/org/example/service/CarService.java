package org.example.service;

import jakarta.annotation.Resource;
import org.example.domain.dto.CarDTO;
import org.example.domain.model.Car;
import org.example.repository.ICarRepository;
import org.example.service.interfaces.ICarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService implements ICarService {

    @Resource
    private final ICarRepository carRepository;

    public CarService(ICarRepository carRepository) {this.carRepository = carRepository;}

    @Override
    public List<CarDTO> findAll(){
        List<Car> cars = carRepository.findAll();
        List<CarDTO> carDTOs = new ArrayList<>();

        for (Car car : cars){
            CarDTO carDTO = new CarDTO();
            carDTO.setId(car.getId());
            carDTO.setModel(car.getModel());
            carDTO.setManufacturer(car.getManufacturer());
            carDTO.setProductionYear(car.getProductionYear());
            carDTOs.add(carDTO);
        }
        return carDTOs;
    }


    public void saveCar(CarDTO carDTO){
        Car car = new Car();
        car.setId(carDTO.getId());
        car.setModel(carDTO.getModel());
        car.setManufacturer(carDTO.getManufacturer());
        car.setProductionYear(carDTO.getProductionYear());
        this.carRepository.save(car);
        //return ResponseEntity.status(HttpStatus.OK).body(CarDTO);
    }

    public CarDTO getCarById(Long id){
        Car car =  carRepository.findById(id).orElse(null);
        CarDTO carDTO= new CarDTO();
        carDTO.setId(car.getId());
        carDTO.setModel(car.getModel());
        carDTO.setManufacturer(car.getManufacturer());
        carDTO.setProductionYear(car.getProductionYear());
        return carDTO;
    }

    public CarDTO updateCar(CarDTO carDTO){
        this.saveCar(carDTO);
        return carDTO;
    }

    public void deleteCar(Long id){
        carRepository.deleteById(id);
    }
}
