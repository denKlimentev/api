package api;

import logic.data.results.ClientsResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import static logic.BaseData.CODE_200;
import static logic.BaseData.CODE_500;

public class TestApiLoginUser extends TestBase {


    @Test
    public void createUser() {
        testUser.create(CODE_200, testUser.getUser());
    }

    @Test(dependsOnMethods = "createUser")
    public void createUserNegative() {
        testUser.create(CODE_500, testUser.getUser());
    }


    @Test(dependsOnMethods = "createUser")
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


    @Test(dependsOnMethods = "createUser")
    public void login() {

      id =  testUser.login();

      Assert.assertNotNull(id, "We don`t get session id");

    }

    @Test(dependsOnMethods = "createUser")
    public void loginOut() {
        testUser.loginOut();
    }
}
