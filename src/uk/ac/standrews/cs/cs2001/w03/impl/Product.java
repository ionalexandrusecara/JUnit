package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;

/**
 * This class represents products that can be stocked and sold in a shop.
 *
 */
public class Product implements IProduct {

    private String barCode;
    private String description;
    private double price; //extension

    public Product(){

    }

    public Product(String barCode, String description){
        this.barCode=barCode;
        this.description=description;
        price = 0;
    }

    public Product(String barCode, String description, double price){ //extension
        this.barCode=barCode;
        this.description=description;
        this.price=price;
    }


   @Override
   public String getBarCode() {
       return barCode;
    }


   @Override
    public String getDescription() {
       return description;
    }

    public double getPrice(){
        return price;
    }

}
