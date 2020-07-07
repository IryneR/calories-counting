package com.caloriescounting.calories.service.jdbc;

import com.caloriescounting.calories.Profiles;
import org.springframework.test.context.ActiveProfiles;
import com.caloriescounting.calories.service.AbstractMealServiceTest;

@ActiveProfiles(Profiles.JDBC)
class JdbcMealServiceTest extends AbstractMealServiceTest {
}