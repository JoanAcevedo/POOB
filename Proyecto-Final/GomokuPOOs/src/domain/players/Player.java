package domain.players;

import java.io.Serializable;
import java.util.ArrayList;
import domain.stones.Heavy;
import domain.stones.NormalStone;
import domain.stones.Stone;
import domain.stones.Temporary;
import domain.*;

public abstract class Player implements Serializable{

    private String name;
    private String color;
    private int scoreboard;
    private boolean turn;
    private ArrayList<Stone> stones = new ArrayList<>();
    private int numStones;
    private int time = 0;
    private int totalTime = 0;
    private int contTem;
    private int numPesadas = 4;
    private int numTemporales = 0;
    private int selectedStone; 

    /**
     * Creates a new player with the specified name and color.
     *
     * @param name The name of the player.
     * @param color The color of the stones assigned to the player.
     */
    public Player(String name, String color){
        this.name = name;
        this.color = color;
        contTem = -1;
    }

    /**
     * Removes the specified stone from the player's bag of stones.
     *
     * @param stone The stone to remove.
     */
    public void takeOffStones(Stone stone) {
        stones.remove(stone);
        if (stone instanceof NormalStone) {
            losePoints(50); // -50 points for losing own stone
        }
    }

    /**
     * Removes the first stone from the player's bag of stones.
     */
    public void takeOffStones2(){
        if(numStones > 0){
            numStones --;
        }
    }
    
    /**
     * Increases the player's score by 1.
     */
    public void play(){
        updateScoreboard(1);
    }
    

    /**
     * Resets the player's score, time, and stones.
     */
    public void reset() {
        scoreboard = 0;
        time = 0;
        clearStones();
    }

    /**
     * Clears the player's bag of stones and generates a new deck.
     */
    public void clearStones() {
        stones.clear();  
        deckOfStones(); 
    }

    /**
     * Generates a new deck of 25 normal stones with the player's assigned color.
     */
    public void deckOfStones(){
        int numPesadas2 = numPesadas;
        // System.out.println("Piedras temporales en la creacion del mazo: " + numTemporales);
        // System.out.println("Piedras temporales en la creacion del mazo2: " + numTemporar);
        int numTemporales2 = numTemporales;
        for (int i = 0; i < 225; i++) {
            int tipoFicha = (int) (Math.random() * 5); // Generar un número entre 0 y 5
            if (numPesadas2 > 0 && tipoFicha == 0) {
                stones.add(new Heavy(this.color));
                numPesadas2--;
            } else if (numTemporales2 > 0 && tipoFicha == 1) {
                stones.add(new Temporary(this.color));
                numTemporales2--;
            } else {
                stones.add(new NormalStone(this.color));
            }
        }
        numStones = this.stones.size();
    }

    /**
     * Initializes the deck of limit stones for the board.
     * The deck is populated with a specific number of normal stones based on the board's dimensions.
     *
     * @param board The game board for which the deck of limit stones is created.
     */
    public void deckOfLimitStones(Board board){
        stones.clear();
        int numStones = (board.getHeight() * (board.getHeight() / 2)) - (board.getHeight() * (board.getHeight() / 3));
        for (int i = 0; i < numStones; i++) {
            stones.add(new NormalStone(this.color));
        }
    }

    /**
     * Adds a stone to the deck of limit stones.
     *
     * @param stone The stone to be added to the deck.
     */
    public void addStone(Stone stone){
        stones.add(stone);
        System.out.println("Confirmacion de casilla Golden. Tipo de ficha recibida: " + stone.getType());
    }

    
    /**
     * Updates the player's score by adding the specified points.
     *
     * @param points The number of points to add.
     */
    public void updateScoreboard(int points) {
        scoreboard += points;
        System.out.println("Player: " + name + ", Score: " + scoreboard);
    }    

    /**
     * Increases the player's current turn time by 1 second.
     * Also increments the total time spent by the player across all turns.
     */
    public void incrementTime() {
        time++;
        totalTime++;
    }

    /**
     * Increases the player's score by the specified points.
     *
     * @param points The number of points to add.
     */
    public void gainPoints(int points) {
        updateScoreboard(points);
    }

    /**
     * Decreases the player's score by the specified points.
     *
     * @param points The number of points to deduct.
     */
    public void losePoints(int points) {
        updateScoreboard(-points);
    }

    /**
     * Plays a stone on the board, updating the player's score based on the game rules.
     *
     * @param stone The stone to play.
     */
    public void playStone(Stone stone) {
        play(); // Increment the score by 1 for making a move
        if (stone instanceof Heavy) {
            gainPoints(100); // +100 points for eliminating an enemy stone
        } else if (stone instanceof Temporary) {
            gainPoints(100); // +100 points for using a special stone
        }
    }

    /**
     * Awards an extra stone to the player and resets the score to 0.
     */
    public void gainExtraStone() {
        gainPoints(100); // Award extra stone for reaching 1000 points
        resetScore();    // Reset the score to 0 after awarding the extra stone
    }

    /**
     * Resets the player's score to 0.
     */
    public void resetScore() {
        scoreboard = 0;
    }
    
    /**
     * Returns the number of stones in the player's bag.
     *
     * @return The number of stones in the bag.
     */
    public int getNumStones(){
        return numStones;
    }
    
    /**
     * Retrieves an array of stone types present in the object's deck.
     * This method iterates through the stones in the object's deck and returns an array
     * containing the types of each stone in the same order as they appear in the deck.
     * @return An array of stone types present in the object's deck.
     */
    public String[] getDeck(){
        String[] stones = new String[this.stones.size()];
        int i = 0;
        for(Stone s: this.stones){
            stones[i] = s.getType();
            i++;
        }
        return stones;
    }

    /**
     * Returns the player's current turn time (seconds).
     *
     * @return The current turn time.
     */
    public int getTime() {
        return time;
    }

    /**
     * Returns the total time spent by the player across all turns (seconds).
     *
     * @return The total time spent.
     */
    public int getTotalTime() {
        return totalTime;
    }

    /**
     * Returns the player's name.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the first stone in the player's bag of stones.
     *
     * @return The first stone in the bag.
     */
    public Stone getFirstStone(){
        if(contTem < stones.size()){
            contTem ++;
        }
        return stones.get(contTem);
    }

    /**
     * Retrieves the current value of the temporal counter.
     * This method returns the current value of the temporal counter associated with the object.
     * @return The current value of the temporal counter.
     */
    public int getContadorTem(){
        return contTem;
    }
    
    /**
     * Returns the player's assigned color.
     *
     * @return The player's color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Returns the player's current score.
     *
     * @return The player's score.
     */
    public int getScoreboard() {
        return scoreboard;
    }

    /**
     * Returns the player's turn status.
     *
     * @return True if it's the player's turn, false otherwise.
     */
    public boolean getTurn() {
        return turn;
    }

    /**
     * Gets the number of heavy stones available.
     *
     * @return The number of heavy stones.
     */
    public int getNumPesadas(){
        return this.numPesadas;
    }

    /**
     * Gets the number of temporary stones available.
     *
     * @return The number of temporary stones.
     */
    public int getNumTemporales(){
        return this.numTemporales;
    }

    /**
     * Gets the currently selected stone type.
     *
     * @return The code representing the selected stone type.
     */
    public int getSelectedStone() {
        return selectedStone;
    }

    /**
     * Sets the currently selected stone type.
     *
     * @param tipo The code representing the selected stone type.
     */
    public void setSelectedStone(int tipo) {
        this.selectedStone = tipo;
    }

    /**
     * Sets the player's turn status.
     *
     * @param turn The player's turn status.
     */
    public void setTurn(boolean turn){
        this.turn = turn;
    }

    /**
     * Sets the player's current turn time explicitly.
     *
     * @param time The new time to set (seconds).
     */
    public void setTime(int time) {
        this.time = time; 
    }

    /**
     * Sets the player's score explicitly to a specific value.
     *
     * @param score The new score to set.
     */
    public void setScore(int score) {
        this.scoreboard = score;
    }

    /**
     * Sets the player's assigned color.
     *
     * @param color The new color to be assigned.
     */
    public void setColor(String color){
        this.color = color;
    }

    /**
     * Sets the name of the object.
     * This method assigns the specified name to the object, updating its internal state.
     * @param name The new name to be set for the object.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the number of available heavy stones.
     *
     * @param valor The new value for the number of heavy stones.
     */
    public void setNumPesadas(int valor){
        this.numPesadas = valor;
    }

    /**
     * Sets the number of available temporary stones.
     *
     * @param valor The new value for the number of temporary stones.
     */
    public void lessNumTemporales(){
        this.numTemporales -= 1;
    }

    /**
     * Sets the number of available heavy stones.
     *
     * @param valor The new value for the number of heavy stones.
     */
    public void lessNumPesadas(){
        this.numPesadas -= 1;
    }

    /**
     * Sets the number of available temporary stones.
     *
     * @param valor The new value for the number of temporary stones.
     */
    public void setNumTemporales(int valor){
        this.numTemporales = valor;
    }
}
