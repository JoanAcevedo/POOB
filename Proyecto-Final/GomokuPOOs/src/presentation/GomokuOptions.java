package presentation;

import javax.swing.*;
import javax.swing.Box.Filler;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import domain.stones.Stone;
import domain.GomokuPOOs;
import domain.boxes.Box;
import domain.boxes.Mine;
import domain.boxes.Teleport;
import domain.players.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.event.DocumentEvent;

/**
 * The GomokuOptions class represents the graphical user interface for configuring Gomoku game options.
 */
public class GomokuOptions extends JFrame implements Serializable{
    private JTextField name1, name2, heightField,widthField,temporaryField,heavyField,goldenField,mineField,teleportField;
    private JRadioButton gameTypeNormal,gameTypeQuicktime,gameTypeLimitStones;
    private JRadioButton gameTypeAggressive, gameTypeExpert,gameTypeFearful;
    private JComboBox<String> combo1;
    private JComboBox<String> combo2;
    private JRadioButton jugadorVsJugadorButton, jugadorVsMaquinaButton,juegoPredeterminadoButton, personalizarJugadoresButton;
    private int height; 
    private int width; 
    private GomokuGame gomokuGame;
    private GomokuPOOsGUI gomokuPOOs;
    private JButton[][] botones;
    private JPanel customPanel;
    private String colorSelect1, colorSelect2;
    private JTextField machineNameField, machineColorField;
    private boolean gameOver = false;
    private boolean goldenBoxClicked = false;
    private JLabel scoreLabelPlayer1;
    private JLabel scoreLabelPlayer2;
    private boolean messageDisplayed = false;
    private String playerName1, playerName2;
    private int newHeight,newWidth;
    private String selectedStoneType = "Normal";
    private Persistence persistence;
    private boolean warningShown = false;
    /**
     * Constructs a new GomokuOptions.
     *
     * @param gomokuPOOsGUI The GomokuPOOsGUI instance to interact with the main game.
     */
    public GomokuOptions(GomokuPOOsGUI gomokuPOOs) {
        this.gomokuPOOs = gomokuPOOs;
        botones = new JButton[height][width]; 
        this.persistence = new Persistence();
    }

    /**
     * Creates the main options panel for the game.
     *
     * @return The JPanel containing the main options for the game.
     */
    public JPanel createOptionsPanel() {
        //this.gomokuPOOs = new GomokuPOOsGUI();
        JPanel optionsPanel = new JPanel(new GridLayout(3, 1));
        jugadorVsJugadorButton = new JRadioButton("Jugador vs Jugador");
        jugadorVsMaquinaButton = new JRadioButton("Jugador vs Máquina");
        ButtonGroup group = new ButtonGroup();
        group.add(jugadorVsJugadorButton);
        group.add(jugadorVsMaquinaButton);
        optionsPanel.add(jugadorVsJugadorButton);
        optionsPanel.add(jugadorVsMaquinaButton);
        return optionsPanel;
    }

    /**
     * Handles the selection of game options made by the player.
     *
     * @param optionsPanel The JPanel containing the game options.
     */
    public void handleOptionsSelection(JPanel optionsPanel) {
        JRadioButton jugadorVsJugadorButton = (JRadioButton) optionsPanel.getComponent(0);
        JRadioButton jugadorVsMaquinaButton = (JRadioButton) optionsPanel.getComponent(1);

        if (jugadorVsJugadorButton.isSelected()) {
            handleJugadorVsJugadorSelection();
        } else if (jugadorVsMaquinaButton.isSelected()) {
            handleJugadorVsMaquinaSelection();
        }
    }

    /**
     * Creates a panel for customizing game options when playing against the machine.
     */
    public void personalizateNewMachinePanel() {
        JPanel customPanel = createCustomMachinePanel();
        int result = JOptionPane.showConfirmDialog(this, customPanel, "Personalizar Jugador y Maquina",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (validateFieldsMachine()) {
                try {
                    handleGameConfigurationMachine();
                    this.dispose();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Ingrese valores válidos para alto y ancho.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    personalizateNewMachinePanel();
                }
            }
        }
    }

    /**
     * Displays a dialog for the user to customize game settings and handles the configuration based on their selections.
     */
    public void personalizateNewPanel() {
        JPanel customPanel = createCustomPanel();
        int result = JOptionPane.showConfirmDialog(this, customPanel, "Personalizar Jugadores",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (validateFields()) {
                try {
                    handleGameConfiguration();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Ingrese valores válidos para alto y ancho.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    personalizateNewPanel();
                }
            }
        }
    }

    /**
     * Restarts the current game.
     */
    public void restartGame() {
        gomokuPOOs.restartGame();
    }


    /**
     * Handles the selection of Player vs. Machine game mode.
     */
    private void handleJugadorVsMaquinaSelection() {
        this.gomokuGame = new GomokuGame();
        JPanel typePanel = createOptionsPanel(true);
        int typeOption = JOptionPane.showOptionDialog(this, typePanel, "Tipo de juego",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (typeOption == JOptionPane.OK_OPTION) {
            if (juegoPredeterminadoButton.isSelected()) {
                createPanelPredeterminateMaquina();
            } else if (personalizarJugadoresButton.isSelected()) {
                personalizateNewMachinePanel();
            }
        }
    }

    /**
     * Creates and displays a default game panel for the machine with predetermined dimensions.
     *
     * This method is a convenience method that invokes the {@code showDefaultGamePanelMachine}
     * method with default dimensions of 15 rows and 15 columns.
     */
    private void createPanelPredeterminateMaquina(){
        showDefaultGamePanelMachine(15,15);
    }

    /**
     * Displays a default game panel for the machine with the specified dimensions.
     * If an existing Gomoku game and default game frame are present, the frame is disposed of.
     * A new default game frame with the specified dimensions is then created and displayed.
     *
     * @param height The number of rows in the default game panel.
     * @param width  The number of columns in the default game panel.
     */
    private void showDefaultGamePanelMachine(int height, int width) {
        if (gomokuGame != null && gomokuGame.getDefaultGameFrame() != null) {
            gomokuGame.getDefaultGameFrame().dispose();
        }
        int defaultHeight = height;
        int defaultWidth = width;
        JFrame defaultGameFrame = createDefaultGameFrame();
        JMenuBar menuBar = createDefaultGameMenuBar(defaultGameFrame);
        defaultGameFrame.setJMenuBar(menuBar);
        JPanel defaultGamePanel = createMainPanel(defaultHeight, defaultWidth);
        defaultGameFrame.getContentPane().add(defaultGamePanel);
        defaultGameFrame.setVisible(true);
        gomokuGame.setDefaultGameFrame(defaultGameFrame);
    }

    /**
     * Handles the game configuration based on the selected options.
     */
    private void handleGameConfigurationMachine() {
        int newHeight = Integer.parseInt(heightField.getText());
        int newWidth = Integer.parseInt(widthField.getText());
        this.height = newHeight;
        this.width = newWidth;
        gomokuPOOs.restartGame();
        gomokuGame.configureMainPanel(height, width);
        JPanel updatedTableroPanel = createBoardPanel(height, width);
        gomokuGame.getBackgroundLabel().removeAll();
        gomokuGame.getBackgroundLabel().add(updatedTableroPanel, BorderLayout.CENTER);
        gomokuGame.getBackgroundLabel().revalidate();
        gomokuGame.getBackgroundLabel().repaint();
        showDefaultGamePanel(height, width);
    }

    /**
     * Handles the selection of Player vs. Player game mode.
     */
    private void handleJugadorVsJugadorSelection() {
        this.gomokuGame = new GomokuGame();
        JPanel typePanel = createOptionsPanel(true);
        int typeOption = JOptionPane.showOptionDialog(this, typePanel, "Tipo de juego",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (typeOption == JOptionPane.OK_OPTION) {
            if (juegoPredeterminadoButton.isSelected()) {
                createPanelPredeterminate();
            } else if (personalizarJugadoresButton.isSelected()) {
                personalizateNewPanel();
            }
        }
    }

    /**
     * Validates the fields in the machine customization panel.
     *
     * @return True if the fields are valid, false otherwise.
     */
    private boolean validateFieldsMachine() {
        String colorJugador1 = combo1.getSelectedItem().toString();
        String colorJugador2 = combo2.getSelectedItem().toString();
        String heightValue = heightField.getText();
        String widthValue = widthField.getText();
        if (name1.getText().isEmpty() || name2.getText().isEmpty() || heightField.getText().isEmpty() || widthField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            personalizateNewMachinePanel();
            return false;
        }
        try {
            int height = Integer.parseInt(heightValue);
            int width = Integer.parseInt(widthValue);

            if (height < 5 || width < 5) {
                JOptionPane.showMessageDialog(this, "Las dimensiones deben ser mayores o iguales a 5.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores válidos para alto y ancho.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (colorJugador1.equals(colorJugador2)) {
            JOptionPane.showMessageDialog(this, "Los colores de los jugadores deben ser diferentes.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            personalizateNewMachinePanel();
            return false;
        }
        return true;
    }

    /**
     * Creates a custom panel for configuring game settings, including game type, player names, colors, dimensions, and machine type.
     *
     * @return The JPanel containing the custom configuration options.
     */
    private JPanel createCustomMachinePanel() {
        JPanel customPanel = new JPanel(new GridLayout(0, 2));
        addGameTypeSection(customPanel);
        addNameSection(customPanel);
        addColorSection(customPanel);
        addDimensionSection(customPanel);
        addGameMachineSection(customPanel);
        return customPanel;
    }

    /**
     * Adds the game machine type selection section to the custom panel, allowing users to choose the type of machine opponent.
     *
     * @param customPanel The JPanel to which the game machine type section will be added.
     */
    private void addGameMachineSection(JPanel customPanel) {
        JPanel gameTypeMachineSection = new JPanel(new GridLayout(3, 1));
        gameTypeMachineSection.setBorder(BorderFactory.createTitledBorder("Tipo de Maquina"));
        gameTypeAggressive = new JRadioButton("Máquina Agresiva");
        gameTypeExpert = new JRadioButton("Máquina Experta");
        gameTypeFearful = new JRadioButton("Máquina Miedosa");
        ButtonGroup group = new ButtonGroup();
        group.add(gameTypeAggressive);
        group.add(gameTypeExpert);
        group.add(gameTypeFearful);
        gameTypeMachineSection.add(gameTypeAggressive);
        gameTypeMachineSection.add(gameTypeExpert);
        gameTypeMachineSection.add(gameTypeFearful);
        customPanel.add(gameTypeMachineSection);
    }

    /**
     * Creates a custom panel for configuring game settings, including game type, player names, colors, and dimensions.
     *
     * @return The JPanel containing the custom configuration options.
     */
    private JPanel createCustomPanel() {
        JPanel customPanel = new JPanel(new GridLayout(0, 2));
        addGameTypeSection(customPanel);
        addNameSection(customPanel);
        addColorSection(customPanel);
        addDimensionSection(customPanel);
        addPercentageStones(customPanel);
        addPercentageBoxes(customPanel);
        return customPanel;
    }

    /**
     * Adds the game type selection section to the custom panel, allowing users to choose between different game types.
     *
     * @param customPanel The JPanel to which the game type section will be added.
     */
    private void addGameTypeSection(JPanel customPanel) {
        JPanel gameTypeSection = new JPanel(new GridLayout(3, 1));
        gameTypeSection.setBorder(BorderFactory.createTitledBorder("Tipo de Juego"));
        gameTypeNormal = new JRadioButton("Juego normal");
        gameTypeQuicktime = new JRadioButton("Juego de tiempo rápido");
        gameTypeLimitStones = new JRadioButton("Juego con piedras limitadas");
        ButtonGroup group = new ButtonGroup();
        group.add(gameTypeNormal);
        group.add(gameTypeQuicktime);
        group.add(gameTypeLimitStones);
        gameTypeSection.add(gameTypeNormal);
        gameTypeSection.add(gameTypeQuicktime);
        gameTypeSection.add(gameTypeLimitStones);
        customPanel.add(gameTypeSection);
    }



    /**
     * Adds the player name input section to the custom panel, allowing users to enter the names of the players.
     *
     * @param customPanel The JPanel to which the name section will be added.
     */
    private void addNameSection(JPanel customPanel) {
        JPanel nameSection = new JPanel(new GridLayout(2, 2));
        nameSection.setBorder(BorderFactory.createTitledBorder("Nombres jugadores"));
        JLabel labelNameP1 = new JLabel("Nombre Jugador 1:");
        name1 = new JTextField();
        JLabel labelNameP2 = new JLabel("Nombre Jugador 2:");
        name2 = new JTextField();
        nameSection.add(labelNameP1);
        nameSection.add(name1);
        nameSection.add(labelNameP2);
        nameSection.add(name2);
        customPanel.add(nameSection);
    }

    /**
     * Adds the color selection section to the custom panel, allowing users to choose colors for each player.
     *
     * @param customPanel The JPanel to which the color section will be added.
     */
    private void addColorSection(JPanel customPanel) {
        JPanel colorSection = new JPanel(new GridLayout(5, 2));
        colorSection.setBorder(BorderFactory.createTitledBorder("Colores"));
        JLabel labelCombo1 = new JLabel("Seleccionar color Jugador 1:");
        combo1 = new JComboBox<>();
        combo1.addItem("Rojo");
        combo1.addItem("Azul");
        combo1.addItem("Verde");
        combo1.addItem("Morado");
        combo1.addItem("Amarillo");
        combo1.addItem("Blanco");
        combo1.addItem("Negro");
        JLabel labelCombo2 = new JLabel("Seleccionar color Jugador 2:");
        combo2 = new JComboBox<>();
        combo2.addItem("Azul");
        combo2.addItem("Rojo");
        combo2.addItem("Verde");
        combo2.addItem("Morado");
        combo2.addItem("Amarillo");
        combo2.addItem("Blanco");
        combo2.addItem("Negro");
        colorSection.add(labelCombo1);
        colorSection.add(combo1);
        colorSection.add(labelCombo2);
        colorSection.add(combo2);
        customPanel.add(colorSection);
    }

    /**
     * Adds the dimension input section to the custom panel, allowing users to set the height and width of the game board.
     *
     * @param customPanel The JPanel to which the dimension section will be added.
     */
    private void addDimensionSection(JPanel customPanel) {
        JPanel dimensionSection = new JPanel(new GridLayout(2, 2));
        dimensionSection.setBorder(BorderFactory.createTitledBorder("Dimensiones"));
        JLabel labelHeight = new JLabel("Alto:");
        heightField = new JTextField();
        JLabel labelWidth = new JLabel("Ancho:");
        widthField = new JTextField();
        dimensionSection.add(labelHeight);
        dimensionSection.add(heightField);
        dimensionSection.add(labelWidth);
        dimensionSection.add(widthField);
        customPanel.add(dimensionSection);
        addHeightDocumentListener();
        addWidthDocumentListener();
    }

    private void addPercentageStones(JPanel customPanel) {
        JPanel percentageSection = new JPanel(new GridLayout(2, 2));
        percentageSection.setBorder(BorderFactory.createTitledBorder("Numero de piedras especiales"));
        JLabel labelTemporary = new JLabel("Temporal:");
        //player1Button.addActionListener(e -> showPlayerInfo(playerName2));
        temporaryField = new JTextField();
        JLabel labelHeavy = new JLabel("Pesada:");
        //player1Button.addActionListener(e -> showPlayerInfo(playerName2));
        heavyField = new JTextField();
        percentageSection.add(labelTemporary);
        percentageSection.add(temporaryField);
        percentageSection.add(labelHeavy);
        percentageSection.add(heavyField);
        customPanel.add(percentageSection);
        addHeightDocumentListener();
        addWidthDocumentListener();
    }

    private void addPercentageBoxes(JPanel customPanel) {
        JPanel percentageSection = new JPanel(new GridLayout(3, 3));
        percentageSection.setBorder(BorderFactory.createTitledBorder("Numero de casillas especiales"));
        JLabel labelGolden = new JLabel("Doradas:");
        goldenField = new JTextField();
        JLabel labelMine = new JLabel("Minas:");
        mineField = new JTextField();
        JLabel labelTeleport = new JLabel("Teletransportar:");
        teleportField = new JTextField();
        percentageSection.add(labelGolden);
        percentageSection.add(goldenField);
        percentageSection.add(labelMine);
        percentageSection.add(mineField);
        percentageSection.add(labelTeleport);
        percentageSection.add(teleportField);
        customPanel.add(percentageSection);
        addHeightDocumentListener();
        addWidthDocumentListener();
    }

    /**
     * Adds a document listener to the height input field to validate input as the user types.
     */
    private void addHeightDocumentListener() {
        heightField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateDimensions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateDimensions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateDimensions();
            }
        });
    }

    /**
     * Adds a document listener to the width input field to validate input as the user types.
     */
    private void addWidthDocumentListener() {
        widthField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateDimensions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateDimensions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateDimensions();
            }
        });
    }

    /**
     * Validates the dimensions entered by the user and shows a warning if the values are invalid.
     */
    private void validateDimensions() {
        try {
            int newHeight = Integer.parseInt(heightField.getText());
            int newWidth = Integer.parseInt(widthField.getText());
    
            if (newHeight < 10 || newWidth < 10 || newHeight >= 30 || newWidth >= 30) {
                if (!warningShown) {
                    JOptionPane.showMessageDialog(customPanel, "Las dimensiones deben ser mayores o iguales a 10 y menores que 30.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    warningShown = true;
                }
            } else {
                warningShown = false;
            }
        } catch (NumberFormatException ex) {

        }
    }


    /**
     * Validates the fields in the player customization panel.
     *
     * @return True if the fields are valid, false otherwise.
     */
    private boolean validateFields() {
        if (name1.getText().isEmpty() || name2.getText().isEmpty() || heightField.getText().isEmpty() || widthField.getText().isEmpty()
                || temporaryField.getText().isEmpty() || heavyField.getText().isEmpty()
                || goldenField.getText().isEmpty() || mineField.getText().isEmpty() || teleportField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            personalizateNewPanel();
            return false;
        }
    
        String colorJugador1 = combo1.getSelectedItem().toString();
        String colorJugador2 = combo2.getSelectedItem().toString();
        if (colorJugador1.equals(colorJugador2)) {
            JOptionPane.showMessageDialog(this, "Los colores de los jugadores deben ser diferentes.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            personalizateNewPanel();
            return false;
        }

        if (!isValidPercentage(temporaryField.getText()) || !isValidPercentage(heavyField.getText())
                || !isValidPercentage(goldenField.getText()) || !isValidPercentage(mineField.getText())
                || !isValidPercentage(teleportField.getText())) {
            JOptionPane.showMessageDialog(this, "Los porcentajes deben estar en el rango de 0 a 100.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            personalizateNewPanel();
            return false;
        }
    
        return true;
    }
    
    /**
    * Checks if a string represents a valid percentage (between 0 and 100).
    *
    * @param percentage The string representing the percentage.
    * @return True if a valid percentage, false otherwise.
    */
    private boolean isValidPercentage(String percentage) {
        try {
            int value = Integer.parseInt(percentage);
            return value >= 0 && value <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Handles the game configuration based on the selected options.
     */
    private void handleGameConfiguration() {
        int newHeight = Integer.parseInt(heightField.getText());
        int newWidth = Integer.parseInt(widthField.getText());
        this.height = newHeight;
        this.width = newWidth;
        gomokuPOOs.getGomokuNormal().addBoard(newHeight, newWidth);
        gomokuPOOs.restartGame();
        gomokuGame.configureMainPanel(height, width);
        JPanel updatedTableroPanel = createBoardPanel(height, width);
        gomokuGame.getBackgroundLabel().removeAll();
        gomokuGame.getBackgroundLabel().add(updatedTableroPanel, BorderLayout.CENTER);
        gomokuGame.getBackgroundLabel().revalidate();
        gomokuGame.getBackgroundLabel().repaint();
        showDefaultGamePanel(height, width);
    }

    /**
     * Displays the default game panel with the specified dimensions.
     *
     * @param height The height of the game board.
     * @param width  The width of the game board.
     */
    private void showDefaultGamePanel(int height, int width) {
        if (gomokuGame != null && gomokuGame.getDefaultGameFrame() != null) {
            gomokuGame.getDefaultGameFrame().dispose();
        }
        int defaultHeight = height;
        int defaultWidth = width;
        JFrame defaultGameFrame = createDefaultGameFrame();
        JMenuBar menuBar = createDefaultGameMenuBar(defaultGameFrame);
        defaultGameFrame.setJMenuBar(menuBar);
        JPanel defaultGamePanel = createMainPanel(defaultHeight, defaultWidth);
        defaultGameFrame.getContentPane().add(defaultGamePanel);
        defaultGameFrame.setVisible(true);
        gomokuGame.setDefaultGameFrame(defaultGameFrame);
    }

    /**
     * Creates and displays the default game panel with predetermined dimensions.
     * This method is typically called when the "Juego predeterminado" (Default game) option is selected.
     */
    private void createPanelPredeterminate(){
        gomokuPOOs.getGomokuNormal().addBoard(15, 15);
        showDefaultGamePanel(15,15);
    }

    /**
     * Creates the default game frame with calculated dimensions.
     *
     * @return The JFrame for the default game.
     */
    private JFrame createDefaultGameFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = screenWidth;
        int frameHeight = screenHeight;
        int frameX = (screenWidth - frameWidth) / 18;
        int frameY = (screenHeight - frameHeight) / 18;
        JFrame defaultGameFrame = new JFrame("Tablero Gomoku");
        defaultGameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        defaultGameFrame.setSize(frameWidth, frameHeight);
        defaultGameFrame.setLocation(frameX, frameY);
        return defaultGameFrame;
    }

    /**
     * Creates the default game menu bar with options.
     *
     * @param defaultGameFrame The JFrame for the default game.
     * @return The JMenuBar for the default game.
     */
    private JMenuBar createDefaultGameMenuBar(JFrame defaultGameFrame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu options = new JMenu("Opciones");
        JMenuItem menuItemReturnToMenu = new JMenuItem("Volver al Menú Inicial");
        JMenuItem menuItemRestart = new JMenuItem("Reiniciar");
        JMenuItem menuItemSave = new JMenuItem("Guardar Partida");
        JMenuItem menuItemLoad = new JMenuItem("Cargar Partida");
        options.add(menuItemReturnToMenu);
        options.add(menuItemSave);
        options.add(menuItemLoad);
        options.add(menuItemRestart);

        menuItemReturnToMenu.addActionListener(e -> {
            defaultGameFrame.dispose();
            gomokuGame.getBackgroundLabel().setVisible(true);
        });

        menuItemRestart.addActionListener(e -> {
            optionNew();
        });

        menuItemSave.addActionListener(e -> {
            if (gomokuGame != null) {
                this.persistence.saveGame();
            }
        });

        menuItemLoad.addActionListener(e -> {
            if (gomokuGame != null) {
                persistence.loadGame();
            }
        });

        menuBar.add(options);
        return menuBar;
    }


    /**
     * Refreshes the game board by updating button icons based on the current state.
     */
    private void refresh() {
        messageDisplayed = false; // <------ importante 
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Stone stone = gomokuPOOs.getGomokuNormal().getBoard().getBox(i, j).getStone();
                Box box = gomokuPOOs.getGomokuNormal().getBoard().getBox(i, j);
                if (box != null) {
                    final int x = i;
                    final int y = j; 
                    botones[i][j].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            boxesImage(box, x, y);
                            handleBoxClick(x, y);
                        }
                    });
                }
                if (stone != null) {
                    colorsImage(stone, i, j);
                } else if(stone == null){
                    botones[i][j].setIcon(null); 
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                botones[i][j].setDisabledIcon(botones[i][j].getIcon());
            }
        }
    }
    
    
    // private void handleBoxClick(int row, int column) {
    //     Box clickedBox = gomokuPOOs.getGomokuNormal().getBoard().getBox(row, column);
    //     if (clickedBox instanceof Mine && !messageDisplayed) {
    //         messageDisplayed = true;
    //         displayImageWithOKButton("/presentation/img/mina.png", "Casilla Mina");
    //     } else if (clickedBox instanceof Teleport && !messageDisplayed) {
    //         messageDisplayed = true;
    //         displayImageWithOKButton("/presentation/img/teleport.png", "Casilla Teleport");
    //     } else if (clickedBox.getType().equals("golden") && !messageDisplayed) {
    //         messageDisplayed = true;
    //         displayImageWithOKButton("/presentation/img/golden.png", "Casilla Golden");
    //     }
    // }
    
    private void displayImageWithOKButton(String imagePath, String frameTitle) {
        JFrame frame = new JFrame(frameTitle);
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
    
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        icon.setImage(scaledImage);
    
        JLabel label = new JLabel(icon);
    
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> frame.dispose());
    
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);
    
        frame.add(panel);
        frame.setSize(350, 400); // Tamaño ligeramente aumentado para acomodar el botón OK
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    

    /**
     * Handles the click event on a game board cell.
     *
     * @param row    The row index of the clicked cell.
     * @param column The column index of the clicked cell.
     */
    private void handleBoxClick(int row, int column) {
        Box clickedBox = gomokuPOOs.getGomokuNormal().getBoard().getBox(row, column);
        if (clickedBox instanceof Mine && !messageDisplayed) {
            messageDisplayed = true;
            JOptionPane.showMessageDialog(null, "¡Pisaste una mina!");
        } else if (clickedBox instanceof Teleport && !messageDisplayed) {
            messageDisplayed = true;
            JOptionPane.showMessageDialog(null, "¡Te han teletransportado!");
        } else if (clickedBox.getType().equals("golden") && !messageDisplayed) {
            messageDisplayed = true;
            JOptionPane.showMessageDialog(null, "¡Piedra golden desbloqueada!"); 
        }
    }

    

    /**
     * Sets the image for a specific type of box on a graphical user interface button.
     * This private method dynamically selects and sets the image for a specified type of box
     * on a graphical user interface button at the specified grid position (i, j).
     *
     * @param box The box for which the image is to be set.
     * @param i   The row index of the graphical user interface button.
     * @param j   The column index of the graphical user interface button.
     */
    private void boxesImage(Box box, int i, int j){
        String nombreFoto = box.getType();
        ImageIcon boxImage;
        Image imagenGood; 
        ImageIcon nuevaImagen; 
        if(!nombreFoto.equals("normal")){
            boxImage = new ImageIcon(getClass().getResource("/presentation/img/c_" + nombreFoto + ".png"));
            imagenGood = boxImage.getImage().getScaledInstance(botones[i][j].getWidth(), botones[i][j].getHeight(), Image.SCALE_SMOOTH);
            nuevaImagen = new ImageIcon(imagenGood); 
            botones[i][j].setIcon(nuevaImagen); 
        }
    }

    /**
     * Sets the icon for a specific button on the game board based on the color of the stone.
     *
     * @param stone The stone object representing the position on the board.
     * @param i The row index of the button.
     * @param j The column index of the button.
     */
    private void colorsImage(Stone stone, int i, int j){
        if(stone.getType().equals("Normal")){
            imageNormalStone(stone, i, j);
            botones[i][j].setEnabled(true);
        } else if(stone.getType().equals("Heavy")){
            imageHeavyStone(stone, i, j);
            botones[i][j].setEnabled(true);
        } else {
            imageTemporaryStone(stone, i, j);
            botones[i][j].setEnabled(true);
        }
    }

    /**
     * Sets an image for a normal stone on a graphical user interface button.
     * This private method dynamically sets an image corresponding to the color of the provided normal stone
     * on a graphical user interface button at the specified grid position (i, j).
     *
     * @param stone The normal stone for which the image is to be set.
     * @param i     The row index of the graphical user interface button.
     * @param j     The column index of the graphical user interface button.
     */
    private void imageNormalStone(Stone stone, int i, int j){
        ImageIcon stoneImage;
        Image imagenGood;
        ImageIcon nuevaImagen;
        String nombreFoto = stone.getColor();
        stoneImage = new ImageIcon(getClass().getResource("/presentation/img/f_normal_"+ nombreFoto +".png"));
        imagenGood = stoneImage.getImage().getScaledInstance(botones[i][j].getWidth(), botones[i][j].getHeight(), Image.SCALE_SMOOTH);
        nuevaImagen = new ImageIcon(imagenGood);
        botones[i][j].setIcon(nuevaImagen);
    }

    /**
     * Sets an image for a heavy stone on a graphical user interface button.
     * This private method dynamically sets an image corresponding to the color of the provided heavy stone
     * on a graphical user interface button at the specified grid position (i, j).
     *
     * @param stone The heavy stone for which the image is to be set.
     * @param i     The row index of the graphical user interface button.
     * @param j     The column index of the graphical user interface button.
     */
    private void imageHeavyStone(Stone stone, int i, int j){
        ImageIcon stoneImage;
        Image imagenGood;
        ImageIcon nuevaImagen;
        String nombreFoto = stone.getColor();
        stoneImage = new ImageIcon(getClass().getResource("/presentation/img/f_pesada_"+ nombreFoto +".png"));
        imagenGood = stoneImage.getImage().getScaledInstance(botones[i][j].getWidth(), botones[i][j].getHeight(), Image.SCALE_SMOOTH);
        nuevaImagen = new ImageIcon(imagenGood);
        botones[i][j].setIcon(nuevaImagen);
    }

    /**
     * Sets a temporary image for a stone on a graphical user interface button.
     * This private method is responsible for dynamically setting a temporary image
     * corresponding to the color of the provided stone on a graphical user interface button
     * at the specified grid position (i, j).
     *
     * @param stone The stone for which the temporary image is to be set.
     * @param i     The row index of the graphical user interface button.
     * @param j     The column index of the graphical user interface button.
     */
    private void imageTemporaryStone(Stone stone, int i, int j){
        ImageIcon stoneImage;
        Image imagenGood;
        ImageIcon nuevaImagen;
        String nombreFoto = stone.getColor();
        stoneImage = new ImageIcon(getClass().getResource("/presentation/img/f_temporal_" + nombreFoto + ".png"));
        imagenGood = stoneImage.getImage().getScaledInstance(botones[i][j].getWidth(), botones[i][j].getHeight(), Image.SCALE_SMOOTH);
        nuevaImagen = new ImageIcon(imagenGood);
        botones[i][j].setIcon(nuevaImagen);
    }

    /**
     * Resets the game board and configuration to start a new game.
     */
    private void optionNew(){
        gomokuPOOs.getGomokuNormal().resetBoard();  
        gomokuPOOs.reset();
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            final int x = i;
            final int y = j;
            botones[i][j].setIcon(null);
            botones[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClick(x, y);
                        botones[x][y].setEnabled(false);
                        botones[x][y].setDisabledIcon(botones[x][y].getIcon());
                    }
                });
          } 
        }
        int newHeight = 15;
        int newWidth = 15;
        height = 15; 
        width = 15;
        
        gomokuPOOs.getGomokuNormal().addBoard(newHeight, newWidth); 
        gomokuPOOs.restartGame();
        gomokuGame.configureMainPanel(height, width);
        JPanel updatedTableroPanel = createBoardPanel(height, width);
        gomokuGame.getBackgroundLabel().removeAll(); 
        gomokuGame.getBackgroundLabel().add(updatedTableroPanel, BorderLayout.CENTER);
        gomokuGame.getBackgroundLabel().revalidate();
        gomokuGame.getBackgroundLabel().repaint(); 
        showDefaultGamePanel(height, width);
    }

    /**
     * Creates the main panel for the game with buttons, player info and turn label.
     *
     * @param newHeight The height of the game board.
     * @param newWidth  The width of the game board.
     * @return The main JPanel for the game.
     */
    private JPanel createBoardPanel(int h, int w) {
        height = h;
        width = w;
        botones = new JButton[height][width];
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel tablero = new JPanel(new GridLayout(height, width));
        Color colorFondo = new Color(0x7185e4);
        tablero.setBackground(colorFondo);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                botones[i][j] = new JButton();
                Color colorMatrix = new Color(0xa2ade6);
                botones[i][j].setBackground(colorMatrix);
                final int x = i;
                final int y = j;
                botones[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClick(x, y);
                        botones[x][y].setEnabled(false);
                        botones[x][y].setDisabledIcon(botones[x][y].getIcon());
                    }
                });
                tablero.add(botones[i][j]);
            }
        }
        tablero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel rightPanel = createRightPanel();
        mainPanel.add(tablero, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        return mainPanel;
    }
    
    private JPanel createRightPanel() {
        JPanel jPanel1 = new JPanel();
        JButton jButton1, jButton2, jButtonImaT, jButtonImaS;
        jButton1 = new JButton("Temporal");
        jButton2 = new JButton("  Pesada ");
        jButton1.addActionListener(e -> {
            selectedStoneType = "Temporary";
            mostrarMensajeInformativo("Tienes "+ gomokuPOOs.getGomokuNormal().getPlayerInTurn().getNumTemporales() + " piedras temporales");
        });

        jButton2.addActionListener(e -> {
            selectedStoneType = "Heavy";
            mostrarMensajeInformativo("Tienes "+ gomokuPOOs.getGomokuNormal().getPlayerInTurn().getNumPesadas() + " piedras pesadas");
        });
        ImageIcon imageIcon1 = new ImageIcon(getClass().getResource("/presentation/img/f_temporal_Negro.png"));
        ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/presentation/img/f_pesada_Negro.png"));
        jButtonImaT = new JButton(imageIcon1);
        jButtonImaS = new JButton(imageIcon2);
        jButtonImaT.addActionListener(e -> {
            selectedStoneType = "Temporary";
        });
        
        jButtonImaS.addActionListener(e -> {
            selectedStoneType = "Heavy";  
        });
        int maxWidth = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2);
        jButtonImaT.setMaximumSize(new Dimension(maxWidth, Short.MAX_VALUE));
        jButtonImaS.setMaximumSize(new Dimension(maxWidth, Short.MAX_VALUE));
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));
        jPanel1.add(jButton1);
        jPanel1.add(jButtonImaT);
        jPanel1.add(jButton2);
        jPanel1.add(jButtonImaS);
        return jPanel1;
    }
    
    private void mostrarMensajeInformativo(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Creates the main panel for the Gomoku game.
     *
     * @param newHeight_ The new height of the game board.
     * @param newWidth_  The new width of the game board.
     * @return The main JPanel for the Gomoku game.
     */
    private JPanel createMainPanel(int newHeight_, int newWidth_) {
        JPanel gomokuPanel = new JPanel(new BorderLayout());
        JButton restartButton = new JButton("Estado del Juego");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                estate();
            }
        });
        JPanel infoJugadoresPanel = createInfoPlayersPanel();
        JLabel turnoLabel = new JLabel("Turno: Jugador");
        JPanel tableroPanel = createBoardPanel(newHeight_, newWidth_);
        gomokuPanel.add(restartButton, BorderLayout.NORTH);
        gomokuPanel.add(infoJugadoresPanel, BorderLayout.SOUTH);
        gomokuPanel.add(turnoLabel, BorderLayout.CENTER);
        gomokuPanel.add(tableroPanel, BorderLayout.CENTER);
        gomokuPanel.setBackground(Color.WHITE);
        return gomokuPanel;
    }

    /**
     * Displays a message dialog with the current game state.
     */
    private void estate() {
        JFrame frame = new JFrame("Juego");
        String ganador = gomokuPOOs.getGomokuNormal().getWinner();
        if (!ganador.equals("")) {
            JOptionPane.showMessageDialog(frame, "Terminó el juego.", "Juego Terminado", JOptionPane.INFORMATION_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(frame, "El puntaje del jugador ", "Mensaje de estado de juego ", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    

    /**
     * Creates a panel displaying information about a player.
     *
     * @param playerLabel The label for the player's information panel.
     * @param n       The name of the player.
     * @return A JPanel displaying information about the player.
     */
    private JPanel createInfoPlayerPanel(String playerName1, String playerName2) {
        JPanel playerPanel = new JPanel();
        JButton player1Button = new JButton(playerName2);
        player1Button.addActionListener(e -> showPlayerInfo(playerName2));
        JButton player2Button = new JButton(playerName1);
        player2Button.addActionListener(e -> showPlayerInfo(playerName1));
        playerPanel.add(player1Button);
        playerPanel.add(player2Button);
        return playerPanel;
    }

    /**
     * Displays player information in a JOptionPane dialog.
     *
     * @param n The name of the player whose information will be displayed.
     */
    private void showPlayerInfo(String n) {
        JPanel infoPanel = new JPanel();
        Player player = gomokuPOOs.getGomokuNormal().getPlayer(n);
        if (player != null) {
            infoPanel.setBorder(BorderFactory.createTitledBorder(n));
            JLabel nameLabel = new JLabel("Nombre: " + n);
            JLabel colorLabel = new JLabel("Color: " + player.getColor());
            String stonesLabelText;
            if (juegoPredeterminadoButton.isSelected()) {
                stonesLabelText = "Piedras disponibles: Ilimitadas";
            } else {
                stonesLabelText = "Piedras disponibles: " + player.getNumStones();
            }
            JLabel stonesLabel = new JLabel(stonesLabelText);
            JLabel timeLabel = new JLabel("Tiempo invertido: 0:00");
            JLabel scoreLabel = new JLabel("Puntaje: " + player.getScoreboard());
            if (n.equals("Jugador 1")) {
                scoreLabelPlayer1 = scoreLabel;
                gomokuPOOs.getGomokuNormal().startTimerForPlayer("Jugador 1", timeLabel);
            } else if (n.equals("Jugador 2")) {
                scoreLabelPlayer2 = scoreLabel;
                gomokuPOOs.getGomokuNormal().startTimerForPlayer("Jugador 2", timeLabel);
            }
            infoPanel.add(nameLabel);
            infoPanel.add(colorLabel);
            infoPanel.add(stonesLabel);
            infoPanel.add(timeLabel);
            infoPanel.add(scoreLabel);
            JOptionPane.showMessageDialog(this, infoPanel);
        } else {
            JOptionPane.showMessageDialog(this, "Jugador no encontrado: " + n, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Creates a panel displaying information about all players.
     *
     * @return A JPanel displaying information about all players.
     */
    private JPanel createInfoPlayersPanel() {
        if (isPersonalizate() == true) {
            playerName1 = name1.getText(); 
            playerName2 = name2.getText();
            colorSelect1 = (String) combo1.getSelectedItem(); 
            colorSelect2 = (String) combo2.getSelectedItem();
            newHeight = Integer.parseInt(heightField.getText());
            newWidth = Integer.parseInt(widthField.getText());
            if (gameTypeNormal.isSelected()) {
                gomokuPOOs.getGomokuNormal().addBoard(newHeight, newWidth);
            } else if (gameTypeQuicktime.isSelected()) {
                gomokuPOOs.getGomokuQuicktime().addBoard(newHeight, newWidth);
            } else if (gameTypeLimitStones.isSelected()) {
                gomokuPOOs.getGomokuLimited().addBoard(newHeight, newWidth);   
            }            
            gomokuPOOs.getGomokuNormal().addPlayer(playerName1, colorSelect1); // se creo el jugador 1
            gomokuPOOs.getGomokuNormal().getPlayer(playerName1).setNumPesadas(Integer.parseInt(heavyField.getText()));
            gomokuPOOs.getGomokuNormal().getPlayer(playerName1).setNumTemporales(Integer.parseInt(temporaryField.getText()));
            gomokuPOOs.getGomokuNormal().getPlayer(playerName1).deckOfStones();
            gomokuPOOs.getGomokuNormal().addPlayer(playerName2, colorSelect2); // se creo el jugador 2
            gomokuPOOs.getGomokuNormal().getPlayer(playerName2).setNumPesadas(Integer.parseInt(heavyField.getText()));
            gomokuPOOs.getGomokuNormal().getPlayer(playerName2).setNumTemporales(Integer.parseInt(temporaryField.getText()));
            gomokuPOOs.getGomokuNormal().getPlayer(playerName2).deckOfStones();
        } else {
            colorSelect1 = "Negro"; 
            colorSelect2 = "Blanco"; 
            playerName1 = "Jugador 1";
            playerName2 = "Jugador 2";
            gomokuPOOs.getGomokuNormal().addPlayer(playerName1, colorSelect1);
            gomokuPOOs.getGomokuNormal().getPlayer(playerName1).setNumPesadas(3);
            gomokuPOOs.getGomokuNormal().getPlayer(playerName1).setNumTemporales(10);
            gomokuPOOs.getGomokuNormal().getPlayer(playerName1).deckOfStones();
            gomokuPOOs.getGomokuNormal().addPlayer(playerName2, colorSelect2);
            gomokuPOOs.getGomokuNormal().getPlayer(playerName2).setNumPesadas(3);
            gomokuPOOs.getGomokuNormal().getPlayer(playerName2).setNumTemporales(10);
            gomokuPOOs.getGomokuNormal().getPlayer(playerName2).deckOfStones();
        }
        playerName1 = gomokuPOOs.getGomokuNormal().getPlayers()[0];
        playerName2 = gomokuPOOs.getGomokuNormal().getPlayers()[1];
        gomokuPOOs.getGomokuNormal().getPlayer(playerName1).setColor(colorSelect1);
        gomokuPOOs.getGomokuNormal().getPlayer(playerName2).setColor(colorSelect2);
        JPanel infoJugadoresPanel = new JPanel();
        infoJugadoresPanel.setLayout(new GridLayout(2, 1));
        JPanel player1Panel = createInfoPlayerPanel(playerName1, playerName2);
        infoJugadoresPanel.add(player1Panel);
        return infoJugadoresPanel;
    }

    /**
     * Determines if personalization is activated.
     * Personalization is enabled if the 'customizePlayersButton' button is selected.
     * If the 'gameDefaultButton' button is selected, customization is disabled.
     *
     * @return true if personalization is enabled, false if it is disabled.
     */
    private boolean isPersonalizate() {
        if (juegoPredeterminadoButton.isSelected()) {
            return false;
        } else if (personalizarJugadoresButton.isSelected()) {
            return true;
        }
        return false; 
    }
    

    /**
     * Handles a click on a specific button on the game board.
     *
     * @param i The row index of the clicked button.
     * @param j The column index of the clicked button.
     */
    private void handleButtonClick(int i, int j) {
        if (gomokuPOOs.getGomokuNormal().getCurrentPlayer() != null && !gameOver) {
            gomokuPOOs.getGomokuNormal().play(i, j);
            String ganador = gomokuPOOs.getGomokuNormal().getWinner();
            if (!ganador.equals("")) {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "El juego ha terminado. El ganador es: " + ganador, "Juego Terminado", JOptionPane.INFORMATION_MESSAGE);
                refresh();
                disableBoardButtons();
                gameOver = true;
            } else if (gomokuPOOs.getGomokuNormal().isGameDraw()) {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "El juego ha terminado en empate.", "Empate", JOptionPane.INFORMATION_MESSAGE);
                refresh();
                disableBoardButtons();
                gameOver = true;
            } else {
                refresh();
            }
        }
    }
    

    /**
     * Disables the buttons on the board to prevent further interactions.
     * This method iterates through all the buttons on the board and disables them, preventing any further interactions.
     * It also removes any associated icons from the buttons to visually indicate their inactive state.
     */
    private void disableBoardButtons() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                botones[i][j].setEnabled(false);
                botones[i][j].setDisabledIcon(botones[i][j].getIcon());
            }
        }
    }
    
    /**
     * Creates the options panel for configuring the game settings.
     *
     * @param includeLabel Indicates whether to include a label in the panel.
     * @return A JPanel representing the options panel.
     */
    private JPanel createOptionsPanel(boolean includeLabel) {
        JPanel optionsPanel = new JPanel(new GridLayout(includeLabel ? 4 : 3, 1));
        juegoPredeterminadoButton = new JRadioButton("Juego predeterminado");
        personalizarJugadoresButton = new JRadioButton("Personalizar jugadores");
        ButtonGroup groupTipoJuego = new ButtonGroup();
        groupTipoJuego.add(juegoPredeterminadoButton);
        groupTipoJuego.add(personalizarJugadoresButton);
        if (includeLabel) {
            optionsPanel.add(new JLabel("Seleccione el tipo de juego:"));
        }
        optionsPanel.add(juegoPredeterminadoButton);
        optionsPanel.add(personalizarJugadoresButton);
        return optionsPanel;
    }
}
