package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.MyLevel;

public class Main {
    public static void main(String[] args) {
        // setting game window: window name and its dimensions
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 1200, 700);

        // creating instance of game application
        // using class `GameApplication` as implementation of interface `Game`
        Game game = new GameApplication(windowSetup, new LwjglBackend()); // in case of Mac OS system use "new Lwjgl2Backend()" as the second parameter

        // creating scene for game
        // using class `World` as implementation of interface `Scene`
        Scene scene = new World("my map", "maps/map.tmx", new MyLevel.Factory());

        //SceneListener FirstSteps = new FirstSteps();
        SceneListener MyLevel = new MyLevel();

        //scene.addListener(FirstSteps);
        scene.addListener(MyLevel);

        // adding scene into the game
        game.addScene(scene);

        // running the game
        game.start();

        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
    }
}
