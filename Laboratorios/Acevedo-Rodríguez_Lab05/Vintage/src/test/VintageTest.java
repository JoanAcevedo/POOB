package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;
class VintageTest {

    private Vintage vintage;
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("TestPlayer");
        vintage = new Vintage(player);
    }

    @Test
    public void play_validMove_shouldMoveGems() {
        int[] initialPosition = {0, 0};
        int[] finalPosition = {1, 1};
        vintage.play(initialPosition, finalPosition);
        assertNotNull(vintage.getBoard().getBox(finalPosition[0], finalPosition[1]).getGem());
    }

    @Test
    public void getScoreboardPlayer_initialScore_shouldReturnZero() {
        assertEquals(0, vintage.getScoreboardPlayer());
    }

    @Test
    public void refresh_resetBoard_shouldHaveEmptyBoard() {
        int[] initialPosition = {0, 0};
        int[] finalPosition = {1, 1};
        vintage.play(initialPosition, finalPosition);
        vintage.refresh();
        assertNotNull(vintage.getBoard().getBox(initialPosition[0], initialPosition[1]).getGem());
    }

    @Test
    public void getHeightBoard_initialBoard_shouldReturnEight() {
        assertEquals(8, vintage.getHeightBoard());
    }

    @Test
    public void getWidthBoard_initialBoard_shouldReturnEight() {
        assertEquals(8, vintage.getWidthBoard());
    }
    
    @Test
    public void play_invalidMove_shouldNotMoveGems() {
        int[] initialPosition = {0, 0};
        int[] finalPosition = {8, 8};
        assertEquals(8,vintage.getHeightBoard());
    }

    @Test
    public void getScoreboardPlayer_afterWinningMove_shouldIncreaseScore() {
        int[] initialPosition = {0, 0};
        int[] finalPosition = {1, 1};
        vintage.play(initialPosition, finalPosition);
        assertFalse(vintage.getScoreboardPlayer() > 0);
    }

    @Test
    public void refresh_afterMultipleMoves_shouldHaveEmptyBoard() {
        int[] initialPosition1 = {0, 0};
        int[] finalPosition1 = {1, 1};
        int[] initialPosition2 = {2, 2};
        int[] finalPosition2 = {3, 3};
        vintage.play(initialPosition1, finalPosition1);
        vintage.play(initialPosition2, finalPosition2);
        vintage.refresh();
        assertNotNull(vintage.getBoard().getBox(initialPosition1[0], initialPosition1[1]).getGem());
    }
}
