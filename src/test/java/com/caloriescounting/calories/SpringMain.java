package com.caloriescounting.calories;

import com.caloriescounting.calories.web.user.AdminRestController;
import org.springframework.context.support.GenericXmlApplicationContext;
import com.caloriescounting.calories.model.Role;
import com.caloriescounting.calories.model.User;
import com.caloriescounting.calories.to.MealTo;
import com.caloriescounting.calories.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static com.caloriescounting.calories.TestUtil.mockAuthorize;
import static com.caloriescounting.calories.UserTestData.USER;

public class SpringMain {
    public static void main(String[] args) {

        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/inmemory.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", 2000, Role.ADMIN));
            System.out.println();

            mockAuthorize(USER);

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealTo> filteredMealsWithExcess =
                    mealController.getBetween(
                            LocalDate.of(2020, Month.JANUARY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(11, 0));
            filteredMealsWithExcess.forEach(System.out::println);
            System.out.println();
            System.out.println(mealController.getBetween(null, null, null, null));
        }
    }
}
