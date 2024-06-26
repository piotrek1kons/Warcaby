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

    public String checkKill(Character chp, int yp, int incrementch, int incrementY, Board board){
        if (!isOnBoard(chp, yp)){
            return null;
        }

        if(this.isWhite){
            Pawn p = board.getField(chp, yp).getPawn();
            if (p != null){
                if (p.isWhite != this.isWhite) {
                    System.out.println(chIncerment(chp,incrementch,1) +";"+ (yp + incrementY));
                    if (isOnBoard(chIncerment(chp,incrementch, 1), yp + incrementY)) {
                        //sprawdza czy pole jest wolne
                        if (isFieldFree(board.getField(chIncerment(chp,incrementch,1), yp + incrementY))) {
                            //zabicie pionka
                            //podswietlenie pola
                            return chIncerment(chp,incrementch, 1) + ";" + (yp + incrementY);
                        }
                    }
                }
            }

        }else{
            Pawn p = board.getField(chp, yp).getPawn();
            if (p != null){
                if (board.getField(chp, yp).getPawn().isWhite != this.isWhite) {
                    System.out.println(chIncerment(chp,incrementch,1) +";"+ (yp + incrementY));
                    if (isOnBoard(chIncerment(chp,incrementch, 1), yp + incrementY)) {
                        //sprawdza czy pole jest wolne
                        if (isFieldFree(board.getField(chIncerment(chp,incrementch, 1), yp + incrementY))) {
                            return chIncerment(chp,incrementch, 1) + ";" + (yp + incrementY);
                        }
                    }
                }
            }

        }

        return null;
    }

    public String checkMove(Character chp, int yp, int increment, int incrementY, Board board){
        //sprawdza czy pole znajduje sie na planszy
        if(isOnBoard(chp, yp)) {
            //sprawdza czy pole jest wolne
            if (isFieldFree(board.getField(chp, yp))) {
                System.out.println(chp + ";" + yp);
                return chp + ";" + yp;
            } else {
                return checkKill(chp,yp,increment,incrementY, board);
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
        System.out.println("xpp1");
        if(firstKill) {
            System.out.println("xpp1.5");

            temp = checkKill((char)(chp - 1),yp - 1,-1, -1,board);
            if(temp != null){
                moves.add(temp);
            }
            System.out.println("xpp1.6");

            temp = checkKill((char)(chp - 1),yp + 1,-1, 1,board);
            if(temp != null){
                moves.add(temp);
            }
            System.out.println("xpp1.7");

            temp = checkKill((char)(chp + 1),yp - 1,1,-1,board);
            if(temp != null){
                moves.add(temp);
            }
            System.out.println("xpp1.");
            temp = checkKill((char)(chp + 1),yp + 1,1,1,board);
            if(temp != null){
                moves.add(temp);
            }
            System.out.println("xpp2");


        }else{
            if(isWhite){
                System.out.println("xpp3");
                temp = checkMove((char)(chp - 1),yp - 1,-1,-1,board);
                if(temp != null){
                    moves.add(temp);
                }
                temp = checkMove((char)(chp - 1),yp + 1,-1, 1,board);
                if(temp != null){
                    moves.add(temp);
                }
            } else {
                System.out.println("xpp4");
                temp = checkMove((char)(chp + 1),yp - 1,1, -1,board);
                if(temp != null){
                    moves.add(temp);
                }
                temp = checkMove((char)(chp + 1),yp + 1,1,1,board);
                if(temp != null){
                    moves.add(temp);
                }
            }

        }
        System.out.println("xpp5");
        if(moves.isEmpty()){
            return null;
        }
        for(int i = 0; i < moves.size(); i++){
            System.out.printf("dupa: "+moves.get(i) + "\n");
        }

        String [] movesArray = new String[moves.size()];
        for(int i = 0; i < moves.size(); i++){
            movesArray[i] = moves.get(i);
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
