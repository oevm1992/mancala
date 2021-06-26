package com.bol.mancala.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AfterMovementDTO {
    private int[] board;
    private boolean player1Turn;
}
