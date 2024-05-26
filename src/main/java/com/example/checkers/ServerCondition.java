package com.example.checkers;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerCondition {
    private Lock lock = new ReentrantLock();
    private Condition player = lock.newCondition();
    private boolean doesSomeonePlay = true;

    // TODO
    public void wykonajRuch(String line, String path){
        lock.lock();
        try{
            while(doesSomeonePlay){
                player.await();
            }

            doesSomeonePlay = true;


            //BufferedWriter bw = new BufferedWriter(new FileWriter(path,true));

            //bw.write(line);
            //bw.newLine();

            // TODO serwer pobiera ruch od klienta i aktualizuje wszystkie pola
            // TODO serwer wysyła zaaktualizowane dane do klienta, u każdego klienta plansza rysuje się osobno


            //bw.close();

            // TODO flaga zmienia się w momencie jak gracz skończy wykonywać swój ruch
            doesSomeonePlay = false;
            player.signal();


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
