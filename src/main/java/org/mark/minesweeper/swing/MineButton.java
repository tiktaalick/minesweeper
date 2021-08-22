package org.mark.minesweeper.swing;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class MineButton extends JButton {
    public static final int FIELD_WIDTH = 50;

    public MineButton(int x, int y, String label, ActionListener actionListener, MouseListener mouseListener) {
        setText(label);
        setBounds(x, y, FIELD_WIDTH, FIELD_WIDTH);
        addActionListener(actionListener);
        addMouseListener(mouseListener);
    }
}
