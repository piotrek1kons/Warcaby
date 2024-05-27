package com.example.checkers;

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

    // TODO to chyba można zrobić inaczej
    public void move(Character ch, int y){
        this.ch = ch;
        this.y = y;
    }


    // sprawdza czy pole znajduje się na planszy
    public boolean isOnBoard(Character chDirection, int yDirection){
        if (chDirection <= 'A' || chDirection > height) {
            return false;
        }

        if (yDirection <= 0 || yDirection > width){
            return false;
        }

        return true;
    }

    public void nextMove(){
        boolean firstKill = false;
        Character chp = getCh();
        int yp = getY();
        if(isWhite){
            if(isOnBoard(chp--, yp - 1)){
                chp++;
                //sprawdzic czy pole wolne
                    //podswietlenie
                //inaczej sprawdza kolor
                    // i sprawdza nastepne pole na skos i czy jest wolne
                    // podswietlanie
            }else if(isOnBoard(chp--, yp + 1)){
                chp++;
                //sprawdzic czy pole wolne
                    //podswietlenie
                //inaczej sprawdza kolor
                    // i sprawdza nastepne pole na skos i czy jest wolne
                    // podswietlanie
            }
        }else {
            if (isOnBoard(chp++, yp - 1)) {
                chp++;
                //sprawdzic czy pole wolne
                    //podswietlenie
                //inaczej sprawdza kolor
                    // i sprawdza nastepne pole na skos i czy jest wolne
                    // podswietlanie
            } else if (isOnBoard(chp++, yp + 1)) {
                chp++;
                //sprawdzic czy pole wolne
                    //podswietlenie
                //inaczej sprawdza kolor
                    // i sprawdza nastepne pole na skos i czy jest wolne
                    // podswietlanie
            }
        }
        if(firstKill) {

        }else{

        }
    }

    // TODO --------- GETTERY I SETTERY ---------
    public boolean isWhite() {
        return isWhite;
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
}
