package com.example.checkers;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerCondition {
    private Lock lock = new ReentrantLock();
    private Condition player = lock.newCondition();
    private boolean doesSomeonePlay = true;
    private Board board;

    public ServerCondition(Board board){
        this.board = board;
    }

    // TODO
    public void wykonajRuch(BufferedReader in, BufferedWriter out){
        lock.lock();
        try{
            while(doesSomeonePlay){
                player.await();
            }

            doesSomeonePlay = true;


            // TODO serwer pobiera ruch od klienta i aktualizuje wszystkie pola (Board i punkty użykownika)
            // TODO serwer wysyła zaaktualizowane dane do klienta, u każdego klienta plansza rysuje się osobno
            String[] msg = boardToString();
            for (int i=0; i<msg.length; i++){
                out.write(msg[i]);
                out.newLine();
                out.flush();
            }

            // TODO serwer pobiera ruch od klienta
            String odpowiedz = in.readLine();

            // wyświetlenie możliwych ruchów

            // wykonanie ruchu

            // sprawdzenie czy jest możliwy kolejny ruch
            // jeśli tak to wykonanie kolejnego ruchu
            // jeśli nie -> idzie dalej

            // TODO flaga zmienia się w momencie jak gracz skończy wykonywać swój ruch
            doesSomeonePlay = false;
            player.signal();


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public String[] boardToString(){
        int width = board.getWidth();
        Character height = board.getHeight();

        String[] msg = new String[width];
        HashMap<Character, HashMap<Integer,Field>> b = board.getBoard();

        Field f;

        int k = 0;
        for (Character i='A'; i<=height; i++){
            msg[k] = "";
            for (int j=1; j<=width; j++){
                f = b.get(i).get(j);
                if (f.getPawn() != null){
                    msg[k] += (f.getPawn().isWhite() ? "W" : "C") + (f.getPawn().getIsQueen() ? "Q " : " ");
                } else {
                    msg[k] += "NULL ";
                }
            }

            k++;
        }

        return msg;
    }
}
