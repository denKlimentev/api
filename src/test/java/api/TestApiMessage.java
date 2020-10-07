package api;

import logic.data.results.MessageResult;
import logic.UserLogic;
import logic.data.results.ResponseResult;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static logic.BaseData.CODE_200;
import static logic.BaseData.OK;

public class TestApiMessage extends TestBase {

    @BeforeMethod
    public void precondition() {
        testUser = new UserLogic();
        testUser.precondition();

        ResponseResult result = testUser.create(testUser.getUser());

        Assert.assertEquals(
                CODE_200,
                result.getResultCode(),
                " Whe don`t have message 200");

         result = testUser.login();

        Assert.assertNotNull(result.getHeader(), "We don`t get session id");

    }


    @Test()
    public void getMessageFirstTime() {

        MessageResult result = testUser.sendMessage(id);

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
