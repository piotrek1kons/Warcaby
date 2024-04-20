package com.example.checkers;

import java.util.HashMap;

public class Board {
    // TODO zamienic Boolean na Pawn
    private HashMap<Character,HashMap<Integer,Pawn>> board;
    private int width = 8;
    private int height = 8;

    public Board() {
        board = new HashMap<Character,HashMap<Integer,Pawn>>();
        Character ch = 'A';
        for(int i = 0; i < height; i++){
            HashMap<Integer,Pawn> temp = new HashMap<Integer,Pawn>();;
            for(int j = 0; j < width; j++){
                temp.put(j+1, null);
            }
            board.put(ch,temp);
            ch++;
        }
    }


    public void setField(Character ch, int y, Pawn pawn){
        HashMap<Integer,Pawn> temp = board.get(ch);
        temp.put(y, pawn);
        board.put(ch,temp);
    }

    public void removeField(Character ch, int y){
        HashMap<Integer,Pawn> temp = board.get(ch);
        temp.put(y, null);
        board.put(ch,temp);
    }

    public void setBoard(){
        Character ch = 'A';
        for(int i = 0; i < height; i++){
            HashMap<Integer,Pawn> temp = board.get(ch);
            for(int j = 0; j < width; j++){
                if(i < 3 && (i+j)%2 == 1){
                    temp.put(j+1, new Pawn(true,ch,i+1));
                }else if(i > 4 && (i+j)%2 == 1){
                    temp.put(j+1, new Pawn(false,ch,i+1));
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
            HashMap<Integer,Pawn> temp = board.get(ch);

            System.out.print(ch + "\t");


            for(int j = 0; j < width; j++){
                if(temp.get(j+1) != null) {
                    System.out.print((temp.get(j + 1).isWhite() ? "W" : "B") + "\t");
                }else{
                    System.out.print("n\t");
                }
            }
            System.out.println();
            ch++;
        }
    }
}
