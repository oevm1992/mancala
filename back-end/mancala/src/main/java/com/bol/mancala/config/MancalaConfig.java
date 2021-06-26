package com.bol.mancala.config;

import com.bol.mancala.enums.MancalaEnum;
import com.bol.mancala.model.Board;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:application.properties")
public class MancalaConfig {

    public static boolean nextTurnPlayer1 = true;

    @Value("${stones.in.pit}")
    public int STONES_IN_PIT;

    public final int BOARD_SIZE = MancalaEnum.BOARD_SIZE.getValue();

    @Bean
    public static boolean getNextTurnPlayer1(){
        return nextTurnPlayer1;
    }

    @PostConstruct
    public void init(){
        Board.pits = new int[BOARD_SIZE];

        for(int i = 0; i < MancalaEnum.PLAYER2_BIG_PIT_POSITION.getValue(); i++){
            if(i != MancalaEnum.PLAYER1_BIG_PIT_POSITION.getValue()) Board.pits[i] = STONES_IN_PIT;
        }
    }
}
