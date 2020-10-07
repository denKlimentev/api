package api;

import data.results.MessageResult;
import logic.User;
import data.results.ResponseResult;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static data.BaseData.*;

public class TestApiMessage extends TestBase {

    private User testUser;

    @Test()
    public void getMessageFirstTime() {

        testUser = precondition();

        ResponseResult loginResult =  testUser.login();

        MessageResult result = testUser.sendMessage(loginResult.getHeader());

        asserts = new SoftAssert();

        asserts.assertEquals(
                OK,
                result.getResultCode(),
                " Whe don`t have expected result "
                        + OK
                        + "Actual result : "
                        + result.getResultCode());


        asserts.assertEquals(
                result.getMessage(),
                "Hello, " + testUser.getUser().getFullName() + "!",
                " Whe don`t have need message ");

        asserts.assertAll();
    }

    @AfterTest(alwaysRun = true)
    public void postcondition() {

        int statusCode = testUser.loginOut();

        asserts = new SoftAssert();

        asserts.assertEquals(
                CODE_200,
                statusCode,
                " Whe don`t have expected result "
                        + CODE_200
                        + "Actual result : "
                        + statusCode);

        asserts.assertAll();
    }

}
