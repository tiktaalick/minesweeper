package org.mark.minesweeper.service;

import org.mark.minesweeper.model.MineField;
import org.mark.minesweeper.swing.MineButton;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MineService {
    public List<MineField> createGrid(int columns, int rows, ActionListener actionListener,
                                      MouseListener mouseListener) {
        List<MineField> grid = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                grid.add(new MineField()
                        .setId(MineField.createId(column, row, columns))
                        .setX(column)
                        .setY(row)
                        .setButton(new MineButton(
                                column * MineButton.FIELD_WIDTH,
                                row * MineButton.FIELD_WIDTH,
                                "",
                                actionListener,
                                mouseListener))
                        .setLocalMineCount(0)
                        .setFlag(false)
                        .setMine(false)
                        .setRevealed(false)
                );
            }
        }

        return grid;
    }

}
