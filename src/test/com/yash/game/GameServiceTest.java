package com.yash.game;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by gagan.ichake on 06-10-2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @InjectMocks
    GameService gameService;

    @Test
    public void generateGameId() throws Exception {

       assertNotNull(gameService.startGameWithUniqueGameId());
    }

    @Test
    public void generateGameIdAsString() throws Exception {

        GameSession gameSession = gameService.startGameWithUniqueGameId();

        assertNotNull(gameSession);
    }

    @Test
    public void generateGameIdShouldBeUnique() throws Exception {

        GameSession gameSession1 = gameService.startGameWithUniqueGameId();

        GameSession gameSession2 = gameService.startGameWithUniqueGameId();

        assertNotEquals(gameSession1.getGameId(), gameSession2.getGameId());
    }

    @Test
    public void ShouldReturnGameId()throws Exception{
        Integer doorNumber = 1;

        GameSession expectedGameSession = gameService.startGameWithUniqueGameId();
        GameSession actualGameSession=gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(),doorNumber);
        assertNotNull(actualGameSession);
        assertEquals(expectedGameSession.getGameId(),actualGameSession.getGameId());
    }


    @Test
    public void DoorNumberShouldHaveOpenStatus() throws Exception{
        Integer doorNumber = 1;

        GameSession expectedGameSession = gameService.startGameWithUniqueGameId();
        expectedGameSession.getDoorStatus().put(doorNumber,"Open");
        GameSession actualGameSession=gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(),doorNumber);
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber), expectedGameSession.getDoorStatus().get(doorNumber));
    }

    @Test
    public void WhenDoor1IsOpenedAllOtherDoorsShouldBeClosed() throws Exception{
        Integer doorNumber = 1;
        Integer doorNumber2 = 2;
        Integer doorNumber3=3;

        GameSession expectedGameSession = gameService.startGameWithUniqueGameId();
        expectedGameSession.getDoorStatus().put(doorNumber, "Open");

        GameSession actualGameSession = gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(), doorNumber);

        assertEquals(actualGameSession.getDoorStatus().get(doorNumber), expectedGameSession.getDoorStatus().get(doorNumber));
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber2), expectedGameSession.getDoorStatus().get(doorNumber2));
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber3), expectedGameSession.getDoorStatus().get(doorNumber3));
    }

    @Test
    public void WhenDoor2IsOpenedAllOtherDoorsShouldBeClosed()throws Exception {
        Integer doorNumber = 1;
        Integer doorNumber2 = 2;
        Integer doorNumber3=3;
        GameSession expectedGameSession = gameService.startGameWithUniqueGameId();
        expectedGameSession.getDoorStatus().put(doorNumber2, "Open");

        GameSession actualGameSession = gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(), doorNumber2);
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber), expectedGameSession.getDoorStatus().get(doorNumber));
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber2), expectedGameSession.getDoorStatus().get(doorNumber2));
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber3), expectedGameSession.getDoorStatus().get(doorNumber3));
    }

    @Test
    public void WhenDoor3IsOpenedAllOtherDoorsShouldBeClosed() throws Exception {
        Integer doorNumber = 1;
        Integer doorNumber2 = 2;
        Integer doorNumber3=3;
        GameSession expectedGameSession = gameService.startGameWithUniqueGameId();
        expectedGameSession.getDoorStatus().put(doorNumber3, "Open");

        GameSession actualGameSession = gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(), doorNumber3);
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber), expectedGameSession.getDoorStatus().get(doorNumber));
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber2), expectedGameSession.getDoorStatus().get(doorNumber2));
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber3), expectedGameSession.getDoorStatus().get(doorNumber3));
    }

    @Test
    public void whenDoor1IsOpenedAfterDoor2() throws Exception{
        Integer doorNumber1 = 1;
        Integer doorNumber2 = 2;
        Integer doorNumber3=3;

        GameSession expectedGameSession = gameService.startGameWithUniqueGameId();

       gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(), doorNumber2);

        GameSession actualGameSession = gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(), doorNumber1);

        assertEquals(actualGameSession.getDoorStatus().get(doorNumber1), "Open");
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber2), "Closed");
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber3), "Closed");
    }

    @Test
    public void whenDoor2IsOpenedAfterDoor1() throws Exception{
        Integer doorNumber1 = 1;
        Integer doorNumber2 = 2;
        Integer doorNumber3=3;

        GameSession expectedGameSession = gameService.startGameWithUniqueGameId();

        gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(), doorNumber1);

        GameSession actualGameSession = gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(), doorNumber2);

        assertEquals(actualGameSession.getDoorStatus().get(doorNumber1), "Closed");
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber2), "Open");
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber3), "Closed");
    }

    @Test
    public void whenDoor3IsOpenedAfterDoor1() throws Exception{
        Integer doorNumber1 = 1;
        Integer doorNumber2 = 2;
        Integer doorNumber3 = 3;

        GameSession expectedGameSession = gameService.startGameWithUniqueGameId();

        gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(), doorNumber1);

        GameSession actualGameSession = gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(), doorNumber3);

        assertEquals(actualGameSession.getDoorStatus().get(doorNumber1), "Closed");
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber2), "Closed");
        assertEquals(actualGameSession.getDoorStatus().get(doorNumber3), "Open");
    }

    @Test(expected = InvalidDoorException.class)
    public void moreThan3DoorsNotAllowed() throws Exception{
        Integer doorNumber = 1;
        Integer doorNumber2 = 2;
        Integer doorNumber3 = 3;
        GameSession expectedGameSession = gameService.startGameWithUniqueGameId();
        expectedGameSession.getDoorStatus().put(doorNumber, "Closed");
        expectedGameSession.getDoorStatus().put(doorNumber2, "Closed");
        expectedGameSession.getDoorStatus().put(doorNumber3, "Closed");

        Integer doorNumber4 = 4;

        GameSession actualGameSession = gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(), doorNumber4);
        assertThat(actualGameSession.getDoorStatus().entrySet(), Matchers.equalTo(expectedGameSession.getDoorStatus().entrySet()));
    }

    @Test(expected = InvalidDoorException.class)
    public void ifGotOtherThan3DoorThanThrowException() throws Exception {
        Integer doorNumber = 1;
        Integer doorNumber2 = 2;
        Integer doorNumber3 = 3;
        GameSession expectedGameSession = gameService.startGameWithUniqueGameId();
        expectedGameSession.getDoorStatus().put(doorNumber, "Closed");
        expectedGameSession.getDoorStatus().put(doorNumber2, "Closed");
        expectedGameSession.getDoorStatus().put(doorNumber3, "Closed");

        Integer doorNumber4 = 4;
        when(gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(), doorNumber4)).thenThrow(new InvalidDoorException());

        GameSession actualGameSession = gameService.updateDoorStatusAsOpen(expectedGameSession.getGameId(), doorNumber4);
        assertThat(actualGameSession.getDoorStatus().entrySet(), Matchers.equalTo(expectedGameSession.getDoorStatus().entrySet()));
    }

    @Test
    public void removeGameIdOnCloser() throws Exception {
        String expectedMessage="Sucssefully Closed Game";
        GameSession gameSession=gameService.startGameWithUniqueGameId();
        String message=gameService.closeGame(gameSession.getGameId());
        assertEquals(expectedMessage,message);
    }
    @Test(expected = InvalidDoorException.class)
    public void throwExceptionsWhenClosingInvalidDoor() throws Exception {
        String GameId="invalid GameID";
        String message=gameService.closeGame(GameId);
    }


}