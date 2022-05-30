package ru.program.coffeeMaker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.program.coffeeMaker.exception.EntityNotFoundException;
import ru.program.coffeeMaker.exception.IncorrectValueException;
import ru.program.coffeeMaker.exception.IncorrectValueIngredients;
import ru.program.coffeeMaker.model.Coffeemaker;
import ru.program.coffeeMaker.model.CoffeemakerStatus;
import ru.program.coffeeMaker.repository.CoffeemakerRepository;

import static ru.program.coffeeMaker.model.CoffeemakerStatus.*;


@RestController
public class CoffeemakerController {

    @Autowired
    private CoffeemakerRepository coffeemakerRepository;

    private final static double MIN_WATER_VALUE = 0.2;
    private final static double MAX_WATER_VALUE = 1.0;
    private final static double MIN_COFFEE_VALUE = 0.025;
    private final static double MAX_COFFEE_VALUE = 0.1;

    /**
     * информация о  кол-ве воды, кофе, статуса готовности к приготовлению и статуса начала приготовления
     */
    @GetMapping("/coffeemaker/status/{id}")
    public Coffeemaker getInfoAboutCoffeemaker(@PathVariable Long id) {
        return coffeemakerRepository.findById(id).get();
    }

    /**
     * добавление кофе (от 0.025 до 0.1 л) и воды (от 0.2 до 1 л), установка  статуса готовности к приготовлению
     */
    @PutMapping("/coffeemaker/add-ingredient/{id}")
    public Coffeemaker addWaterAndCoffee(@RequestBody Coffeemaker newCoffeemaker, @PathVariable Long id) {
        Coffeemaker coffeemaker = coffeemakerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        if ((newCoffeemaker.getCoffee() < MIN_COFFEE_VALUE || newCoffeemaker.getCoffee() > MAX_COFFEE_VALUE) ||
                (newCoffeemaker.getWater() < MIN_WATER_VALUE || newCoffeemaker.getWater() > MAX_WATER_VALUE)) {
            throw new IncorrectValueException("Неверное кол-во ингредиентов!! Требуется кофе от 0.025 до 0.1 л, воды от 0.2 до 1 л");
        }
        coffeemaker.setWater(newCoffeemaker.getWater());
        coffeemaker.setCoffee(newCoffeemaker.getCoffee());
        coffeemaker.setStatus(CoffeemakerStatus.READY_TO_COOK);
        return coffeemakerRepository.save(coffeemaker);
    }

    /**
     * нажатие кнопки начала приготовления
     */
    @PutMapping("/coffeemaker/start/{id}")
    public Coffeemaker startCookingCoffee(@PathVariable Long id) {
        Coffeemaker coffeemaker = coffeemakerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        if (coffeemaker.getStatus().equals(READY_TO_COOK)) {
            coffeemaker.setStatus(FINISH_COOK);
            return coffeemakerRepository.save(coffeemaker);
        } else {
            throw new IncorrectValueException("Неверное кол-во ингредиентов!! Требуется кофе от 0.025 до 0.1 л, воды от 0.2 до 1 л");
        }
    }

    /**
     * забрать кофе
     */
    @PutMapping("/coffeemaker/take-coffee/{id}")
    public Coffeemaker takeCoffeeAfterCooking(@PathVariable Long id) {
        Coffeemaker coffeemaker = coffeemakerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        if (coffeemaker.getStatus().equals(FINISH_COOK)) {
            coffeemaker.setStatus(EMPTY);
            coffeemaker.setWater(0.0);
            coffeemaker.setCoffee(0.0);
            return coffeemakerRepository.save(coffeemaker);
        } else {
            throw new IncorrectValueException("Кофе еще не готов!!");
        }
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectValueIngredients> handleException(
            IncorrectValueException e) {
        IncorrectValueIngredients ingredients = new IncorrectValueIngredients();
        ingredients.setInfo(e.getMessage());
        return new ResponseEntity<>(ingredients, HttpStatus.BAD_REQUEST);
    }
}
