package ch.bbw.controller;

import ch.bbw.model.Cell;

public class Game {

    private int width = 20;
    private int height = 20;

    private Cell [][] grid;

    public Game(Cell[][] grid) {
        this.grid = grid;
        this.width = grid.length;
        this.height = grid.length;
    }

    public int countNeighbors(int row, int collumn){
        int result = 0;


        if(hasNeighbor(row-1,collumn-1)){
            result++;
        }

        if(hasNeighbor(row-1,collumn)){
            result++;
        }

        if(hasNeighbor(row-1,collumn+1)){
            result++;
        }

        if(hasNeighbor(row,collumn-1)){
            result++;
        }

        if(hasNeighbor(row,collumn+1)){
            result++;
        }

        if(hasNeighbor(row+1,collumn-1)){
            result++;
        }

        if(hasNeighbor(row+1,collumn)){
            result++;
        }

        if(hasNeighbor(row+1,collumn+1)){
            result++;
        }




        return result;
    }

    private boolean hasNeighbor(int row, int collumn){

        if(row >= 0 && collumn >= 0 && row < grid.length && collumn < grid.length){

            return getCell(row,collumn).isAlive();

        }

        return false;
    }

    public void calculateGeneration(){

        for (int x = 0; x <= grid.length; x++) {
            for (int y = 0; y <= grid[x].length; y++) {

                int neighbors = countNeighbors(x,y);

                if(neighbors<=1 || neighbors > 3){
                    //Cell dies

                    getCell(x,y).setNewState(false);

                }else if(neighbors == 2){
                    //Cell survives

                    boolean currentCellState = getCell(x,y).isAlive();
                    getCell(x,y).setNewState(currentCellState);

                }else if(neighbors == 3){
                    //new Cell

                    getCell(x,y).setNewState(true);
                }
            }
        }


    }

    public void updateCells(){

        for (int x = 0; x <= grid.length; x++) {
            for(int y = 0; y <= grid[x].length; y++){
                getCell(x,y).updateState();
            }
        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public Cell getCell(int row, int collumn){

        return grid[row][collumn];
    }

}
