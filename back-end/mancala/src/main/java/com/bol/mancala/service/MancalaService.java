package com.bol.mancala.service;

import com.bol.mancala.model.AfterMovementDTO;
import com.bol.mancala.model.MovementDTO;
import com.bol.mancala.model.WinnerDTO;

public interface MancalaService {

    /**
     * This method uses the pit chosen by de player to mover the stones and complete a movement
     * @param movementDTO this dto contains the pit chosen
     * @return AfterMovementDTO that contains the board after the movement and which player has the turn
     * @throws Exception
     */
    public AfterMovementDTO makeMove(MovementDTO movementDTO) throws Exception;

    /**
     * This method returns the initial configuration to start the game
     * @return AfterMovementDTO that contains the board initial configuration and player 1 turn
     * @throws Exception
     */
    public AfterMovementDTO startGame() throws Exception;

    /**
     * This method checks the board status and tell us who is the winner or if it´s a draw
     * @return WinnerDTO contains a String that tell us who is the winner or if it´s a draw
     * @throws Exception
     */
    public WinnerDTO getWinner() throws Exception;
}
