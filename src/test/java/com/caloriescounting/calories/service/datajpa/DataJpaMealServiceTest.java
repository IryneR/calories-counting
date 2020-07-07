package com.caloriescounting.calories.service.datajpa;

import com.caloriescounting.calories.MealTestData;
import com.caloriescounting.calories.Profiles;
import com.caloriescounting.calories.UserTestData;
import com.caloriescounting.calories.service.AbstractMealServiceTest;
import com.caloriescounting.calories.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import com.caloriescounting.calories.model.Meal;

@ActiveProfiles(Profiles.DATAJPA)
class DataJpaMealServiceTest extends AbstractMealServiceTest {
    @Test
    void getWithUser() throws Exception {
        Meal adminMeal = service.getWithUser(MealTestData.ADMIN_MEAL_ID, UserTestData.ADMIN_ID);
        MealTestData.MEAL_MATCHER.assertMatch(adminMeal, MealTestData.ADMIN_MEAL1);
        UserTestData.USER_MATCHER.assertMatch(adminMeal.getUser(), UserTestData.ADMIN);
    }

    @Test
    void getWithUserNotFound() throws Exception {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithUser(1, UserTestData.ADMIN_ID));
    }
}
