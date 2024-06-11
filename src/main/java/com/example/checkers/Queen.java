package com.example.checkers;

import java.util.ArrayList;

public class Queen extends Pawn {

    public Queen(Pawn pawn) {
        super(pawn.isWhite(), pawn.getCh(), pawn.getY());
        super.setQueen(true);
    }

    // TODO musi zmienić planszę na to żeby Pawn na polu Field było NULL ale nw jak to zrobić
    public void kill(){
        super.setDead(true);
    }

    // TODO ta funkcja poza ruchem powinna też wykonywać bicie pionków (jeżeli taka sytuacja zaistniała)
    // TODO możemy określić ile pkt gracz dostanie za zbicie pionka (maybe 10??) i na podstawie liczby punktów określimy
    // TODO kto wygrał w danej rundzie (max punktów -> gra się zakończy)
    public void move(Character ch, int y){
        super.setCh(ch);
        super.setY(y);
    }

    // sprawdza czy pole jest wolne
    public boolean isFieldFree(Field field){
        if (field.getPawn() != null){
            return false;
        }
        return true;
    }

    // sprawdza czy pole znajduje się na planszy
    public boolean isOnBoard(Character chDirection, int yDirection){
        if (chDirection <= 'A' || chDirection > super.getHeight()) {
            return false;
        }

        if (yDirection <= 0 || yDirection > super.getWidth()){
            return false;
        }

        return true;
    }

    public Character chIncerment(Character chp, int i){
        return (chp == 1 ? chp++ : chp--);
    }

    public String checkKill(Character chp, int yp, int increment, Board board){
        if (board.getField(chp, yp).getPawn().isWhite() == super.isWhite()) {

            //koniec ruchu
        } else{
            if (isOnBoard(chIncerment(getCh(),increment), yp + increment)) {
                //sprawdza czy pole jest wolne
                if (isFieldFree(board.getField(chp, yp + increment))) {
                    //zabicie pionka
                    //podswietlenie pola
                    return chp + ";" + (yp + increment);
                } else {
                    //koniec ruchu
                }
            }else{
                //koniec ruchu
            }
        }
        return null;
    }

    public String checkMove(Character chp, int yp, int increment, Board board){
        //sprawdza czy pole znajduje sie na planszy
        if(isOnBoard(chp, yp)) {
            //sprawdza czy pole jest wolne
            if (isFieldFree(board.getField(chp, yp))) {
                return chp + ";" + yp;
                //podswietlenia pola
                //sprawdza kolor pionka na nastepnym polu
            } else {
                return checkKill(chp,yp,increment,board);
            }
        }else{
            //koniec ruchu

        }
        return null;
    }
    // TODO powinno zwracać listę możliwych ruchów żeby można było wysłać ją do klienta w stringu
    // TODO wystarczy na jedno sprawdzenie, ta funkcja będzie w pętli while w wątku
    public String[] nextMove(boolean firstKill, Board board){
        //boolean firstKill = false;
        Character chp = getCh();
        ArrayList<String> moves = new ArrayList<String>();
        String temp;
        int yp = getY();

        if(firstKill) {
            temp = checkMove(chp--,yp--,-1,board);
            if(temp != null){
                moves.add(temp);
            }

            temp = checkMove(chp--,yp++,1,board);
            if(temp != null){
                moves.add(temp);
            }

            temp = checkMove(chp++,yp++,1,board);
            if(temp != null){
                moves.add(temp);
            }

            temp = checkMove(chp++,yp--,1,board);
            if(temp != null){
                moves.add(temp);
            }

        }else{
            if(isWhite()){
                temp = checkMove(chp--,yp--,-1,board);
                if(temp != null){
                    moves.add(temp);
                }
                temp = checkMove(chp--,yp++,1,board);
                if(temp != null){
                    moves.add(temp);
                }
            }else {
                temp = checkMove(chp++,yp--,-1,board);
                if(temp != null){
                    moves.add(temp);
                }
                temp = checkMove(chp++,yp++,1,board);
                if(temp != null){
                    moves.add(temp);
                }
            }
        }

        String [] movesArray = new String[moves.size()];
        for(int i = 0; i < moves.size(); i++){
            movesArray[i] = moves.get(i);
        }
        // TODO jeśli nie ma następnego ruchu to niech zwróci NULL
        return movesArray;
    }
}
