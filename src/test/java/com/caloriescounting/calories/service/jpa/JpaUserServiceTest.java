package com.caloriescounting.calories.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import com.caloriescounting.calories.service.AbstractUserServiceTest;

import static com.caloriescounting.calories.Profiles.JPA;

@ActiveProfiles(JPA)
class JpaUserServiceTest extends AbstractUserServiceTest {
}