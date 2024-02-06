package com.example.demo.service;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TerminalPrinterConsumer {

//    @KafkaListener(topics = "terminal-printer-topic", groupId = "terminal-printer")
//    public void consume(String message) {
//        System.out.println("Consumed message: " + message);
//    }
}
