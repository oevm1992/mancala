package com.bol.mancala.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("singleton")
@Component
public class Board {

    public static int[] pits;

}
