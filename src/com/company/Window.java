package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Window implements Runnable {

    static int SIZE = 20;
    static int WIDTH = 40;
    static int HEIGHT = 30;
    private Timer timer;
    JFrame frame;
    Box[][] boxes;
    JPanel buttons = new JPanel();

    @Override
    public void run() {
        initFrame();
        initBoxes();
        initButtons();
    }

    void initFrame(){
        frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(SIZE*WIDTH-14, SIZE*HEIGHT + 70);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Game of Life");
    }

    void initBoxes(){
        boxes = new Box[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT ; y++) {
                Cell cell = new Cell();
                boxes[x][y] = new Box(x, y, cell);
                frame.add(boxes[x][y]);
            }
        }

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) { //выбираем конкретную клетку
                for (int sx = -1; sx <= 1; sx++) {
                    for (int sy = -1; sy <= 1; sy++) { //проверяем все 9 клеток вокруг этой клетки(включая эту же центральную клетку)
                        if(!(sx==0 && sy == 0)){ //здесь мы отсеиваем центральную клетку (с координатами x и y)
                            boxes[x][y].cell.addNear(boxes
                                    [(x + sx + WIDTH) % WIDTH] //если мы выйдем за рамки влево, вправо вверхи или вниз, мы перейдем на другую сторону(то есть, мы "склеиили" наше поле со всех сторон)
                                    [(y + sy + HEIGHT) % HEIGHT].cell); //и добавляем все эти 8 клеток в качестве соседов центарльной клетки
                        }
                    }
                }
            }
        }

    }

    void initButtons(){
        buttons.setLayout(new FlowLayout());

        JLabel label = new JLabel("Time");
        JTextField timeField = new JTextField(10);

        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    timer.cancel();
                }catch (NullPointerException ex){

                }

                try{
                    double time = Double.parseDouble(timeField.getText());
                    startGame(time);
                }catch (Exception ex){

                }

            }
        });

        JButton stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.cancel();
            }
        });

        JButton nextStep = new JButton("next step");
        nextStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int x = 0; x < WIDTH; x++) {
                    for (int y = 0; y < HEIGHT ; y++) {
                        boxes[x][y].cell.step1();
                    }
                }

                for (int x = 0; x < WIDTH; x++) {
                    for (int y = 0; y < HEIGHT; y++) {
                        boxes[x][y].cell.step2();
                        boxes[x][y].setBoxColor();
                    }
                }
            }
        });

        JButton clear = new JButton("clear area");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int x = 0; x < WIDTH; x++) {
                    for (int y = 0; y < HEIGHT; y++) {
                        boxes[x][y].cell.state = 0;
                        boxes[x][y].setBoxColor();
                    }
                }
            }
        });

        buttons.add(label);
        buttons.add(timeField);
        buttons.add(start);
        buttons.add(stop);
        buttons.add(nextStep);
        buttons.add(clear);
        frame.getContentPane().add(buttons, BorderLayout.AFTER_LAST_LINE);
    }

    private void startGame(double time){
        int T = (int)(time*1000);
        timer = new Timer();
        timer.schedule(new TimerTask() { //и теперь, когда первый игрок подключился, запускаем таймер

            @Override
            public void run() { //ПЕРЕЗАГРУЖАЕМ МЕТОД RUN В КОТОРОМ ДЕЛАЕТЕ ТО ЧТО ВАМ НАДО
                for (int x = 0; x < WIDTH; x++) {
                    for (int y = 0; y < HEIGHT ; y++) {
                        boxes[x][y].cell.step1();
                    }
                }

                for (int x = 0; x < WIDTH; x++) {
                    for (int y = 0; y < HEIGHT; y++) {
                        boxes[x][y].cell.step2();
                        boxes[x][y].setBoxColor();
                    }
                }

            }
        }, 0, T); //(delay - ПОДОЖДАТЬ ПЕРЕД НАЧАЛОМ В МИЛИСЕК, ПОВТОРЯТСЯ period СЕКУНД (1 СЕК = 1000 МИЛИСЕК))
    }




}
