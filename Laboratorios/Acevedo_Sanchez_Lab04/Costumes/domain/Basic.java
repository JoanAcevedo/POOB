package domain;  

public class Basic extends Costume{
    
    private Integer price;
    
    public Basic(String name, Integer price, int discount){
        this.name=name;
        this.price=price;
        this.discount=discount;
    }    
    

    @Override
    public int price() throws CostumeShopException{
       if (price == null) throw new CostumeShopException(CostumeShopException.PRICE_EMPTY);
       if (price < 1) throw new CostumeShopException(CostumeShopException.PRICE_ERROR);
       if(price != null && price >= 1){
           this.price = price - ((this.price*this.discount)/100);
       }
       return price;
    }    
    
    public String data(){
        return name+". Precio:" +price+".Descuento"+discount;
    }
}
