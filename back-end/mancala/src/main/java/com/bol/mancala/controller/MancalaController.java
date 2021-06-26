package com.bol.mancala.controller;

import com.bol.mancala.model.AfterMovementDTO;
import com.bol.mancala.model.MovementDTO;
import com.bol.mancala.model.WinnerDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Mancala API", description = "Mancala service for bol.com")
public interface MancalaController {
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })

    /**
     * This endpoint uses the pit chosen by de player to mover the stones and complete a movement
     * @param movementDTO this dto contains the pit chosen
     * @return AfterMovementDTO that contains the board after the movement and which player has the turn
     * @throws Exception when pit chosen has 0 stones, or the player in turn selects an opponent pit
     */
    public ResponseEntity<AfterMovementDTO> makeMove(MovementDTO movementDTO) throws Exception;
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })

    /**
     * This endpoint returns the initial configuration to start the game
     * @return AfterMovementDTO that contains the board initial configuration and player 1 turn
     * @throws Exception
     */
    public ResponseEntity<AfterMovementDTO> startGame() throws Exception;
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })

    /**
     * This endpoint checks the board status and tell us who is the winner or if it´s a draw
     * @return WinnerDTO contains a String that tell us who is the winner or if it´s a draw
     * @throws Exception
     */
    public ResponseEntity<WinnerDTO> getWinner() throws Exception;
}
