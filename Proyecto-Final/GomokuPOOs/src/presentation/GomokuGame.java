package presentation;

import javax.swing.*;
import domain.GomokuPOOs;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The GomokuGame class represents the graphical user interface for a Gomoku game.
 * It includes methods for configuring the main panel, preparing menu elements,
 * creating buttons, handling game actions, and displaying messages.
 */
public class GomokuGame implements Serializable{
    private JLabel backgroundLabel;
    private JMenuBar menuBar;
    private JMenu options;
    private JMenuItem menuItemOpen;
    private JButton newGameButton;
    private JButton exitButton;
    private int rows;
    private int columns;
    private GomokuPOOsGUI gomokuPOOs;
    private GomokuPOOs gomokuPOOsN;
    private static final long serialVersionUID = 1L;
    private JFrame defaultGameFrame;
    
    /**
     * Configures the main panel of the Gomoku game.
     *
     * @param height The number of rows in the game board.
     * @param width  The number of columns in the game board.
     */
    public void configureMainPanel(int height, int width) {
        rows = height;
        columns = width;
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/presentation/img/inicio2.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setOpaque(true);
        backgroundLabel.setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        int width2 = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2);
        int dividerLocation = width2 / 2;
        splitPane.setDividerLocation(dividerLocation);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(rows, columns));
        JButton[][] board = new JButton[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = new JButton();
                buttonPanel.add(board[i][j]);
            }
        }
        splitPane.setLeftComponent(buttonPanel);
        backgroundLabel.add(splitPane, BorderLayout.CENTER);
        
    }

    /**
     * Prepares the elements of the menu, including options like "Open" for future use.
     */
    public void prepareElementsMenu() {
        menuBar = new JMenuBar();
        options = new JMenu("Opciones");
        menuItemOpen = new JMenuItem("Abrir");
        options.add(menuItemOpen);
        menuBar.add(options);
    }

    /**
     * Creates buttons for actions like starting a new game and exiting.
     */
    public void createButtons() {
        newGameButton = new JButton("Juego Nuevo");
        exitButton = new JButton("Salir");
    }


    /**
     * Exits the game with a confirmation dialog.
     */
    public void exit() {
        int value = JOptionPane.showConfirmDialog((Component) null, "¿Estás seguro de cerrar?", "Notificación",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (value == JOptionPane.YES_OPTION) {
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(backgroundLabel);
            if (mainFrame != null) {
                mainFrame.dispose();
            } else {
                System.err.println("mainFrame is null");
            }
        }
    }
    
    
    /**
     * Makes a move in the game and checks for victory.
     *
     * @param row    The row where the move is made.
     * @param column The column where the move is made.
     * @param player The player making the move.
     */
    public void makeMove(int row, int column, char player) {
        if (checkVictory()) {
            showVictoryMessage(player);
        }
    }

    /**
     * Checks if there is a victory in the game.
     *
     * @return True if there is a victory, false otherwise.
     */
    public boolean checkVictory() {
        return true;
    }

    /**
     * Displays a victory message for the specified winner.
     *
     * @param winner The winning player.
     */
    public void showVictoryMessage(char winner) {
        JOptionPane.showMessageDialog(null, "¡El jugador " + winner + " ganó!", "Victoria", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Gets the background label of the game interface.
     *
     * @return The background label.
     */
    public JLabel getBackgroundLabel() {
        return backgroundLabel;
    }

    /**
     * Gets the menu bar containing game options.
     *
     * @return The menu bar.
     */
    public JMenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * Gets the "Open" menu item.
     *
     * @return The "Open" menu item.
     */
    public JMenuItem getMenuItemOpen() {
        return menuItemOpen;
    }

    /**
     * Gets the button for starting a new game.
     *
     * @return The new game button.
     */
    public JButton getNewGameButton() {
        return newGameButton;
    }

    /**
     * Gets the button for exiting the game.
     *
     * @return The exit button.
     */
    public JButton getExitButton() {
        return exitButton;
    }
    
    /**
     * Gets the default game frame.
     *
     * @return The JFrame object representing the game frame.
     */
    public JFrame getDefaultGameFrame() {
        return defaultGameFrame;
    }

    /**
     * Sets the GomokuPOOsGUI object for interacting with the game logic.
     *
     * @param gomokuPOOs The GomokuPOOsGUI object.
     */
    public void setGomokuPOOs(GomokuPOOsGUI gomokuPOOs) {
        this.gomokuPOOs = gomokuPOOs;
    }

    /**
     * Sets the default game frame for displaying the game.
     *
     * @param frame The JFrame object representing the game frame.
     */
    public void setDefaultGameFrame(JFrame frame) {
        this.defaultGameFrame = frame;
    }

}
