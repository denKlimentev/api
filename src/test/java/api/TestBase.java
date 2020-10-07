package api;

import data.BaseData;
import logic.User;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

public class TestBase {

    protected User testUser;
    protected String id;
    protected SoftAssert asserts;

    @BeforeSuite
    public void setStaticCondition(){
        BaseData.setRequestSpec();
    }
}
