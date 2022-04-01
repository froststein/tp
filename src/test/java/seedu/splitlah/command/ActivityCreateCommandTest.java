package seedu.splitlah.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.splitlah.data.Manager;
import seedu.splitlah.data.Person;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActivityCreateCommandTest {

    Manager manager = new Manager();

    /**
     * Creates a session that is stored and managed by the Manager object.
     * Creates an activity in the new session that was created.
     */
    @BeforeEach
    void setUp() {
        String sessionOneArgs = "session /create /n Class outing /d 15-02-2022 /pl Alice Bob Charlie";
        Command createSessionOne = Parser.getCommand(sessionOneArgs);
        createSessionOne.run(manager);
        String activityOneArgs = "activity /create /sid 1 /n Lunch /p Alice /i Alice Bob Charlie /co 15";
        Command createActivityOne = Parser.getCommand(activityOneArgs);
        createActivityOne.run(manager);
    }

    /**
     * Checks if an activity is not created when an activity has duplicate names in the involved list.
     *
     * @throws InvalidDataException If there are no sessions stored or
     *                              if the session unique identifier specified was not found.
     */
    @Test
    public void run_hasNameDuplicatesInInvolvedList_activityNotCreated() throws InvalidDataException {
        String userInput = "activity /create /sid 1 /n Dinner /p Alice /i Alice Alice Charlie /co 30";

        //Checks that command returned is an ActivityCreateCommand object
        Command command = Parser.getCommand(userInput);
        assertEquals(ActivityCreateCommand.class, command.getClass());

        //Checks that the number of activities stored remains the same
        int currentActivityId = manager.getProfile().getActivityIdTracker();
        command.run(manager);
        assertEquals(1, manager.getProfile().getSession(1).getActivityList().size());

        //Checks that the activityId is not incremented
        int testActivityId = manager.getProfile().getActivityIdTracker();
        assertEquals(currentActivityId, testActivityId);
    }

    /**
     * Checks if an activity is not created when an activity has at least one name in involved list that is
     * not found in session.
     *
     * @throws InvalidDataException If there are no sessions stored or
     *                              if the session unique identifier specified was not found.
     */
    @Test
    public void run_hasInvalidNameInInvolvedList_activityListSizeRemainsOne() throws InvalidDataException {
        String userInput = "activity /create /sid 1 /n Dinner /p Alice /i Eve Mallory /co 30";

        //Checks that command returned is an ActivityCreateCommand object
        Command command = Parser.getCommand(userInput);
        assertEquals(ActivityCreateCommand.class, command.getClass());

        //Checks that the number of activities stored remains the same
        int currentActivityId = manager.getProfile().getActivityIdTracker();
        command.run(manager);
        assertEquals(1, manager.getProfile().getSession(1).getActivityList().size());

        //Checks that the activityId is not incremented
        int testActivityId = manager.getProfile().getActivityIdTracker();
        assertEquals(currentActivityId, testActivityId);
    }


    /**
     * Checks if activity is created successfully and added into list of activities.
     *
     * @throws InvalidDataException If there are no sessions stored or
     *                              if the session unique identifier specified was not found.
     */
    @Test
    public void run_validCommand_activitySuccessfullyCreated() throws InvalidDataException {
        Person alice = manager.getProfile().getSession(1).getPersonByName("Alice");
        Person bob = manager.getProfile().getSession(1).getPersonByName("Bob");
        Person charlie = manager.getProfile().getSession(1).getPersonByName("Charlie");

        //Case 1: Split costs evenly among 3 persons
        String userInputOne = "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob Charlie /co 30";

        //Checks that command returned is an ActivityCreateCommand object
        Command commandOne = Parser.getCommand(userInputOne);
        assertEquals(ActivityCreateCommand.class, commandOne.getClass());

        //Checks that the number of activities stored increases by 1
        int currentActivityOneId = manager.getProfile().getActivityIdTracker();
        commandOne.run(manager);
        assertEquals(2, manager.getProfile().getSession(1).getActivityList().size());

        //Checks that ActivityCost of all 3 persons are updated correctly
        assertEquals(10, alice.getActivityCostOwed(2));
        assertEquals(10, bob.getActivityCostOwed(2));
        assertEquals(10, charlie.getActivityCostOwed(2));

        //Checks that the activityId is incremented
        int testActivityOneId = manager.getProfile().getActivityIdTracker();
        assertEquals(currentActivityOneId + 1, testActivityOneId);

        //Case 2: Individual costs provided
        String userInputTwo = "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob Charlie /cl 5 10 15";

        //Checks that command returned is an ActivityCreateCommand object
        Command commandTwo = Parser.getCommand(userInputTwo);
        assertEquals(ActivityCreateCommand.class, commandTwo.getClass());

        //Checks that the number of activities stored increases by 1
        int currentActivityTwoId = manager.getProfile().getActivityIdTracker();
        commandTwo.run(manager);
        assertEquals(3, manager.getProfile().getSession(1).getActivityList().size());

        //Checks that ActivityCost of all 3 persons are updated correctly
        assertEquals(5, alice.getActivityCostOwed(3));
        assertEquals(10, bob.getActivityCostOwed(3));
        assertEquals(15, charlie.getActivityCostOwed(3));

        //Checks that the activityId is incremented
        int testActivityTwoId = manager.getProfile().getActivityIdTracker();
        assertEquals(currentActivityTwoId + 1, testActivityTwoId);
    }
}
