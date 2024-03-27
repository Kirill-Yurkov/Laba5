package org.example;

import server.Server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        //  src/main/resources/Collection.xml
        //  C:\Users\Ender\Desktop\Script.txt
        Server server = new Server(new BufferedReader(new InputStreamReader(System.in)), new BufferedOutputStream(System.out));
        server.start();
    }
}
