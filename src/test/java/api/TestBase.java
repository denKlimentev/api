package api;

import data.BaseData;
import data.results.ResponseResult;
import logic.User;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import static data.BaseData.CODE_200;

@Log4j
public class TestBase {

//    protected User testUser;
    protected String id;
    protected SoftAssert asserts;

    @BeforeSuite
    public void setStaticCondition(){
        BaseData.setRequestSpec();
    }


    public User precondition() {
        log.info("precondition  " + this.hashCode());

        User testUser = new User();

        testUser.precondition();

        String userName = testUser.getUser().getUsername();

        log.info(userName);

        ResponseResult result = testUser.create(testUser.getUser());

        Assert.assertEquals(
                CODE_200,
                result.getResultCode(),
                " Whe don`t have expected result "
                        + CODE_200
                        + " Actual result : "
                        + result.getResultCode()
                        + " Body : " + result.getBody());
        return testUser;
    }
}
