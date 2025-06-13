import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdvancedToDoListApp {

    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = getUserChoice();
            handleUserChoice(choice);
        } while (choice != 6);

        System.out.println("Goodbye! 👋");
    }

    private static void printMenu() {
        System.out.println("\n===== ADVANCED TO-DO LIST MENU =====");
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Mark Task as Completed");
        System.out.println("4. Edit Task");
        System.out.println("5. Remove Task");
        System.out.println("6. Exit");
        System.out.print("Choose an option (1-6): ");
    }

    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void handleUserChoice(int choice) {
        scanner.nextLine(); // consume newline
        switch (choice) {
            case 1 -> addTask();
            case 2 -> viewTasks();
            case 3 -> markTaskCompleted();
            case 4 -> editTask();
            case 5 -> removeTask();
            case 6 -> System.out.println("Exiting...");
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private static void addTask() {
        System.out.print("Enter task description: ");
        String desc = scanner.nextLine().trim();
        if (desc.isEmpty()) {
            System.out.println("Task description cannot be empty.");
            return;
        }

        System.out.print("Enter priority (1-High, 2-Medium, 3-Low): ");
        int priority = 0;
        try {
            priority = Integer.parseInt(scanner.nextLine().trim());
            if (priority < 1 || priority > 3) {
                System.out.println("Invalid priority. Set to Medium (2) by default.");
                priority = 2;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Set priority to Medium (2) by default.");
            priority = 2;
        }

        System.out.print("Enter due date (yyyy-MM-dd) or leave blank: ");
        String dueDateInput = scanner.nextLine().trim();
        LocalDate dueDate = null;
        if (!dueDateInput.isEmpty()) {
            try {
                dueDate = LocalDate.parse(dueDateInput, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Due date will be empty.");
            }
        }

        tasks.add(new Task(desc, priority, dueDate));
        System.out.println("Task added!");
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        System.out.println("\nYour Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, tasks.get(i));
        }
    }

    private static void markTaskCompleted() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to mark completed.");
            return;
        }
        System.out.print("Enter task number to mark as completed: ");
        int index = getUserChoice();
        scanner.nextLine(); // consume newline
        if (index >= 1 && index <= tasks.size()) {
            Task t = tasks.get(index - 1);
            t.setCompleted(true);
            System.out.println("Task marked as completed.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private static void editTask() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to edit.");
            return;
        }
        System.out.print("Enter task number to edit: ");
        int index = getUserChoice();
        scanner.nextLine();
        if (index < 1 || index > tasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }
        Task task = tasks.get(index - 1);

        System.out.print("Enter new description (leave blank to keep current): ");
        String newDesc = scanner.nextLine().trim();
        if (!newDesc.isEmpty()) {
            task.setDescription(newDesc);
        }

        System.out.print("Enter new priority (1-High, 2-Medium, 3-Low) or leave blank: ");
        String priorityInput = scanner.nextLine().trim();
        if (!priorityInput.isEmpty()) {
            try {
                int newPriority = Integer.parseInt(priorityInput);
                if (newPriority >= 1 && newPriority <= 3) {
                    task.setPriority(newPriority);
                } else {
                    System.out.println("Invalid priority input. Keeping current priority.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping current priority.");
            }
        }

        System.out.print("Enter new due date (yyyy-MM-dd) or leave blank: ");
        String dueDateInput = scanner.nextLine().trim();
        if (!dueDateInput.isEmpty()) {
            try {
                LocalDate newDueDate = LocalDate.parse(dueDateInput, DATE_FORMAT);
                task.setDueDate(newDueDate);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Keeping current due date.");
            }
        }

        System.out.println("Task updated.");
    }

    private static void removeTask() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to remove.");
            return;
        }
        System.out.print("Enter task number to remove: ");
        int index = getUserChoice();
        scanner.nextLine();
        if (index >= 1 && index <= tasks.size()) {
            Task removed = tasks.remove(index - 1);
            System.out.println("Removed task: " + removed.getDescription());
        } else {
            System.out.println("Invalid task number.");
        }
    }
}

class Task {
    private String description;
    private boolean completed;
    private int priority; // 1=High, 2=Medium, 3=Low
    private LocalDate dueDate;

    public Task(String description, int priority, LocalDate dueDate) {
        this.description = description;
        this.completed = false;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    @Override
    public String toString() {
        String status = completed ? "[✔]" : "[ ]";
        String prio;
        switch(priority) {
            case 1 -> prio = "High";
            case 3 -> prio = "Low";
            default -> prio = "Medium";
        }
        String due = (dueDate != null) ? dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "No due date";
        return String.format("%s %s (Priority: %s, Due: %s)", status, description, prio, due);
    }
}
