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
            // wysłanie aktualizacji tablicy
            String[] msg = boardToString();
            wyslanieTablicy(msg, out);

            // TODO serwer pobiera ruch od klienta
            // wybranie pionka do ruszenia
            String[] odpowiedz = in.readLine().split(";");  // CH;Y
            Character ch = odpowiedz[0].charAt(0);
            int y = Integer.parseInt(odpowiedz[1]);

            // wyświetlenie możliwych ruchów
            HashMap<Character,HashMap<Integer,Field>> b = board.getBoard();
            Field f = b.get(ch).get(y);
            String[] nextMove = f.getPawn().nextMove(false);

            // wysłanie listy do klienta
            wyslanieTablicy(nextMove, out);

            // odebranie nowego ruchu od klienta
            odpowiedz = in.readLine().split(";");  // CH;Y
            ch = odpowiedz[0].charAt(0);
            y = Integer.parseInt(odpowiedz[1]);

            // wykonanie ruchu
            f.getPawn().move(ch,y);

            // wysłanie aktualizacji tablicy
            msg = boardToString();
            wyslanieTablicy(msg, out);

            // sprawdzenie czy jest możliwy kolejny ruch
            f = b.get(ch).get(y);
            nextMove = f.getPawn().nextMove(true);

            while (nextMove != null){
                out.write("NEXT");  // klient ma znowu odebrać kolejny ruch
                out.newLine();
                out.flush();

                // wysłanie listy do klienta
                wyslanieTablicy(nextMove, out);

                // odebranie nowego ruchu od klienta
                odpowiedz = in.readLine().split(";");  // CH;Y
                ch = odpowiedz[0].charAt(0);
                y = Integer.parseInt(odpowiedz[1]);

                // wykonanie ruchu
                f.getPawn().move(ch,y);

                // wysłanie aktualizacji tablicy
                msg = boardToString();
                wyslanieTablicy(msg, out);

                // sprawdzenie czy jest możliwy kolejny ruch
                f = b.get(ch).get(y);
                nextMove = f.getPawn().nextMove(true);

            }


            // jeśli nie ma ruchu -> idzie dalej
            out.write("STOP");  // koniec ruchu użytkownika
            out.newLine();
            out.flush();

            // TODO flaga zmienia się w momencie jak gracz skończy wykonywać swój ruch
            doesSomeonePlay = false;
            player.signal();


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void wyslanieTablicy(String[] arr, BufferedWriter out) throws IOException {
        out.write("" + arr.length);
        out.newLine();
        out.flush();
        for (int i=0; i<arr.length; i++){
            out.write(arr[i]);
            out.newLine();
            out.flush();
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
