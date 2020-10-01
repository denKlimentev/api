package api.load;

import logic.UserLogic;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestCreate  {

    @Parameters({"quantity"})
    @Test()
    public void createUser(@Optional int quantity) {

        UserLogic testUser = new UserLogic();

        int bedore = testUser.getUsersList().getClients().size();

        testUser.create(quantity);

        int after = testUser.getUsersList().getClients().size();

        Assert.assertTrue(
                bedore == (after-quantity),
                "Whe dont create needed quantity users : count before - "
                        + bedore
                        + " count after  - "
                        +after
                );


    }


}
