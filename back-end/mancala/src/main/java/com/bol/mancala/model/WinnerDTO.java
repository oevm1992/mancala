package com.bol.mancala.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WinnerDTO {
    private String gameFinishedStatus;

    public WinnerDTO(String gameFinishedStatus){
        this.gameFinishedStatus = gameFinishedStatus;
    }
}
