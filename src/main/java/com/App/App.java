package com.App;

import com.Service.Buyer.Buyers;
import com.Service.Producers.Producer;
import com.Service.Shop.Stock;
import com.Service.WriteInfo.WriteInfoToFile;

import java.io.File;

public class App {
    private static File file=new File("./History.txt");

    public static File getFile() {
        return file;
    }

    public static void main(String[] args){

        WriteInfoToFile.deleteFile(file);
        Stock stock=new Stock(7);
        Buyers buyer1=new Buyers("Покупатель 1",150,stock);
        Buyers buyer2=new Buyers("Покупатель 2",100,stock);
        Producer producer1=new Producer("Мясник ","Мясо", 150,stock);
        Producer producer2=new Producer("Пекарь ","Хлеб", 120,stock);
        Producer producer3=new Producer("Молочник ","Масло", 100,stock);


        producer1.start();
        producer2.start();
        producer3.start();
        buyer1.start();
        buyer2.start();

        try {
            producer1.join();
            producer2.join();
            producer3.join();
            buyer1.join();
            buyer2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Stock.printResult();
    }
}
