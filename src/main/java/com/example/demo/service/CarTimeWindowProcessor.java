package com.example.demo.service;


import com.example.demo.model.Car;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.example.demo.config.KStreamConfig.*;

@Service
@Slf4j
public class CarTimeWindowProcessor {
    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, String> messageStream = streamsBuilder
                .stream("cars-topic", Consumed.with(STRING_SERDE, STRING_SERDE));

        Duration windowSize = Duration.ofSeconds(10);

        messageStream
                .groupByKey()
                .windowedBy(TimeWindows.of(windowSize))
                .count()
                .toStream()
                .peek((key, value) -> log.info("Windowed count: key={}, value={}", key, value))
                .map((key, value) -> new KeyValue<>(key.key(), key.key()))
                .to(TERMINAL_PRINTER_TOPIC, Produced.with(STRING_SERDE, STRING_SERDE));

    }
}
