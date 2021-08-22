package org.mark.minesweeper.logic;

import org.mark.minesweeper.MineSweeper;
import org.mark.minesweeper.model.MineField;
import org.mark.minesweeper.service.MineService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MineLogic {
    public static final int IN_PROGRESS = 1;
    public static final int HAS_LOST = 2;
    public static final int HAS_WON = 3;
    public static final int COLUMNS = 9;
    public static final int ROWS = 9;
    public static final int NUMBER_OF_MINES = 10;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 450;

    @Autowired
    MineService mineService;

    public void addMines(List<MineField> grid, int numberOfMines) {
        int mine = 0;

        while (mine < numberOfMines) {
            int index = new Random().nextInt(grid.size());

            if (!grid.get(index).hasMine()) {
                grid.get(index).setMine(true);
                mine++;
            }
        }
    }

    public void addLocalMineCount(List<MineField> grid) {
        for (MineField mineField : grid) {
            mineField.setLocalMineCount(calculateLocalMineCount(grid, mineField));
        }
    }

    private long calculateLocalMineCount(
            List<MineField> grid,
            MineField mineField) {

        return grid.stream()
                .filter(neighbour -> Math.abs(neighbour.getX() - mineField.getX()) <= 1)
                .filter(neighbour -> Math.abs(neighbour.getY() - mineField.getY()) <= 1)
                .filter(MineField::hasMine).count();
    }

    public void revealGrid(List<MineField> grid, JButton button) {
        MineField mineField = getMineFieldFromGrid(grid, button);

        revealField(grid, button, mineField);

        List<MineField> neighbours = grid.stream()
                .filter(neighbour -> Math.abs(neighbour.getX() - mineField.getX()) <= 1)
                .filter(neighbour -> Math.abs(neighbour.getY() - mineField.getY()) <= 1)
                .filter(neighbour -> !neighbour.isRevealed())
                .filter(neighbour -> !neighbour.hasMine())
                .collect(Collectors.toList());

        neighbours.forEach(neighbour -> {
            if (neighbour.getLocalMineCount() == 0) {
                revealGrid(grid, neighbour.getButton());
            } else {
                revealField(grid, neighbour.getButton(), neighbour);
            }
        });
    }

    private void revealField(List<MineField> grid, JButton button, MineField mineField) {
        button.setEnabled(!mineField.setRevealed(true).isRevealed());
        grid.set(mineField.setButton(button).getId(), mineField);
        revealText(mineField);
    }

    private MineField getMineFieldFromGrid(List<MineField> grid, JButton button) {
        return grid.stream().filter(field -> field.getButton() == button).findAny().orElse(new MineField());
    }

    private void revealText(MineField mineField) {
        if (mineField.hasFlag()) {
            mineField.getButton().setText("F");
        } else if (mineField.hasMine()) {
            mineField.getButton().setText("M");
        } else if (mineField.getLocalMineCount() > 0) {
            mineField.getButton().setText(String.valueOf(mineField.getLocalMineCount()));
        }
    }

    public void toggleFlag(List<MineField> grid, JButton button) {
        if (!button.isEnabled()) {
            return;
        }

        MineField mineField = getMineFieldFromGrid(grid, button);
        mineField.setFlag(!mineField.hasFlag());
        mineField.getButton().setText(mineField.hasFlag() ? "F" : "");
    }

    public boolean isMineExploding(List<MineField> grid, JButton button, int buttonClick) {
        return buttonClick == MouseEvent.BUTTON1 && getMineFieldFromGrid(grid, button).hasMine();
    }

    public boolean isChangingFlagStatus(int buttonClick) {
        return buttonClick == MouseEvent.BUTTON3;
    }

    public boolean isRevealingGrid(int buttonClick) {
        return buttonClick == MouseEvent.BUTTON1;
    }

    public int endGame(List<MineField> grid, boolean hasWon) {
        grid.stream().filter(MineField::hasMine).forEach(mineField -> {
            mineField.getButton().setBackground(hasWon ? Color.GREEN : Color.RED);
            mineField.getButton().setText("M");
        });
        return hasWon ? HAS_WON : HAS_LOST;
    }

    public boolean hasWon(List<MineField> grid, int numberOfMines) {
        return grid.stream().filter((MineField mineField) -> !mineField.isRevealed()).count() == numberOfMines;
    }

    public void initializeMineSweeper(MineSweeper mineSweeper) {
        mineSweeper.setGameStatus(IN_PROGRESS);
        mineSweeper.setGrid(mineService.createGrid(COLUMNS, ROWS, mineSweeper, mineSweeper));
        addMines(mineSweeper.getGrid(), NUMBER_OF_MINES);
        addLocalMineCount(mineSweeper.getGrid());

        for (MineField mineField : mineSweeper.getGrid()) {
            mineSweeper.add(mineField.getButton());
        }

        mineSweeper.setSize(WIDTH, HEIGHT);
        mineSweeper.setLayout(new GridLayout(9, 9));
        mineSweeper.setVisible(true);
        mineSweeper.setResizable(false);

        mineSweeper.getTimer().start();
    }
}
