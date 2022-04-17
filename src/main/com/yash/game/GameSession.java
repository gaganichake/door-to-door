package com.yash.game;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gagan.ichake on 06-10-2016.
 */
@Component
public class GameSession {

    private String gameId;
    private String options;
    private java.util.Map<Integer, String> doorStatus = new HashMap<Integer, String>() {{
        put(1, "Closed");
        put(2, "Closed");
        put(3, "Closed");
    }};
    ;


    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Map<Integer, String> getDoorStatus() {
        return doorStatus;
    }

    @Override
    public String toString() {
        return "{" +
                "\"gameId\" : \"" + gameId + '\"' +
                ", \"options\" : \"" + options + '\"' +
                '}';
    }
}
