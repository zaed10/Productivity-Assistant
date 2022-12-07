package home;

public class Task {
    public String taskName;
    public String priority; // can only be 1, 2 or 3

    public Task(String taskName, String priority) {
        this.taskName = taskName;
        this.priority = priority;
    }

    public void changeName(String newName) {
        this.taskName = newName;
    }

    public void changePriority(String  newPriority) {
        this.priority = newPriority;
    }

    @Override
    public String toString(){
        return String.format("PRIORITY %s - %s", this.priority, this.taskName);
    }
}