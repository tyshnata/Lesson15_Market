package com.Service.Producers;

import com.Service.Shop.Stock;
import static com.Service.Shop.Stock.getAtomicCountProductsProduce;

public class Producer extends Thread{//implements Runnable{

    private String nameProducer, nameProduct;
    private long timeBetweenProduce;
    private Stock stock;

    public Producer(String nameProducer, String nameProduct, long timeBetweenProduce, Stock stock) {
        this.nameProducer = nameProducer;
        this.nameProduct = nameProduct;
        this.timeBetweenProduce = timeBetweenProduce;
        this.stock = stock;
    }

        @Override
    public synchronized void run() {
        while (getAtomicCountProductsProduce().get()<40){
            stock.produceProduct(nameProducer,nameProduct);
            try {
                Thread.sleep(timeBetweenProduce);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
