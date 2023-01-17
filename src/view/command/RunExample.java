package view.command;

import controller.Controller;
import exceptions.ControllerException;

import java.io.IOException;

public class RunExample extends Command {
    private Controller controller;

    public RunExample(String key, String description, Controller c) {
        super(key, description);
        controller = c;
    }

    @Override
    public void execute() {

        try {
            controller.allStep();
        }
        catch (ControllerException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
