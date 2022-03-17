package seedu.splitlah.data;

import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;

/**
 * Represents a name. Names must contain only alphabetical characters.
 *
 * @author Saurav
 */
public class Name {

    private final String name;

    public Name(String name) throws InvalidDataException {
        if (!name.matches("[a-zA-Z]+")) {
            throw new InvalidDataException(Message.ERROR_NAME_INVALID_NAME);
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}