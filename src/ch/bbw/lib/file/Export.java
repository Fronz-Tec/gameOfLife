package ch.bbw.lib.file;

import ch.bbw.model.Cell;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Export {

    public static void exportIsAliveState(Cell[][] cell){
        StringBuilder stringBuilder = new StringBuilder();

        for (int x = 0; x < cell.length; x++) {
            for(int y = 0; y < cell[x].length; y++){
                stringBuilder.append(cell[x][y].isAlive()?1:0);
            }
            stringBuilder.append("\n");
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:/tmp/conway_export.txt"));
            writer.write(stringBuilder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
