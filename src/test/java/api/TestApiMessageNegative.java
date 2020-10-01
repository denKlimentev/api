package api;

import logic.UserLogic;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static logic.BaseData.CODE_401;

public class TestApiMessageNegative extends TestBase {

    @BeforeTest
    public void precondition() {
        testUser = new UserLogic();
    }


    @Test()
    public void getMessageWithoutLogin() {
        String randomNumbers  = "123";
        testUser.senMessage(CODE_401, randomNumbers);
    }

}
