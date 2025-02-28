package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Car {
    private String carId = UUID.randomUUID().toString();
    private String carName;
    private String carColor;
    private int carQuantity;

}
