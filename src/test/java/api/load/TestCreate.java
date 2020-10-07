package api.load;

import api.TestBase;
import logic.UserLogic;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestCreate extends TestBase {

    @Parameters({"quantity"})
    @Test()
    public void createUser(@Optional int quantity) {

        UserLogic testUser = new UserLogic();

        int before = testUser.getUsersList().getClients().size();

        testUser.create(quantity);

        int after = testUser.getUsersList().getClients().size();

        Assert.assertTrue(
                before == (after-quantity),
                "Whe dont create needed quantity users : count before - "
                        + before
                        + " count after  - "
                        +after
                );


    }


}
