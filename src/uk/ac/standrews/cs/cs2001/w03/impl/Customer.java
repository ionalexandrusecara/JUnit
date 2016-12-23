package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.interfaces.ICustomer;

/**
 * Created by Alex Jr on 9/26/2016.
 */
public class Customer implements ICustomer {

    private String name;
    private double money;

    public Customer(){
        name = null;
        money=0;
    }

    public Customer(String name){
        this.name=name;
        money=0;
    }

    public Customer(String name, double money){
        this.name=name;
        this.money=money;
    }

    public String getName(){
        return name;
    }
    public double getMoney(){
        return money;
    }

}
