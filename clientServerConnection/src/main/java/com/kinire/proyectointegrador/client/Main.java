package com.kinire.proyectointegrador.client;

import java.io.*;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        Connection.startInstance(new byte[]{127 , 0 ,0, 1}, () -> {
            System.out.println("thing");
            CountDownLatch countDownLatch = new CountDownLatch(10);
            for (int i = 0; i < 1; i++) {

                int finalI = i;
                Connection.getInstance().getImage("./img/test.png",
                        (inputStream) -> {
                            System.out.println(inputStream);
                            File file = new File("./test/test" + finalI + ".png");
                            try {
                                file.createNewFile();
                                System.out.println("aqui");
                                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                                BufferedInputStream inputStream1 = new BufferedInputStream(inputStream);
                                int value;
                                while((value = inputStream1.read()) != -1) {
                                    outputStream.write(value);
                                }
                                System.out.println("aqui");
                                outputStream.close();
                                inputStream1.close();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            countDownLatch.countDown();
                        }, (e) -> System.out.println("a;djfalksdjflk;"));
            }
            try {
                countDownLatch.await();
                System.out.println("Acabo la espera");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
