package com.yash.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by gagan.ichake on 06-10-2016.
 */
@Controller
public class GameController {

    @Autowired
    GameService gameService;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ResponseEntity startGame() {

        return new ResponseEntity(gameService.startGameWithUniqueGameId(), HttpStatus.OK);
    }

    @RequestMapping(value = "/open", method = RequestMethod.GET)
    public ResponseEntity openDoor(@RequestParam String gameId, @RequestParam Integer doorNumber) {
        GameSession gameSession = null;
        try {
            gameSession = gameService.updateDoorStatusAsOpen(gameId, doorNumber);
        } catch (InvalidDoorException e) {
            return new ResponseEntity("This is a invalid door, Please start game for getting new GameID", HttpStatus.OK);
        }
        return new ResponseEntity(gameSession, HttpStatus.OK);
    }

    @RequestMapping(value = "/close", method = RequestMethod.GET)
    public ResponseEntity closeGame(@RequestParam String gameId) {
        String status = null;
        try {
            status = gameService.closeGame(gameId);
        } catch (InvalidDoorException e) {
            return new ResponseEntity("This is a invalid Game Id, Please start game for getting new GameID", HttpStatus.OK);
        }
        return new ResponseEntity(status, HttpStatus.OK);

    }

}
