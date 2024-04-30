package ui;

import core.CheckersLogic;

import java.util.Scanner;

/**
 * @author Alex Ishchenko
 * @version 0.6 Apr 25, 2024
 * CheckersTextConsole represents the game UI for a Checkers game.
 * This class handles interaction with the use
 * methods for .
 */
public class CheckersTextConsole extends CheckersLogic
{
    /** Scanner abject to scan input of the users. */
    Scanner scan = new Scanner(System.in);

    /**
     *  Class constructor.
     *  Starts the game right away by calling the startGame() method
     */
    public CheckersTextConsole()
    {
        startGame();
    }

    /**
     *  Main method for the overall UI handling.
     *  Calls gameStatus() to ask user if he/she is willing to play the game.
     */
    public void startGame()
    {
        // int i represents the player whose move it is, initially 0 - player Xs move
        int i = 0;
        // ask user to start the game
        boolean inGame = gameStatus();
        // Loop through the game till stated otherwise by either winning or saying no to gameStatus();
        while(inGame)
        {
            displayBoard(); // show the board
            // if its player Xs move now
            if(i == 0)
            {
                System.out.println("\nPlayer X - your turn");
                int xMove = tryToMove("X");
                // if move was successfully made - continue to player O
                if(xMove == 1)
                    i++;
            }
            // In any other case its player Os move now
            else
            {
                System.out.println("\nPlayer O - your turn");
                int oMove = tryToMove("O");
                // if move was successfully made - continue to player X
                if(oMove == 1)
                    i--;
            }
            // check if any of the players has won, if yes, ask if they want to try again
            int winStatus = winStatus();
            switch (winStatus)
            {
                case 105:
                    System.out.println("\u001B[32mPlayer O is the WINNER\u001B[0m");
                    inGame = gameStatus(); // prompt user to decide if he wants to play again
                case 106:
                    System.out.println("\u001B[32mPlayer X is the WINNER\u001B[0m");
                    inGame = gameStatus();
            }
        }
    }

    /**
     * tryToMove is the front side of checkPiece in CheckersLogic.
     * This method handles input of the user, splits it into an appropriate way so it can be used further.
     * Then it calls the checkPiece method to try and make the move.
     * If the checkPiece output is something other than 1 (1 indicating a successfull move),
     * then it outputs an error message corresponding to the error index and tryies
     * @param player represents players index (either X or O)
     * @return 1 in case of success or 0 in case of failure, indicating then that player X has to try again
     * @throws Exception that may occur due to an invalid output, or the wrong cell index.
     */
    public int tryToMove(String player)
    {
        try
        {
            System.out.println("Choose a cell position of piece to be moved");

            // Read the input string
            String input = scan.nextLine();
            // Extract numeric and alphabetic parts using regular expressions
            String[] parts = input.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            // Convert the numeric part to an integer
            int from = Integer.parseInt(parts[0]);
            // Extract alphabetic part
            int fromA = convertStrToInt(parts[1]);

            System.out.println("Choose a cell position to move to");

            // Read the input string
            String input2 = scan.nextLine();
            // Extract numeric and alphabetic parts using regular expressions
            String[] parts2 = input2.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            // Convert the numeric part to an integer
            int to = Integer.parseInt(parts2[0]);
            // Extract alphabetic part
            int toA = convertStrToInt(parts2[1]);

            int move = checkPiece(from, fromA, to, toA, player);

            switch (move)
            {
                case 1:
                    return 1; // Successful move
                case 2:
                    System.out.println("\u001B[31mThe figure chosen is not your figure\u001B[0m");
                    return 0;
                case 3:
                    System.out.println("\u001B[31mYou can't make such move\u001B[0m");
                    return 0;
                case 4:
                    System.out.println("\u001B[31mYou have occupied that space alreydy!\u001B[0m");
                    return 0;
            }
        }
        catch (Exception e)
        {
            System.out.println("\u001B[31mSomething went wrong, try again\u001B[0m");
            return 0;
        }
        return 1;
    }

    /**
     * Converts given column to the appropriate int value from 1 to 8
     * @param str represents column value
     * @return
     */
    public int convertStrToInt(String str)
    {
        switch (str.toLowerCase())
        {
            case "a":
                return 1;
            case "b":
                return 2;
            case "c":
                return 3;
            case "d":
                return 4;
            case "e":
                return 5;
            case "f":
                return 6;
            case "g":
                return 7;
            case "h":
                return 8;
            default:
                throw new IllegalArgumentException("Invalid column: " + str);
        }
    }

    /**
     * Iterates though each row and column of the board to print it.
     * Also prints each rows number and column name
     */
    public void displayBoard()
    {
        // Print column numbers
        System.out.println(); // Move to the next line

        for (int row = 0; row < 8; row++)
        {
            int rown = row + 1;
            System.out.print(rown + " | "); // Print row number
            for (int col = 0; col < 8; col++)
            {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println(); // Move to the next line
        }
        System.out.print("    a   b   c   d   e   f   g   h");
    }

    /**
     * Asks user if he's willing to play the game
     * @return true in case user is willing to play/continue playing
     */
    public boolean gameStatus()
    {
        int i = 0;
        while(i == 0)
        {
            System.out.println("\u001B[32mWelcome to the CHECKERS GAME\nInput Y/N to start the game or leave\u001B[0m");
            String input = scan.nextLine().trim().toLowerCase();
            switch (input)
            {
                case "y":
                    initiateBoard();
                    i++;
                    return true; // start the game
                case "n":
                    i = 1;
                    System.out.println("Shame to hear, see you!");
                    return false; // end the game
                default:
                    i = 0;
                    System.out.println("\u001B[31mSomething went wrong, try again\u001B[0m");
            }
        }
        return true;
    }
}

