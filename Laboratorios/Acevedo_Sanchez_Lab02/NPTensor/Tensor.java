import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ECI, 2023
 *
 */
public class Tensor{
    private int [] shape;
    private int [] values;
    
    /**
     * Creates a tensor with the defined size and value
     * 
     * @param shape, arreglo de enteros que define la dimension del Tensor
     * @param value, unico valor que va a Tensor en su interior
     */
    public Tensor(int[] shape, int value) {
        this.shape = shape;
        this.values = new int[calculateSize(shape)];
        for (int i = 0; i < values.length; i++) {
            this.values[i] = value;
        }
    }
    
    /**
     * Creates a tensor with the defined size and value
     * 
     * @param shape, arreglo de enteros que define la dimension del Tensor
     * @param value, arreglo de valores que va a Tensor en su interior
     */
    public Tensor(int[] shape, int[] values) {
        this.shape = shape;
        this.values = values;
    }
    
    /**
     * 
     */
    public Tensor shape() {
        return new Tensor(new int[]{shape.length}, shape);
    }
    
    /**
     * Cambia la figura de un Tensor ya existente
     * 
     * @param newShape, nueva figura que va a tener el Tensor ya existente
     * @return Tensor con la nueva figura
     */
    public Tensor reshape(int[] newShape) {
        return new Tensor(newShape, values);
    }
    
    /**
     * Realiza una operacion de barajado de los valores en el Tensor original
     * 
     * @return Tensor con los nuevos valores
     */
    public Tensor shuffle() {
        int[] shuffledValues = Arrays.copyOf(values, values.length);
        Random rand = new Random();
        for (int i = shuffledValues.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = shuffledValues[i];
            shuffledValues[i] = shuffledValues[j];
            shuffledValues[j] = temp;
        }
        return new Tensor(shape, shuffledValues);
    }
    
    /**
     * 
     */
    public int value(int[] index) {
        int flatIndex = 0;
        for (int i = 0; i < index.length; i++) {
            flatIndex = flatIndex * shape[i] + index[i];
        }
        return values[flatIndex];
    }
    
    /**
     * Devuelve un nuevo Tensor que representa una rebanada del tensor original
     * 
     * @param start, inicio del Tensor
     * @param end, fin del Tensor
     * @param step,
     */
    public Tensor slide(int start, int end, int step) {
        int newLength = (end - start + step - 1) / step;
        int[] slicedValues = new int[newLength];
        int index = 0;
        for (int i = start; i < end; i += step) {
            slicedValues[index] = values[i];
            index++;
        }
        return new Tensor(new int[]{newLength}, slicedValues);
    }

    /**
     * Calcula la media a lo largo del eje especificado
     * 
     * @param axis, eje en el que se calculara la media 
     */
    public Tensor mean(int axis) {
        int axisSize = shape[axis];
        int[] meanValues = new int[values.length / axisSize];
        for (int i = 0; i < values.length; i += axisSize) {
            int sum = 0;
            for (int j = i; j < i + axisSize; j++) {
                sum += values[j];
            }
            meanValues[i / axisSize] = sum / axisSize;
        }
        return new Tensor(new int[]{axisSize}, meanValues);
    }

    /**
     * Busca el valor especificado en el Tensor y devuelve un nuevo Tensor
     * 
     * @param value, valor que se desea buscar
     */
    public Tensor find(int value) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == value) {
                indices.add(i);
            }
        }
        int[] foundIndices = indices.stream().mapToInt(i -> i).toArray();
        return new Tensor(new int[]{foundIndices.length}, foundIndices);
    }
    
    /**
     * Recibe un Tensor y suma sus valores a un Tensor ya existente
     * 
     * @param t, nuevo Tensor
     * @return Tensor con los nuevos valores
     */
    public Tensor add(Tensor t) {
        int[] newValues = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            newValues[i] = this.values[i] + t.values[i];
        }
        return new Tensor(shape, newValues);
    }
    
    /**
     * Recibe un Tensor y resta sus valores a un Tensor ya existente
     * 
     * @param other, nuevo Tensor
     * @return Tensor con los nuevos valores
     */
    public Tensor subtract(Tensor other) {
        int[] newValues = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            newValues[i] = this.values[i] - other.values[i];
        }
        return new Tensor(shape, newValues);
    }

    /**
     * Recibe un Tensor y multiplica sus valores a un Tensor ya existente
     * 
     * @param other, nuevo Tensor
     * @return Tensor con los nuevos valores
     */
    public Tensor multiply(Tensor other) {
        int[] newValues = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            newValues[i] = this.values[i] * other.values[i];
        }
        return new Tensor(shape, newValues);
    }
    
    /**
     * Compara un Tensor con otro
     * 
     * @param other, Tensor con el que va a ser comparado
     * @return Verdadero o falso según corresponda
     */
    public boolean equals(Tensor other) {
        if (!java.util.Arrays.equals(this.shape, other.shape)) {
            return false;
        }
        for (int i = 0; i < values.length; i++) {
            if (this.values[i] != other.values[i]) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean equals(Object other) {
        return equals((Tensor) other);
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < values.length; i++) {
            result.append(values[i]);
            if (i < values.length - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }
    
    /**
     * Retorna la figura del Tensor
     * 
     * @return int[] figura del Tensor
     */
    public int[] getShape(){
        return this.shape;
    }
    
    /*
     * Calcula el tamaño de shape
     */
    private int calculateSize(int[] shape) {
        int size = 1;
        for (int dim : shape) {
            size *= dim;
        }
        return size;
    }
}
