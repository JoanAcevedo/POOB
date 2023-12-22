package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import domain.players.Human;
import domain.players.Machine;
import domain.players.Player;
import domain.stones.Stone;
import presentation.GomokuPOOsGUI;
import java.io.Serializable;
import javax.swing.Timer;

import domain.gomokus.*;

public class GomokuPOOs implements Serializable{
    private Player currentPlayer;
    private boolean gameIsFinished = false;
    public boolean thereIsAWinner = false;
    private static GomokuPOOs play = null;
    private GomokuPOOsGUI gomokuPOOs;
    private Machine gomokuLogicMachine;
    private String nombreGanador = "";
    private int[] tiempoJugador1 = {0};
    private int[] tiempoJugador2 = {0};
    private Player player;
    private int time;
    protected Board board;
    protected static int cont = 0;
    protected HashMap<String, Player> players = new HashMap<>();
    private volatile int gameTime = 0;
    private static NormalGomoku instanceNormal;
    private static LimitedStones instanceLimited;
    private static Quicktime instanceQuicktime;

    private boolean ok = false;;

    /**
     * Initializes a new GomokuPOOs object with the initial game state.
     * 
     * @param none
     * @return A new GomokuPOOs object.
     */
    public GomokuPOOs() {
        this.time = 0;
    }

    /**
     * Retrieves the singleton instance of the NormalGomoku game mode.
     *
     * @return The singleton instance of the NormalGomoku game mode.
     */
    public static NormalGomoku getInstanceNormal(){
        if(instanceNormal == null){
            instanceNormal = NormalGomoku.getInstance();
        }
        return instanceNormal;
    }

    /**
     * Retrieves the singleton instance of the LimitedStones game mode.
     *
     * @return The singleton instance of the LimitedStones game mode.
     */
    public static LimitedStones getInstanceLimited(){
        if(instanceLimited == null){
            instanceLimited = LimitedStones.getInstance();
        }
        return instanceLimited;
    }

    /**
     * Retrieves the singleton instance of the Quicktime game mode.
     *
     * @return The singleton instance of the Quicktime game mode.
     */
    public static Quicktime getInstanceQuicktime(){
        if(instanceQuicktime == null){
            instanceQuicktime = Quicktime.getInstance();
        }
        return instanceQuicktime;
    }

    /**
     * Creates a new game board with the specified dimensions.
     *
     * @param height The height of the board
     * @param width The width of the board
     */
    public void addBoard(int height, int width){
        if(height > 0 && width > 0){
            this.board = new Board(this, height, width);
            this.ok = true;
        } else {
            this.ok = false;
        }
    }
    
    /**
     * Places a stone on the board at the specified position for the current player.
     *
     * @param i The row index of the position
     * @param j The column index of the position
     */
    public void play(int i, int j) {
        if(i >= 0 && j >= 0 && i <= getBoard().getWidth() && j <= getBoard().getHeight()){
            Player player = getPlayerInTurn();
            Stone stoneP = player.getFirstStone();
            if(stoneP.getType().equals("Heavy")){
                player.lessNumPesadas();
            } else if(stoneP.getType().equals("Temporary")){
                player.lessNumTemporales();
            } 
            board.play(stoneP, i, j);
            player.playStone(stoneP);
            if (board.getNumberOfMoves() == 0) {
                startGameTimer();
            }
            hasPlayerWon(player, stoneP, i ,j );
            player.takeOffStones2(); 
            System.out.println("Numero de piedras que le quedan: " + player.getNumStones());
            currentPlayer = getNextPlayerInTurn();
            currentPlayer.setTurn(true);
            player.setTurn(false);
            this.ok = true;
        } else {
            this. ok = false;
        }

        
    }

    /**
     * Checks if the specified player has won the game based on the last move.
     *
     * @param player The player to check for victory.
     * @param stoneP The stone placed in the last move.
     * @param i The row index of the last move.
     * @param j The column index of the last move.
     * @return True if the player has won, false otherwise.
     */
    public boolean hasPlayerWon(Player player, Stone stoneP, int i , int j){
        boolean finalizoJuego = false;
        String winnerColor = board.checkForWinner(stoneP, i, j);
        if(winnerColor.equals("")){
            finalizoJuego = false;
        } else {
            finalizoJuego = true;
            playerWin(winnerColor);
        }
        return finalizoJuego;
    }

    /**
     * Checks if the game is over by determining if there is a winner.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        String ganador = getWinner();
        return !ganador.equals("");
    }
    
    /**
     * Checks if the Gomoku game has ended in a draw.
     *
     * @return {@code true} if the game has ended in a draw, {@code false} otherwise.
     */
    public boolean isGameDraw() {
        return board.isGameDraw();
    }

    /**
     * Updates the score label for the specified player on the GUI.
     *
     * @param playerName The name of the player whose score label will be updated.
     * @param scoreLabel The JLabel representing the player's score on the GUI.
     */
    public void updateScoreLabel(String playerName, JLabel scoreLabel) {
        Player player = getPlayer(playerName);
        if (player != null) {
            scoreLabel.setText("Puntaje: " + player.getScoreboard());
        }
    }

    /**
     * Resets the game board and players.
     */
    public void reset() {
        board.reset();
        players.clear();
        currentPlayer = null;

    }

    /**
     * Adds a machine player to the game.
     *
     * @param name The player's name
     * @param color The player's color
     */
    public void addMachine(String name, String color){
        Player player = new Machine(name, color);
        players.put(name, player);
        if(cont == 0){
            player.setTurn(true);
            cont++;
        } else {
            player.setTurn(false);
        }
    }

    /**
     * Adds a human player to the game.
     *
     * @param name The player's name
     * @param color The player's color
     */
    public void addPlayer(String name, String color){
        Player player = new Human(name, color);
        players.put(name, player);
        if(cont == 0){
            player.setTurn(true);
            cont++;
        } else {
            player.setTurn(false);
        }
    }

    /**
     * Checks if there is a winner on the current board.
     *
     * @return True if there is a winner, false otherwise
     */
    public boolean isThereAWinner() {
        Player currentPlayer = getCurrentPlayer();
        Stone stone = currentPlayer.getFirstStone(); 
        int row = board.getRow();
        int column = board.getWidth();
        return board.isThereAWinner(stone, row, column);
    }
    
    /**
     * Resets the game board and all player attributes to their initial state.
     */
    public void resetBoard() {
        board.reset(); 
        currentPlayer = null;
        time = 0;
        cont = 0;
        for (Player player : players.values()) {
            player.reset(); 
            if (cont == 0) {
                player.setTurn(true);
                cont++;
            } else {
                player.setTurn(false);
            }
        }
    }  
    
    /**
     * Gets the name of the winner in the Gomoku game.
     *
     * @return The name of the player who won the game, or an empty string if no winner yet.
     */
    public String getWinner(){
        return this.nombreGanador;
    }

    /**
     * Returns the current game board object.
     *
     * @return The Board object representing the game board.
     */
    public Board getBoard(){
        return board;
    }
    
    /**
     * Returns an array of player names participating in the game.
     *
     * @return An array of strings containing player names.
     */
    public String[] getPlayers(){
        String[] players2 = new String[2];
        int i = 0;
        for (String nombre : players.keySet()) {
            players2[i] = nombre;
            i++;
        }
        return players2;
    }
    
    /**
     * Retrieves a specific player object by their name.
     *
     * @param name The name of the player to retrieve.
     * @return The Player object associated with the provided name.
     */
    public Player getPlayer(String name){
        return players.get(name);
    }

    /**
     * Finds and returns the player whose turn it is currently.
     *
     * @return The Player object representing the player in turn, or null if no player has turn.
     */
    public Player getPlayerInTurn(){
        for (String nombre : players.keySet()) {
            if(players.get(nombre).getTurn()){
                return players.get(nombre);
            }
        }
        return null;
    }

    /**
     * Gets the player whose turn it is currently.
     *
     * @return The Player object representing the current player.
     */
    public Player getCurrentPlayer() {
        for (String nombre : players.keySet()) {
            if (players.get(nombre).getTurn()) {
                return players.get(nombre);
            }
        }
        return null;
    }

    
    /**
     * Finds and returns the player who will take the next turn.
     *
     * @return The Player object representing the player who will play next, or null if no player has turn assigned.
     */
    public Player getNextPlayerInTurn() {
        for (String nombre : players.keySet()) {
            if (!players.get(nombre).getTurn()) {
                return players.get(nombre);
            }
        }
        return null;
    }

    /**
     * Declares the winner based on the color of the winning player.
     *
     * @param winnerColor The color of the winning player.
     */
    private void playerWin(String winnerColor){
        for(String nombre : players.keySet()){
            if(getPlayer(nombre).getColor().equals(winnerColor)){
                this.nombreGanador = getPlayer(nombre).getName();
            }
        }
        // If there is no winner, winningPlayerName will be an empty string
        // If there is a winner, it will contain the name of the winning player
        System.out.println("El jugador que gano fue: " + this.nombreGanador);
        System.out.println("El color que gano fue: " + getPlayer(this.nombreGanador).getColor());
    }

    /**
     * Starts a background thread to track and update individual player time spent in the game.
     */
    public void startGameTimer() {
        final int[] lastPlayerTime = {0}; 
        Thread timerThread = new Thread(() -> {
            while (!gameIsFinished) {
                try {
                    Thread.sleep(1000);
                    Player currentPlayer = getCurrentPlayer();
                    if (currentPlayer != null) {
                        currentPlayer.incrementTime();
                        int playerTime = currentPlayer.getTime();
                        gameTime = playerTime;
                        lastPlayerTime[0] = playerTime;
                        System.out.println("Tiempo transcurrido para " + currentPlayer.getName() + ": " + playerTime + " segundos");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Tiempo total para el último jugador: " + lastPlayerTime[0] + " segundos");
        });
        timerThread.start();
    }

    /**
     * Retrieves the total elapsed time of the Gomoku game.
     *
     * @return The total elapsed time of the game in seconds.
     */
    public int getGameTime() {
        return gameTime;
    }
 
    /**
     * Starts a timer for the specified player and updates the provided JLabel with the elapsed time.
     *
     * @param playerName The name of the player for whom the timer is started.
     * @param timeLabel  The JLabel to display the elapsed time.
     */
    public void startTimerForPlayer(String playerName, JLabel timeLabel) {
        Timer timer;
        int[] tiempoJugador = (playerName.equals("Jugador 1")) ? tiempoJugador1 : tiempoJugador2;
        timer = startTimer(1000, timeLabel, () -> tiempoJugador[0]++);
    }

    /**
     * Starts a timer with the given interval, updates the provided JLabel on each tick, and returns the Timer instance.
     *
     * @param interval  The time interval between ticks, in milliseconds.
     * @param timeLabel The JLabel to update with the elapsed time.
     * @param onTick    The action to be performed on each tick of the timer.
     * @return The Timer instance representing the started timer.
     */
    private Timer startTimer(int interval, JLabel timeLabel, Runnable onTick) {
        Timer timer = new Timer(interval, e -> {
            onTick.run();
            timeLabel.setText("Tiempo invertido: " + formatTiempo(tiempoJugador1[0]));
        });
        timer.start();
        return timer;
    }

    /**
     * Formats the given time in seconds into a human-readable string representation (minutes:seconds).
     *
     * @param seconds The time in seconds to be formatted.
     * @return A formatted string representing the elapsed time.
     */
    private static String formatTiempo(int segundos) {
        int minutos = segundos / 60;
        segundos = segundos % 60;
        return String.format("%d:%02d", minutos, segundos);
    }

    public boolean ok(){
        return this.ok;
    }

}
