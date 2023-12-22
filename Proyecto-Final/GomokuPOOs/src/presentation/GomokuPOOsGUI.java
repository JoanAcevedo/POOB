package presentation;

import javax.swing.*;
import domain.GomokuPOOs;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * The GomokuPOOsGUI class represents the graphical user interface for the Gomoku game using object-oriented design.
 */
public class GomokuPOOsGUI extends JFrame implements Serializable {

    private GomokuGame gomokuGame;
    private GomokuOptions gomokuOptions;
    private JButton[][] board;
    private char currentPlayer;
    private GomokuPOOs gomokuPOOs;
    private JLabel timeLabel;
    private Persistence persistence;
    
    /**
     * Constructs a new GomokuPOOsGUI.
     */
    public GomokuPOOsGUI() {
        this.gomokuPOOs = new GomokuPOOs();
        gomokuPOOs.addBoard(15, 15); // logica
        prepareElements();
        prepareActions();this.persistence = new Persistence();
    }

    /**
     * Retrieves the game board.
     *
     * @return The 2D array of JButtons representing the game board.
     */
    public JButton[][] getBoard(){
        return this.board;
    }

    /**
     * Retrieves the game logic.
     *
     * @return The GomokuPOOs object representing the game logic.
     */
    // public GomokuPOOs getGomokuNormal(){
    //     return gomokuPOOs; 
    // }

    public GomokuPOOs getGomokuNormal(){
        gomokuPOOs = GomokuPOOs.getInstanceNormal();
        return gomokuPOOs;
    }

    public GomokuPOOs getGomokuLimited(){
        System.out.println("Se selecciono el juego limitado");
        gomokuPOOs = GomokuPOOs.getInstanceLimited();
        return gomokuPOOs;
    }

    public GomokuPOOs getGomokuQuicktime(){
        gomokuPOOs = GomokuPOOs.getInstanceQuicktime();
        return gomokuPOOs;
    }

    /**
     * Retrieves the current player.
     *
     * @return The current player (X or O).
     */
    public char getCurrentPlayer() {
        return currentPlayer;
    }

    
    /**
     * Restarts the current game and refreshes the game interface.
     */
    public void restartGame() {
        if (gomokuPOOs != null) {
            gomokuPOOs.resetBoard();
            configureMainPanel();
            initComponents();
            setLayout();
            pack();
        }
    }
    
    /**
     * Resets the game to its initial state.
     */
    public void reset() {
        gomokuPOOs.reset();
    }
    
    public void setTimeLabel(JLabel label) {
        this.timeLabel = label; 
      }

    /**
     * Displays a message indicating the winner of the game.
     *
     * @param player The name of the winning player.
     */
    public void displayWinnerMessage() {
        //Aca se supone que va lo de el mensaje cuando gana
        String ganador = gomokuPOOs.getWinner();
        if(!ganador.equals("")){
            JOptionPane.showMessageDialog(this, ganador + " ha ganado el juego!", "Felicitaciones!", JOptionPane.INFORMATION_MESSAGE);
        }
        // SwingUtilities.invokeLater(() -> {
        //     JOptionPane.showMessageDialog(this, player + " ha ganado el juego!", "Felicitaciones!", JOptionPane.INFORMATION_MESSAGE);
        // });
    }

    /**
     * Prepares the elements of the GUI, including the main panel, menu, and game options.
     */
    private void prepareElements() {
        this.setTitle("GomokuPOOs");
        int width = 800;
        int height = 800;
        this.setSize(width, height);
        this.setResizable(false); 
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int x = (screenWidth - width) / 2;
        int y = (screenHeight - height) / 2;
        this.setLocation(x, y);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.gomokuGame = new GomokuGame(); 
        this.gomokuOptions = new GomokuOptions(this); 
        this.configureMainPanel();
        this.setContentPane(gomokuGame.getBackgroundLabel());
        this.prepareElementsMenu();
        this.setJMenuBar(gomokuGame.getMenuBar());
        this.initComponents();
        this.pack();
    }

    /**
     * Prepares the elements of the menu.
     */
    private void prepareElementsMenu() {
        gomokuGame.prepareElementsMenu();
    }

    /**
     * Configures the main panel of the game.
     */
    private void configureMainPanel() {
        gomokuGame.configureMainPanel(12,12); 
    }

    /**
     * Initializes the components of the GUI, including buttons and action listeners.
     */
    private void initComponents() {
        gomokuGame.createButtons();
        setLayout();
        board = new JButton[12][12];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                board[i][j] = new JButton();
                int finalI = i;
                int finalJ = j;
                board[i][j].addActionListener(e -> makeMove(finalI, finalJ));
            }
        }
    }

    /**
     * Handles a move made by the player.
     *
     * @param row    The row where the move is made.
     * @param column The column where the move is made.
     */
    private void makeMove(int row, int column) {
        char currentPlayer = getCurrentPlayer(); 
        gomokuGame.makeMove(row, column, currentPlayer);
        displayWinnerMessage();
    }  
    

    /**
     * Prepares the actions for menu items and buttons.
     */
    private void prepareActions() {
        gomokuGame.getMenuItemOpen().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
        
        gomokuGame.getNewGameButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                newGame();
            }
        });
        gomokuGame.getExitButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exit();
            }
        });
    }

    /**
     * Exits the game with a confirmation dialog.
     */
    private void exit() {
        gomokuGame.exit();
    }

    /**
     * Opens a saved game (to be implemented in the future).
     */
    private void open() {
        persistence.loadGame();
    }

    /**
     * Starts a new game, prompting the user for game options.
     */
    private void newGame() {
        JPanel optionsPanel = gomokuOptions.createOptionsPanel();
        int option = JOptionPane.showOptionDialog(this, optionsPanel, "Selecciona el modo de juego",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.OK_OPTION) {
            gomokuOptions.handleOptionsSelection(optionsPanel);
        }
    }

    /**
     * Sets the layout of the GUI using GroupLayout.
     */
    private void setLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(470, 470, 470)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(gomokuGame.getNewGameButton(), GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                                        .addComponent(gomokuGame.getExitButton(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                                .addContainerGap(470, Short.MAX_VALUE)));

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(190, 190, 190)
                                .addComponent(gomokuGame.getNewGameButton(), GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(gomokuGame.getExitButton(), GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(110, Short.MAX_VALUE)));
    }

    /**
     * The main method to start the GomokuPOOsGUI application.
     *
     * @param args The command line arguments.
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            GomokuPOOsGUI gui = new GomokuPOOsGUI();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setVisible(true);
        });
    }
    
}
