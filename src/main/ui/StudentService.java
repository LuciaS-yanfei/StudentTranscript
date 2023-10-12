package ui;

import model.Award;
import model.Course;
import model.StudentTranscript;

import java.util.Scanner;

public class StudentService {
    private StudentTranscript myTranscript;
    private Scanner input;

    // EFFECTS: runs the student service application
    public StudentService() {
        runStudentService();
    }

    private void runStudentService() {
        boolean keepGoing = true;
        String command = null;

        init();

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
    // EFFECTS: initializes accounts
    private void init() {
        myTranscript = new StudentTranscript("Lucia", 68849942);

        Award a1 = new Award("Contest1st", 2022, 5000);
        Award a2 = new Award("Scholarship1", 2023, 8000);
        Course c1 = new Course("CPSC210","2023W1",4,100, 90);
        Course c2 = new Course("CPSC110","2022W1",4,90, 80);

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
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Add a course to my transcript
    private void doAddCourse() {
        input = new Scanner(System.in);
        System.out.print("Enter the course name you want to add (eg. CPSC210) ");
        String courseName = input.next();
        System.out.print("Enter the semester you took this course (eg. 2022S1) ");
        String courseSemester = input.next();
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
        String courseName = input.next();
        for (Course next: myTranscript.getCourses()) {
            if (courseName.equals(next.getCourseName())) {
                myTranscript.removeCourse(next);
                System.out.println("\nCourse successfully removed! ");
                break;
            } else {
                System.out.println("\nCourse is not in the Transcript! ");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Add an award to my transcript
    private void doAddAward() {
        input = new Scanner(System.in);
        System.out.print("Enter the Award name you want to add (no spaces) ");
        String awardName = input.next();
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
        String awardName = input.next();
        for (Award next: myTranscript.getAwards()) {
            if (awardName.equals(next.getAwardName())) {
                myTranscript.removeAward(next);
                System.out.println("\nAward successfully removed! ");
                break;
            } else {
                System.out.println("\nAward is not in the Transcript! ");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: get GPA of selected time period
    private void doGpaCalculator() {
        StudentTranscript selected = selectPeriod();
        printGPA(selected);
    }

    // MODIFIES: this
    // EFFECTS: get Course Information of selected time period
    private void doCourseInformation() {
        StudentTranscript selected = selectPeriod();
        printCourseInformation(selected);
    }

    // MODIFIES: this
    // EFFECTS: get Course Information of selected time period
    private void doTranscript() {
        StudentTranscript selected = selectPeriod();
        printTranscript(selected);
    }

    // MODIFIES: this
    // EFFECTS: get AwardList
    private void doAwardList() {
        printAwards(myTranscript);
        printMoney(myTranscript);
    }

    // EFFECTS: prompts user to select chequing or savings account and returns it
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
            selectSemester = input.next();
            StudentTranscript semesterTranscript = myTranscript.getTranscriptBySemester(selectSemester);
            return semesterTranscript;
        }
    }

    // EFFECTS: prints GPA of selected time period to the screen
    private void printGPA(StudentTranscript selected) {
        System.out.println("Your GPA is " + selected.getAverage());
    }

    // EFFECTS: prints Course Information of selected time period to the screen
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

    // EFFECTS: prints GPA of selected time period to the screen
    private void printAwards(StudentTranscript myTranscript) {
        System.out.println("\nYour Awards are\n" + myTranscript.getAwardsInformation());
    }

    private void printMoney(StudentTranscript myTranscript) {
        System.out.println("\nYou won " + myTranscript.getAllMoney() + " in total.");
    }




}
