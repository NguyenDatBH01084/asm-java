import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

// Định nghĩa giao diện cho ADT Sinh viên
interface StudentADT {
    void addStudent(Student student);
    void editStudent(int studentId, String newName, double newMarks);
    void deleteStudent(int studentId);
    Student searchStudent(int studentId);
    void sortStudentsByMarks();
    void sortStudentsByName();
    void displayStudents();
}

// Lớp Sinh viên
class Student {
    private int studentId;
    private String name;
    private double marks;

    public Student(int studentId, String name, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.marks = marks;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    public String getRanking() {
        if (marks < 5.0) {
            return "Fail";
        } else if (marks < 6.5) {
            return "Medium";
        } else if (marks < 7.5) {
            return "Good";
        } else if (marks < 9.0) {
            return "Very Good";
        } else {
            return "Excellent";
        }
    }
}

// Lớp Quản lý Sinh viên
class StudentManagement implements StudentADT {
    private ArrayList<Student> students;

    public StudentManagement() {
        students = new ArrayList<>();
    }

    @Override
    public void addStudent(Student student) {
        students.add(student);
    }

    @Override
    public void editStudent(int studentId, String newName, double newMarks) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId() == studentId) {
                students.set(i, new Student(studentId, newName, newMarks));
                return;
            }
        }
        System.out.println("Error: Student ID does not exist.");
    }

    @Override
    public void deleteStudent(int studentId) {
        boolean removed = students.removeIf(student -> student.getStudentId() == studentId);
        if (!removed) {
            System.out.println("Error: Student ID does not exist.");
        }
    }

    @Override
    public void sortStudentsByMarks() {
        Collections.sort(students, Comparator.comparingDouble(Student::getMarks));
    }

    @Override
    public void sortStudentsByName() {
        Collections.sort(students, Comparator.comparing(Student::getName));
    }

    @Override
    public Student searchStudent(int studentId) {
        for (Student student : students) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }
        return null;
    }

    @Override
    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("There are no students to display.");
            return;
        }
        for (Student student : students) {
            System.out.println("ID: " + student.getStudentId() +
                    ", Name: " + student.getName() +
                    ", Marks: " + student.getMarks() +
                    ", Ranking: " + student.getRanking());
        }
    }
}

// Lớp Chính
public class AssignmentPart1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagement management = new StudentManagement();

        try {
            System.out.print("Enter the number of students: ");
            int numberOfStudents = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            for (int i = 0; i < numberOfStudents; i++) {
                System.out.print("Enter Student ID: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                System.out.print("Enter Student Name: ");
                String name = scanner.nextLine();

                System.out.print("Enter Student Marks: ");
                double marks = scanner.nextDouble();

                management.addStudent(new Student(id, name, marks));
            }

            management.displayStudents();

            // sắp xếp sinh viên
            management.sortStudentsByMarks();
            System.out.println("\nStudents sorted by marks:");
            management.displayStudents();

            // tìm kiếm một sinh viên
            System.out.print("\nEnter Student ID to search: ");
            int searchId = scanner.nextInt();
            Student foundStudent = management.searchStudent(searchId);
            if (foundStudent != null) {
                System.out.println("Found Student: " + foundStudent.getName());
            } else {
                System.out.println("Student not found.");
            }

            // chỉnh sửa thông tin sinh viên
            System.out.print("\nEnter Student ID to edit: ");
            int editId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new marks: ");
            double newMarks = scanner.nextDouble();
            management.editStudent(editId, newName, newMarks);
            System.out.println("\nUpdated Student List:");
            management.displayStudents();

            // xóa một sinh viên
            System.out.print("\nEnter Student ID to delete: ");
            int deleteId = scanner.nextInt();
            management.deleteStudent(deleteId);
            System.out.println("\nUpdated Student List after deletion:");
            management.displayStudents();

        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter the correct data type.");
        } finally {
            scanner.close(); // Đóng scanner
        }
    }
}
