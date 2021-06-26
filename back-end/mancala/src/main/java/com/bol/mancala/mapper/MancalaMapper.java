package com.bol.mancala.mapper;

import com.bol.mancala.config.MancalaConfig;
import com.bol.mancala.enums.MancalaEnum;
import com.bol.mancala.model.AfterMovementDTO;
import com.bol.mancala.model.Board;

public class MancalaMapper {

    private MancalaMapper(){}

    public static AfterMovementDTO getAfterMovementDTO(boolean player1, int lastPosition){
        boolean isPlayer1Turn = isPlayer1Turn(lastPosition,player1);
        MancalaConfig.nextTurnPlayer1 = isPlayer1Turn;
        return AfterMovementDTO.builder()
                .board(Board.pits)
                .player1Turn(isPlayer1Turn)
                .build();
    }

    /**
     * This method checks if it´s player 1 turn
     * @param player1 represents the player 1 if it´s true and player 2 if it´s false
     * @param lastPosition represents the the position where the last stone was deposited in the turn
     * @return true if it´s the player 1 turn or false if it´s the player two turn
     */
    public static boolean isPlayer1Turn(int lastPosition, boolean player1){
        if(player1 && lastPosition == MancalaEnum.PLAYER1_BIG_PIT_POSITION.getValue()) return true;
        if(!player1 && lastPosition == MancalaEnum.PLAYER2_BIG_PIT_POSITION.getValue()) return false;
        return !player1;
    }

}
