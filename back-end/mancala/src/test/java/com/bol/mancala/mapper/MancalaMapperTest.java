package com.bol.mancala.mapper;

import com.bol.mancala.model.AfterMovementDTO;
import com.bol.mancala.model.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MancalaMapperTest {

    @Test
    public void testPlayerOneRepeatsTurn(){
        Assertions.assertTrue(MancalaMapper.isPlayer1Turn(6, true));
    }

    @Test
    public void testPlayerTwoRepeatsTurn(){
        Assertions.assertFalse(MancalaMapper.isPlayer1Turn(13, true));
    }

    @Test
    public void testPlayerOneGetsTurnAfterPlayerTwo(){
        Assertions.assertFalse(MancalaMapper.isPlayer1Turn(12, true));
    }

    @Test
    public void testPlayerTwoGetsTurnAfterPlayerOne(){
        Assertions.assertFalse(MancalaMapper.isPlayer1Turn(3, true));
    }

    @Test
    public void testGetAfterMovementDTO(){
        AfterMovementDTO actual = AfterMovementDTO.builder().player1Turn(true).board(Board.pits).build();
        AfterMovementDTO expected = MancalaMapper.getAfterMovementDTO(false, 9);
        Assertions.assertSame(expected.getBoard(), actual.getBoard());
    }


}
