package tests.gametests;

import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TagGameManagerTests extends MockBukkitTests {

    @Test
    void createGame_ShouldIncrementGameId(){
        TagGameManager manager = new TagGameManager(plugin);
        manager.createGame(1,1);
        assertEquals(1, manager.createGame(1,1).getId());
    }

    @Test
    void addAndRemoveGame_ShouldAddRemoveThenThrow() {
        TagGameManager manager = new TagGameManager(plugin);
        TagGame game = manager.createGame(1,1);
        assertEquals(manager.getGame(game.getId()), game);
        manager.deleteGame(0);
        assertThrows(NoSuchElementException.class, () ->{
            manager.getGame(0);
        });
    }

}
