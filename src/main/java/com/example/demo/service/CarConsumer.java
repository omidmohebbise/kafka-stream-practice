package com.example.demo.service;


import com.example.demo.model.Car;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.KStreamConfig.*;

@Service
public class CarConsumer {
    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, String> messageStream = streamsBuilder
                .stream("cars-topic", Consumed.with(STRING_SERDE, STRING_SERDE));

        messageStream.to(CARS_OUTPUT_TOPIC, Produced.with(STRING_SERDE, STRING_SERDE));

        messageStream.filter((key, value) -> value.contains("Toyota"))
                .map((key, value) -> new KeyValue<>(key, value.toUpperCase()))
                .to(TOYOTA_SUPPORT_TOPIC, Produced.with(STRING_SERDE, STRING_SERDE));

        messageStream.filter((key, value) -> value.contains("Honda"))
                .map((key, value) -> new KeyValue<>(key, value.toUpperCase()))
                .to(HONDA_SUPPORT_TOPIC, Produced.with(STRING_SERDE, STRING_SERDE));

        messageStream.filter((key, value) -> value.contains("Ford"))
                .map((key, value) -> new KeyValue<>(key, value.toUpperCase()))
                .to(FORD_SUPPORT_TOPIC, Produced.with(STRING_SERDE, STRING_SERDE));

        messageStream.filter((key, value) -> value.contains("Chevrolet"))
                .map((key, value) -> new KeyValue<>(key, value.toUpperCase()))
                .to(CHEVROLET_SUPPORT_TOPIC, Produced.with(STRING_SERDE, STRING_SERDE));

        messageStream.filter((key, value) -> value.contains("BMW"))
                .map((key, value) -> new KeyValue<>(key, value.toUpperCase()))
                .to(BMW_SUPPORT_TOPIC, Produced.with(STRING_SERDE, STRING_SERDE));


        messageStream.map((key, value) -> {
                    try {
                        System.out.println("value = " + value);
                        var car =  new JsonMapper().readValue(value, Car.class);
                        return new KeyValue<>(key, car);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }

                })
                .filter((key, value) -> value != null)
                .filter((key, value) -> value.getPrice() > 2000)
                .map((key, value) -> new KeyValue<>(value.getUuid(), value.getBrand()))

                .to(CARS_PROCESSED_TOPIC, Produced.with(STRING_SERDE, STRING_SERDE));


    }
}
