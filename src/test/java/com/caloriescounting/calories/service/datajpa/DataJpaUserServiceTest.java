package com.caloriescounting.calories.service.datajpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import com.caloriescounting.calories.MealTestData;
import com.caloriescounting.calories.model.User;
import com.caloriescounting.calories.service.AbstractUserServiceTest;
import com.caloriescounting.calories.util.exception.NotFoundException;

import static com.caloriescounting.calories.Profiles.DATAJPA;
import static com.caloriescounting.calories.UserTestData.*;

@ActiveProfiles(DATAJPA)
class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    void getWithMeals() throws Exception {
        User admin = service.getWithMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(admin, ADMIN);
        MealTestData.MEAL_MATCHER.assertMatch(admin.getMeals(), MealTestData.ADMIN_MEAL2, MealTestData.ADMIN_MEAL1);
    }

    @Test
    void getWithMealsNotFound() throws Exception {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithMeals(1));
    }
}