package seedu.splitlah;

import seedu.splitlah.command.ActivityEditCommand;
import seedu.splitlah.command.Command;
import seedu.splitlah.data.Activity;
import seedu.splitlah.data.Manager;
import seedu.splitlah.data.Person;
import seedu.splitlah.data.Session;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.parser.Parser;
import seedu.splitlah.ui.Message;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Entry point of the SplitLah application.
 * Initializes SplitLah and starts interacting with the user.
 */
public class SplitLah {
    Manager manager;

    public static void main(String[] args) {
        new SplitLah().run();
    }

    /** Sets up the required objects for application.  */
    public SplitLah() {
        manager = new Manager(true);
    }

    /** Runs the program until it terminates.  */
    private void run() {
        showWelcomeMessage();
        runProcessLoop();
        exitApplication();
    }

    /** Prints welcome message.  */
    private void showWelcomeMessage() {
        manager.getUi().printWelcome();
    }

    /** Exits the program.  */
    private void exitApplication() {
        manager.getLogger().log(Level.INFO, Message.LOGGER_SPLITLAH_APPLICATION_EXIT);
        System.exit(0);
    }

    /** Reads the user input until the user enters the exit command.  */
    private void runProcessLoop() {
        Command command;
        do {
            String userInput = manager.getUi().readNextLine();
            command = Parser.getCommand(userInput);
            command.run(manager);
        } while (!Command.isExitCommand(command));
        manager.saveProfile();
    }
}
