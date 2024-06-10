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
    private boolean doesSomeonePlay = false;
    private Board board;
    private boolean isGameOn = true;


    public ServerCondition(Board board){
        this.board = board;
    }

    // TODO
    public boolean wykonajRuch(BufferedReader in, BufferedWriter out, Player p){
        lock.lock();
        try{
            while(doesSomeonePlay){
                player.await();
            }

            System.out.println("jeden gracz gra");
            doesSomeonePlay = true;

            if (!isGameOn){
                return false;
            }

            System.out.println("wysyla START");
            // TODO serwer wysyła info o rozpoczęciu ruchu
            out.write("START");  // poinformowanie klienta o rozpoczęciu ruchu
            out.newLine();
            out.flush();
            System.out.println("Wysłano START");

            // TODO serwer wysyła zaaktualizowane dane do klienta, u każdego klienta plansza rysuje się osobno
            // wysłanie aktualizacji tablicy
            String[] msg = boardToString();
            wyslanieTablicy(msg, out, in);
            System.out.println("Wysłano aktualizację tablicy");

            // TODO serwer pobiera ruch od klienta
            // wybranie pionka do ruszenia
            String[] odpowiedz = in.readLine().split(";");  // CH;Y
            System.out.println("Odebrano wybór pionka " + odpowiedz[0]);
            System.out.println("Odebrano wybór pionka " + odpowiedz[1]);

            // TODO JEŚLI OTRZYMA KOMUNIKAT END - KONIEC GRY
            if (odpowiedz[0].equals("END")){
                isGameOn = false;
                p.resetPoints();
                return false;
            }

            Character ch = odpowiedz[0].charAt(0);
            int y = Integer.parseInt(odpowiedz[1]);

            // wyświetlenie możliwych ruchów
            HashMap<Character,HashMap<Integer,Field>> b = board.getBoard();
            Field f = b.get(ch).get(y);
            String[] nextMove = f.getPawn().nextMove(false, board);
            System.out.println("Utworzono tablice ruchow");
            for(String s : nextMove){
                System.out.println(s);
            }

            // wysłanie listy do klienta
            wyslanieTablicy(nextMove, out, in);
            System.out.println("Wysłano tablice");

            // odebranie nowego ruchu od klienta
            odpowiedz = in.readLine().split(";"); // CH;Y
            System.out.println("Odebrano nowy ruch od klienta");
            if (odpowiedz[0].equals("NULL")){
                isGameOn = false;
                p.resetPoints();
                return false;
            }
            ch = odpowiedz[0].charAt(0);
            y = Integer.parseInt(odpowiedz[1]);

            // wykonanie ruchu
            board = f.getPawn().move(ch,y,board);

            // wysłanie aktualizacji tablicy
            msg = boardToString();
            wyslanieTablicy(msg, out, in);

            // sprawdzenie czy jest możliwy kolejny ruch
            f = b.get(ch).get(y);
            nextMove = f.getPawn().nextMove(true, board);

            while (nextMove != null){
                out.write("NEXT");  // poinformowanie klienta o możliwości następnego ruchu
                out.newLine();
                out.flush();

                // wysłanie listy do klienta
                wyslanieTablicy(nextMove, out, in);

                // odebranie nowego ruchu od klienta
                odpowiedz = in.readLine().split(";");  // CH;Y
                if (odpowiedz[0].equals("NULL")){
                    isGameOn = false;
                    p.resetPoints();
                    return false;
                }
                ch = odpowiedz[0].charAt(0);
                y = Integer.parseInt(odpowiedz[1]);

                // wykonanie ruchu
                f.getPawn().move(ch,y,board);

                // wysłanie aktualizacji tablicy
                msg = boardToString();
                wyslanieTablicy(msg, out, in);

                // sprawdzenie czy jest możliwy kolejny ruch
                f = b.get(ch).get(y);
                nextMove = f.getPawn().nextMove(true, board);

            }

            // jeśli nie ma ruchu -> idzie dalej
            out.write("STOP");  // koniec ruchu użytkownika
            out.newLine();
            out.flush();

            // TODO flaga zmienia się w momencie jak gracz skończy wykonywać swój ruch
            doesSomeonePlay = false;
            player.signal();

            return true;


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void wyslanieTablicy(String[] arr, BufferedWriter out, BufferedReader in) throws IOException {
        System.out.println("wyslano: " + arr.length);
        out.write("" + arr.length);
        out.newLine();
        out.flush();
        for (int i=0; i<arr.length; i++){
            System.out.println("wyslano: " + arr[i]);
            out.write(arr[i]);
            out.newLine();
            out.flush();
            String ack = in.readLine();
        }
    }

    public String[] boardToString(){
        int width = board.getWidth();
        Character height = board.getHeight();

        String[] msg = new String[width];
        HashMap<Character, HashMap<Integer,Field>> b = board.getBoard();

        for (Character i='A'; i<=height; i++){
            for (int j=1; j<=width; j++){
                Field f = b.get(i).get(j);
                System.out.println(f.getPawn());
            }
            System.out.println();
        }

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

            System.out.println("TABLICA KOMPRES: -> " + msg[k]);

            k++;
        }

        return msg;
    }
}
