package org.mark.minesweeper.model;

import javax.swing.*;

public class MineField {
    private int id;
    private int x;
    private int y;
    private JButton button;
    private long localMineCount;
    private boolean hasFlag;
    private boolean hasMine;
    private boolean isRevealed;

    public static int createId(int column, int row, int columns) {
        return row * columns + column;
    }

    public int getId() {
        return id;
    }

    public MineField setId(int id) {
        this.id = id;
        return this;
    }

    public int getX() {
        return x;
    }

    public MineField setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public MineField setY(int y) {
        this.y = y;
        return this;
    }

    public JButton getButton() {
        return button;
    }

    public MineField setButton(JButton button) {
        this.button = button;
        return this;
    }

    public long getLocalMineCount() {
        return localMineCount;
    }

    public MineField setLocalMineCount(long localMineCount) {
        this.localMineCount = localMineCount;
        return this;
    }

    public boolean hasFlag() {
        return hasFlag;
    }

    public MineField setFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
        return this;
    }

    public boolean hasMine() {
        return hasMine;
    }

    public MineField setMine(boolean hasMine) {
        this.hasMine = hasMine;
        return this;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public MineField setRevealed(boolean revealed) {
        isRevealed = revealed;
        return this;
    }

    @Override
    public String toString() {
        return "MineField{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", button=" + button +
                ", localMineCount=" + localMineCount +
                ", hasFlag=" + hasFlag +
                ", hasMine=" + hasMine +
                ", isRevealed=" + isRevealed +
                '}';
    }
}

