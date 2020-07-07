package com.caloriescounting.calories.service;

import com.caloriescounting.calories.MealTestData;
import com.caloriescounting.calories.UserTestData;
import com.caloriescounting.calories.util.exception.ErrorType;
import com.caloriescounting.calories.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.caloriescounting.calories.model.Meal;
import com.caloriescounting.calories.repository.MealRepository;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractMealServiceTest extends AbstractServiceTest {

    @Autowired
    protected MealService service;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MealRepository repository;

    @Test
    void delete() throws Exception {
        service.delete(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        Assertions.assertNull(repository.get(MealTestData.MEAL1_ID, UserTestData.USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> service.delete(1, UserTestData.USER_ID));
    }

    @Test
    void deleteNotOwn() throws Exception {
        assertThrows(NotFoundException.class,
                () -> service.delete(MealTestData.MEAL1_ID, UserTestData.ADMIN_ID));
    }

    @Test
    void create() throws Exception {
        Meal newMeal = MealTestData.getNew();
        Meal created = service.create(newMeal, UserTestData.USER_ID);
        int newId = created.id();
        newMeal.setId(newId);
        MealTestData.MEAL_MATCHER.assertMatch(created, newMeal);
        MealTestData.MEAL_MATCHER.assertMatch(service.get(newId, UserTestData.USER_ID), newMeal);
    }

    @Test
    void get() throws Exception {
        Meal actual = service.get(MealTestData.ADMIN_MEAL_ID, UserTestData.ADMIN_ID);
        MealTestData.MEAL_MATCHER.assertMatch(actual, MealTestData.ADMIN_MEAL1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> service.get(MealTestData.MEAL1_ID, UserTestData.ADMIN_ID));
    }

    @Test
    void getNotOwn() throws Exception {
        assertThrows(NotFoundException.class,
                () -> service.get(MealTestData.MEAL1_ID, UserTestData.ADMIN_ID));
    }

    @Test
    void update() throws Exception {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, UserTestData.USER_ID);
        MealTestData.MEAL_MATCHER.assertMatch(service.get(MealTestData.MEAL1_ID, UserTestData.USER_ID), updated);
    }

    @Test
    void updateNotFound() throws Exception {
        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> service.update(MealTestData.MEAL1, UserTestData.ADMIN_ID));
        String msg = ex.getMessage();
        assertTrue(msg.contains(ErrorType.DATA_NOT_FOUND.name()));
        assertTrue(msg.contains(NotFoundException.NOT_FOUND_EXCEPTION));
        assertTrue(msg.contains(String.valueOf(MealTestData.MEAL1_ID)));
    }

    @Test
    void getAll() throws Exception {
        MealTestData.MEAL_MATCHER.assertMatch(service.getAll(UserTestData.USER_ID), MealTestData.MEALS);
    }

    @Test
    void getBetweenInclusive() throws Exception {
        MealTestData.MEAL_MATCHER.assertMatch(service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), UserTestData.USER_ID),
                MealTestData.MEAL3, MealTestData.MEAL2, MealTestData.MEAL1);
    }

    @Test
    void getBetweenWithNullDates() throws Exception {
        MealTestData.MEAL_MATCHER.assertMatch(service.getBetweenInclusive(null, null, UserTestData.USER_ID), MealTestData.MEALS);
    }


}