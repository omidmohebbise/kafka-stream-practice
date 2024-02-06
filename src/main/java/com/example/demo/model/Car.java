package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car implements Serializable {
    private String uuid;
    private String brand;
    private String model;
    private String color;
    private int year;
    private int price;
}
