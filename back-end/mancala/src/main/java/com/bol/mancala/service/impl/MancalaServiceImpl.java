package com.bol.mancala.service.impl;

import com.bol.mancala.config.MancalaConfig;
import com.bol.mancala.enums.MancalaEnum;
import com.bol.mancala.model.AfterMovementDTO;
import com.bol.mancala.model.Board;
import com.bol.mancala.model.MovementDTO;
import com.bol.mancala.model.WinnerDTO;
import com.bol.mancala.service.MancalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bol.mancala.mapper.MancalaMapper;

@Service
public class MancalaServiceImpl implements MancalaService{

    private MancalaConfig mancalaConfig;

    @Override
    public AfterMovementDTO startGame() {
        mancalaConfig.init();
        return MancalaMapper.getAfterMovementDTO(true, MancalaEnum.PLAYER1_BIG_PIT_POSITION.getValue());
    }

    @Override
    public WinnerDTO getWinner() {

        int pit = MancalaEnum.PLAYER1_FIRST_PIT_POSITION.getValue();
        int player1Stones = MancalaEnum.NO_STONES.getValue();
        int player2Stones = MancalaEnum.NO_STONES.getValue();

        while (pit < MancalaEnum.BOARD_SIZE.getValue()){
            if(pit <= MancalaEnum.PLAYER1_BIG_PIT_POSITION.getValue()) player1Stones += Board.pits[pit];
            else player2Stones += Board.pits[pit];
            pit++;
        }

        return new WinnerDTO((player1Stones != player2Stones) ?
                (player1Stones > player2Stones ?
                        String.format("PLAYER 1 WINS WITH: PLAYER 1 STONES = %s AND PLAYER 2 STONES = %s", player1Stones, player2Stones)
                        : String.format("PLAYER 2 WINS WITH: PLAYER 1 STONES = %s AND PLAYER 2 STONES = %s", player1Stones, player2Stones))
                : String.format("DRAW WITH: PLAYER 1 STONES = %s AND PLAYER 2 STONES = %s", player1Stones, player2Stones));
    }

    @Override
    public AfterMovementDTO makeMove(MovementDTO movementDTO){
        int position = movementDTO.getPitChosen();
        int stones = Board.pits[position];
        Board.pits[position] = MancalaEnum.NO_STONES.getValue();
        position++;
        
        while(stones != MancalaEnum.NO_STONES.getValue()){
            if(hasToRemoveStone(position, movementDTO.getPitChosen())){
                addStone(position);
                stones--;
            }
            if(position == MancalaEnum.PLAYER2_BIG_PIT_POSITION.getValue()) {
                if(stones!= MancalaEnum.NO_STONES.getValue()) position = MancalaEnum.PLAYER1_FIRST_PIT_POSITION.getValue();
            }else{
                if(stones != MancalaEnum.NO_STONES.getValue()) position++;
            }
        }

        stealStones(isPlayer1(movementDTO.getPitChosen()), position);

        return MancalaMapper.getAfterMovementDTO(isPlayer1(movementDTO.getPitChosen()), position);
    }

    /**
     * This method add a stone in the board in the selected position
     * @param position This is the position where the stone is going to be added
     */
    public void addStone(int position){
        Board.pits[position]++;
    }

    /**
     * This method checks if we have to remove a stone of the group of stones taken of a pit
     * @param actualPosition This param represents the position we are standing
     * @param pitChosen This param represents the pit position chosen by the player
     * @return a true or false, if we have to remove or not a stone from the stones collected
     */
    public boolean hasToRemoveStone(int actualPosition, int pitChosen){
        return isPlayer1(pitChosen) && actualPosition != MancalaEnum.PLAYER2_BIG_PIT_POSITION.getValue()
                || !isPlayer1(pitChosen) && actualPosition != MancalaEnum.PLAYER1_BIG_PIT_POSITION.getValue();
    }

    /**
     * This method checks if its player 1 or two by a position given
     * @param position represents a position in the board to check if it belongs to player 1 or two
     * @return return true if it´s player 1 or false if it´s player 2
     */
    public boolean isPlayer1(int position){
        return position <= MancalaEnum.PLAYER1_BIG_PIT_POSITION.getValue();
    }

    /**
     * This method checks if the position of the enemy has stones in it
     * @param enemyPosition represents a pit position in the enemy side
     * @return true if the pit has stones or false if it has not
     */
    public boolean enemyPositionHasStones(int enemyPosition){
        return Board.pits[enemyPosition] != MancalaEnum.NO_STONES.getValue();
    }

    /**
     * this method obtains the position of the pit that is in front of the given position
     * @param position the position we are going to evaluate
     * @return returns the position of the enemy pit that is in front of the given position
     */
    public int getEnemyPosition(int position){
        return MancalaEnum.TOTAL_PITS.getValue() - position;
    }

    /**
     * This method checks if we can steal stones from the enemy
     * @param player1 represents the player 1 if it´s true and player 2 if it´s false
     * @param lastPosition represents the the position where the last stone was deposited in the turn
     * @return true if we can steal the stones of the enemy or false if we cant´t
     */
    public boolean canStealStones(boolean player1, int lastPosition){
        return  ((player1 && lastPosition <= MancalaEnum.PLAYER1_LAST_PIT_POSITION.getValue())
                || (!player1 && lastPosition >= MancalaEnum.PLAYER2_FIRST_PIT_POSITION.getValue()
                && lastPosition < MancalaEnum.PLAYER2_BIG_PIT_POSITION.getValue()))
                && Board.pits[lastPosition] == MancalaEnum.ONE_STONE.getValue()
                && enemyPositionHasStones(getEnemyPosition(lastPosition));
    }

    /**
     * This method takes the stones from the enemy pit and deposit them in the big pit
     * @param player1 represents the player 1 if it´s true and player 2 if it´s false
     * @param lastPosition represents the the position where the last stone was deposited in the turn
     */
    public void stealStones(boolean player1, int lastPosition){
        int gainedStones = MancalaEnum.NO_STONES.getValue();
        if(canStealStones(player1, lastPosition)){
            gainedStones += Board.pits[lastPosition];
            Board.pits[lastPosition] = MancalaEnum.NO_STONES.getValue();
            gainedStones += Board.pits[getEnemyPosition(lastPosition)];
            Board.pits[getEnemyPosition(lastPosition)] = MancalaEnum.NO_STONES.getValue();
            Board.pits[getBigPit(player1)] += gainedStones;
        }
    }

    /**
     * This method gets the big pit position of a player
     * @param player1 represents the player 1 if it´s true and player 2 if it´s false
     * @return the big pit position
     */
    public int getBigPit(boolean player1){
        return player1 ? MancalaEnum.PLAYER1_BIG_PIT_POSITION.getValue() : MancalaEnum.PLAYER2_BIG_PIT_POSITION.getValue();
    }

    @Autowired
    public void setMancalaConfig(MancalaConfig mancalaConfig) {
        this.mancalaConfig = mancalaConfig;
    }

}
