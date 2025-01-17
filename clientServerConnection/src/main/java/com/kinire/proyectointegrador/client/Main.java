package com.kinire.proyectointegrador.client;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        Connection.startInstance(new byte[]{127 , 0 ,0, 1}, () -> {
            System.out.println("thing");
            CountDownLatch countDownLatch = new CountDownLatch(10);
            for (int i = 0; i < 10; i++) {

                Connection.getInstance().getImage("./img/test.png",
                        (inputStream) -> {
                            System.out.println(inputStream);
                            countDownLatch.countDown();
                        }, (e) -> System.out.println("a;djfalksdjflk;"));
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
