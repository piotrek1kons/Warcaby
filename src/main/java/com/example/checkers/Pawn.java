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

    // TODO ta funkcja poza ruchem powinna też wykonywać bicie pionków (jeżeli taka sytuacja zaistniała)
    // TODO możemy określić ile pkt gracz dostanie za zbicie pionka (maybe 10??) i na podstawie liczby punktów określimy
    // TODO kto wygrał w danej rundzie (max punktów -> gra się zakończy)
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

    // TODO powinno zwracać listę możliwych ruchów żeby można było wysłać ją do klienta w stringu
    // TODO wystarczy na jedno sprawdzenie, ta funkcja będzie w pętli while w wątku
    public String[] nextMove(boolean firstKill){
        //boolean firstKill = false;
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

        // TODO jeśli nie ma następnego ruchu to niech zwróci NULL
        return null;
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
    public boolean getIsQueen(){
        return isQueen;
    }
}
