package com.example.demo.service;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class TerminalPrinterConsumer {

    @KafkaListener(topics = "terminal-printer-topic", groupId = "terminal-printer")
    public void consume(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        System.out.println("Consumed message: " + record.value());
        acknowledgment.acknowledge();
    }
}
