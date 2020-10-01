package api;

import logic.data.results.MessageResult;
import logic.UserLogic;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static logic.BaseData.CODE_200;

public class TestApiMessage extends TestBase {

    @BeforeTest
    public void precondition() {
        testUser = new UserLogic();
        testUser.precondition();
        testUser.create(CODE_200, testUser.getUser());

        id =  testUser.login();

        Assert.assertNotNull(id, "We don`t get session id");

    }


    @Test()
    public void getMessageFirstTime() {

        MessageResult result = testUser.senMessage(CODE_200, id);

        Assert.assertEquals(
                result.getMessage(),
                "Hello, " + testUser.getUser().getFullName()+ "!",
                " Whe don`t have need message ");
    }

    @AfterTest(alwaysRun = true)
    public void postcondition(){
        testUser.loginOut();
    }

}
