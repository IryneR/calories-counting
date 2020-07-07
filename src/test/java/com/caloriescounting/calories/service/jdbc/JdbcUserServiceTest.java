package com.caloriescounting.calories.service.jdbc;

import com.caloriescounting.calories.Profiles;
import com.caloriescounting.calories.service.AbstractUserServiceTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(Profiles.JDBC)
class JdbcUserServiceTest extends AbstractUserServiceTest {
}