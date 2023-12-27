package presentation;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Graphics;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import domain.*;
/**
 * VintageGUI represents a simple graphical user interface for a game or application named "Vintage."
 */
public class VintageGUI extends JFrame{
    private JPanel boardPanel;
    private JMenuBar mb;
    private JMenu opciones, editar;
    private JMenuItem o1, o2, o3, o4, o5, o6;
    private JFileChooser fileChooser = new JFileChooser();
    private File file = null;
    private Button color;
    private JButton infoPlayer, numOfPlays, move;
    private JButton[][] board;
    //private JButton[][] boardOld;
    private int heightBoard = 8;
    private int widthBoard = 8;
    private Vintage vintage;
    private Vintage vintageOld;
    private Board basicBoard;
    private ActionListener infoPlayerListener;
    private ActionListener numOfPlaysListener;
    private ActionListener colorListener;
    
    /**
     * Constructs a new VintageGUI instance, initializing the graphical user interface.
     */
    private VintageGUI(){
        vintage = new Vintage(8,8);
        vintageOld = vintage;
        prepareElements();
        prepareActions();
    }
    
    /**
     * The main entry point for the application.
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String args[]){
        VintageGUI gui=new VintageGUI();
        gui.setVisible(true);
    }

    /**
     * Retrieves the Vintage instance associated with the graphical user interface.
     *
     * @return The Vintage instance representing the game logic and state.
     */
    public Vintage getVintage(){
        return vintage;
    }
    
    /**
     * Prepares the elements of the GUI, including the title, size, location, menu, and game board.
     */
    private void prepareElements(){
        setTitle("Vintage"); // It stays
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) ((screenSize.getWidth()/2));
        int heigth = (int ) ((screenSize.getHeight()/2));
        setSize(width, heigth ); // It stays
        setLocationRelativeTo(null); // Maybe move to the constructor
        prepareElementsMenu();
        prepareElementsBoard();
    }

    /**
     * Prepares the actions for buttons and menu items, including exit, open, save, and change color.
     */
    private void prepareActions(){
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // When 'NO' is selected, it does nothing
        addWindowListener(
            new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                exit();
                }
            });

        o2.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    open();
                }
            });
        
        o3.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    save();
                }
            });
        
        o4.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    exit();
                }
            });    
            
        o5.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyDimensions();
                }
            });
        
        o6.addActionListener(
            new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    restart();
                }
            });
                
        color.addActionListener(
            new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    changeBoardColor();
                }
            });

        infoPlayer.addActionListener(
            new ActionListener(){
            public void actionPerformed (ActionEvent e){
                infoPlayer();
            }
        });
        
        numOfPlays.addActionListener(
            new ActionListener(){
            public void actionPerformed (ActionEvent e){
                numOfPlays();
            }
        });
        
        infoPlayerListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                infoPlayer();
            }
        };
    
        numOfPlaysListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numOfPlays();
            }
        };
    
        colorListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeBoardColor();
            }
        };
        
        move.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    move();
                }
            });
    
    }

    /**
     * Handles the application exit by displaying a confirmation dialog.
     */
    private void exit(){
        int valor = JOptionPane.showConfirmDialog(this, "¿Estás seguro de cerrar", "Notificacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(valor == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    /**
     * Prepares the menu elements, including the "Options" menu and its items.
     */
    private void prepareElementsMenu(){
        setLayout(null);
        mb = new JMenuBar();
        setJMenuBar(mb);
        opciones = new JMenu("Opciones");
        editar=new JMenu("Editar");
        mb.add(opciones);
        mb.add(editar);
        o1 = new JMenuItem("Nuevo");
        opciones.add(o1);
        o2 = new JMenuItem("Abrir");
        opciones.add(o2);
        o3 = new JMenuItem("Guardar");
        opciones.add(o3);
        o4 = new JMenuItem("Salir");
        opciones.add(o4);
        o5 = new JMenuItem("Modificar dimensiones");
        editar.add(o5);
        o6 = new JMenuItem("Reiniciar");
        editar.add(o6);
    }

    /**
     * Handles the action of opening a file, displaying a file chooser dialog.
     */
    private void open(){
        int response = fileChooser.showOpenDialog(o2);
        if (response == JFileChooser.APPROVE_OPTION){
            file = new File (fileChooser.getSelectedFile().getAbsolutePath());
            System.out.println(file);
        }
        JOptionPane.showMessageDialog(fileChooser, "La funcionalidad de abrir el archivo: "+file.getName()+" está en preparación.","Notificación", response);
    }

    /**
     * Handles the action of saving a file, displaying a file chooser dialog.
     */
    private void save(){
        int response = fileChooser.showSaveDialog(o3);
        if (response == JFileChooser.APPROVE_OPTION){
            file = new File (fileChooser.getSelectedFile().getAbsolutePath());
            System.out.println(file);
        }
        JOptionPane.showMessageDialog(fileChooser, "La funcionalidad de guardar el archivo: "+file.getName()+" está en preparación.","Notificación", response);
    }

    /**
     * Prepares the game board and other related elements using a BorderLayout and a GridLayout.
     */
    private void prepareElementsBoard(){
        setLayout(new BorderLayout());
        add(move = new JButton("Movimiento"), BorderLayout.NORTH);
        add(infoPlayer = new JButton("Información del jugador"), BorderLayout.SOUTH);
        add(color = new Button("Color"), BorderLayout.EAST);
        add(numOfPlays= new JButton("# de jugadas"), BorderLayout.WEST);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(heightBoard, widthBoard));
        board = new JButton[heightBoard][widthBoard];
        for (int i = 0; i < heightBoard; i++) {
            for (int j = 0; j < widthBoard; j++) {
                String color = vintage.getBoard().getBox(i,j).getGemColor();
                String imagePath = color;
                ImageIcon icon = new ImageIcon(imagePath);
                board[i][j] = new JButton(icon);
                buttonPanel.add(board[i][j]);
            }
        }
        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Refreshes the game board in the graphical user interface with default dimensions (8x8).
     */
    private void restart() {
        restartDimensions(8, 8);
    }
    
    /**
     * Adds a JButton to the specified position in the BorderLayout of the container.
     *
     * @param text      The text to be displayed on the button.
     * @param background The background color of the button.
     * @param position  The position where the button should be added (e.g., BorderLayout.SOUTH).
     * @param listener  The ActionListener to be attached to the button.
     */
    private void addButton(String text, Color background, String position, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(Color.BLACK);
        button.addActionListener(listener);
        switch (position) {
            case BorderLayout.SOUTH:
                add(button, BorderLayout.SOUTH);
                break;
            case BorderLayout.WEST:
                add(button, BorderLayout.WEST);
                break;
            case BorderLayout.EAST:
                add(button, BorderLayout.EAST);
                break;
            default:
                break;
        }
        revalidate();
        repaint();
    }

    /**
     * Changes the color of the game board and its components based on the selected color using JColorChooser.
     * The method prompts the user to choose a color and updates the background of the board and individual buttons.
     */
    private void changeBoardColor() {
        Color selectedColor = JColorChooser.showDialog(this, "Seleccionar Color", Color.WHITE);
        for (int i = 0; i < heightBoard; i++) {
            for (int j = 0; j < widthBoard; j++) {
                board[i][j].setBackground(selectedColor);;
                this.setVisible(true);
            }
        }
    }

    /**
     * Displays a message dialog with information about the player's score.
     * The dialog shows the player's current score in points.
     */
    private void infoPlayer() {
        //JOptionPane.showMessageDialog(this, "El nombre del jugador es: " + nombre + " puntos", "Información del jugador", JOptionPane.WARNING_MESSAGE);
        JOptionPane.showMessageDialog(this, "El puntaje del jugador es: " + vintage.getScoreboardPlayer() + " puntos", "Información del jugador", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Displays a message dialog with information about the number of moves made by the player.
     * The dialog shows the current number of moves made by the player during the game.
     */
    private void numOfPlays() {
        JOptionPane.showMessageDialog(this, "El número de jugadas es: " + vintage.getMoveNumberPlayer() + ".", "Número de jugada", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Refreshes the game board in the graphical user interface with the specified dimensions.
     *
     * @param newHeight The new height of the game board.
     * @param newWidth  The new width of the game board.
     */
    private void restartDimensions(int newHeight, int newWidth) {
        heightBoard = newHeight;
        widthBoard = newWidth; 
        Container contentPane = getContentPane();
        contentPane.remove(((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER));
        contentPane.remove(((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.SOUTH));
        contentPane.remove(((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.WEST));
        contentPane.remove(((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.EAST));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(newHeight, newWidth));
        board = new JButton[newHeight][newWidth];
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                Color darkCoffeeColor = new Color(0x3E2723);
                String color = vintage.getBoard().getBox(i,j).getGemColor();
                String imagePath = color;
                ImageIcon icon = new ImageIcon(imagePath);
                board[i][j] = new JButton(icon);
                board[i][j].setBackground(darkCoffeeColor);
                buttonPanel.add(board[i][j]);
            }
        }
        Color lightBlueColor = new Color(173, 216, 230);
        add(buttonPanel, BorderLayout.CENTER);
        addButton("Información del jugador", lightBlueColor, BorderLayout.SOUTH, infoPlayerListener);
        addButton("# de jugadas", lightBlueColor, BorderLayout.WEST, numOfPlaysListener);
        addButton("Color", lightBlueColor, BorderLayout.EAST, colorListener);
        revalidate();
        repaint();
    }
    
    /**
     * Displays a dialog to modify the dimensions of the game board matrix.
     * Prompts the user to input new height and width values, then updates the game board accordingly.
     */
    private void modifyDimensions() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JTextField heightField = new JTextField();
        JTextField widthField = new JTextField();
        inputPanel.add(new JLabel("Alto:"));
        inputPanel.add(heightField);
        inputPanel.add(new JLabel("Ancho:"));
        inputPanel.add(widthField);
        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Modificar Dimensiones", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int newHeight = Integer.parseInt(heightField.getText());
                int newWidth = Integer.parseInt(widthField.getText());
                vintage = new Vintage(newHeight, newWidth);
                if (newHeight <= 20 && newWidth <= 20) {
                    heightBoard = newHeight;
                    widthBoard = newWidth;
                    restartDimensions(heightBoard,widthBoard);
                } else {
                    JOptionPane.showMessageDialog(this, "Las dimensiones deben ser menos o iguales a 20.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese números válidos para alto y ancho.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Handles the action of moving a gem on the game board based on user input.
     * Displays a dialog prompting the user to input the original and new positions for the gem.
     * Performs the gem movement, verifies for alignments, and updates the graphical user interface.
     */
    public void move(){
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JTextField xO = new JTextField();
        JTextField yO = new JTextField();
        JTextField xN = new JTextField();
        JTextField yN = new JTextField();
        inputPanel.add(new JLabel("Posicion y original:"));
        inputPanel.add(xO);
        inputPanel.add(new JLabel("Posicion x original:"));
        inputPanel.add(yO);
        inputPanel.add(new JLabel("Posicion y nueva:"));
        inputPanel.add(xN);
        inputPanel.add(new JLabel("Posicion x nueva:"));
        inputPanel.add(yN);
        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Realizar movimiento", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int[] cooInicial = new int[2];
                int[] cooFinal = new int[2];
                cooInicial[0] = Integer.parseInt(xO.getText());
                cooInicial[1] = Integer.parseInt(yO.getText());
                cooFinal[0] = Integer.parseInt(xN.getText());
                cooFinal[1] = Integer.parseInt(yN.getText());
                vintage.play(cooInicial, cooFinal);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        eliminada();
        refresh(); 
    }
    
    /**
     * Refreshes the graphical user interface by updating the displayed gems on the game board.
     */
    private void refresh(){
       for (int i = 0; i < heightBoard; i++) {
            for (int j = 0; j < widthBoard; j++) {
                String color = vintage.getBoard().getBox(i,j).getGemColor(); 
                String imagePath = color; 
                ImageIcon icon = new ImageIcon(imagePath); 
                board[i][j].setIcon(icon);
            }
        } 
    }
    
    /**
     * Checks for gem alignments on the game board and updates the player's score accordingly.
     */
    private void eliminada(){
        vintage.verficar();
    }
}
