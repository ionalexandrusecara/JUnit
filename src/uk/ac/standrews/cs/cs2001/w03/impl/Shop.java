package uk.ac.standrews.cs.cs2001.w03.impl;


import uk.ac.standrews.cs.cs2001.w03.common.AbstractFactoryClient;
import uk.ac.standrews.cs.cs2001.w03.common.BarCodeAlreadyInUseException;
import uk.ac.standrews.cs.cs2001.w03.common.ProductNotRegisteredException;
import uk.ac.standrews.cs.cs2001.w03.common.StockUnavailableException;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IShop;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IStockRecord;

import java.util.ArrayList;

/**
 * This class represents a simple shop which can stock and sell products.
 *
 */
public class Shop extends AbstractFactoryClient implements IShop {

    private ArrayList<IProduct> products;
    private ArrayList<IStockRecord> stockRecords;

    public Shop(){
        products = new ArrayList<IProduct>();
        stockRecords = new ArrayList<IStockRecord>();
    }

    /*public Shop(ArrayList<IProduct> products, ArrayList<IStockRecord> stockRecords){
        this.products=products;
        this.stockRecords=stockRecords;
    }*/


    @Override
    public void registerProduct(IProduct product) throws BarCodeAlreadyInUseException {
        if(product == null){
            product = new Product();
        }
        for(int i=0;i<products.size();i++){
            if(products.get(i).getBarCode() == product.getBarCode()){
                throw new BarCodeAlreadyInUseException();
            }
        }
        products.add(product);
        stockRecords.add(new StockRecord(product));
    }


    @Override
    public void unregisterProduct(IProduct product) throws ProductNotRegisteredException {
        if(product==null){
            throw new ProductNotRegisteredException();
        } else if(products.contains(product)){
            products.remove(product);
        } else {
            throw new ProductNotRegisteredException();
        }

    }


    @Override
    public void addStock(String barCode) throws ProductNotRegisteredException {
        int i;
        boolean ok=false;
        for(i=0;i<products.size();i++){
            if(products.get(i).getBarCode().equals(barCode)){
                stockRecords.get(i).addStock();
                ok=true;
            }
        }
        if(!ok){
            throw new ProductNotRegisteredException();
        }
    }


    @Override
    public void buyProduct(String barCode) throws StockUnavailableException, ProductNotRegisteredException {
        int i;
        boolean ok=false;
        for(i=0;i<products.size();i++){
            if(products.get(i).getBarCode().equals(barCode)){
                stockRecords.get(i).buyProduct();
                ok=true;
            }
        }
        if(!ok){
            throw new ProductNotRegisteredException();
        }
    }


    @Override
    public int getNumberOfProducts() {
        return products.size();
    }


    @Override
    public int getTotalStockCount() {
        int s=0;
        for(int i=0;i<stockRecords.size();i++){
            s=s+stockRecords.get(i).getStockCount();
        }
        return s;
    }

    @Override
    public int getStockCount(String barCode) throws ProductNotRegisteredException {
        int i;
        boolean ok=false;
        for(i=0;i<products.size();i++){
            if(products.get(i).getBarCode().equals(barCode)){
                ok=true;
                return stockRecords.get(i).getStockCount();
            }
        }
        if(!ok){
            throw new ProductNotRegisteredException();
        }
        return 0;
    }

    @Override
    public int getNumberOfSales(String barCode) throws ProductNotRegisteredException {
        int i;
        boolean ok=false;
        for(i=0;i<products.size();i++){
            if(products.get(i).getBarCode().equals(barCode)){
                ok=true;
                return stockRecords.get(i).getNumberOfSales();
            }
        }
        if(!ok){
            throw new ProductNotRegisteredException();
        }
        return 0;
    }

    @Override
    public IProduct getMostPopular() throws ProductNotRegisteredException {
        if(products.size()==0){
            throw new ProductNotRegisteredException();
        }
        int i;
        boolean ok=false;
        int max=stockRecords.get(0).getNumberOfSales(),j=0;
        for(i=1;i<products.size();i++){

                if(stockRecords.get(i).getNumberOfSales()>max) {
                    max = stockRecords.get(i).getNumberOfSales();
                    j = i;
                }
        }
        return stockRecords.get(j).getProduct();
    }

}
