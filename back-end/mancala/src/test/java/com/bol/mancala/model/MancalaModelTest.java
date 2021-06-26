package com.bol.mancala.model;

import com.bol.mancala.MancalaApplication;
import com.bol.mancala.config.MancalaConfig;
import com.bol.mancala.exception.BadRequestException;
import com.bol.mancala.handler.BaseRestExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class MancalaModelTest {

    @Test
    public void testModels(){

        BeanTester tester = new BeanTester();
        tester.testBean(MovementDTO.class);
        tester.testBean(Board.class);
        tester.testBean(MancalaApplication.class);
        tester.testBean(BaseRestExceptionHandler.class);
        tester.testBean(MancalaConfig.class);

        WinnerDTO winnerDTO = new WinnerDTO("DRAW");
        winnerDTO.setGameFinishedStatus("DRAW");

        ApiErrorMessageBody apiErrorMessageBody = new ApiErrorMessageBody("error message");
        Assertions.assertSame("error message",apiErrorMessageBody.getErrorMessage());
        apiErrorMessageBody.setErrorMessage("another error message");
        Assertions.assertSame("another error message",apiErrorMessageBody.getErrorMessage());
    }

    @Test
    public void testGetNextTurnPlayer(){
        boolean nextTurnPlayer1 = MancalaConfig.nextTurnPlayer1;
        boolean anotherTurnPlayer1 = MancalaConfig.getNextTurnPlayer1();
        Assertions.assertSame(nextTurnPlayer1,anotherTurnPlayer1);
    }

    @Test
    public void testBuilder(){
        AfterMovementDTO afterMovementDTO = AfterMovementDTO.builder().board(new int[]{1,2,3})
                .player1Turn(true).build();
        Assertions.assertTrue(Arrays.equals(new int[]{1,2,3},afterMovementDTO.getBoard()));
        Assertions.assertTrue(afterMovementDTO.isPlayer1Turn());
    }


}
