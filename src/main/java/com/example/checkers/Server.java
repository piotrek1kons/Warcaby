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
                    Game game = new Game(waitingPlayers.poll(),waitingPlayers.poll());
                    System.out.println("Rozpoczęto grę");
                    System.out.println();

                    exec.submit(new FutureTask<>(game){
                        protected void done(){
                            try{
                                boolean result = get();
                                if (!result){
                                    System.out.println("SOMETHING WENT WRONG");
                                }


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
