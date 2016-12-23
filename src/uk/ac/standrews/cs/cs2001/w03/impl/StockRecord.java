package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.common.StockUnavailableException;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IStockRecord;

/**
 * This class represents a record held by the shop for a particular product.
 *
 */
public class StockRecord implements IStockRecord {

    private IProduct product;
    private int stockCount;
    private int sales;

    /*public StockRecord(){

    }*/

    public StockRecord(IProduct product){
        this.product=product;
        stockCount=0;
        sales=0;
    }


    @Override
    public IProduct getProduct() {
        if(product == null){
            product=new Product();
            stockCount=0;
            sales=0;
        }
        return product;
    }


    @Override
    public int getStockCount() {
        return stockCount;
    }


    @Override
    public int getNumberOfSales() {
        return sales;
    }

    @Override
    public void addStock() {
        stockCount++;
    }


    @Override
    public void buyProduct() throws StockUnavailableException {
        if(stockCount==0){
            throw new StockUnavailableException();
        } else {
            stockCount--;
            sales++;
        }
    }

}
