package com.example.tdd;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class UserServiceImplTest {
    private UserDAO userDAO;
    private SecurityService securityService;
    private UserServiceImpl userService;

    private User user;

    @Before
    public void setUp() {
        userDAO = mock(UserDAO.class);
        securityService = mock(SecurityService.class);
        user = mock(User.class);
        userService = new UserServiceImpl(userDAO, securityService);
    }

    @Test
    public void whenAssignPasswordCalledThenUserDasUpdateUSerMethodCalled() throws Exception {
        String PASSWORD = "NEW_PASSWORD";
        user.setPassword(PASSWORD);
        userService.assignPassword(user);
        verify(userDAO).updateUser(user);
    }
}