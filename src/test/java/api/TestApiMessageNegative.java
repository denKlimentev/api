package api;

import logic.User;
import data.results.MessageResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestApiMessageNegative extends TestBase {

    private User testUser;

    @BeforeMethod
    public void create() {
        testUser = new User();
    }


    @Test()
    public void getMessageWithoutLogin() {
        String randomNumbers = "123";
        MessageResult result = testUser.sendMessage(randomNumbers);
        String message = result.getResultCode();


        Assert.assertEquals(
                message,
                "Unauthorized",
                " Whe don`t have message Unauthorized" + result.getResultCode());

    }

}
