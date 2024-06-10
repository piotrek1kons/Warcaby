package com.example.checkers;

import java.util.ArrayList;

public class Pawn {
    private boolean isWhite;
    private boolean isDead;
    private boolean isQueen;
    private Character ch;
    private Integer y;
    private int width = 8;
    private Character height = 'H';


    public Pawn(boolean isWhite, Character ch, Integer y){
            this.isWhite = isWhite;
            this.isDead = false;
            this.isQueen = false;
            this.ch = ch;
            this.y = y;
    }

    // TODO musi zmienić planszę na to żeby Pawn na polu Field było NULL ale nw jak to zrobić
    public void kill(){
        isDead = true;
    }

    // TODO ta funkcja poza ruchem powinna też wykonywać bicie pionków (jeżeli taka sytuacja zaistniała)
    // TODO możemy określić ile pkt gracz dostanie za zbicie pionka (maybe 10??) i na podstawie liczby punktów określimy
    // TODO kto wygrał w danej rundzie (max punktów -> gra się zakończy)
    public Pair<Board,Boolean> move(Character ch, int y, Board board){
        Boolean czyBicie = false;
        if (Math.abs(this.ch - ch) == 2 && Math.abs(this.y - y) == 2) {
            Character middleCh = (char) ((this.ch + ch) / 2);
            int middleY = (this.y + y) / 2;

            if (board.getField(middleCh, middleY).getPawn() != null &&
                    board.getField(middleCh, middleY).getPawn().isWhite() != this.isWhite()) {
                // Wykonaj bicie
                board.getField(middleCh, middleY).getPawn().kill();
                board.getField(middleCh, middleY).setPawn(null);
                czyBicie = true;
            }
        }
        board.getField(this.ch, this.y).setPawn(null);
        board.getField(ch, y).setPawn(this);
        this.ch = ch;
        this.y = y;

        return new Pair<>(board, czyBicie);
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
        if (chDirection < 'A' || chDirection > height) {
            return false;
        }

        if (yDirection <= 0 || yDirection > width){
            return false;
        }

        return true;
    }

    public Character chIncerment(Character chp, int i, int increment){
        return (i == 1 ? (char)(chp + increment) : (char)(chp - increment));
    }

    public String checkKill(Character chp, int yp, int increment, Board board){
        if(this.isWhite){
            if (board.getField(chp, yp).getPawn().isWhite != this.isWhite) {
                System.out.println(chIncerment(chp,increment,1) +";"+ yp + increment);
                if (isOnBoard(chIncerment(chp,increment, 1), yp + increment)) {
                    //sprawdza czy pole jest wolne
                    if (isFieldFree(board.getField(chIncerment(chp,increment,1), yp + increment))) {
                        //zabicie pionka
                        //podswietlenie pola
                        return chIncerment(chp,increment, 1) + ";" + (yp + increment);
                    }
                }
            }
        }else{
            if (board.getField(chp, yp).getPawn().isWhite != this.isWhite) {
                System.out.println(chIncerment(chp,increment,1) +";"+ yp + increment);
                if (isOnBoard(chIncerment(chp,increment, 1), yp + increment)) {
                    //sprawdza czy pole jest wolne
                    if (isFieldFree(board.getField(chIncerment(chp,increment, 1), yp + increment))) {
                        //zabicie pionka
                        //podswietlenie pola
                        return chIncerment(chp,increment, 1) + ";" + (yp + increment);
                    }
                }
            }
        }

        return null;
    }

    public String checkMove(Character chp, int yp, int increment, Board board){
        //sprawdza czy pole znajduje sie na planszy
        if(isOnBoard(chp, yp)) {
            //sprawdza czy pole jest wolne
            if (isFieldFree(board.getField(chp, yp))) {
                System.out.println(chp + ";" + yp);
                return chp + ";" + yp;
            } else {
                return checkKill(chp,yp,increment,board);
            }
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
            temp = checkKill((char)(chp - 1),yp - 1,-1,board);
            if(temp != null){
                moves.add(temp);
            }

            temp = checkKill((char)(chp - 1),yp + 1,1,board);
            if(temp != null){
                moves.add(temp);
            }

            temp = checkKill((char)(chp + 1),yp - 1,-1,board);
            if(temp != null){
                moves.add(temp);
            }

            temp = checkKill((char)(chp + 1),yp + 1,1,board);
            if(temp != null){
                moves.add(temp);
            }

        }else{
            if(isWhite){
                temp = checkMove((char)(chp - 1),yp - 1,-1,board);
                if(temp != null){
                    moves.add(temp);
                }
                temp = checkMove((char)(chp - 1),yp + 1,1,board);
                if(temp != null){
                    moves.add(temp);
                }
            }else {
                temp = checkMove((char)(chp + 1),yp - 1,-1,board);
                if(temp != null){
                    moves.add(temp);
                }
                temp = checkMove((char)(chp + 1),yp + 1,1,board);
                if(temp != null){
                    moves.add(temp);
                }
            }

        }



        String [] movesArray = new String[moves.size()];
        for(int i = 0; i < moves.size(); i++){
            movesArray[i] = moves.get(i);
        }
        if(moves.size() == 0){
            return null;
        }
        // TODO jeśli nie ma następnego ruchu to niech zwróci NULL
        return movesArray;
    }

    // TODO --------- GETTERY I SETTERY ---------
    public boolean isWhite() {
        return this.isWhite;
    }
    public Character getCh() {
        return ch;
    }
    public Integer getY() {
        return y;
    }
    public void setQueen(boolean isQueen){
        this.isQueen = isQueen;
    }
    public boolean getIsQueen(){
        return isQueen;
    }
    public int getWidth() {return width;}

    public Character getHeight() {return height;}
    public void setDead(boolean dead) {isDead = dead;}
    public void setCh(Character ch) {this.ch = ch;}
    public void setY(Integer y) {this.y = y;}
}
