package uk.ac.standrews.cs.cs2001.w03.interfaces;


import uk.ac.standrews.cs.cs2001.w03.common.InsufficientFundsException;
import uk.ac.standrews.cs.cs2001.w03.common.ProductNotRegisteredException;
import uk.ac.standrews.cs.cs2001.w03.common.StockUnavailableException;

import java.util.ArrayList;

/**
 * Created by Alex Jr on 9/26/2016.
 */
public interface IOrder {

    /**
     * This method returns the customer.
     * @return the customer
     */
    public ICustomer getCustomer();

    /**
     * This method returns the order's products.
     * @return the products of this order
     */
    public ArrayList<IProduct> getProducts();

    public double getTotalPrice();

    public void buyProduct(IProduct product) throws StockUnavailableException, ProductNotRegisteredException, InsufficientFundsException;

    public int getProductsNo();


}
