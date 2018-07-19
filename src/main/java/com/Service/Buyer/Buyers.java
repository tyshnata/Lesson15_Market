package com.Service.Buyer;

import com.Service.Shop.Stock;
import static com.Service.Shop.Stock.getAtomicCountProductsBuy;

public  class Buyers extends Thread{//implements Runnable{
    private String nameBuyer;
    private long timeBetweenPurchases;
    private Stock stock;

    public Buyers(String nameBuyer, long timeBetweenPurchases, Stock stock) {
        this.nameBuyer = nameBuyer;
        this.timeBetweenPurchases = timeBetweenPurchases;
        this.stock = stock;
    }

    @Override
    public synchronized void run() {
        while (getAtomicCountProductsBuy().get()<40){
            stock.saleProduct(nameBuyer);
            try {
                Thread.sleep(timeBetweenPurchases);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
