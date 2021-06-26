package com.bol.mancala.controller;

import com.bol.mancala.config.MancalaConfig;
import com.bol.mancala.controller.impl.MancalaControllerImpl;
import com.bol.mancala.domain.util.MancalaUtils;
import com.bol.mancala.model.AfterMovementDTO;
import com.bol.mancala.model.Board;
import com.bol.mancala.model.MovementDTO;
import com.bol.mancala.service.impl.MancalaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class MancalaControllerTest {

    @InjectMocks
    MancalaControllerImpl mancalaController;

    private ObjectMapper mapper = new ObjectMapper();

    @Mock
    private MancalaServiceImpl mancalaService;

    @Mock
    private MancalaConfig mancalaConfig;

    @Mock
    private MancalaUtils mancalaUtils;

    @Test
    public void testMoveBadRequest() throws Exception {
        
        MovementDTO movementDTO = new MovementDTO();
        movementDTO.setPitChosen(15);

        this.mvc.perform(
                post("/mancala/move")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(movementDTO)))
                .andExpect(status().is4xxClientError());
        
        movementDTO.setPitChosen(-1);
        
        this.mvc.perform(
                post("/mancala/move")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(movementDTO)))
                .andExpect(status().is4xxClientError());

        
        movementDTO.setPitChosen(13);
        
        this.mvc.perform(
                post("/mancala/move")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(movementDTO)))
                .andExpect(status().is4xxClientError());

        movementDTO.setPitChosen(14);
        
        this.mvc.perform(
                post("/mancala/move")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(movementDTO)))
                .andExpect(status().is4xxClientError());
    }
    

    @Test
    public void testMoveOK() throws Exception {
        Board.pits = new int[]{6,6,6,6,6,6,0,6,6,6,6,6,6,0};
        MovementDTO movementDTO = new MovementDTO();
        movementDTO.setPitChosen(3);

        when(mancalaService.makeMove(movementDTO)).thenReturn(AfterMovementDTO.builder().build());
        
        this.mvc.perform(
                post("/mancala/move")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(movementDTO)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
        public void testStartOK() throws Exception {

        doNothing().when(mancalaConfig).init();

        this.mvc.perform(
                get("/mancala/start"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testWinnerOK() throws Exception {

        this.mvc.perform(
                get("/mancala/winner"))
                .andExpect(status().is2xxSuccessful());
    }

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(mancalaController).build();
    }

}
