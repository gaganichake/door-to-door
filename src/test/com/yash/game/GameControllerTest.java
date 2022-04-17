package com.yash.game;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by gagan.ichake on 06-10-2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

    @InjectMocks
    GameController gameController;

    @Mock
    GameService gameService;

    @Test
    public void startGameWithOK() throws Exception {

        ResponseEntity responseEntity = gameController.startGame();
        HttpStatus actualStatus = responseEntity.getStatusCode();

        HttpStatus expectedStatus = HttpStatus.OK;

        assertEquals(expectedStatus.value(), actualStatus.value());
    }

    @Test
    public void startGameWithMessageBody() throws Exception {

        Mockito.when(gameService.startGameWithUniqueGameId()).thenReturn(new GameSession());

        ResponseEntity responseEntity = gameController.startGame();
        HttpStatus actualStatus = responseEntity.getStatusCode();

        HttpStatus expectedStatus = HttpStatus.OK;

        assertEquals(expectedStatus.value(), actualStatus.value());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void generateGameIdCalled() throws Exception {

        gameController.startGame();
        Mockito.verify(gameService).startGameWithUniqueGameId();
    }

    @Test
    public void startGameReturnsGameSession() throws Exception {

        HttpStatus expectedStatus = HttpStatus.OK;
        String id = "34563";
        GameSession expectedGameSession = new GameSession();
        expectedGameSession.setGameId(id.toString());
        expectedGameSession.setOptions("Options[ 1 : Door1, 2 : Door2, 3 : Door3");

        Mockito.when(gameService.startGameWithUniqueGameId()).thenReturn(expectedGameSession);

        ResponseEntity responseEntity = gameController.startGame();
        HttpStatus actualStatus = responseEntity.getStatusCode();
        GameSession actualGameSession = (GameSession)responseEntity.getBody();

        assertEquals(expectedStatus.value(), actualStatus.value());
        assertNotNull(actualGameSession);
        assertEquals(expectedGameSession.getGameId(), actualGameSession.getGameId());
        assertEquals(expectedGameSession.getOptions(), actualGameSession.getOptions());

    }

    @Test
    public void openDoorWithOK() throws InvalidDoorException {

        HttpStatus expectedStatus = HttpStatus.OK;
        ResponseEntity responseEntity = gameController.openDoor("0", 0);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        assertEquals(expectedStatus.value(), actualStatus.value());
    }

    @Test
    public void openDoorWithMessageBody() throws Exception {

        HttpStatus expectedStatus = HttpStatus.OK;

        Mockito.when(gameService.updateDoorStatusAsOpen("0", 2)).thenReturn(new GameSession());

        ResponseEntity responseEntity = gameController.openDoor("0", 2);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        assertEquals(expectedStatus.value(), actualStatus.value());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void openDoorReturnsGameSession() throws Exception {

        HttpStatus expectedStatus = HttpStatus.OK;
        String gameId = "0";
        Integer doorNumber = 1;
        GameSession expectedGameSession = new GameSession();
        expectedGameSession.setGameId(gameId);
        expectedGameSession.setOptions("Options[ 1 : Door1, 2 : Door2, 3 : Door3]");

        Mockito.when(gameService.updateDoorStatusAsOpen(gameId, doorNumber)).thenReturn(expectedGameSession);

        ResponseEntity responseEntity = gameController.openDoor(gameId, doorNumber);
        HttpStatus actualStatus = responseEntity.getStatusCode();
        GameSession actualGameSession = (GameSession)responseEntity.getBody();

        assertEquals(expectedStatus.value(), actualStatus.value());
        assertNotNull(actualGameSession);
        assertEquals(expectedGameSession.getGameId(), actualGameSession.getGameId());
        assertEquals(expectedGameSession.getOptions(), actualGameSession.getOptions());

    }

    @Test
    public void openDoorReturnsGameStatus() throws Exception {

        HttpStatus expectedStatus = HttpStatus.OK;

        String gameId = "0";
        Integer doorNumber = 1;
        GameSession expectedGameSession = new GameSession();
        expectedGameSession.setGameId(gameId);
        expectedGameSession.setOptions("Options[ 1 : Door1, 2 : Door2, 3 : Door3]");
        java.util.Map<Integer, String> doorStatus = expectedGameSession.getDoorStatus();
        doorStatus.put(doorNumber, "Open");

        Mockito.when(gameService.updateDoorStatusAsOpen(gameId, doorNumber)).thenReturn(expectedGameSession);

        ResponseEntity responseEntity = gameController.openDoor(gameId, doorNumber);

        HttpStatus actualStatus = responseEntity.getStatusCode();
        GameSession actualGameSession = (GameSession)responseEntity.getBody();
        assertEquals(expectedStatus.value(), actualStatus.value());
        assertNotNull(actualGameSession);
        assertEquals(expectedGameSession.getGameId(), actualGameSession.getGameId());
        assertEquals(expectedGameSession.getOptions(), actualGameSession.getOptions());

        assertEquals(expectedGameSession.getDoorStatus().get(doorNumber), actualGameSession.getDoorStatus().get(doorNumber));

    }

    @Test
    public void openDoorShouldCallGameService() throws Exception {

        gameController.openDoor("0", 0);
        Mockito.verify(gameService).updateDoorStatusAsOpen("0", 0);
    }

    @Test
    public void openDoorShouldGetGameSessionFromService() throws Exception {

        HttpStatus expectedStatus = HttpStatus.OK;
        String gameId = "0";
        Integer doorNumber = 1;
        GameSession expectedGameSession = new GameSession();
        expectedGameSession.setGameId(gameId);
        expectedGameSession.setOptions("XYZ");
        java.util.Map<Integer, String> doorStatus = expectedGameSession.getDoorStatus();

        Mockito.when(gameService.updateDoorStatusAsOpen(gameId, doorNumber)).thenReturn(expectedGameSession);

        ResponseEntity responseEntity = gameController.openDoor(gameId, doorNumber);

        HttpStatus actualStatus = responseEntity.getStatusCode();
        GameSession actualGameSession = (GameSession)responseEntity.getBody();

        assertEquals(expectedStatus.value(), actualStatus.value());
        assertNotNull(actualGameSession);
        assertEquals(expectedGameSession.getGameId(), actualGameSession.getGameId());
        assertEquals(expectedGameSession.getOptions(), actualGameSession.getOptions());
        assertEquals(expectedGameSession.getDoorStatus().get(doorNumber), actualGameSession.getDoorStatus().get(doorNumber));

    }

    @Test
    public void ifOtherThanDoor3CalledExceptionMessageShouldBeThrownWithMessage() throws Exception {

        String expectedMessage = "This is a invalid door, Please start game for getting new GameID";
        String gameId = "0";
        Integer doorNumber = 4;

        Mockito.when(gameService.updateDoorStatusAsOpen(gameId, doorNumber)).thenThrow(InvalidDoorException.class);

        ResponseEntity responseEntity = gameController.openDoor(gameId, doorNumber);

        String actualMessage = responseEntity.getBody().toString();

        assertEquals(expectedMessage, actualMessage);

    }

}