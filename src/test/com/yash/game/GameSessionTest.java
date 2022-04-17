package com.yash.game;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.junit.Assert.assertThat;

/**
 * Created by gagan.ichake on 06-10-2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameSessionTest {

    @InjectMocks
    GameSession gameSession;

    @Test
    public void toStringAsJSON() throws Exception {

        String expectedJSONInToString = "{\"gameId\" : \"12\"," +" \"options\" : \"XYZ\"" + "}";

        GameSession gameSession = new GameSession();
        gameSession .setGameId("12");
        gameSession.setOptions("XYZ");

        String actualToString = gameSession.toString();

        assertEquals(expectedJSONInToString,actualToString);

    }

    @Test
    public void doorStatusValue() throws Exception {

        java.util.Map<Integer, String> expectedDoorStatus = new HashMap<Integer, String>() {{
            put(1, "Closed");
            put(2, "Closed");
            put(3, "Closed");
        }};

        GameSession gameSession = new GameSession();

        assertThat(gameSession.getDoorStatus().entrySet(), Matchers.equalTo(expectedDoorStatus.entrySet()));
    }
}