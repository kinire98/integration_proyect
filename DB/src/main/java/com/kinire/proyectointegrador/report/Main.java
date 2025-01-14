package com.kinire.proyectointegrador.report;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.err.println("Introduce the path for storing the reports");
            return;
        }
        File dir = new File(args[0]);
        if(!dir.isDirectory()) {
            System.err.println("The path must be a directory");
            return;
        }
        File file = new File(dir, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
        try {
            if(!file.createNewFile()) {
                System.err.println("There was an error creating the file for the report. Make sure you have the right" +
                        "permissions");
                return;
            }
            throw new UnsupportedOperationException("Feature incomplete. Contact assistance.");
        } catch (IOException e) {
            System.err.println("There was an error generating the report");
        }

    }
}
