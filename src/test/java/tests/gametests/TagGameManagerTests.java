package tests.gametests;

import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TagGameManagerTests extends MockBukkitTests {

    @BeforeEach
    void setUp(){
        setUpBukkit();
    }

    @AfterEach
    void tearDown(){
        tearDownBukkit();
    }

    @Test
    void createGame_ShouldIncrementGameId(){
        TagGameManager manager = new TagGameManager(plugin);
        manager.createGame();
        assertEquals(1, manager.createGame().getId());
    }

    @Test
    void addAndRemoveGame_ShouldAddRemoveThenThrow() {
        TagGameManager manager = new TagGameManager(plugin);
        TagGame game = manager.createGame();
        assertEquals(manager.getGame(game.getId()), game);
        manager.deleteGame(0);
        assertThrows(NoSuchElementException.class, () ->{
            manager.getGame(0);
        });
    }

}
