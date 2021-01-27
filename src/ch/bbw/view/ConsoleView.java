package ch.bbw.view;

import ch.bbw.model.Cell;

import java.io.IOException;

public class ConsoleView {

    public ConsoleView() {

    }

    public void displayCells(Cell[][] grid){

//        System.out.print("\033[H\033[2J");
//
//        System.out.flush();

        try {
            Runtime.getRuntime().exec("clear");
        }catch (IOException e){

        }


        String border = "";

        for(int i = 0; i < grid.length; i++){
            border += " - ";
        }

        System.out.print(border+"\n");

        String splitter = "|";

        for(Cell[] row : grid){
            String line = splitter;

            for(Cell collumn : row){
                if (collumn.isAlive()) {
                    line += " # ";
                }else{
                    line += "   ";
                }

            }
            line += splitter;
            System.out.print(line+"\n");

        }

        System.out.print(border+"\n");


    }
}
