package com.yash.game;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gagan.ichake on 06-10-2016.
 */
@Service
public class GameService {

    private static Map<String, GameSession> gamePool = new HashMap<String, GameSession>();

    public GameSession startGameWithUniqueGameId() {

        String gameId = System.currentTimeMillis()-Math.random()+"";

        GameSession gameSession = new GameSession();
        gameSession.setGameId(gameId);
        gameSession.setOptions("Options[ 1 : Door1, 2 : Door2, 3 : Door3]");

        gamePool.put(gameId, gameSession);

        return gameSession;
    }

    public GameSession updateDoorStatusAsOpen(String gameId, Integer doorId) throws InvalidDoorException {

        GameSession gameSession = gamePool.get(gameId);
        if(gameSession.getDoorStatus().containsKey(doorId)){
            gameSession.setGameId(gameId);
            gameSession.getDoorStatus().put(doorId,"Open");
            Iterator<Integer> iterator = gameSession.getDoorStatus().keySet().iterator();
            while(iterator.hasNext()){
                Integer hisDoor = iterator.next();
                if(!hisDoor.equals(doorId)){
                    gameSession.getDoorStatus().get(hisDoor);
                    gameSession.getDoorStatus().put(hisDoor, "Closed");
                }
            }

        }else throw new InvalidDoorException();

        return gameSession;
    }

    public String closeGame(String gameId) throws InvalidDoorException{
        if(gamePool.containsKey(gameId)) {
            gamePool.remove(gameId);
            return "Sucssefully Closed Game";
        }else{
            throw new InvalidDoorException();
        }
    }


}
