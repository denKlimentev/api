package api.load;

import api.TestBase;
import logic.User;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestCreate extends TestBase {

    @Parameters({"quantity"})
    @Test()
    public void createUser(@Optional int quantity) {

        User testUser = new User();

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
