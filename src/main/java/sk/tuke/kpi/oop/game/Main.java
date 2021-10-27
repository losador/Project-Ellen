package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.gamelib.inspector.InspectableScene;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;

import java.util.List;

public class Main {
    public static void main(String[] args) {
// setting game window: window name and its dimensions
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        // creating instance of game application
        // using class `GameApplication` as implementation of interface `Game`
        Game game = new GameApplication(windowSetup, new LwjglBackend()); // in case of Mac OS system use "new Lwjgl2Backend()" as the second parameter

        // creating scene for game
        // using class `World` as implementation of interface `Scene`
        Scene scene = new InspectableScene(new World("world"), List.of("sk.tuke.kpi"));

        SceneListener FirstSteps = new FirstSteps();

        scene.addListener(FirstSteps);

        // adding scene into the game
        game.addScene(scene);

        // running the game
        game.start();

        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
    }
}
