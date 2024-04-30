package core;

import java.util.Objects;

/**
 * @author Alex Ishchenko
 * @version 0.8 Apr 25, 2024
 * CheckersLogic represents the game logic for a Checkers game.
 * It draws the board, and handles the logic behind the moves.
 * It handles the initialization of the game board and provides
 * methods for manipulating and querying the game state.
 */
public class CheckersLogic
{
    /** The game board represented as a 2D array of strings. */
    public String[][] board = new String[8][8];

    /**
     * Initializes the board for the beginning of the game with pieces for Player O and Player X.
     * Empty cells are represented by "-" while pieces for Player O and Player X
     * are represented by "O" and "X" respectively.
     */
    public void initiateBoard()
    {
        // Iterate through each cell of the board
        for (int row = 0; row < 8; row++)
        {
            // Initialize cells
            for (int col = 0; col < 8; col++)
            {
                // When iterating through rows 0 to 2 start filling the player X's pieces
                if (row <= 2)
                {
                    if ((row + col) % 2 == 0)
                        board[row][col] = "-";
                    else
                        board[row][col] = "O"; // Fill with X pieces
                }
                // When iterating through rows 5 to 7 start filling the player X's pieces
                else if (row >= 5)
                {
                    if ((row + col) % 2 == 0)
                        board[row][col] = "-";
                    else
                        board[row][col] = "X"; // Fill with X pieces
                }
                // In any other scenario fill out the empty spaces
                else
                    board[row][col] = "-"; // fill all other spaces with "-"
            }
        }
    }

    /**
     * Checks if the move can be made by that player.
     * If it can be done, then it calls its helper method to change the character
     * If no, then it returns an error code to be processed by the UI class
     * @param rowFrom describes a row player wants to move his figure form
     * @param colFrom describes a column player wants to move his figure form
     * @param rowTo describes a row player wants to move his figure to
     * @param colTo describes a column player wants to move his figure to
     * @param player players index (either X or O)
     * @return success code
     */
    public int checkPiece(int rowFrom, int colFrom, int rowTo, int colTo, String player)
    {
        // Check if the players chosen figure is indeed his figure
        if(board[rowFrom - 1][colFrom - 1].equals(player))
        {
            // Check if the cell player wants to make the move isn't already occupied by himself
            if(!Objects.equals(board[rowTo - 1][colTo - 1], player))
            {
                // Check the column they move to
                if((colTo - 1) == (colFrom) || (colTo + 1) == (colFrom))
                {
                    if(rowTo + 1 == rowFrom || rowTo - 1 == rowFrom)
                    {
                        movePiece(rowFrom, colFrom, rowTo, colTo, player);
                        return 1;
                    }
                    else
                        return 3; // Place you want to move piece to does not exist
                }
                else
                    return 3; // Invalid place you want to move piece to
            }
            else
                return 4; // Place occupied by player
        }
        else
            return 2; // 2 indicates that the figure chosen by the player is not his figure
        // 1 indicates that the move has been done
    }

    /**
     * Just makes the move by rewriting the cells
     * @param rowFrom indicates the row player moves from
     * @param colFrom indicates the col player moves from
     * @param rowTo indicates the row player moves to
     * @param colTo indicates the col player moves to
     * @param player indicates the players index (x or o)
    */
    public void movePiece(int rowFrom, int colFrom, int rowTo, int colTo, String player)
    {
        board[rowFrom - 1][colFrom - 1] = "-"; // Change the place piece was on to blank
        board[rowTo - 1][colTo - 1] = player; // Change the place piece goes on to corresponding letter
    }

    /**
     * Iterates through the whole board and counts how many pieces are left.
     * Whenever there are no figures of one side left, return the corresponding status.
     * @return the corresponding int value showing if the game continues or if someone has won
     */
    public int winStatus()
    {
        int xCount = 0; // Number of x figures left
        int oCount = 0; // Number of o figures left
        // Iterate through the board and count every figure left
        for(int i = 0; i <= 7; i++)
        {
            for(int j = 0; j <= 7; j++)
            {
                if(board[i][j].equals("X"))
                    xCount++; // increment the count of x figures left
                else if(board[i][j].equals("O"))
                    oCount++; // increment the count of o figures left
            }
        }
        if(xCount == 0)
            return 105; // indicating that player O has won the game
        else if (oCount == 0)
            return 106; // indicating that player X has won the game
        return 1; // indicating that the game continues if there are still figures of each player left
    }
}
