package junitTestServer;

import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.FillRequest;
import result.FillResult;
import services.ClearService;
import services.FillService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    private ClearService clearService;
    private Database db;
    private UserDao uDao;
    private FillService fillService;
    private FillRequest fillRequest;
    private FillResult fillResult;

    @BeforeEach
    public void setUp() {
        clearService = new ClearService();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        clearService.clear();
    }

    @Test
    public void fillPass() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        uDao = new UserDao(conn);

        uDao.clear();

        User user = new User();
        user.setEmail("email@email");
        user.setGender("m");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPersonID("person_id");
        user.setUsername("username");
        user.setPassword("password");
        uDao.insert(user);

        db.closeConnection(true);

        fillService = new FillService();
        fillRequest = new FillRequest();
        fillRequest.setUsername("username");
        fillRequest.setGenerations(4);
        fillResult = fillService.fill(fillRequest);

        assertNotNull(fillResult.getMessage());
        assertTrue(fillResult.isSuccess());
    }

    @Test
    public void fillFail() throws DataAccessException {
        fillService = new FillService();
        fillRequest = new FillRequest();
        fillRequest.setUsername("username");
        fillRequest.setGenerations(4);
        fillResult = fillService.fill(fillRequest);

        assertNotNull(fillResult.getMessage());
        assertFalse(fillResult.isSuccess());
    }
}
