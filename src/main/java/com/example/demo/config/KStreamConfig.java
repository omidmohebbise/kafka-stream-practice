package com.example.demo.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.streams.StreamsConfig.*;

@Configuration
@EnableKafkaStreams
public class KStreamConfig implements CommandLineRunner {

    public static final String CARS_OUTPUT_TOPIC = "cars-output-topic";
    public static final String CARS_PROCESSED_TOPIC = "cars-processed-topic";
    public static final String CARS_TOPIC = "cars-topic";
    public static final String TERMINAL_PRINTER_TOPIC = "terminal-printer-topic";
    public static final String TOYOTA_SUPPORT_TOPIC = "toyota-support-topic";
    public static final String HONDA_SUPPORT_TOPIC = "honda-support-topic";
    public static final String FORD_SUPPORT_TOPIC = "ford-support-topic";
    public static final String CHEVROLET_SUPPORT_TOPIC = "chevrolet-support-topic";
    public static final String BMW_SUPPORT_TOPIC = "bmw-support-topic";

    @Value(value = "${spring.kafka.streams.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.streams.application-id}")
    private String applicationId;


    public void createSupportTopics() {
        AdminClient adminClient = AdminClient.create(Map.of(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress));

        int partitions = 1;
        short replicationFactor = 1;
        adminClient.createTopics(Arrays.asList(
                new NewTopic(TOYOTA_SUPPORT_TOPIC, partitions, replicationFactor),
                new NewTopic(HONDA_SUPPORT_TOPIC, partitions, replicationFactor),
                new NewTopic(FORD_SUPPORT_TOPIC, partitions, replicationFactor),
                new NewTopic(CHEVROLET_SUPPORT_TOPIC, partitions, replicationFactor),
                new NewTopic(BMW_SUPPORT_TOPIC, partitions, replicationFactor)
        ));

        adminClient.close();
    }

    @Bean
    public NewTopic carsOutPutTopic() {
        return TopicBuilder.name(CARS_OUTPUT_TOPIC)
                .partitions(1)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    public NewTopic carsProcessedTopic() {
        return TopicBuilder.name(CARS_PROCESSED_TOPIC)
                .partitions(1)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    public NewTopic carsTopic() {
        return TopicBuilder.name(CARS_TOPIC)
                .partitions(1)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    public NewTopic terminalPrinter() {
        return TopicBuilder.name(TERMINAL_PRINTER_TOPIC)
                .partitions(1)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, applicationId);
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        return new KafkaStreamsConfiguration(props);
    }

    @Override
    public void run(String... args) throws Exception {
        createSupportTopics();
    }
}
