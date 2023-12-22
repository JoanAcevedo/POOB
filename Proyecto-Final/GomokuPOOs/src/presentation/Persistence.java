package presentation;

import javax.swing.*;
import java.io.*;

import domain.GomokuPOOs;

public class Persistence extends JFrame {
    private GomokuPOOs gomokuPOOs;
    private GomokuPOOs gomokuPOOsN;
    /**
     * Loads a saved game.
     */
    public void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Abrir Juego");
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile))) {
                Object loadedObject = ois.readObject();
                         
                if (loadedObject instanceof GomokuPOOs) {
                    this.gomokuPOOsN = (GomokuPOOs) loadedObject;
                    System.out.println("Juego cargado exitosamente");
                } else {
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar el juego", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }

    /**
     * Saves the current game state.
     */
    public void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Juego");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(selectedFile))) {
                oos.writeObject(this.gomokuPOOs);
                System.out.println("Juego guardado exitosamente");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar el juego", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
