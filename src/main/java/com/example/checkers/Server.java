package com.example.checkers;

import com.example.checkers.RuchGraczaThread;
import com.example.checkers.ServerCondition;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Server {

    public static void main(String[] args) throws IOException {

        // komunikacja z serwerem
        int port = 6666;
        Socket socket = null;
        ServerSocket serverSocket = null;
        serverSocket = new ServerSocket(port);

        // Wątki
        //ServerCondition sc = new ServerCondition();
        RuchGraczaThread rg = null;
        ExecutorService exec = Executors.newFixedThreadPool(250);

        // kolejka wątków
        Queue<RuchGraczaThread> waitingPlayers = new LinkedList<>();


        // TODO łączy się z hostem
        while (true) {
            try {
                System.out.println("Waiting for a client to connect...");
                socket = serverSocket.accept();
                System.out.println("Client connected");

                rg = new RuchGraczaThread(socket);
                waitingPlayers.add(rg);

                if (waitingPlayers.size() == 2){
                    // TODO jeżeli coś by nie działało to sprawdzić czy nie zwraca 2 razy tego samego wątku
                    Game game = new Game(waitingPlayers.poll(),waitingPlayers.poll());
                    exec.submit(new FutureTask<>(game){
                        protected void done(){
                            try{
                                // TODO może np. tu zwrócić wynik i wg niego dokonać zwiększenia się wygranych/przegranych usera
                                get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //if (serverSocket != null){
        //     serverSocket.close();
        // }


    }
}
