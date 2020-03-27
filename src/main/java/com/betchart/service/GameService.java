package com.betchart.service;

import com.betchart.model.Game;
import com.betchart.model.GameGoalsEst;
import com.betchart.model.GameResultEst;

import java.math.BigDecimal;

public interface GameService {

    GameResultEst getResultEstimation(Game game);

    GameGoalsEst getGoalsEstimation(Game game);
}
