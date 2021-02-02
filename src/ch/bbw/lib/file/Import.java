package ch.bbw.lib.file;

import ch.bbw.model.Cell;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Import {


    public static Cell[][] importIsAliveStates(String path){

        Cell[][] cell = null;
        int length = 0;
        int x = 0;

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line;
            while ((line = bufferedReader.readLine())!= null){
                length = line.length();
                if (cell == null) {
                    cell = new Cell[length][length];
                }
                for(int y = 0; y < length; y++){
                    cell[x][y] = new Cell();
                    if (line.charAt(y) == '1') {
                        cell[x][y].setNewState(true);
                    } else {
                        cell[x][y].setNewState(false);
                    }
                    cell[x][y].updateState();
                }
                x++;
            }

        }catch (Exception e){

        }
        return cell;
    }
}
