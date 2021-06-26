package com.bol.mancala.service;

import com.bol.mancala.config.MancalaConfig;
import com.bol.mancala.model.Board;
import com.bol.mancala.model.MovementDTO;
import com.bol.mancala.service.impl.MancalaServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import java.util.Arrays;

import static org.mockito.Mockito.doNothing;

public class MancalaServiceTest {

    @InjectMocks
    MancalaServiceImpl mancalaService;

    @Mock
    MancalaConfig mancalaConfig;

    @Test
    public void testGetWinnerPLayer1() {
        Board.pits = new int[]{0, 0, 0, 0, 0, 0, 40, 6, 5, 0, 0, 1, 0, 20};
        Assertions.assertTrue(mancalaService.getWinner().getGameFinishedStatus().equals("PLAYER 1 WINS WITH: PLAYER 1 STONES = 40 AND PLAYER 2 STONES = 32"));
    }

    @Test
    public void testGetWinnerPLayer2() {
        Board.pits = new int[]{6, 0, 5, 1, 0, 0, 20, 0, 0, 0, 0, 0, 0,40};
        Assertions.assertTrue(mancalaService.getWinner().getGameFinishedStatus().equals("PLAYER 2 WINS WITH: PLAYER 1 STONES = 32 AND PLAYER 2 STONES = 40"));
    }

    @Test
    public void testGetDraw() {
        Board.pits = new int[]{6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        Assertions.assertTrue(mancalaService.getWinner().getGameFinishedStatus().equals("DRAW WITH: PLAYER 1 STONES = 36 AND PLAYER 2 STONES = 36"));
    }

    @Test
    public void testMakeMove() {
        Board.pits = new int[]{1, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 6};
        MovementDTO movement = new MovementDTO();
        movement.setPitChosen(0);
        mancalaService.makeMove(movement);
        Assertions.assertTrue(Arrays.equals(Board.pits, new int[]{0, 7, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 6}));
    }

    @Test
    public void testMakeMoveLastPositionEnemySide() {
        Board.pits = new int[]{1, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 2, 6};
        MovementDTO movement = new MovementDTO();
        movement.setPitChosen(12);
        mancalaService.makeMove(movement);
        Assertions.assertTrue(Arrays.equals(Board.pits, new int[]{2, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 0, 7}));
    }

    @Test
    public void testStealStone() {
        Board.pits = new int[]{1, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 6};
        mancalaService.stealStones(true, 0);
        Assertions.assertTrue(Arrays.equals(Board.pits, new int[]{0, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 0, 6}));
    }

    @Test
    public void testAddStone() {

        Board.pits = new int[]{6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6};
        doNothing().when(mancalaConfig).init();
        mancalaService.addStone(0);
        Assertions.assertTrue(Arrays.equals(Board.pits, new int[]{7, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6}));
        Assertions.assertTrue(mancalaService.startGame().isPlayer1Turn());
    }

    @Test
    public void testPlayerOneBigPitIs6() {
        Assertions.assertSame(6, mancalaService.getBigPit(true));
    }

    @Test
    public void testPlayerTwoBigPitIs13() {
        Assertions.assertSame(13, mancalaService.getBigPit(false));
    }

    @Test
    public void testEnemyPositionOf3is9() {
        Assertions.assertSame(9, mancalaService.getEnemyPosition(3));
    }

    @Test
    public void testEnemyPositionOf12is0() {
        Assertions.assertSame(0, mancalaService.getEnemyPosition(12));
    }

    @Test
    public void testEnemyPositionHasStones() {
        Board.pits = new int[]{0,6,6,6,6,6,5,6,6,6,6,6,1,5};
        Assertions.assertTrue(mancalaService.enemyPositionHasStones(12));
    }

    @Test
    public void testEnemyPositionDoesNotHaveStones() {
        Board.pits = new int[]  {0,6,6,6,6,6,5,6,6,6,6,6,0,6};
        Assertions.assertFalse(mancalaService.enemyPositionHasStones(12));
    }

    @Test
    public void testIsPlayerOneWhenPositionIs3() {
        Assertions.assertTrue(mancalaService.isPlayer1(3));
    }

    @Test
    public void testIsPlayerTwoWhenPositionIs9() {
        Assertions.assertFalse(mancalaService.isPlayer1(9));
    }

    @Test
    public void testDoesNotHaveToRemoveStoneWhenIsPlayerOneAndActualPositionIs13() {
        Assertions.assertFalse(mancalaService.hasToRemoveStone(13,3));
    }

    @Test
    public void testDoesNotHaveToRemoveStoneWhenIsPlayerTwoAndActualPositionIs6() {
        Assertions.assertFalse(mancalaService.hasToRemoveStone(6,10));
    }

    @Test
    public void testHasToRemoveStoneWhenIsPlayerOneAndActualPositionIs6() {
        Assertions.assertTrue(mancalaService.hasToRemoveStone(6,3));
    }

    @Test
    public void testHasToRemoveStoneWhenIsPlayerTwoAndActualPositionIs13() {
        Assertions.assertTrue(mancalaService.hasToRemoveStone(13,12));
    }

    @Test
    public void testCanStealStonesWhenLastPositionIsEmptyAndEnemyPositionIsNotEmpty() {
        Board.pits = new int[]{1, 1, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        Assertions.assertTrue(mancalaService.canStealStones(true,1));
    }

    @Test
    public void testCantStealStonesWhenLastPositionIsNotEmptyAndEnemyPositionIsNotEmpty() {
        Board.pits = new int[]{1, 2, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        Assertions.assertFalse(mancalaService.canStealStones(true,1));
    }

    @Test
    public void testCantStealStonesWhenLastPositionIsEmptyAndEnemyPositionIsEmpty() {
        Board.pits = new int[]{1, 1, 6, 6, 6, 6, 0, 6, 6, 6, 6, 0, 6, 0};
        Assertions.assertFalse(mancalaService.canStealStones(true,1));
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}
