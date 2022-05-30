package ru.program.coffeeMaker.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Сущность Кофеварка
 */
@Data
@Entity
public class Coffeemaker {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coffee_seq")
    @SequenceGenerator(name = "coffee_seq", sequenceName = "coffee_seq",
            allocationSize = 1)
    private Long id;
    private Double water;
    private Double coffee;
    @Enumerated(EnumType.STRING)
    private CoffeemakerStatus status = CoffeemakerStatus.EMPTY;
}
