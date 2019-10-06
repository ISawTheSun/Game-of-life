package com.company;


import java.util.ArrayList;

public class Cell {
    ArrayList<Cell> near;
    int state = 0;
    int nextState;

    public Cell(){
        near = new ArrayList<Cell>();
    }

    void addNear(Cell cell){
        near.add(cell);
    }

    void step1(){
        int sumAround = countNearCells();
        if(state == 0 && sumAround == 3)
            nextState = 1;
        else if(state == 1 && (sumAround == 2 || sumAround == 3)){
            nextState = 1;
        } else
            nextState = 0;
    }

    void step2(){
        state = nextState;
    }


    int countNearCells(){
       int sum = 0;
       for (Cell cell: near){
           sum += cell.state;
       }
       return sum;
    }
}
