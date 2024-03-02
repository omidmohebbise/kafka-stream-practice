package com.example.demo.service;

import com.example.demo.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.example.demo.config.KStreamConfig.CARS_TOPIC;

@Service
@RequiredArgsConstructor
public class CarProducer {
    private int counter = 0;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public void produce(String brand, String model, String color, int year, int price) {
        var car = new Car(UUID.randomUUID().toString(), brand, model, color, year, price);
        CompletableFuture<SendResult<String, Object>> result = null;

        result = kafkaTemplate.send(CARS_TOPIC, UUID.randomUUID().toString(), car);

        result.whenComplete((success, failure) -> {
            if (failure != null) {
                failure.printStackTrace();
            }
        });
    }

    @Scheduled(fixedRate = 3000)
    public void produce() {
        String[] carMakes = {"Toyota", "Honda", "Ford", "Chevrolet", "BMW"};
        String[] carModels = {"Corolla", "Civic", "F-150", "Silverado", "3 Series"};
        String[] carColors = {"White", "Black", "Silver", "Red", "Blue"};
        int[] carYears = {2015, 2018, 2020, 2022, 2023};
        int[] carPrices = {15000, 20000, 25000, 30000, 35000};

        Random random = new Random();

        // Generate random index for each attribute array
        int makeIndex = random.nextInt(carMakes.length);
        int modelIndex = random.nextInt(carModels.length);
        int colorIndex = random.nextInt(carColors.length);
        int yearIndex = random.nextInt(carYears.length);
        int priceIndex = random.nextInt(carPrices.length);

        // Create a new car record using the randomly selected attributes
        produce(carMakes[makeIndex], carModels[modelIndex], carColors[colorIndex],
                carYears[yearIndex], carPrices[priceIndex]);
    }

}
