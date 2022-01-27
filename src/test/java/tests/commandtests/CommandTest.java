package tests.commandtests;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.junit.jupiter.api.BeforeEach;
import tests.MockBukkitTests;

public class CommandTest extends MockBukkitTests {

    PlayerMock p;

    @Override
    @BeforeEach
    protected void setUpBukkit(){
        super.setUpBukkit();
        p = server.addPlayer("p");
    }

}
