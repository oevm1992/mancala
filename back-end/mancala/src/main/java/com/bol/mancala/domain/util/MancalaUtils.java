package com.bol.mancala.domain.util;

import com.bol.mancala.enums.MancalaEnum;
import com.bol.mancala.exception.BadRequestException;
import com.bol.mancala.model.Board;

public class MancalaUtils {

    /**
     * This methods validates if the chosen pit is valid
     * @param player1 represents the player 1 if it´s true and player 2 if it´s false
     * @param pitChosen This param represents the pit position chosen by the player
     */
    public static void validatePits(boolean player1, int pitChosen){
        if(player1 && (pitChosen > MancalaEnum.PLAYER1_LAST_PIT_POSITION.getValue() || pitChosen < MancalaEnum.PLAYER1_FIRST_PIT_POSITION.getValue())) throw new BadRequestException("Player 1 pit chosen must be from his side of the board");
        if(!player1 && (pitChosen < MancalaEnum.PLAYER1_BIG_PIT_POSITION.getValue() || pitChosen > MancalaEnum.PLAYER2_LAST_PIT_POSITION.getValue())) throw new BadRequestException("Player 2 pit chosen must be from his side of the board");
        if(Board.pits[pitChosen] == MancalaEnum.NO_STONES.getValue()) throw new BadRequestException("Cannot select an empty pit");
    }
}
