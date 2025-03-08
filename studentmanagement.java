import java.util.*;
import java.io.*;

class Student {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    // Getters
    public String getName() { return name; }
    public int getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setRollNumber(int rollNumber) { this.rollNumber = rollNumber; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "Roll Number: " + rollNumber + ", Name: " + name + ", Grade: " + grade;
    }
}

// Main class for the Student Management System
public class studentmanagement {
    // List to store student records
    private static List<Student> students = new ArrayList<>();
    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        // Load existing students from file
        loadStudentsFromFile();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Menu-driven interface
        while (!exit) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Save and Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    removeStudent(scanner);
                    break;
                case 3:
                    searchStudent(scanner);
                    break;
                case 4:
                    displayAllStudents();
                    break;
                case 5:
                    saveStudentsToFile();
                    exit = true;
                    System.out.println("Data saved. Exiting the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    // Method to add a new student
    private static void addStudent(Scanner scanner) {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Roll Number: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Grade: ");
        String grade = scanner.nextLine();

        Student student = new Student(name, rollNumber, grade);
        students.add(student);
        System.out.println("Student added successfully!");
    }

    // Method to remove a student by roll number
    private static void removeStudent(Scanner scanner) {
        System.out.print("Enter Roll Number of the student to remove: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean removed = students.removeIf(s -> s.getRollNumber() == rollNumber);
        if (removed) {
            System.out.println("Student removed successfully!");
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }

    // Method to search for a student by roll number
    private static void searchStudent(Scanner scanner) {
        System.out.print("Enter Roll Number of the student to search: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                System.out.println("Student found: " + student);
                return;
            }
        }
        System.out.println("Student with roll number " + rollNumber + " not found.");
    }

    // Method to display all students
    private static void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            System.out.println("\nList of Students:");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // Method to load student data from a file
    private static void loadStudentsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            students.clear();
            while ((line = br.readLine()) != null) {
                // Each line format: rollNumber,name,grade
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int rollNumber = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String grade = parts[2].trim();
                    students.add(new Student(name, rollNumber, grade));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }

    // Method to save student data to a file
    private static void saveStudentsToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students) {
                // Save as: rollNumber,name,grade
                pw.println(student.getRollNumber() + "," + student.getName() + "," + student.getGrade());
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }
}
