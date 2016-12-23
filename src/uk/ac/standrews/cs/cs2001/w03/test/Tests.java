package uk.ac.standrews.cs.cs2001.w03.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import uk.ac.standrews.cs.cs2001.w03.common.*;
import uk.ac.standrews.cs.cs2001.w03.impl.Customer;
import uk.ac.standrews.cs.cs2001.w03.impl.Order;
import uk.ac.standrews.cs.cs2001.w03.interfaces.*;

/**
 * This is a JUnit test class for the Shop class.
 */
public class Tests extends AbstractFactoryClient {


    public IShop shop;
    public IProduct product1;
    public IProduct product2;
    public IProduct product3;
    public IProduct product4;
    public IProduct product5;
    public IProduct product6;
    public IStockRecord stockRecord1;
    public IStockRecord stockRecord2;
    public IStockRecord stockRecord3;
    public IStockRecord stockRecord4;
    public IStockRecord stockRecord5;
    public IStockRecord stockRecord6;

    //Extensions
    public ICustomer customer1;
    public ICustomer customer2;
    public ICustomer customer3;
    public IProduct product7;
    public IProduct product8;
    public IStockRecord stockRecord7;
    public IStockRecord stockRecord8;
    public IOrder order1;
    public IOrder order2;
    public IOrder order3;

    @Before
    public void initialize() {
        //Make shop
        shop = getFactory().makeShop();

        //Make products
        product1 = getFactory().makeProduct("012758127423", "Milk");
        product2 = getFactory().makeProduct("192859128594", "Water");
        product3 = getFactory().makeProduct("984981728748", "Mug");
        product4 = getFactory().makeProduct("627848912721", "Notebook");
        product5 = getFactory().makeProduct("627848912721", "Notebook");
        product6 = null;

        //Make stockRecords
        stockRecord1 = getFactory().makeStockRecord(product1);
        stockRecord2 = getFactory().makeStockRecord(product2);
        stockRecord3 = getFactory().makeStockRecord(product3);
        stockRecord4 = getFactory().makeStockRecord(product4);
        stockRecord5 = getFactory().makeStockRecord(product5);
        stockRecord6 = getFactory().makeStockRecord(product6);

        //Extensions - customer and order
        customer1 = new Customer("Andrew James", 200);
        customer2 = new Customer("John Smith");
        customer3 = new Customer();
        product7 = getFactory().makeProduct("012758185937", "Milk", 10);
        product8 = getFactory().makeProduct("192859100000", "Water", 5);
        stockRecord7 = getFactory().makeStockRecord(product7);
        stockRecord8 = getFactory().makeStockRecord(product8);
        order1 = new Order(customer1, shop);
        order2 = new Order(customer2, shop);
        order3 = new Order(customer3, shop);
    }

    //Registers one product to the shop
    //Checks the barcode and description of the product to be unchanged
    //Checks whether the shop has one registered product
    //Checks the stock count, since a product registered doesn't mean a change in stock
    @Test
    public void registerOneProduct() throws BarCodeAlreadyInUseException {
        shop.registerProduct(product1);

        assertEquals(product1.getBarCode(), "012758127423");
        assertEquals(product1.getDescription(), "Milk");
        assertEquals(shop.getNumberOfProducts(), 1);
        assertEquals(shop.getTotalStockCount(), 0);
    }

    //Registers a few products to the shop
    //Checks whether the shop has the right amount of registered products
    //Checks the stock count
    @Test
    public void registerMoreProducts() throws BarCodeAlreadyInUseException {
        shop.registerProduct(product1);
        shop.registerProduct(product2);
        shop.registerProduct(product3);
        shop.registerProduct(product4);

        assertEquals(shop.getNumberOfProducts(), 4);
        assertEquals(shop.getTotalStockCount(), 0);

    }

    //Checks for registering the identical products
    @Test(expected = BarCodeAlreadyInUseException.class)
    public void registerDuplicatedProducts() throws BarCodeAlreadyInUseException {
        shop.registerProduct(product4);
        shop.registerProduct(product5);

        assertEquals(shop.getNumberOfProducts(), 1);
        assertEquals(shop.getTotalStockCount(), 0);
    }

    //Checks for registering the same product twice
    @Test(expected = BarCodeAlreadyInUseException.class)
    public void registerSameProductTwice() throws BarCodeAlreadyInUseException {

        shop.registerProduct(product4);
        shop.registerProduct(product4);

        assertEquals(shop.getNumberOfProducts(), 1);
        assertEquals(shop.getTotalStockCount(), 0);

    }


    //Add null product
    @Test
    public void registerNullProduct() throws BarCodeAlreadyInUseException {

        shop.registerProduct(null);

        assertEquals(shop.getNumberOfProducts(), 1);
        assertEquals(shop.getTotalStockCount(), 0);


    }

    //Add stock for registered product
    @Test
    public void addProductStock() throws BarCodeAlreadyInUseException, ProductNotRegisteredException {
        shop.registerProduct(product1);

        shop.addStock(product1.getBarCode());

        assertEquals(shop.getNumberOfProducts(), 1);
        assertEquals(shop.getStockCount(product1.getBarCode()), 1);
        assertEquals(shop.getTotalStockCount(), 1);

    }

    //Add stock for registered product twice
    @Test
    public void addProductStockTwice() throws BarCodeAlreadyInUseException, ProductNotRegisteredException {
        shop.registerProduct(product1);

        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());

        assertEquals(shop.getNumberOfProducts(), 1);
        assertEquals(shop.getStockCount(product1.getBarCode()), 2);
        assertEquals(shop.getTotalStockCount(), 2);

    }

    //Add stock for registered products
    @Test
    public void addSeveralProductsStock() throws BarCodeAlreadyInUseException, ProductNotRegisteredException {
        shop.registerProduct(product1);
        shop.registerProduct(product2);
        shop.registerProduct(product3);

        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product2.getBarCode());
        shop.addStock(product2.getBarCode());
        shop.addStock(product2.getBarCode());
        shop.addStock(product3.getBarCode());

        assertEquals(shop.getNumberOfProducts(), 3);
        assertEquals(shop.getStockCount(product1.getBarCode()), 2);
        assertEquals(shop.getStockCount(product2.getBarCode()), 3);
        assertEquals(shop.getStockCount(product3.getBarCode()), 1);
        assertEquals(shop.getTotalStockCount(), 6);
    }

    //Add stock for not registered product
    @Test(expected = ProductNotRegisteredException.class)
    public void addNotRegisteredProductStock() throws ProductNotRegisteredException {
        shop.addStock(product1.getBarCode());

        assertEquals(shop.getNumberOfProducts(), 1);
        assertEquals(shop.getStockCount(product1.getBarCode()), 1);
        assertEquals(shop.getTotalStockCount(), 1);

    }

    //Buy product from shop that is in stock
    @Test
    public void buyProductTest() throws BarCodeAlreadyInUseException, ProductNotRegisteredException, StockUnavailableException {
        shop.registerProduct(product1);

        shop.addStock(product1.getBarCode());

        shop.buyProduct(product1.getBarCode());

        assertEquals(shop.getNumberOfProducts(), 1);
        assertEquals(shop.getStockCount(product1.getBarCode()), 0);
        assertEquals(shop.getTotalStockCount(), 0);
        assertEquals(shop.getMostPopular(), product1);
        assertEquals(shop.getNumberOfSales(product1.getBarCode()), 1);

    }

    //Buy product from shop that is in stock twice
    @Test
    public void buyProductTest2() throws BarCodeAlreadyInUseException, ProductNotRegisteredException, StockUnavailableException {
        shop.registerProduct(product1);

        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());

        shop.buyProduct(product1.getBarCode());

        assertEquals(shop.getNumberOfProducts(), 1);
        assertEquals(shop.getStockCount(product1.getBarCode()), 1);
        assertEquals(shop.getTotalStockCount(), 1);
        assertEquals(shop.getMostPopular(), product1);
        assertEquals(shop.getNumberOfSales(product1.getBarCode()), 1);
    }

    //Buy one product from shop that has more products registered and in stock
    @Test
    public void buyProductTest3() throws BarCodeAlreadyInUseException, ProductNotRegisteredException, StockUnavailableException {
        shop.registerProduct(product1);
        shop.registerProduct(product2);
        shop.registerProduct(product3);
        shop.registerProduct(product4);

        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product2.getBarCode());
        shop.addStock(product3.getBarCode());
        shop.addStock(product3.getBarCode());
        shop.addStock(product3.getBarCode());
        shop.addStock(product4.getBarCode());

        shop.buyProduct(product1.getBarCode());

        assertEquals(shop.getNumberOfProducts(), 4);
        assertEquals(shop.getStockCount(product1.getBarCode()), 1);
        assertEquals(shop.getStockCount(product2.getBarCode()), 1);
        assertEquals(shop.getStockCount(product3.getBarCode()), 3);
        assertEquals(shop.getStockCount(product4.getBarCode()), 1);
        assertEquals(shop.getTotalStockCount(), 6);
        assertEquals(shop.getMostPopular(), product1);
        assertEquals(shop.getNumberOfSales(product1.getBarCode()), 1);
        assertEquals(shop.getNumberOfSales(product2.getBarCode()), 0);
        assertEquals(shop.getNumberOfSales(product3.getBarCode()), 0);
        assertEquals(shop.getNumberOfSales(product4.getBarCode()), 0);

    }

    //Buy more products from shop
    //Checks the most popular sold product and number of sales for all products
    @Test
    public void buyProductsTest() throws BarCodeAlreadyInUseException, ProductNotRegisteredException, StockUnavailableException {
        shop.registerProduct(product1);
        shop.registerProduct(product2);
        shop.registerProduct(product3);
        shop.registerProduct(product4);

        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product2.getBarCode());
        shop.addStock(product3.getBarCode());
        shop.addStock(product3.getBarCode());
        shop.addStock(product3.getBarCode());
        shop.addStock(product4.getBarCode());

        shop.buyProduct(product1.getBarCode());
        shop.buyProduct(product3.getBarCode());
        shop.buyProduct(product3.getBarCode());

        assertEquals(shop.getNumberOfProducts(), 4);
        assertEquals(shop.getStockCount(product1.getBarCode()), 1);
        assertEquals(shop.getStockCount(product2.getBarCode()), 1);
        assertEquals(shop.getStockCount(product3.getBarCode()), 1);
        assertEquals(shop.getStockCount(product4.getBarCode()), 1);
        assertEquals(shop.getTotalStockCount(), 4);
        assertEquals(shop.getMostPopular(), product3);
        assertEquals(shop.getNumberOfSales(product1.getBarCode()), 1);
        assertEquals(shop.getNumberOfSales(product3.getBarCode()), 2);
    }

    //Buy product from shop that is out of stock
    //Checks the most popular sold product and number of sales for all products
    @Test(expected = StockUnavailableException.class)
    public void buyMoreProductsThanStock() throws BarCodeAlreadyInUseException, ProductNotRegisteredException, StockUnavailableException {
        shop.registerProduct(product1);
        shop.registerProduct(product2);
        shop.registerProduct(product3);
        shop.registerProduct(product4);

        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product2.getBarCode());
        shop.addStock(product3.getBarCode());
        shop.addStock(product3.getBarCode());
        shop.addStock(product3.getBarCode());
        shop.addStock(product4.getBarCode());

        shop.buyProduct(product2.getBarCode());
        shop.buyProduct(product2.getBarCode());
        assertEquals(shop.getNumberOfProducts(), 4);
        assertEquals(shop.getStockCount(product1.getBarCode()), 2);
        assertEquals(shop.getStockCount(product2.getBarCode()), 0);
        assertEquals(shop.getStockCount(product3.getBarCode()), 3);
        assertEquals(shop.getStockCount(product4.getBarCode()), 1);
        assertEquals(shop.getTotalStockCount(), 6);
        assertEquals(shop.getMostPopular(), product2);
        assertEquals(shop.getNumberOfSales(product1.getBarCode()), 0);
        assertEquals(shop.getNumberOfSales(product2.getBarCode()), 1);
        assertEquals(shop.getNumberOfSales(product3.getBarCode()), 0);
        assertEquals(shop.getNumberOfSales(product4.getBarCode()), 0);
    }

    //Buy product that is not registered
    @Test(expected = ProductNotRegisteredException.class)
    public void buyNotRegisteredProductTest() throws ProductNotRegisteredException, StockUnavailableException {
        shop.buyProduct(product1.getBarCode());

        assertEquals(shop.getNumberOfProducts(), 0);
        assertEquals(shop.getStockCount(product1.getBarCode()), 0);
        assertEquals(shop.getTotalStockCount(), 0);
    }

    //Register product that is null and unregister it
    @Test(expected = ProductNotRegisteredException.class)
    public void unregisterProductTest() throws BarCodeAlreadyInUseException, ProductNotRegisteredException {

        shop.registerProduct(product1);
        shop.registerProduct(product2);
        shop.registerProduct(product3);
        shop.registerProduct(product4);

        shop.unregisterProduct(product2);

        assertEquals(shop.getNumberOfProducts(), 3);
        assertEquals(shop.getStockCount(product1.getBarCode()), 0);
        assertEquals(shop.getStockCount(product2.getBarCode()), 0);
        assertEquals(shop.getStockCount(product3.getBarCode()), 0);
        assertEquals(shop.getStockCount(product4.getBarCode()), 0);
        assertEquals(shop.getTotalStockCount(), 0);

    }

    //Buy product that was registered and then unregistered
    @Test(expected = ProductNotRegisteredException.class)
    public void buyUnregisteredProductTest() throws BarCodeAlreadyInUseException, ProductNotRegisteredException, StockUnavailableException {

        shop.registerProduct(product1);

        shop.unregisterProduct(product1);

        shop.buyProduct(product1.getBarCode());

        assertEquals(shop.getNumberOfProducts(), 0);
        assertEquals(shop.getStockCount(product1.getBarCode()), 0);
        assertEquals(shop.getTotalStockCount(), 0);

    }

    //Unregister product that is not registered
    @Test(expected = ProductNotRegisteredException.class)
    public void unregisterNotRegisteredProductTest() throws ProductNotRegisteredException {
        shop.unregisterProduct(product1);

        assertEquals(shop.getNumberOfProducts(), 0);
        assertEquals(shop.getStockCount(product1.getBarCode()), 0);
        assertEquals(shop.getTotalStockCount(), 0);
    }

    //Add stock for product from StockRecord
    @Test
    public void addStockForProductToStockRecord() {
        stockRecord1.addStock();
        assertEquals(stockRecord1.getStockCount(), 1);
    }

    //Buy product from stockRecord
    @Test
    public void buyProductFromStockRecord() {
        try {
            stockRecord1.addStock();
            stockRecord1.buyProduct();
            assertEquals(stockRecord1.getStockCount(), 0);
            assertEquals(stockRecord1.getNumberOfSales(), 1);
        } catch (StockUnavailableException e) {
            fail("Exception detected: " + e.getMessage());
        }
    }

    //Buy product from stockRecord
    @Test
    public void buyProductsFromStockRecord() {
        try {
            stockRecord1.addStock();
            stockRecord1.addStock();
            stockRecord1.addStock();
            stockRecord1.buyProduct();
            stockRecord1.buyProduct();
            assertEquals(stockRecord1.getStockCount(), 1);
            assertEquals(stockRecord1.getNumberOfSales(), 2);
        } catch (StockUnavailableException e) {
            fail("Exception detected: " + e.getMessage());
        }
    }


    //Buy out of stock product from StockRecord
    @Test(expected = StockUnavailableException.class)
    public void buyOutOfStockProductFromStockRecord() throws StockUnavailableException {
        stockRecord1.buyProduct();
    }

    //Buy null product from StockRecord
    @Test
    public void buyNullProductFromStockRecord() {
        try {
            stockRecord6.addStock();
            stockRecord6.buyProduct();
        } catch (StockUnavailableException e) {
            fail("Exception detected: " + e.getMessage());
        }
    }

    //Extensions

    //Buy product out of stock for customer through order
    @Test
    public void orderProduct() {
        try {
            shop.registerProduct(product7);
            shop.addStock(product7.getBarCode());
            order1.buyProduct(product7);

            assertEquals(order1.getProductsNo(), 1);
            assertEquals(order1.getTotalPrice(), 10, 0);
        } catch (StockUnavailableException e) {
            fail("Exception detected: " + e.getMessage());
        } catch (ProductNotRegisteredException e) {
            fail("Exception detected: " + e.getMessage());
        } catch (BarCodeAlreadyInUseException e) {
            fail("Exception detected: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            fail("Exception detected: " + e.getMessage());
        }
    }

    //Buy product out of stock for customer through order
    @Test(expected = StockUnavailableException.class)
    public void orderOutOfStockProduct() throws BarCodeAlreadyInUseException, ProductNotRegisteredException, StockUnavailableException, InsufficientFundsException {
        shop.registerProduct(product7);
        order1.buyProduct(product7);

        assertEquals(order1.getProductsNo(), 1);
        assertEquals(order1.getTotalPrice(), 10, 0);

    }

    //Buy products for customer through order
    @Test
    public void orderProducts() {
        try {
            shop.registerProduct(product7);
            shop.registerProduct(product8);
            shop.addStock(product7.getBarCode());
            shop.addStock(product8.getBarCode());
            order1.buyProduct(product7);
            order1.buyProduct(product8);

            assertEquals(order1.getProductsNo(), 2);
            assertEquals(order1.getTotalPrice(), 15, 0);
        } catch (StockUnavailableException e) {
            fail("Exception detected: " + e.getMessage());
        } catch (ProductNotRegisteredException e) {
            fail("Exception detected: " + e.getMessage());
        } catch (BarCodeAlreadyInUseException e) {
            fail("Exception detected: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            fail("Exception detected: " + e.getMessage());
        }
    }

    //Buy products for customer through order
    @Test(expected = ProductNotRegisteredException.class)
    public void orderUnregisteredProduct() throws ProductNotRegisteredException, StockUnavailableException, InsufficientFundsException {

        order1.buyProduct(product7);

        assertEquals(order1.getProductsNo(), 0);
        assertEquals(order1.getTotalPrice(), 0, 0);

    }

    //Not enough money for products
    @Test(expected = InsufficientFundsException.class)
    public void orderProductsForMoreThanCanAfford() throws ProductNotRegisteredException, StockUnavailableException, InsufficientFundsException {
        order2.buyProduct(product8);

        assertEquals(order1.getProductsNo(), 0);
        assertEquals(order1.getTotalPrice(), 0, 0);

    }

    //Not enough money for products
    @Test(expected = ProductNotRegisteredException.class)
    public void orderNullProduct() throws ProductNotRegisteredException, StockUnavailableException, InsufficientFundsException {
        order1.buyProduct(product6);

        assertEquals(order1.getProductsNo(), 0);
        assertEquals(order1.getTotalPrice(), 0, 0);

    }


}
