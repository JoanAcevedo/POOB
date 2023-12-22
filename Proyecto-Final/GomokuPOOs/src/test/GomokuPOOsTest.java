package test;

import static org.junit.Assert.*;
import org.junit.*;


import domain.*;
import domain.gomokus.*;
import domain.players.*;
import domain.boxes.*;
import domain.stones.*;

public class GomokuPOOsTest {

    private GomokuPOOs juego;

    @Before
    public void before() {
        juego = new GomokuPOOs();
        juego = GomokuPOOs.getInstanceNormal();
        
        juego.addPlayer("Laura", "red");
        juego.getPlayer("Laura").setNumPesadas(3);
        juego.getPlayer("Laura").setNumTemporales(10);
        juego.getPlayer("Laura").deckOfStones();
        juego.getPlayer("Laura").setTurn(true);
        juego.addPlayer("Joan", "blue");
        juego.getPlayer("Joan").setNumPesadas(3);
        juego.getPlayer("Joan").setNumTemporales(10);
        juego.getPlayer("Joan").deckOfStones();

    }

    @Test
    public void shouldAddBoard(){
        juego.addBoard(12, 12);
        Board board = juego.getBoard();
        assertTrue(board != null);
    }

    @Test
    public void shouldNotAddBoard(){
        juego.addBoard(0, 0);
        assertFalse(juego.ok());
    }

    @Test
    public void shouldPlay() {
        juego.addBoard(12, 12);
        juego.play(3, 3);
        Stone stoneAtPosition = juego.getBoard().getStoneAt(3, 3); // Obtenemos la piedra en una posición específica
        assertEquals(stoneAtPosition.getColor(), juego.getNextPlayerInTurn().getColor());
    }

    @Test
    public void shouldNotPlay1() {
        juego.addBoard(12, 12);
        juego.play(15, 15); // no deberia poder jugar porque se sale del tablero
        assertFalse(juego.ok());
    }

    @Test
    public void shouldNotPlay2() {
        juego.addBoard(12, 12);
        juego.play(-1, -1); // no deberia poder jugar porque son valores negativos
        assertFalse(juego.ok());
    }

    @Test
    public void shouldHasPlayerWon(){ // <------------------------------------------------
        juego.addBoard(12, 12);
        juego.play(0, 0); // jugador 1 #1
        juego.play(3, 3); // jugador 2
        juego.play(0, 1); // jugador 1 #2
        juego.play(3, 7); // jugador 2
        juego.play(0, 2); // jugador 1 #3
        juego.play(1, 1); // jugador 2
        juego.play(0, 3); // jugador 1 #4
        juego.play(0, 6); // jugador 2
        juego.play(0, 4); // jugador 1 #5
        // aca ya se supone que gano el jugador 1
        // aca se debe hacer la comprobacion de que gano
        assertTrue(true);
    }

    @Test
    public void shouldNotHasPlayerWon(){ // <------------------------------------------------
        juego.addBoard(12, 12);
        // aca se debe hacer la comprobacion de que nadie ha ganado
        assertTrue(true);
    }

    @Test
    public void shouldGetPlayers(){
        String[] players = juego.getPlayers();
        assertTrue(players.length == 2);
    }

    @Test
    public void shouldGetSpecifPlayer(){
        Player player = juego.getPlayer("Laura");
        assertTrue(player != null);
    }

    @Test
    public void shouldNotGetSpecifPlayer(){
        Player player = juego.getPlayer("Pedro"); // este jugador no existe
        assertTrue(player == null);
    }

    @Test 
    public void shouldGetPlayerInTurn(){
        Player player = juego.getPlayerInTurn();
        assertEquals(player.getName(), "Laura");
    }

    @Test 
    public void shouldNotGetPlayerInTurn(){
        Player player = juego.getPlayerInTurn();
        assertNotEquals(player.getName(), "Joan");
    }

    @Test 
    public void shouldGetPlayerNextTurn(){
        Player player = juego.getNextPlayerInTurn();
        assertEquals(player.getName(), "Joan");
    }

    @Test 
    public void shouldNotGetPlayerNextTurn(){
        Player player = juego.getNextPlayerInTurn();
        assertNotEquals(player.getName(), "Laura");
    }


    @After
    public void after() {
        System.out.println("After");
    }

}