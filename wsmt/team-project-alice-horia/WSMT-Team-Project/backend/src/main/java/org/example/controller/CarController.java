package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.example.domain.dto.CarDTO;
import org.example.domain.model.Message;
import org.example.rabbitmq.RabbitMqSender;
import org.example.service.interfaces.ICarService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CarController {

    private RabbitMqSender rabbitMqSender;
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Resource
    private final ICarService carService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CarController(RabbitMqSender rabbitMqSender, ICarService service) {
        this.rabbitMqSender = rabbitMqSender;
        this.carService =  service;
    }

    @Value("${app.message}")
    private String message;

    @GetMapping("/cars")
    public List<CarDTO> getAllCars() throws JsonProcessingException {
/*        List<CarDTO> carDTOS = carService.findAll();
        if (carDTOS != null) {
            return new ResponseEntity<>(carDTOS, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);*/
        String response = rabbitMqSender.send(new Message("READ",""));
        CarDTO[] carDTOS = objectMapper.readValue(response,CarDTO[].class);
        return Arrays.asList(carDTOS);
    }

    @PostMapping("/car")
    public ResponseEntity<?> addCar(@RequestBody CarDTO carDTO){
        try {
            logger.info("S-a efectuat un request pentru a adauga o masina");
            String response = rabbitMqSender.send(new Message("CREATE", objectMapper.writeValueAsString(carDTO)));
            //this.carService.save(carDTO);
            logger.info("Masina adaugata cu succes");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/car/{id}")
    public CarDTO getCarById(@PathVariable Long id) throws JsonProcessingException {
        logger.info("S-a efectuat un request pentru a returna datele unei masin pe baza id-ului");
        CarDTO carDTO = new CarDTO();
        carDTO.setId(id);
        String response = rabbitMqSender.send(new Message("READ", objectMapper.writeValueAsString(carDTO)));
        CarDTO carDTO1 = objectMapper.readValue(response, CarDTO.class);
        return carDTO1;
    }

    @PutMapping("/car/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Long id, @RequestBody CarDTO car) throws JsonProcessingException{
        CarDTO existingCarDTO = carService.getCarById(id);
        if (existingCarDTO == null) {
            return ResponseEntity.notFound().build();
        }
        existingCarDTO.setModel(car.getModel());
        existingCarDTO.setManufacturer(car.getManufacturer());
        existingCarDTO.setProductionYear(car.getProductionYear());
        String response = rabbitMqSender.send(new Message("UPDATE", objectMapper.writeValueAsString(existingCarDTO)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/car/{id}")
    public void deleteCar(@PathVariable Long id){
        try {
            CarDTO existingCar = carService.getCarById(id);
            logger.info("S-a efectuat un request pentru a sterge o masina");
            rabbitMqSender.send(new Message("DELETE", objectMapper.writeValueAsString(existingCar)));
            //carService.deleteCar(id);
            logger.info("Masina stersa cu succes");
        }catch (Exception e){
            logger.info(e.getMessage());
        }
    }
}
