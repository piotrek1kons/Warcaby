package com.example.checkers;

public class Pair<V,K> {
    private V first;
    private K second;

    public Pair (V first, K second){
        this.first = first;
        this.second = second;
    }

    public V getFirst(){
        return first;
    }

    public K getSecond(){
        return second;
    }
}