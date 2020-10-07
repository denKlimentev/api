package api;

import logic.User;
import data.results.ClientsResult;
import data.results.ResponseResult;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.jws.soap.SOAPBinding;

import static data.BaseData.CODE_200;
import static data.BaseData.CODE_500;

@Log4j
public class TestApiLoginUser extends TestBase {




    @Test
    public void createUserNegative() {
        log.info("createUserNegative");
        User testUser = precondition();

        ResponseResult result = testUser.create(testUser.getUser());

        Assert.assertEquals(CODE_500,
                result.getResultCode(),
                " Whe don`t have expected result "
                        + CODE_500
                        + " Actual result : "

                        + result.getResultCode());

    }


    @Test()
    public void getUser() {
        log.info("getUser");

        User testUser = precondition();

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
        log.info("login");

        User testUser = precondition();

        ResponseResult result = testUser.login();

        Assert.assertEquals(
                CODE_200,
                result.getResultCode(),
                " Whe don`t have expected result "
                        + CODE_200
                        + " Actual result : "
                        + result.getResultCode()
                        + " Body : " + result.getBody());


        Assert.assertNotNull(result.getHeader(), "We don`t get session id");

    }


    @Test()
    public void loginOut() {
        log.info("loginOut");

        User testUser = precondition();

        ResponseResult result = testUser.login();

        Assert.assertNotNull(result.getHeader(), "We don`t get session id");

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
