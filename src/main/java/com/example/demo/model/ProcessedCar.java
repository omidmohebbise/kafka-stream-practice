package com.example.demo.model;


import java.io.Serializable;


public record ProcessedCar(String brand, String model, String color, int year, int price,
                           boolean expensive) implements Serializable {
    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", year=" + year +
                ", price=" + price +
                '}';
    }
}
