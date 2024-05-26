package com.example.checkers;

import java.util.HashMap;

public class Board {
    private HashMap<Character,HashMap<Integer,Field>> board;
    private int width = 8;
    private int height = 8;

    public Board() {
        // utworzenie planszy
        board = new HashMap<Character,HashMap<Integer,Field>>();
        Character ch = 'A';
        for(int i = 0; i < height; i++){
            HashMap<Integer,Field> temp = new HashMap<Integer,Field>();;
            for(int j = 0; j < width; j++){
                temp.put(j+1, null);
            }
            board.put(ch,temp);
            ch++;
        }

        // ustawienie pionków na planszy
        setBoard();
    }

    // TODO nie rozumiem co te funkcje poniżej miały robić ale się nie zgrywają z nowymi ustaleniami
/*
    public void setField(Character ch, int y, Pawn pawn){
        HashMap<Integer,Field> temp = board.get(ch);
        temp.put(y, pawn);
        board.put(ch,temp);
    }

    public void removeField(Character ch, int y){
        HashMap<Integer,Pawn> temp = board.get(ch);
        temp.put(y, null);
        board.put(ch,temp);
    }

 */

    public void setBoard(){
        Character ch = 'A';
        for(int i = 0; i < height; i++){
            HashMap<Integer,Field> temp = board.get(ch);
            for(int j = 0; j < width; j++){
                if(i < 3 && (i+j)%2 == 1){
                    temp.get(j+1).setPawn(new Pawn(true,ch,i+1));
                    temp.put(j+1, temp.get(j+1));
                }else if(i > 4 && (i+j)%2 == 1){
                    temp.get(j+1).setPawn(new Pawn(false,ch,i+1));
                    temp.put(j+1, temp.get(j+1));
                }
            }
            board.put(ch,temp);
            ch++;
        }
    }

    public void showBoard(){
        Character ch = 'A';

        System.out.print("\t");
        for(int i = 0; i < width; i++){
            System.out.print((i+1) + "\t");
        }
        System.out.println();

        for(int i = 0; i < height; i++){
            HashMap<Integer,Field> temp = board.get(ch);

            System.out.print(ch + "\t");


            for(int j = 0; j < width; j++){
                if(temp.get(j+1) != null) {
                    System.out.print((temp.get(j + 1).getPawn().isWhite() ? "W" : "B") + "\t");
                }else{
                    System.out.print("n\t");
                }
            }
            System.out.println();
            ch++;
        }
    }
}
