package domain;
import domain.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CompleteTest{
   
    
    public CompleteTest(){
    }


    @Before
    public void setUp(){    
    }

    @After
    public void tearDown(){
    }
    
 
    @Test
    public void shouldCalculateTheCostOfACompleteCostume(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", 20000, 0));
        c.addBasic(new Basic("Pantalon negro", 10000, 0));
        c.addBasic(new Basic("Capa negra", 10000, 0));
        try {
           assertEquals(50000,c.price());
        } catch (CostumeShopException e){
            fail("Threw a exception");
        }    
    }    
    
    @Test
    public void shouldCalculateTheCostOfACompleteCostumeWithDiscount(){
        Complete c = new Complete("El zorro", 10000, 10);
        c.addBasic(new Basic("Camisa blanca", 20000, 10));
        c.addBasic(new Basic("Pantalon negro", 10000, 10));
        c.addBasic(new Basic("Capa negra", 10000, 10));
        try {
           assertEquals(41400,c.price());
        } catch (CostumeShopException e){
            fail("Threw a exception");
        }    
    }  
    
    @Test
    public void shouldThrowExceptionIfACompleteCostumeHasNoBasicCustom(){
        Complete c= new Complete("El zorro", 10000, 10);
        try { 
           int price=c.price();
           fail("Did not throw exception");
        } catch (CostumeShopException e) {
            assertEquals(CostumeShopException.COMPLETE_EMPTY,e.getMessage());
        }    
    }    
    
    
   @Test
    public void shouldThrowExceptionIfThereIsErrorInPrice(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", 20000, 0));
        c.addBasic(new Basic("Pantalon negro", -10000, 0));
        c.addBasic(new Basic("Capa negra", 10000, 0));
        try { 
           int price=c.price();
           fail("Did not throw exception");
        } catch (CostumeShopException e) {
            assertEquals(CostumeShopException.PRICE_ERROR,e.getMessage());
        }    
    }     
    
   @Test
    public void shouldThrowExceptionIfPriceIsNotKnown(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", null, 0));
        c.addBasic(new Basic("Pantalon negro", 10000, 0));
        c.addBasic(new Basic("Capa negra", 10000, 0));
        try { 
           int price=c.price();
           fail("Did not throw exception");
        } catch (CostumeShopException e) {
            assertEquals(CostumeShopException.PRICE_EMPTY,e.getMessage());
        }    
    }  
    
}