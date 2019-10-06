package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Box extends JPanel{

    Cell cell;
    static Color defaultColor = Color.BLUE;

    public Box(int x, int y, final Cell cell) { //координаты ячейки
        super();
        this.cell = cell;
        setBounds(x * Window.SIZE, y * Window.SIZE, Window.SIZE, Window.SIZE);
        setBackground(defaultColor);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeCellState();
                setBoxColor();
            }
        });
    }

    public void changeCellState(){
        if(cell.state == 0){
            cell.state = 1;
        }
        else
            cell.state = 0;
    }


    public void setBoxColor(){
        if(cell.state == 1){
            setBackground(Color.WHITE);
        }else
            setBackground(defaultColor);
    }



}
