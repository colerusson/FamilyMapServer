package junitTestServer;

import dao.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import result.RegisterResult;
import services.ClearService;
import services.RegisterService;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    private ClearService clearService;
    private RegisterService registerService;
    private RegisterRequest registerRequest;
    private RegisterResult registerResult;

    @BeforeEach
    public void setUp() {
        clearService = new ClearService();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        clearService.clear();
    }

    @Test
    public void registerPass() throws DataAccessException {
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setEmail("email@email");
        registerRequest.setFirstName("firstName");
        registerRequest.setLastName("lastName");
        registerRequest.setGender("m");

        registerService = new RegisterService();
        registerResult = registerService.register(registerRequest);

        assertNotNull(registerResult.getAuthtoken());
        assertEquals("username", registerResult.getUsername());
        assertNotNull(registerResult.getPersonID());
        assertTrue(registerResult.isSuccess());
        assertNull(registerResult.getMessage());
    }

    @Test
    public void registerFail() throws DataAccessException {
        registerService = new RegisterService();
        registerResult = registerService.register(registerRequest);

        assertNotNull(registerResult.getMessage());
        assertFalse(registerResult.isSuccess());
        assertNull(registerResult.getUsername());
        assertNull(registerResult.getAuthtoken());
    }
}
