package ui;

import model.Award;
import model.Course;
import model.StudentTranscript;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

 //represents student service application
public class StudentService {
    private static final String JSON_STORE = "./data/studentTranscript.json";
    private StudentTranscript myTranscript;
    private Scanner input;
    private Award a1 = new Award("Contest1st", 2022, 5000);
    private Award a2 = new Award("Scholarship1", 2023, 8000);
    private Course c1 = new Course("CPSC210","2023W1",4,100, 90);
    private Course c2 = new Course("CPSC110","2022W1",4,90, 80);
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the student service application
    public StudentService() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runStudentService();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runStudentService() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        System.out.println("Please enter your name");
        String studentName = input.nextLine();
        System.out.println("Please enter your Student ID");
        int studentId = input.nextInt();

        init(studentName, studentId);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes a student transcript and several awards and courses
    private void init(String studentName, int studentId) {
        myTranscript = new StudentTranscript(studentName, studentId);

        myTranscript.addAward(a1);
        myTranscript.addAward(a2);
        myTranscript.addCourse(c1);
        myTranscript.addCourse(c2);

        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\taddc -> add a course to my transcript");
        System.out.println("\tremovec -> remove a course from my transcript");
        System.out.println("\tadda -> add an award to my transcript");
        System.out.println("\tremovea -> remove an award from my transcript");
        System.out.println("\tgpa -> GPA Calculator");
        System.out.println("\tinfo -> Course Information");
        System.out.println("\tt -> Display Transcript");
        System.out.println("\ta -> Display Award List");
        System.out.println("\ts -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tq -> quit");

    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("gpa")) {
            doGpaCalculator();
        } else if (command.equals("info")) {
            doCourseInformation();
        } else if (command.equals("t")) {
            doTranscript();
        } else if (command.equals("a")) {
            doAwardList();
        } else if (command.equals("addc")) {
            doAddCourse();
        } else if (command.equals("removec")) {
            doRemoveCourse();
        } else if (command.equals("adda")) {
            doAddAward();
        } else if (command.equals("removea")) {
            doRemoveAward();
        } else if (command.equals("s")) {
            saveStudentTranscript();
        } else if (command.equals("l")) {
            loadStudentTranscript();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Add a course to my transcript
    protected void doAddCourse() {
        input = new Scanner(System.in);
        System.out.print("Enter the course name you want to add (eg. CPSC210) ");
        String courseName = input.nextLine();
        System.out.print("Enter the semester you took this course (eg. 2022S1) ");
        String courseSemester = input.nextLine();
        System.out.print("Enter the credits this course worth ");
        int courseCredits = input.nextInt();
        System.out.print("Enter your final grade for this course (out of 100) ");
        int courseGrade = input.nextInt();
        System.out.print("Enter the average this course (out of 100) ");
        int courseAverage = input.nextInt();

        Course myCourse = new Course(courseName,courseSemester,courseCredits,courseGrade,courseAverage);
        myTranscript.addCourse(myCourse);
        System.out.println("\nCourse successfully added! ");
    }

    // MODIFIES: this
    // EFFECTS: Remove a course from my transcript
    private void doRemoveCourse() {
        input = new Scanner(System.in);
        System.out.print("Enter the course name you want to remove (eg. CPSC210) ");
        String courseName = input.nextLine();
        for (Course next: myTranscript.getCourses()) {
            if (courseName.equals(next.getCourseName())) {
                myTranscript.removeCourse(next);
                System.out.println("\nCourse successfully removed! ");
                break;
            }
        }
        System.out.println("\nWork done! ");
    }

    // MODIFIES: this
    // EFFECTS: Add an award to my transcript
    private void doAddAward() {
        input = new Scanner(System.in);
        System.out.print("Enter the Award name you want to add ");
        String awardName = input.nextLine();
        System.out.print("Enter the year you won this award ");
        int yearWon = input.nextInt();
        System.out.print("Enter the prize for this award ");
        int moneyWon = input.nextInt();

        Award myAward  = new Award(awardName,yearWon,moneyWon);
        myTranscript.addAward(myAward);
        System.out.println("\nAward successfully added! ");
    }

    // MODIFIES: this
    // EFFECTS: Remove an award from my transcript
    private void doRemoveAward() {
        input = new Scanner(System.in);
        System.out.print("Enter the Award name you want to remove ");
        String awardName = input.nextLine();
        for (Award next: myTranscript.getAwards()) {
            if (awardName.equals(next.getAwardName())) {
                myTranscript.removeAward(next);
                System.out.println("\nAward successfully removed! ");
                break;
            }
        }
        System.out.println("\nWork done! ");
    }

    // MODIFIES: this
    // EFFECTS: get GPA of selected time period
    private void doGpaCalculator() {
        StudentTranscript selected = selectPeriod();
        printGPA(selected);
    }

    // MODIFIES: this
    // EFFECTS: conducts a course information display
    private void doCourseInformation() {
        StudentTranscript selected = selectPeriod();
        printCourseInformation(selected);
        System.out.println("Above are course information");
    }

    // MODIFIES: this
    // EFFECTS: conducts a transcript display
    private void doTranscript() {
        StudentTranscript selected = selectPeriod();
        printTranscript(selected);
        System.out.println("That's the end of your transcript");
    }

    // MODIFIES: this
    // EFFECTS: get AwardList
    private void doAwardList() {
        printAwards(myTranscript);
        printMoney(myTranscript);
        System.out.println("That's all for award information");
    }

    // EFFECTS: prompts user to select overall or semester(and specify which) and returns it
    private StudentTranscript selectPeriod() {
        String selection = "";  // force entry into loop
        String selectSemester = "";

        while (!(selection.equals("overall") || selection.equals("semester"))) {
            System.out.println("select from:");
            System.out.println("overall / semester");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("overall")) {
            return myTranscript;
        } else {
            input = new Scanner(System.in);
            System.out.println("Type in the semester(eg. 2002W1):");
            selectSemester = input.nextLine();
            StudentTranscript semesterTranscript = myTranscript.getTranscriptBySemester(selectSemester);
            return semesterTranscript;
        }
    }

    // EFFECTS: prints GPA of selected time period to the screen
    private void printGPA(StudentTranscript selected) {
        System.out.println("Your GPA is " + selected.getAverage());
    }

    // EFFECTS: prints course Information of selected time period to the screen
    private void printCourseInformation(StudentTranscript selected) {
        for (Course next : selected.getCourses()) {
            System.out.println("\n" + next.getCourseName() + "\t"
                    + next.getSemester() + "\tCredits Worth: "
                    + next.getCredits() + "\tStudent's Grade is: "
                    + next.getGrade() + "\tCourse Average is: "
                    + next.getCourseAverage());
        }
    }

    // EFFECTS: prints Transcript of selected time period to the screen
    private void printTranscript(StudentTranscript selected) {
        System.out.println("Student Transcript for: " + selected.getStudentName() + "\t" + selected.getStudentID());
        for (Course next : selected.getCourses()) {
            System.out.println("\n" + next.getCourseName() + "\t"
                    + next.getSemester() + "\tCredits Worth: "
                    + next.getCredits() + "\tStudent's Grade is: "
                    + next.getGrade() + "\tCourse Average is: "
                    + next.getCourseAverage());
        }
        printAwards(myTranscript);
        printMoney(myTranscript);
        System.out.println();
    }

    // EFFECTS: prints all award information to the screen
    private void printAwards(StudentTranscript myTranscript) {
        System.out.println("\nYour Awards are\n" + myTranscript.getAwardsInformation());
    }

    // EFFECTS: prints the total number of prizes won to the screen
    private void printMoney(StudentTranscript myTranscript) {
        System.out.println("\nYou won " + myTranscript.getAllMoney() + " in total.");
    }

    // EFFECTS: saves my transcript to file
    protected void saveStudentTranscript() {
        try {
            jsonWriter.open();
            jsonWriter.write(myTranscript);
            jsonWriter.close();
            System.out.println("Saved " + myTranscript.getStudentName() + " to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads my transcript from file
    protected void loadStudentTranscript() {
        try {
            myTranscript = jsonReader.read();
            System.out.println("Loaded " + myTranscript.getStudentName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }








}
