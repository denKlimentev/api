package api;

import logic.UserLogic;
import logic.data.results.ClientsResult;
import logic.data.results.ResponseResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static logic.BaseData.CODE_200;
import static logic.BaseData.CODE_500;

public class TestApiLoginUser extends TestBase {

    @BeforeMethod
    public void precondition() {
        testUser = new UserLogic();

        testUser.precondition();
        ResponseResult result = testUser.create(testUser.getUser());

        Assert.assertEquals(
                CODE_200,
                result.getResultCode(),
                " Whe don`t have expected result "
                        + CODE_200
                        + " Actual result : "
                        + result.getResultCode()
                        + " Body : " + result.getBody());
    }

    @Test
    public void createUserNegative() {

        ResponseResult result = testUser.create(testUser.getUser());

        Assert.assertEquals(CODE_500,
                result.getResultCode(),
                " Whe don`t have expected result "
                        + CODE_500
                        + " Actual result : "
                        +  result.getResultCode());

    }


    @Test()
    public void getUser() {

        ClientsResult userList = testUser.getUsersList();

        Assert.assertTrue(

                userList
                        .getClients()
                        .stream()
                        .anyMatch(a -> a.contains(testUser.getUser().getUsername())),

                "We don`t have crated user  : " +
                        testUser.getUser().getFullName() +
                        "in actual users list : \n" +
                        String.join("\n", userList.getClients() + "\n")
        );
    }


    @Test()
    public void login() {

        id = testUser.login();

        Assert.assertNotNull(id, "We don`t get session id");

    }


    @Test()
    public void loginOut() {

        id = testUser.login();

        Assert.assertNotNull(id, "We don`t get session id");

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
