package view.command;

public abstract class Command {
    private String key;
    private String description;

    public Command(String k, String d) {
        key = k;
        description = d;
    }

    public abstract void execute();

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
