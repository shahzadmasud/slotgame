package shahzad.game.slotgame.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/minesweeper")
public class MinesweeperController {

    private static final int BOARD_SIZE = 10; // Adjust the board size as needed
    private static final int NUM_MINES = 15; // Adjust the number of mines as needed
    private static final char MINE_CHAR = 'X';
    private static final char EMPTY_CHAR = '.';
    private static final char FLAG_CHAR = 'F';
    private static final char HIDDEN_CHAR = '#';

    private char[][] board;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private boolean gameLost;

    public MinesweeperController() {
        initializeBoard();
    }

    private void initializeBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        revealed = new boolean[BOARD_SIZE][BOARD_SIZE];
        flagged = new boolean[BOARD_SIZE][BOARD_SIZE];
        gameLost = false;

        // Fill the board with empty cells
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CHAR;
            }
        }

        // Place mines randomly
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < NUM_MINES) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);
            if (board[row][col] != MINE_CHAR) {
                board[row][col] = MINE_CHAR;
                minesPlaced++;
            }
        }
    }

    @GetMapping("/board")
    public char[][] getBoard() {
        return board;
    }

    @PostMapping("/reveal")
    public char[][] revealCell(@RequestParam int row, @RequestParam int col) {
        if (gameLost || revealed[row][col] || flagged[row][col]) {
            return board;
        }

        if (board[row][col] == MINE_CHAR) {
            gameLost = true;
            revealMines();
        } else {
            revealEmptyCells(row, col);
        }

        return board;
    }

    @PostMapping("/flag")
    public char[][] flagCell(@RequestParam int row, @RequestParam int col) {
        if (gameLost || revealed[row][col]) {
            return board;
        }

        flagged[row][col] = !flagged[row][col];
        return board;
    }

    private void revealEmptyCells(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE || revealed[row][col] || flagged[row][col]) {
            return;
        }

        revealed[row][col] = true;
        if (countAdjacentMines(row, col) == 0) {
            revealEmptyCells(row - 1, col);
            revealEmptyCells(row + 1, col);
            revealEmptyCells(row, col - 1);
            revealEmptyCells(row, col + 1);
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int r = row + i;
                int c = col + j;
                if (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == MINE_CHAR) {
                    count++;
                }
            }
        }
        return count;
    }

    private void revealMines() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == MINE_CHAR) {
                    revealed[i][j] = true;
                }
            }
        }
    }
}


