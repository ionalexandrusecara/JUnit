package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.common.InsufficientFundsException;
import uk.ac.standrews.cs.cs2001.w03.common.ProductNotRegisteredException;
import uk.ac.standrews.cs.cs2001.w03.common.StockUnavailableException;
import uk.ac.standrews.cs.cs2001.w03.interfaces.ICustomer;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IOrder;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IShop;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * Created by Alex Jr on 9/26/2016.
 */
public class Order implements IOrder{

    private ArrayList<IProduct> products;
    private double totalPrice;
    private ICustomer customer;
    private IShop shop;

    public Order (ICustomer customer, IShop shop){
        products = new ArrayList<IProduct>();
        this.customer = customer;
        this.shop=shop;
        totalPrice = 0;
    }

    public ICustomer getCustomer(){
        return customer;
    }

    public ArrayList<IProduct> getProducts(){
        return products;
    }

    public int getProductsNo(){
        return products.size();
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public void buyProduct(IProduct product) throws StockUnavailableException, ProductNotRegisteredException, InsufficientFundsException {
        if(product == null){
            throw new ProductNotRegisteredException();
        }
        if(product.getPrice() <= customer.getMoney()) {
            shop.buyProduct(product.getBarCode());
            products.add(product);
            totalPrice = totalPrice + product.getPrice();
        } else {
            throw new InsufficientFundsException();
        }
    }

}
