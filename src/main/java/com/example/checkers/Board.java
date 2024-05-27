package com.example.checkers;

import java.util.HashMap;

public class Board {
    private HashMap<Character,HashMap<Integer,Field>> board;
    private int width = 8;
    private Character height = 'H';

    public Board() {
        // utworzenie planszy
        board = new HashMap<Character,HashMap<Integer,Field>>();
        Character ch = 'A';
        boolean isWhite = true;
        for(int i = 0; i < height; i++){
            HashMap<Integer,Field> temp = new HashMap<Integer,Field>();;
            for(int j = 0; j < width; j++){
                temp.put(j+1, new Field(isWhite));
                isWhite = !isWhite;
            }
            board.put(ch,temp);
            ch++;
        }

        // ustawienie pionków na planszy
        setBoard();
    }

    // TODO --------- FUNKCJE SPRAWDZAJĄCE ---------
    // sprawdza czy pole jest wolne
    public boolean isFieldFree(Field field){
        if (field.getPawn() != null){
            return false;
        }

        return true;
    }




    // TODO --------- ZMIANA PIONKA NA KRÓLOWĄ ---------
    public void avancePawn(Pawn pawn){
        Pawn avancedPawn;

        if (pawn.isWhite()){
            if (pawn.getCh()-1 < 'A'){
                avancedPawn = new Queen(pawn);
                board.get('A').get(pawn.getY()).setPawn(avancedPawn);
            }
        } else {
            if (pawn.getCh()-1 > height){
                avancedPawn = new Queen(pawn);
                board.get(height).get(pawn.getY()).setPawn(avancedPawn);
            }
        }

    }


    // TODO --------- PLANSZA ---------
    // ustawienie pionków na planszy
    public void setBoard(){

        for(Character i = 'A'; i < height; i++){
            HashMap<Integer,Field> temp = board.get(i);
            for(int j = 0; j < width; j++){
                if(i < 3 && (i+j)%2 == 1){
                    temp.get(j+1).setPawn(new Pawn(true,i,i+1));
                    temp.put(j+1, temp.get(j+1));
                }else if(i > 4 && (i+j)%2 == 1){
                    temp.get(j+1).setPawn(new Pawn(false,i,i+1));
                    temp.put(j+1, temp.get(j+1));
                }
            }
            board.put(i,temp);
        }
    }
    // wyświetlenie planszy
    public void showBoard(){

        System.out.print("\t");
        for(int i = 0; i < width; i++){
            System.out.print((i+1) + "\t");
        }
        System.out.println();

        for(Character i = 'A'; i < height; i++){
            HashMap<Integer,Field> temp = board.get(i);

            System.out.print(i + "\t");


            for(int j = 0; j < width; j++){
                if(temp.get(j+1) != null) {
                    System.out.print((temp.get(j + 1).getPawn().isWhite() ? "W" : "B") + "\t");
                }else{
                    System.out.print("n\t");
                }
            }
            System.out.println();
        }
    }
}
