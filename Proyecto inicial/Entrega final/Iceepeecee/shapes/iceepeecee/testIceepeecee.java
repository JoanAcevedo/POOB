package iceepeecee;

import excepciones.IceepeeceeException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class testIceepeecee.
 *
 * @author  Joan Acevedo
 * @version 29/09/2023
 */
public class testIceepeecee
{
    @Test
    public void shouldCreateIceepeecee() throws IceepeeceeException {
        // Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        // Accion
        // Verificacion
        Assert.assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotCreateIceepeecee() throws IceepeeceeException {
        // Creaciones
        Iceepeecee prueba = new Iceepeecee(-300, -300); // se crea un territorio con valores negativos
        // Accion
        // Verificacion
        Assert.assertFalse(prueba.ok());
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldCreateIceepeecee2() throws IceepeeceeException {
        // Creaciones
        int[][][] islands = {{{20,30}, {50,50}, {10,50}}, 
                             {{40,20}, {60,10}, {72,20}, {60,30}}, 
                             {{45,60}, {55,55}, {60,60}, {55,65}}};
        int[][][] fligths = {{{0,30,20}, {78,70,5}}, 
                             {{55,0,20}, {70,60,10}}};
        Iceepeecee prueba = new Iceepeecee(islands, fligths);
        // Accion
        // Verificacion
        Assert.assertEquals(prueba.fligths().length, 2);
        Assert.assertEquals(prueba.islands().length, 3);
    }
    
    @Test
    public void shouldNotCreateIceepeecee2() throws IceepeeceeException { 
        // Creaciones
        int[][][] islands = {{{20,30}, {50,50}, {10,50}}, 
                             {{40,20}, {60,10}, {72,20}, {60,30}}, 
                             {{45,60}, {55,55}, {60,60}, {55,65}}};
        int[][][] fligths = {{{0,30,20}, {78,70,5}}, 
                             {{-10,0,20}, {70,60,10}}}; // coordenadas incorrectas
        Iceepeecee prueba = new Iceepeecee(islands, fligths);
        // Accion
        // Verificacion
        Assert.assertFalse(prueba.ok());
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------

    @Test
    public void shouldAddIsland() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[][] matrix = {{10,70}, {10,240}, {80,240}, {80,180}};
        //Acciones
        prueba.addIsland("green", matrix);
        //Verificacion
        Assert.assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotAddIsland() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[][] matrix = {{10,70}, {10,240}, {80,240}, {80,180}};
        //Acciones
        prueba.addIsland("green", matrix);
        prueba.addIsland("green", matrix); // se ingresa una isla que ya existe
        //Verificacion
        Assert.assertFalse(prueba.ok());
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldAddFlight() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("red", from, to);
        //Verificacion
        Assert.assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotFlight() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("red", from, to);
        prueba.addFligth("red", from, to); // se ingresa un vuelo que ya existe
        //Verificacion
        Assert.assertFalse(prueba.ok());
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldPhotograph1() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("red", from, to);
        prueba.photograph("red", 45);
        //Verificacion
        Assert.assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotPhotograph1() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("red", from, to);
        prueba.photograph("green", 45); // se ingresa un vuelo que no existe
        prueba.photograph("red", -10); // se ingresa un angulo incorrecto
        //Verificacion
        Assert.assertFalse(prueba.ok());
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldPhotograph2() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("red", from, to);
        prueba.photograph("red", 45);
        //Verificacion
        Assert.assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotPhotograph2() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("red", from, to);
        prueba.photograph("green", 45); // se ingresa un vuelo que no existe
        prueba.photograph("red", -10); // se ingresa un angulo incorrecto
        //Verificacion
        Assert.assertFalse(prueba.ok());
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldPhotograph3() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("red", from, to);
        prueba.photograph("red", 45);
        //Verificacion
        Assert.assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotPhotograph3() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("red", from, to);
        prueba.photograph("green", 45); // se ingresa un vuelo que no existe
        prueba.photograph("red", -10); // se ingresa un angulo incorrecto
        //Verificacion
        Assert.assertFalse(prueba.ok());
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldDelIsland() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[][] matrix = {{10,70}, {10,240}, {80,240}, {80,180}};
        //Acciones
        prueba.addIsland("green", matrix);
        prueba.delIsland("green");
        //Verificacion
        Assert.assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotDelIsland() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[][] matrix = {{10,70}, {10,240}, {80,240}, {80,180}};
        //Acciones
        prueba.addIsland("green", matrix);
        prueba.delIsland("red"); // trata de eliminar una isla que no existe
        //Verificacion
        Assert.assertFalse(prueba.ok());
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldDelFlight() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("red", from, to);
        prueba.delFligth("red");
        //Verificacion
        Assert.assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotDelFlight() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("red", from, to);
        prueba.delFligth("green"); //trata de eliminar un vuelo que no existe
        //Verificacion
        Assert.assertFalse(prueba.ok());
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldIslandLocation() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[][] matrix = {{10,70}, {10,240}, {80,240}, {80,180}};
        //Acciones
        prueba.addIsland("green", matrix);
        //Verificacion
        Assert.assertEquals(prueba.islandLocation("green"), matrix);
    }
    
    @Test
    public void shouldNotIslandLocation() throws IceepeeceeException {
        // Esta prueba esta incompleta, no verifica nada    
        
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[][] matrix = {{10,70}, {10,240}, {80,240}, {80,180}};
        //Acciones
        prueba.addIsland("red", matrix);
        //Verificacion
        Assert.assertFalse(prueba.ok());
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldFligthLocation() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        int[][] locationCorrect = {{70,70,80}, {130,130,40}};
        //Acciones
        prueba.addFligth("green", from, to);
        //Verificacion
        Assert.assertEquals(prueba.islandLocation("green"), locationCorrect);
    }
    
    @Test
    public void shouldNotFligthLocation() throws IceepeeceeException {
        // Esta prueba esta incompleta, no verifica nada    
        
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[][] matrix = {{10,70}, {10,240}, {80,240}, {80,180}};
        //Acciones
        prueba.addIsland("green", matrix);
        //Verificacion
        Assert.assertEquals(prueba.islandLocation("green"), matrix);
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldFligthCamera() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("green", from, to);
        prueba.photograph(40);
        //Verificacion
        Assert.assertEquals(prueba.fligthCamera("green"), 40, 0.1);
    }
    
    @Test
    public void shouldNotFligthCamera() throws IceepeeceeException {
        // Esta prueba esta incompleta, no verifica nada    
        
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[][] matrix = {{10,70}, {10,240}, {80,240}, {80,180}};
        //Acciones
        prueba.addIsland("green", matrix);
        //Verificacion
        Assert.assertEquals(prueba.islandLocation("green"), matrix);
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldIslands() throws IceepeeceeException {
        // Creaciones
        int[][] island = {{20,30}, {50,50}, {10,50}};
        int[][] island2 = {{40,20}, {60,10}, {72,20}, {60,30}};
        int[][] island3 = {{45,60}, {55,55}, {60,60}, {55,65}};
        Iceepeecee prueba = new Iceepeecee(500, 500);
        String[] islasCorrectas = {"red", "green", "blue"};
        // Accion
        prueba.addIsland("red", island);
        prueba.addIsland("green", island2);
        prueba.addIsland("blue", island3);
        // Verificacion
        Assert.assertEquals(islasCorrectas, prueba.islands());
    }
    
    @Test
    public void shouldNotIslands() throws IceepeeceeException { 
        // Esta prueba esta incompleta, no verifica nada   
        
        // Creaciones
        int[][] island = {{20,30}, {50,50}, {10,50}};
        int[][] island2 = {{40,20}, {60,10}, {72,20}, {60,30}};
        int[][] island3 = {{45,60}, {55,55}, {60,60}, {55,65}};
        Iceepeecee prueba = new Iceepeecee(500, 500);
        //String[] islasCorrectas = {"red", "green", "blue"};
        // Accion
        prueba.addIsland("red", island);
        prueba.addIsland("green", island2);
        prueba.addIsland("blue", island3);
        
        // Verificacion
        Assert.assertEquals(prueba.islands().length, 3);
    }
    
    //-------------------------------------------------------------------------------------------------------------------------------- 
    
    @Test
    public void shouldFlights() throws IceepeeceeException {
        // Creaciones
        int[] from1 = {0,30,20};
        int[] to1 = {78,70,5};
        int[] from2 = {55,0,20};
        int[] to2 = {70,60,10};
        Iceepeecee prueba = new Iceepeecee(500, 500);
        // Accion
        prueba.addFligth("red", from1, to1);
        prueba.addFligth("green", from2, to2);
        // Verificacion
        Assert.assertEquals(prueba.fligths().length, 2);
    }
    
    @Test
    public void shouldNotFlights() throws IceepeeceeException { 
        // Esta prueba esta incompleta, no verifica nada   
        
        // Creaciones
        int[] from1 = {0,30,20};
        int[] to1 = {78,70,5};
        int[] from2 = {55,0,20};
        int[] to2 = {70,60,10};
        Iceepeecee prueba = new Iceepeecee(500, 500);
        // Accion
        prueba.addFligth("red", from1, to1);
        prueba.addFligth("green", from2, to2);
        // Verificacion
        Assert.assertEquals(prueba.fligths().length, 2);
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------

    @Test
    public void shouldAddIsland2() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[][] matrix = {{10,70}, {10,240}, {80,240}, {80,180}};
        //Acciones
        prueba.addIsland("n","green", matrix);
        //Verificacion
        Assert.assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotAddIsland2() throws IceepeeceeException { 
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[][] matrix = {{10,70}, {10,240}, {80,240}, {80,180}};
        //Acciones
        prueba.addIsland("b","green", matrix);
        //Verificacion
        Assert.assertFalse(prueba.ok());
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------

    @Test
    public void shouldAddFlight2() throws IceepeeceeException {
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("n","red", from, to);
        //Verificacion
        Assert.assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotAddFlight2() throws IceepeeceeException { 
        //Creaciones
        Iceepeecee prueba = new Iceepeecee(300, 300);
        int[] from = {70,70,80};
        int[] to = {130,130,40};
        //Acciones
        prueba.addFligth("k", "red", from, to);
        //Verificacion
        Assert.assertFalse(prueba.ok());
    }
}
