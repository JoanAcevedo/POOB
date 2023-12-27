package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

/**
 * Represents the game board.
 * @author Joan Acevedo / Laura Rodríguez
 * @version 11/18/2023
 */
public class Board {
    private Board board;
    private int HEIGHT;
    private int WIDTH;
    private static Box[][] boxes;

    /**
     * Constructs the game board.
     * @param height Number of vertical cells in the board.
     * @param width Number of horizontal cells in the board.
     */
    public Board(int height, int width) {
        this.HEIGHT = height;
        this.WIDTH = width;
        this.boxes = new Box[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                int[] positionBox = {i, j};
                boxes[i][j] = new Box(positionBox);
            }
        }
    }

    /**
     * Moves gems from one position to another based on the player's action.
     * @param position1 Initial position of the gem.
     * @param position2 Final position of the gem.
     */
    public void play(int[] position1, int[] position2){
        Gem gemS = boxes[position1[0]][position1[1]].getGem();
        boxes[position1[0]][position1[1]].setGem(boxes[position2[0]][position2[1]].getGem());
        boxes[position2[0]][position2[1]].setGem(gemS);
    }

    /**
     * Resets the state of the board.
     */
    public void reset() {
        Random random = new Random();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                boolean isBlack = (i + j) % 2 == 0;
                boxes[i][j] = new Box(new int[]{i, j});
            }
        }
    }
    
     /**
     * Verifies and returns an array of boxes containing aligned gems.
     * Replaces the gems in the aligned boxes with a placeholder gem.
     * @return An array of boxes containing aligned gems.
     */
    public Box[] verificar(){
        List<Box[]> listaDeArrays = encontrarAlineacion();
        int totalLength = 0;
        for (Box[] array : listaDeArrays) {
            totalLength += array.length;
        }
        Box[] resultado = new Box[totalLength];
        int index = 0;
        for (Box[] array : listaDeArrays) {
            System.arraycopy(array, 0, resultado, index, array.length);
            index += array.length;
        }
        
        for(int i = 0; i < resultado.length; i++){
            resultado[i].setGem(new Gem("x"));
        }
        return resultado;
    }
   
    /**
     * Finds and returns an array of boxes containing aligned gems on the game board.
     * Replaces the gems in the aligned boxes with a placeholder gem.
     * @return An array of boxes containing aligned gems.
     */
    private static List<Box[]> encontrarAlineacion() {
        List<Box[]> posiciones = new ArrayList<>();
        int n = boxes.length;
        for (int i = 0; i < n; i++) {
            posiciones.addAll(encontrarAlineacionEnLinea(boxes[i], 3));
        }
        for (int j = 0; j < n; j++) {
            Box[] columna = new Box[n];
            for (int i = 0; i < n; i++) {
                columna[i] = boxes[i][j];
            }
            posiciones.addAll(encontrarAlineacionEnLinea(columna, 3));
        }
        for (int i = 0; i <= n - 3; i++) {
            for (int j = 0; j <= n - 3; j++) {
                Box[] diagonal = new Box[n - Math.max(i, j)];
                for (int k = 0; k < n - Math.max(i, j); k++) {
                    diagonal[k] = boxes[i + k][j + k];
                }
                posiciones.addAll(encontrarAlineacionEnLinea(diagonal, 3));
            }
        }
        for (int i = 0; i <= n - 3; i++) {
            for (int j = n - 1; j >= 3 - 1; j--) {
                Box[] diagonal = new Box[n - Math.max(i, n - 1 - j)];
                for (int k = 0; k < n - Math.max(i, n - 1 - j); k++) {
                    diagonal[k] = boxes[i + k][j - k];
                }
                posiciones.addAll(encontrarAlineacionEnLinea(diagonal, 3));
            }
        }
        return posiciones;
    }

    /**
     * Finds and returns an array of boxes containing aligned gems in a given line.
     * @param line The line of boxes to check.
     * @param quantity The minimum quantity of aligned gems.
     * @return An array of boxes containing aligned gems in the line.
     */
    private static List<Box[]> encontrarAlineacionEnLinea(Box[] linea, int cantidad) {
        List<Box[]> posiciones = new ArrayList<>();
        int count = 1;
        int start = 0;
        for (int i = 1; i < linea.length; i++) {
            if (linea[i].getGemColor().equals(linea[i - 1].getGemColor())) {
                count++;
                if (count == cantidad) {
                    start = i - cantidad + 1;
                    Box[] posicion = new Box[cantidad];
                    for (int j = 0; j < cantidad; j++) {
                        posicion[j] = linea[start + j];
                    }
                    posiciones.add(posicion);
                }
            } else {
                count = 1;
            }
        }
        return posiciones;
    }
    
    /**
     * Gets the height of the game board.
     * @return The height of the board.
     */
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * Gets the width of the game board.
     * @return The width of the board.
     */
    public int getWidth() {
        return WIDTH;
    }
    
    /**
     * Gets the box at the specified coordinates.
     * @param x The vertical coordinate.
     * @param y The horizontal coordinate.
     * @return The box at the specified coordinates.
     */
    public Box getBox(int x, int y){
        return boxes[x][y];
    }
}

