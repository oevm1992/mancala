package com.bol.mancala.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MancalaEnum {

    PLAYER1_LAST_PIT_POSITION(5),
    PLAYER2_LAST_PIT_POSITION(12),
    PLAYER1_FIRST_PIT_POSITION(0),
    PLAYER2_FIRST_PIT_POSITION(7),
    PLAYER1_BIG_PIT_POSITION(6),
    PLAYER2_BIG_PIT_POSITION(13),
    TOTAL_PITS(12),
    NO_STONES(0),
    ONE_STONE(1),
    BOARD_SIZE(14)
    ;


    private final int value;
}
