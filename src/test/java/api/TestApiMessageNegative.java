package api;

import logic.UserLogic;
import logic.data.results.MessageResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static logic.BaseData.CODE_401;

public class TestApiMessageNegative extends TestBase {

    @BeforeMethod
    public void precondition() {
        testUser = new UserLogic();
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
