package api;

import logic.UserLogic;
import org.testng.annotations.BeforeSuite;

public class TestBase {

    protected UserLogic testUser;
    protected String id;

    @BeforeSuite
    public void init() {
        testUser = new UserLogic();
        testUser.precondition();

    }
}
