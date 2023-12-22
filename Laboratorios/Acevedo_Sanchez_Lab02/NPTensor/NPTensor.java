import java.util.HashMap;
import java.util.Arrays; 

/** NPTensor.java
 * 
 * @author CIS 2023-02
 */
    
public class NPTensor{
    
    private HashMap<String, Tensor> variables;
    
    public NPTensor(){
    }


    /**
     * Asigna un Tensor a una variable 
     * 
     * @param name, nombre del Tensor
     * @param shape[], figura del Tensor
     * @param value, valor del interior del Tensor
     */
    public void assign(String name, int shape[], int value ){
        int[] values = new int[calculateSize(shape)];
        Arrays.fill(values, value);
        variables.put(name, new Tensor(shape, values));
    }  
    
    /**
     * Consulta el valor de un Tensor en una varibale par un conjunto de indices
     * 
     * @param variable, nombre del Tensor 
     * @param index, indice correspondiente
     */
    public int get(String variable, int[] index) {
        Tensor tensor = variables.get(variable);
        return tensor.value(index);
    }
    
    //Assign a tensor to a variable
    //The values are given
    public void assign(String name, int shape[], int[] values ){
    }    
        
    /**
     * Permite asignar un resultado a una operación unaria a una varibale "a" utilizando otro Tensor "b"
     * operaciones: ("shape", "reshape", "shuffle")
     * 
     * @param a, Tensor con la variable a
     * @param unary, operacion unaria que se quiere realizar
     * @param b, Tensor con la variable b
     */
    public void assign(String a, String unary, String b){
        Tensor tensorB = variables.get(b);
        Tensor result = null;

        switch (unary) {
            case "shape":
                result = tensorB.shape();
                break;
            case "reshape":
                result = tensorB.reshape(tensorB.getShape());
                break;
            case "shuffle":
                result = tensorB.shuffle();
                break;
        }
        variables.put(a, result);
    }    
    
    
    /**
     * Permite asignar un resultado a una operación unaria a una varibale "a" utilizando otro Tensor "b"
     * operaciones: ("slide", "mean", "find")
     * 
     * @param a, Tensor con la variable a
     * @param unary, operacion unaria que se quiere realizar
     * @param b, Tensor con la variable b
     * @param parameters, parametros necesarios para realizar la operacion unaria
     */
    public void assign(String a, String unary, String b, int [] parameters){
        Tensor tensorB = variables.get(b);
        Tensor result = null;
        switch (unary) {
            case "slide":
                result = tensorB.slide(parameters[0], parameters[1], parameters[2]);
                break;
            case "mean":
                result = tensorB.mean(parameters[0]);
                break;
            case "find":
                result = tensorB.find(parameters[0]);
                break;
        }
        variables.put(a, result);
    }  

    /**
     * Permite asignar un resultado a una operación binaria a una varibale "a" utilizando otro Tensor "b"
     * operaciones: ("suma", "resta", "multiplicacion")
     * 
     * @param a, Tensor con la variable a
     * @param b, Tensor con la variable b
     * @param sBinary, operacion binaria que se desea realizar
     * @param c, Tensor con la variable c
     */
    public void assign(String a, String b, String sBinary, String c){
        Tensor tensorB = variables.get(b);
        Tensor tensorC = variables.get(c);
        Tensor result = null;
        switch (sBinary) {
            case "+":
                result = tensorB.add(tensorC);
                break;
            case "-":
                result = tensorB.subtract(tensorC);
                break;
            case "*":
                result = tensorB.multiply(tensorC);
                break;
        }
        variables.put(a, result);
    }   
    

    
    //Returns the string representtion of a tensor
    public String toString(String variable){
        return null;
    }
    
    //If the last operation was successful
    public boolean ok(){
        return false;
    }
    
    /*
     * Calcula el tamaño del Tensor
     * 
     * @param shape, figura propia del Tensor
     * @return entero con el tamaño del Tensor
     */
    private int calculateSize(int[] shape) {
        int size = 1;
        for (int dim : shape) {
            size *= dim;
        }
        return size;
    }
}
    



