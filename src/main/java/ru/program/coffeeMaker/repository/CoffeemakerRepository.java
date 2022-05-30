package ru.program.coffeeMaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.program.coffeeMaker.model.Coffeemaker;

@Repository
public interface CoffeemakerRepository extends JpaRepository<Coffeemaker, Long> {

}


