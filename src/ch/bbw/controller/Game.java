package ch.bbw.controller;

import ch.bbw.model.Cell;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Random;


public class Game {
    private Cell [][] grid;
    private boolean showCellAge;
    private int speed;
    private Thread runnerThread;
    private GridPane gridPane;

    private boolean pauseRunningThread;

    private int width = 20;
    private int height = 20;


    public Game(Cell[][] grid, GridPane gridPane) {
        this.grid = grid;
        this.gridPane = gridPane;
        this.showCellAge = false;
        this.pauseRunningThread = true;
        this.speed = 1000;
        this.width = grid.length;
        this.height = grid.length;

        runnerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (!pauseRunningThread) {
                    try {
                        Game.this.calculateGeneration();
                        if (!Game.this.cellsChanging()) {
                            Platform.runLater(() -> {
                                Stage dialog = new Stage();
                                dialog.initStyle(StageStyle.UTILITY);
                                Scene scene = new Scene(new Group(new Text(25, 25, "No more generations!")));
                                dialog.setScene(scene);
                                dialog.showAndWait();
                            });
                            pauseRunningThread = true;
                        }
                        Game.this.updateCells();
                        Game.this.showGrid();
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        runnerThread.start();
    }

    private boolean cellsChanging() {
        boolean cellsChanged = false;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y].isOldState() != grid[x][y].isNewState()) {
                    cellsChanged = true;
                }
            }
        }
        return cellsChanged;
    }

    public void showGrid(){
        for (int x = 0; x < grid.length; x++) {
            for(int y = 0; y < grid[x].length; y++){
                if(grid[x][y].isAlive()){
                    if (showCellAge) {
                        ((Rectangle)(gridPane.getChildren().get(x * grid.length + y))).setFill(Color.RED);
                        if (grid[x][y].getAge() > 0) {
                            ((gridPane.getChildren().get(x * grid.length + y))).setOpacity(1 / new Double(grid[x][y].getAge()));
                        }
                    } else {
                        ((Rectangle)(gridPane.getChildren().get(x * grid.length + y))).setFill(Color.RED);
                    }
                }else{
                    ((Rectangle)(gridPane.getChildren().get(x * grid.length + y))).setFill(Color.WHITE);
                    ((gridPane.getChildren().get(x * grid.length + y))).setOpacity(1);
                }
            }
        }
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

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {

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

        for (int x = 0; x < grid.length; x++) {
            for(int y = 0; y < grid[x].length; y++){
                getCell(x,y).updateState();
            }
        }

    }

    public Cell[][] getGrid() {
        return grid;
    }

    public Cell getCell(int row, int collumn){

        return grid[row][collumn];
    }

    public EventHandler<ActionEvent> start() {
        return event -> pauseRunningThread = false;
    }

    public EventHandler<ActionEvent> stop() {
        return event -> pauseRunningThread = true;
    }

    public EventHandler<ActionEvent> showCellAge() {
        return event -> showCellAge = !showCellAge;
    }

    public void setGenerationSpeed(Number newValue) {
        speed = (int) (newValue.doubleValue()*1000);
    }

    public EventHandler<ActionEvent> random() {
        return event -> {
            pauseRunningThread = true;
            Random random = new Random();
            for (int x = 0; x < getGrid().length; x++) {
                for (int y = 0; y < getGrid()[x].length; y++) {
                    getGrid()[x][y].setNewState(random.nextBoolean());
                    getGrid()[x][y].resetAge();
                    getGrid()[x][y].updateState();
                }
            }
            showGrid();
        };
    }

    public void importFile(Cell[][] importIsAliveStates) {
        pauseRunningThread = true;
        this.grid = importIsAliveStates;
        showGrid();
    }
}

