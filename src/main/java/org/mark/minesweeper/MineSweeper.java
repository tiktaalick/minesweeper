package org.mark.minesweeper;

import org.mark.minesweeper.logic.MineLogic;
import org.mark.minesweeper.model.MineField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

@SpringBootApplication
@ComponentScan({"org.mark.minesweeper"})
public class MineSweeper extends JFrame implements ActionListener, MouseListener {
	private transient List<MineField> grid;
	private int gameStatus;
	private Timer timer = new Timer(1000, this);
	private int timeLeft = 1000;

	@Autowired
	private transient MineLogic mineLogic;

	public static void main(String[] args) {
		getInstance().startApplication();
	}

	private static MineSweeper getInstance() {
		return new SpringApplicationBuilder(MineSweeper.class)
				.headless(false)
				.run()
				.getBean(MineSweeper.class);
	}

	public void startApplication() {
		mineLogic.initializeMineSweeper(this);
	}

	public List<MineField> getGrid() {
		return grid;
	}

	public MineSweeper setGrid(List<MineField> grid) {
		this.grid = grid;
		return this;
	}

	public MineSweeper setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
		return this;
	}

	public Timer getTimer() {
		return timer;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		timeLeft = timeLeft - 1;
		this.setTitle(String.valueOf(timeLeft));
		if (timeLeft <= 0 || gameStatus != MineLogic.IN_PROGRESS) {
			timer.stop();
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
		int buttonClick = event.getButton();
		JButton button = ((JButton) event.getSource());

		if (gameStatus != MineLogic.IN_PROGRESS) {
			dispose();
			getInstance().startApplication();
		} else if (mineLogic.isMineExploding(grid, button, buttonClick)) {
			this.gameStatus = mineLogic.endGame(grid, false);
		} else if (mineLogic.isChangingFlagStatus(buttonClick)) {
			mineLogic.toggleFlag(grid, button);
		} else if (mineLogic.isRevealingGrid(buttonClick)) {
			mineLogic.revealGrid(grid, button);
		}

		if (mineLogic.hasWon(grid, MineLogic.NUMBER_OF_MINES)) {
			this.gameStatus = mineLogic.endGame(grid, true);
		}

	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// Ignored
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// Ignored
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// Ignored
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// Ignored
	}
}
