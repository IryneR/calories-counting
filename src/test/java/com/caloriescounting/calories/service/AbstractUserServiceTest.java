package com.caloriescounting.calories.service;

import com.caloriescounting.calories.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import com.caloriescounting.calories.model.Role;
import com.caloriescounting.calories.model.User;
import com.caloriescounting.calories.repository.UserRepository;

import javax.validation.ConstraintViolationException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static com.caloriescounting.calories.UserTestData.*;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private UserRepository repository;

    @Test
    void create() throws Exception {
        User newUser = getNew();
        User created = service.create(new User(newUser));
        int newId = created.id();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", 2000, Role.USER)));
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_ID);
        Assertions.assertNull(repository.get(USER_ID));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }

    @Test
    void get() throws Exception {
        User user = service.get(ADMIN_ID);
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        service.update(new User(updated));
        USER_MATCHER.assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<User> all = service.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER);
    }


    @Test
    void enable() {
        service.enable(USER_ID, false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        assertTrue(service.get(USER_ID).isEnabled());
    }
}