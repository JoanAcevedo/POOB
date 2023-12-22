package domain;  
 
import java.util.ArrayList;

public class Complete extends Costume{
   

    private int makeUp;
    private ArrayList<Basic> pieces;
    
    /**
     * Constructs a new complete custom
     * @param name 
     * @param makeUp
     * @param discount 
     */
    public Complete(String name, int makeUp, int discount){
        this.name=name;
        this.makeUp=makeUp;
        this.discount=discount;
        pieces= new ArrayList<Basic>();
    }


     /**
     * Add a new basic piece
     * @param b
     */   
    public void addBasic(Basic b){
        pieces.add(b);
    }
       
 
    /**
     * Nos da el precio del disfraz completo
     * 
     * @return int con el precio del disfraz completo
     * @throws PRICE_EMPTY si el precio es vacio
     * @throws PRICE_ERROR si el precio es incorrecto
     */
    @Override
    public int price() throws CostumeShopException{
        int price = 0;
        if(pieces.size() == 0){
            throw new CostumeShopException(CostumeShopException.COMPLETE_EMPTY);
        }else{
           for(Basic c: pieces){
            price += c.price();
            }
        }
        price = price+this.makeUp - (((price+this.makeUp)*this.discount)/100);
        return price;
    }
    
    
     /**
     * Calculates an estimate price
     * For basics where the price cannot be known or has error, the first o last value is assumed
     * @param type (first, last) 
     * @return 
     * @throws CostumeShopException COMPLETE_EMPTY, if it don't have basics. IMPOSSIBLE, if it can't be calculated
     */
    public int price(String type) throws CostumeShopException{
        switch(type){
            
        }
        return 0;
    }   
    
    
     /**
     * Calculates an estimate price
     * For basics where the price cannot be known, if makeUp then the makeUp is assumed
     * @param unknown 
     * @return 
     * @throws CostumeShopException COMPLETE_EMPTY, if it don't have basics. PRICE_UNKNOWN, if some basic is unknown and not makeUp. PRICE_ERROR, if some basic has error
     */
    public int price(boolean makeUp) throws CostumeShopException{
        return 0;
    } 
    
    @Override
    public String data() throws CostumeShopException{
        StringBuffer answer=new StringBuffer();
        answer.append(name+". Maquillaje "+ makeUp+". Descuento: "+ discount);
        for(Basic b: pieces) {
            answer.append("\n\t"+b.data());
        }
        return answer.toString();
    } 
    

}
