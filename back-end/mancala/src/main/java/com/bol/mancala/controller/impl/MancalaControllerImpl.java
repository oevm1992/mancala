package com.bol.mancala.controller.impl;

import com.bol.mancala.config.MancalaConfig;
import com.bol.mancala.controller.MancalaController;
import com.bol.mancala.domain.util.MancalaUtils;
import com.bol.mancala.model.AfterMovementDTO;
import com.bol.mancala.model.MovementDTO;
import com.bol.mancala.model.WinnerDTO;
import com.bol.mancala.service.MancalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/mancala")
public class MancalaControllerImpl implements MancalaController {

    private MancalaService mancalaService;

    @Override
    @PostMapping(value= "/move", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AfterMovementDTO> makeMove(@RequestBody MovementDTO movementDTO) throws Exception {
        MancalaUtils.validatePits(MancalaConfig.nextTurnPlayer1,movementDTO.getPitChosen());
        return new ResponseEntity<>(mancalaService.makeMove(movementDTO), HttpStatus.OK);
    }

    @Override
    @GetMapping(value= "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AfterMovementDTO> startGame() throws Exception {
        return new ResponseEntity<>(mancalaService.startGame(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value= "/winner", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WinnerDTO> getWinner() throws Exception {
        return new ResponseEntity<>(mancalaService.getWinner(), HttpStatus.OK);
    }

    @Autowired
    public void setMancalaService(MancalaService mancalaService) {
        this.mancalaService = mancalaService;
    }

}
