package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.interfaces.IFactory;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IShop;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IStockRecord;


/**
 * This class implements a singleton factory.
 *
 */
public final class Factory implements IFactory {

    private static IFactory factoryInstance = null;

    private Factory() {

    }

    /**
     * Method which returns an instance of the singleton Factory class.
     * @return the instance of the Factory
     */
    public static IFactory getInstance() {
        if (factoryInstance == null) {
            factoryInstance = new Factory();
        }
        return factoryInstance;
    }

    @Override
    public IProduct makeProduct(String barCode, String description) {
        IProduct product = new Product(barCode, description);
        return product;
    }

    @Override
    public IProduct makeProduct(String barCode, String description, double price) {
        IProduct product = new Product(barCode, description, price);
        return product;
    }

    @Override
    public IStockRecord makeStockRecord(IProduct product) {
        IStockRecord stockRecord = new StockRecord(product);
        return stockRecord;
    }

    @Override
    public IShop makeShop() {
        IShop shop = new Shop();
        return shop;
    }

}
