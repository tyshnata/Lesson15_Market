package com.Service.Shop;

import com.App.App;
import com.Service.WriteInfo.WriteInfoToFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Stock {

    private  List<String> stockProducts =new ArrayList<>();
    private static AtomicInteger atomicCountProductsProduce =new AtomicInteger(0),
                                 atomicCountProductsBuy =new AtomicInteger(0);
    private static int countMeat=0, countBread=0, countButter =0;
    private Integer stockSize;

    public Stock(Integer stockSize) {
        this.stockSize = stockSize;
    }

    public static AtomicInteger getAtomicCountProductsProduce() {
        return atomicCountProductsProduce;
    }

    public static AtomicInteger getAtomicCountProductsBuy() {
        return atomicCountProductsBuy;
    }

    /** the method takes the buyer's name at the entrance.
     *  Randomly selects one product in the list of goods in the warehouse,
     *  removes it from the list and displays a message stating that the buyer bought the goods*/
    public synchronized void saleProduct(String nameBuyer){
        int index;
        while (stockProducts.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            while (true){
                Random random=new Random();
                index=random.nextInt(stockProducts.size());
                if (!stockProducts.get(index).isEmpty()){
                    String line=nameBuyer+" купил "+stockProducts.get(index);
                    stockProducts.remove(index);
                    atomicCountProductsBuy.getAndIncrement();
                    System.out.println(line);
                    WriteInfoToFile.writeToFile(App.getFile(),line);
                    return;
                }
                notify();
            }
    }

    /** The method takes the manufacturer's name and product name on the input.
     * If there is no space in the warehouse, the method of the message is called up
     * and a message is displayed that the producer is waiting until the warehouse is vacated.
     * If there is space in the warehouse, adds the name of the product to the list of products in the warehouse
     * and displays a message stating that the manufacturer has added the product to the warehouse.*/
    public synchronized void produceProduct(String nameProducer,String nameProduct){
        while (stockProducts.size()>=stockSize){
            System.out.println(nameProducer + " ожидает, пока освободится склад.");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            atomicCountProductsProduce.getAndIncrement();
            stockProducts.add(nameProduct);
            counters(nameProduct);
            String line=nameProducer +" добавил в магазин 1 товар. ";
            System.out.println(line);
        System.out.println("Всего произвели "+ atomicCountProductsProduce+" товаров");

        WriteInfoToFile.writeToFile(App.getFile(),line);
        notify();
    }

    /** The method takes the name of the product on the input and increments the corresponding counter.*/
    private void counters(String nameProduct){
        switch (nameProduct){
            case "Хлеб":
                countBread++;
                break;
            case "Мясо":
                countMeat++;
                break;
            case "Масло":
                countButter++;
                break;
            default:
                System.out.println("Неизвестный продукт ");
        }
    }

    /** The method outputs messages to the console about how many products were produced and how much it was purchased.*/
    public static void printResult(){
        System.out.println();
        System.out.println("Мяса произвели: "+ countMeat);
        System.out.println("Хлеба произвели: "+ countBread);
        System.out.println("Масла произвели: "+ countButter);
        System.out.println("Всего купили "+ atomicCountProductsBuy+" товаров");
    }
}
