import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.*;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

@Data
class Student {
    private int studentId;
    private String studentName;
    private int studentDOB;
    private String studentSubject;
}

interface StudentService {
    void addNewStudent(Student student);
    void displayAllStudents();
    void updateStudent(int studentId, Student updatedStudent);
    void removeStudentById(int studentId);
    Student getStudentById(int studentId);
    List<Student> searchStudents(String keyword);
}

@NoArgsConstructor
class StudentServiceImp implements StudentService {
    private final List<Student> studentList = new ArrayList<>();
    private final String FILE_PATH = "student.dat";


    @Override
    public void addNewStudent(Student student) {
        studentList.add(student);
    }

    @Override
    public void displayAllStudents() {
        Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.ALL);
        table.addCell("ID");
        table.addCell("STUDENT'S NAME");
        table.addCell("DATE OF BRITH");
        table.addCell("CLASS");
        table.addCell("UPDATED");
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))){
            String data;
            for (int i=0; i<5; i++){
                table.setColumnWidth(i, 30, 30);
            }
            while ((data = bufferedReader.readLine()) != null){
                String[] value = data.split(",");
                table.addCell(value[0], 1);
                table.addCell(value[1], 1);
                table.addCell(value[2], 1);
                table.addCell(value[3], 1);
                table.addCell(value[4], 1);
            }
            System.out.println(table.render());
        } catch (Exception exception) {
            System.out.println("[!] Problem during get all courses: " + exception.getMessage());
        }
    }

    @Override
    public void updateStudent(int studentId, Student updatedStudent) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentId() == studentId) {
                studentList.set(i, updatedStudent);
                break;
            }
        }
    }

    @Override
    public void removeStudentById(int studentId) {
        studentList.removeIf(student -> student.getStudentId() == studentId);
    }

    @Override
    public Student getStudentById(int studentId) {
        return studentList.stream()
                .filter(student -> student.getStudentId() == studentId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Student> searchStudents(String keyword) {
        List<Student> searchResults = new ArrayList<>();
        for (Student student : studentList) {
            if (student.getStudentName().contains(keyword) || String.valueOf(student.getStudentId()).contains(keyword)) {
                searchResults.add(student);
            }
        }
        return searchResults;
    }
}

@RequiredArgsConstructor
class View {
    private final StudentService studentService;

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("""
                    ░█████╗░░██████╗████████╗░█████╗░██████╗░
                    ██╔══██╗██╔════╝╚══██╔══╝██╔══██╗██╔══██╗
                    ██║░░╚═╝╚█████╗░░░░██║░░░███████║██║░░██║
                    ██║░░██╗░╚═══██╗░░░██║░░░██╔══██║██║░░██║
                    ╚█████╔╝██████╔╝░░░██║░░░██║░░██║██████╔╝
                    ░╚════╝░╚═════╝░░░░╚═╝░░░╚═╝░░╚═╝╚═════╝░
                    
                    """);
            System.out.println("=".repeat(150));
            System.out.println("1. ADD NEW STUDENT\t2. LIST ALL STUDENTS\t3. COMMIT DATA TO FILE");
            System.out.println("4. SEARCH FOR STUDENT\t5. UPDATE STUDENT\t6. DELETE STUDENT'S DATA");
            System.out.println("7. GENERATE DATA TO FILE\t0. Exit");
            System.out.println("=".repeat(150));
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character


            switch (choice) {
                case 1:
                    addNewStudent(scanner);
                    break;
                case 2:
                    studentService.displayAllStudents();
                    break;
                case 3:
                    // Not implemented yet
                    break;
                case 4:
                    searchStudent(scanner);
                    break;
                case 5:
                    updateStudent(scanner);
                    break;
                case 6:
                    removeStudent(scanner);
                    break;
                case 7:
                    // Not implemented yet
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a number between 0 and 7.");
            }
        }
    }

    private void addNewStudent(Scanner scanner) {
        // Implement logic to input student details and add to service

    }

    private void updateStudent(Scanner scanner) {
        // Implement logic to input student ID and updated details and update in service
    }

    private void removeStudent(Scanner scanner) {
        // Implement logic to input student ID and remove from service
    }

    private void searchStudent(Scanner scanner) {
        // Implement logic to input search keyword and display search results from service
    }
}

public class Main {
    public static void main(String[] args) {
        StudentService studentService = new StudentServiceImp();
        View view = new View(studentService);
        view.menu();
    }
}
