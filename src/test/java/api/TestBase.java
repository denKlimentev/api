package api;

import logic.BaseData;
import logic.UserLogic;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

public class TestBase {

    protected UserLogic testUser;
    protected String id;
    protected SoftAssert asserts;

    @BeforeSuite
    public void setStaticCondition(){
        BaseData.setRequestSpec();
    }
}
