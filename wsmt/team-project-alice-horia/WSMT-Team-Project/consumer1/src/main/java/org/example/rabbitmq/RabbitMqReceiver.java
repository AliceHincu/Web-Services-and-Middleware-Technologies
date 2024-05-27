package org.example.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dto.CarDTO;
import org.example.domain.model.Message;
import org.example.service.CarService;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RabbitListener(queues = "${spring.rabbitmq.queue}")
public class RabbitMqReceiver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final CarService carService;

    @Autowired
    public RabbitMqReceiver(CarService carService) {
        this.carService = carService;
    }

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);

    @RabbitHandler
    public String receivedMessage(Message message) throws JsonProcessingException {
        logger.info("Song Details Received By Consumer is.. " + message);

        String messagePrefix = "Worker1: ";
        logger.info(messagePrefix + "Am primit mesajul: " + message.toString());
        CarDTO carDTO;

        switch (message.getOperation()) {
            case "CREATE":
                carDTO = objectMapper.readValue(message.getMessage(), CarDTO.class);
                carService.saveCar(carDTO);
                //return objectMapper.writeValueAsString(carDTO);
            case "READ":
                List<CarDTO> carDTOS = carService.findAll();
                return objectMapper.writeValueAsString(carDTOS);
            case "READBYID":
                carDTO = objectMapper.readValue(message.getMessage(), CarDTO.class);
                CarDTO carDTO1 = carService.getCarById(carDTO.getId());
                return objectMapper.writeValueAsString(carDTO1);
            case "UPDATE":
                carDTO = objectMapper.readValue(message.getMessage(), CarDTO.class);
                CarDTO updatedCar = carService.updateCar(carDTO);
                return objectMapper.writeValueAsString(updatedCar);
            case "DELETE":
                carDTO = objectMapper.readValue(message.getMessage(), CarDTO.class);
                carService.deleteCar(carDTO.getId());
                return "";
            default:
                System.err.println(messagePrefix + "Received unknown message " + message);
        }
        return "";
    }
}
