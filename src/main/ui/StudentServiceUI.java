package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Award;
import model.EventLog;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import model.Course;
import model.StudentTranscript;

//represents GUI student service application
public class StudentServiceUI extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/studentTranscript.json";

    private StudentTranscript st;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("File");

    private JPanel courses = new JPanel();
    private JLabel course;
    private JPanel awards = new JPanel();
    private JLabel award;
    private JPanel studentTranscript = new JPanel();
    private JLabel transcript;

    private JLabel courseNameLabel = new JLabel("Course name");
    private JLabel courseSemesterLabel = new JLabel("Course Semester");
    private JLabel courseCreditLabel = new JLabel("Course credit");
    private JLabel studentGradeLabel = new JLabel("Course final grade");
    private JLabel courseAverageLabel = new JLabel("Course Average");

    private JLabel awardNameLabel = new JLabel("Award name");
    private JLabel yearWinLabel = new JLabel("Year win Award");
    private JLabel awardPrizeLabel = new JLabel("Award prize");

    private JButton addCourse = new JButton("Add Course");
    private JButton removeCourse = new JButton("Remove Course");
    private JButton showOverallCourses = new JButton("Show overall courses");
    private JButton findCourseBySemester = new JButton("Find by semester");

    private JButton addAward = new JButton("Add Award");
    private JButton removeAward = new JButton("Remove Award");
    private JButton showAwards = new JButton("Show awards");

    private JButton showOverallTranscript = new JButton("Show overall transcript");
    private JButton showTranscriptBySemester = new JButton("Find by semester");

    private JButton calculateOverallGpa = new JButton("Overall GPA");
    private JButton gpaBySemester = new JButton("GPA by semester");

    private JTextField courseNameTextField = new JTextField("", 10);
    private JTextField courseSemesterTextField = new JTextField("", 10);
    private JTextField courseCreditTextField = new JTextField("", 10);
    private JTextField studentGradeTextField = new JTextField("", 10);
    private JTextField courseAverageTextField = new JTextField("", 10);

    private JTextField awardNameTextField = new JTextField("", 10);
    private JTextField yearWinTextField = new JTextField("", 10);
    private JTextField awardPrizeTextField = new JTextField("", 10);

    // EFFECTS: runs the student service application
    public StudentServiceUI() throws FileNotFoundException {
        super("Student Service Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        String studentName = JOptionPane.showInputDialog("Enter your name");
//        String studentId = JOptionPane.showInputDialog("Enter your student ID");
        String studentId = getStudentId();

        st = new StudentTranscript(studentName, Integer.parseInt(studentId));

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        addMenu();
        addPanel();
        addCourseInfo();
        addCourseBottom();
        addAwardBottom();
        addTranscriptBottom();

        // Add a WindowListener to perform actions when the window is closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
            }
        });

        setVisible(true);
    }

    // EFFECTS: Prints the event log to the console, indicating that the window is closing
    // The event log includes each event's string representation.
    private void handleWindowClosing() {
        System.out.println("Window is closing. Event log:");

        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: Prompts the user to enter a valid student ID, and returns the valid student ID.
    private static String getStudentId() {
        String input;
        boolean validInput = false;

        do {
            try {
                input = JOptionPane.showInputDialog("Enter your student ID");

                // Validate input using regular expression (allow only numeric input)
                if (input != null && input.matches("\\d+")) {
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid input. Please enter numeric characters only.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Invalid input. Please enter a valid numeric value.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                input = null;
            }
        } while (!validInput);

        return input;
    }

    // MODIFIES: This
    // EFFECTS: Sets background colors for the panels, sets the layout to null,
    // adds the panels to the frame with specific bounds.
    private void addPanel() {

        courses.setBackground(new Color(229, 229, 129));
        awards.setBackground(new Color(144, 238, 144));
        studentTranscript.setBackground(new Color(141, 208, 232));

        setLayout(null);

        studentTranscript.setBounds(0, 0, WIDTH, HEIGHT / 3);
        courses.setBounds(0, HEIGHT / 3, WIDTH / 2, 2 * HEIGHT / 3);
        awards.setBounds(WIDTH / 2, HEIGHT / 3, WIDTH / 2, 2 * HEIGHT / 3);

        add(studentTranscript);
        add(courses);
        add(awards);
    }

    // MODIFIES: This
    // EFFECTS: Sets up and adds a menu to the frame's menu bar with "Save," "Load," and "Show" options.
    // Configures mnemonics, accelerators, and action listeners for each menu item.
    private void addMenu() {
        this.setJMenuBar(menuBar);
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("Save");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("save");
        menuItem.addActionListener(new DoSave());
        menu.add(menuItem);

        menuItem = new JMenuItem("Load");
        menuItem.setMnemonic(KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("load");
        menuItem.addActionListener(new DoLoad());
        menu.add(menuItem);

        menu = new JMenu("show");
        menu.setMnemonic(KeyEvent.VK_N);
        menuBar.add(menu);

        menuItem = new JMenuItem("School Picture");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.addActionListener(new DisplayPicture());
        menu.add(menuItem);

    }

    // MODIFIES: st, JSON_STORE
    // EFFECTS: Save the current student transcript (st) to a JSON file (JSON_STORE)
    public class DoSave implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            if ("save".equals(a.getActionCommand())) {
                try {
//                        StudentTranscript oldSt = jsonReader.read();
                    System.out.println("Loaded " + st.getStudentName() + " from " + JSON_STORE);
//
//                        for (Course c: oldSt.getCourses()) {
//                            st.addCourse(c);
//                        }
                    jsonWriter.open();
                    jsonWriter.write(st);
                    jsonWriter.close();
                    System.out.println("Saved " + st.getStudentName() + " to " + JSON_STORE);
                } catch (IOException e) {
                    System.out.println("Unable to write to file: " + JSON_STORE);
                }
            }
        }
    }

    // EFFECTS: Load the current student transcript (st) from a JSON file (JSON_STORE)
    public class DoLoad implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            if ("load".equals(a.getActionCommand())) {
                try {
                    st = jsonReader.read();
                    System.out.println("Loaded " + st.getStudentName() + " from " + JSON_STORE);
                } catch (IOException e) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                }
            }
        }
    }

    // EFFECTS: Displays a school picture using a JFrame and JLabel.
    public class DisplayPicture implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String desktopPath = "data";
            JFrame pictureFrame = new JFrame("School Picture");
            pictureFrame.setSize(1500, 1200);

            JLabel pictureLabel = new JLabel();
            ImageIcon imageIcon = new ImageIcon(desktopPath + "/UBC.jpeg");
            pictureLabel.setIcon(imageIcon);
            pictureFrame.getContentPane().add(pictureLabel, BorderLayout.CENTER);
            pictureFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pictureFrame.setVisible(true);
        }
    }

    // MODIFIES: addCourse, removeCourse, showOverallCourses, findCourseBySemester, course, courses
    // EFFECTS: Configures action commands for buttons,
    // sets up course-related labels and text fields in the courses panel
    private void addCourseInfo() {
        addCourse.setActionCommand("addCourse");
        removeCourse.setActionCommand("removeCourse");
        showOverallCourses.setActionCommand("showOverallCourses");
        findCourseBySemester.setActionCommand("findCourseBySemester");

        course = new JLabel("Course");
        course.setForeground(Color.WHITE);
        course.setFont(new Font("Arial", Font.BOLD, 30));
        courses.add(course);

        courses.add(courseNameLabel);
        courses.add(courseNameTextField);
        courses.add(courseSemesterLabel);
        courses.add(courseSemesterTextField);
        courses.add(courseCreditLabel);
        courses.add(courseCreditTextField);
        courses.add(studentGradeLabel);
        courses.add(studentGradeTextField);
        courses.add(courseAverageLabel);
        courses.add(courseAverageTextField);
    }

    // MODIFIES: courses
    // EFFECTS: Adds course-related buttons to the courses panel and assigns corresponding actions
    private void addCourseBottom() {
        courses.add(addCourse);
        courses.add(removeCourse);
        courses.add(showOverallCourses);
        courses.add(findCourseBySemester);

        addCourseAction();
        removeCourseAction();
        showOverallCoursesAction();
        findCourseBySemesterAction();
    }

    // MODIFIES: st
    // EFFECTS: Assigns an action to the addCourse button to add a course to the student transcript (st)
    private void addCourseAction() {
        addCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = courseNameTextField.getText();
                    String semester = courseSemesterTextField.getText();
                    int credit = Integer.parseInt(courseCreditTextField.getText());
                    int grade = Integer.parseInt(studentGradeTextField.getText());
                    int avg = Integer.parseInt(courseAverageTextField.getText());
                    Course c = new Course(name, semester, credit, grade, avg);
                    st.addCourse(c);

                    JOptionPane.showMessageDialog(null, "Course added successfully",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numeric values",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // MODIFIES: st
    // EFFECTS: Assigns an action to the removeCourse button to remove a course from the student transcript (st)
    private void removeCourseAction() {
        removeCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = courseNameTextField.getText();
                boolean courseRemoved = false;

                for (Course next : st.getCourses()) {
                    if (name.equals(next.getCourseName())) {
                        st.removeCourse(next);
                        courseRemoved = true;
                        break;
                    }
                }
                if (courseRemoved) {
                    JOptionPane.showMessageDialog(
                            null, "Course removed successfully", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            null, "Course not found", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // EFFECTS: Assigns an action to the showOverallCourses button to display course information in a new JFrame
    private void showOverallCoursesAction() {
        showOverallCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Course Information");
                frame.setSize(400, 400);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                DefaultListModel<String> listModel = new DefaultListModel<>();

                for (Course next : st.getCourses()) {
                    listModel.addElement(next.toString());
                }
                JList<String> courseList = new JList<>(listModel);
                JScrollPane scrollPane = new JScrollPane(courseList);
                scrollPane.setPreferredSize(new Dimension(400, 600));
                frame.add(scrollPane);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    // EFFECTS: Assigns an action to the findCourseBySemester button
    // to display course information for a specific semester in a new JFrame
    private void findCourseBySemesterAction() {
        findCourseBySemester.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Course Information");
                frame.setSize(400, 400);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                DefaultListModel<String> listModel = new DefaultListModel<>();

                String semester = JOptionPane.showInputDialog("Enter the semester you want to choose: ");
                for (Course next : st.getCourses()) {
                    if (semester.equals(next.getSemester())) {
                        listModel.addElement(next.toString());
                    }
                }
                JList<String> courseList = new JList<>(listModel);
                JScrollPane scrollPane = new JScrollPane(courseList);
                scrollPane.setPreferredSize(new Dimension(400, 600));
                frame.add(scrollPane);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    // MODIFIES: addAward, removeAward, showAwards, award, awards
    // EFFECTS: Configures action commands for buttons,
    // sets up award-related labels and text fields in the awards panel
    private void addAwardBottom() {
        addAward.setActionCommand("addAward");
        removeAward.setActionCommand("removeAward");
        showAwards.setActionCommand("showAwards");

        award = new JLabel("Award");
        award.setForeground(Color.WHITE);
        award.setFont(new Font("Arial", Font.BOLD, 30));
        awards.add(award);

        awards.add(awardNameLabel);
        awards.add(awardNameTextField);
        awards.add(yearWinLabel);
        awards.add(yearWinTextField);
        awards.add(awardPrizeLabel);
        awards.add(awardPrizeTextField);

        awards.add(addAward);
        awards.add(removeAward);
        awards.add(showAwards);

        addAwardAction();
        removeAwardAction();
        showAwardsAction();
    }

    // MODIFIES: st
    // EFFECTS: Assigns an action to the addAward button to add an award to the student transcript (st)
    private void addAwardAction() {
        addAward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = awardNameTextField.getText();
                    int year = Integer.parseInt(yearWinTextField.getText());
                    int prize = Integer.parseInt(awardPrizeTextField.getText());
                    Award aw = new Award(name, year, prize);
                    st.addAward(aw);

                    JOptionPane.showMessageDialog(null, "Award added successfully",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid values",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // MODIFIES: st
    // EFFECTS: Assigns an action to the removeAward button to remove an award from the student transcript (st)
    private void removeAwardAction() {
        removeAward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = awardNameTextField.getText();
                boolean awardRemoved = false;
                for (Award next : st.getAwards()) {
                    if (name.equals(next.getAwardName())) {
                        st.removeAward(next);
                        awardRemoved = true;
                        break;
                    }
                }
                if (awardRemoved) {
                    JOptionPane.showMessageDialog(null, "Award removed successfully",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Award not found", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // EFFECTS: Assigns an action to the showAwards button to display award information in a new JFrame
    private void showAwardsAction() {
        showAwards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Award Information");
                frame.setSize(400, 400);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                DefaultListModel<String> listModel = new DefaultListModel<>();

                for (Award next : st.getAwards()) {
                    listModel.addElement(next.toString());
                }
                JList<String> awardList = new JList<>(listModel);
                JScrollPane scrollPane = new JScrollPane(awardList);
                scrollPane.setPreferredSize(new Dimension(400, 600));
                frame.add(scrollPane);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    // MODIFIES: showOverallTranscript, showTranscriptBySemester, calculateOverallGpa,
    // gpaBySemester, transcript, studentTranscript
    // EFFECTS: Configures action commands for buttons, sets up labels,
    // and buttons related to the transcript in the studentTranscript panel
    private void addTranscriptBottom() {

        showOverallTranscript.setActionCommand("showOverallTranscript");
        showTranscriptBySemester.setActionCommand("showTranscriptBySemester");
        calculateOverallGpa.setActionCommand("calculateOverallGpa");
        gpaBySemester.setActionCommand("gpaBySemester");

        transcript = new JLabel("Transcript");
        transcript.setForeground(Color.WHITE);
        transcript.setFont(new Font("Arial", Font.BOLD, 30));
        studentTranscript.add(transcript);
        studentTranscript.add(showOverallTranscript);
        studentTranscript.add(showTranscriptBySemester);

        JLabel gpa = new JLabel("GPA calculator");
        gpa.setForeground(Color.WHITE);
        gpa.setFont(new Font("Arial", Font.BOLD, 30));
        studentTranscript.add(gpa);
        studentTranscript.add(calculateOverallGpa);
        studentTranscript.add(gpaBySemester);

        showOverallTranscriptAction();
        showTranscriptBySemesterAction();
        calculateOverallGpaAction();
        gpaBySemesterAction();
    }

    // EFFECTS: Assigns an action to the showOverallTranscript button
    // to display the overall transcript in a new JFrame
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void showOverallTranscriptAction() {
        showOverallTranscript.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTranscript();
            }
        });
    }

    private void showTranscript() {
        JFrame frame = new JFrame("Your Transcript");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        DefaultListModel<String> listModel = new DefaultListModel<>();

        listModel.addElement(st.getStudentName());
        listModel.addElement(String.valueOf(st.getStudentID()));
        listModel.addElement("\n");

        for (Course next : st.getCourses()) {
            listModel.addElement(next.toString());
        }
        for (Award next : st.getAwards()) {
            listModel.addElement(next.toString());
        }
        JList<String> transcriptList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(transcriptList);
        scrollPane.setPreferredSize(new Dimension(400, 600));

        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    // EFFECTS: Assigns an action to the showTranscriptBySemester button to display the transcript
    // for a specific semester in a new JFrame
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void showTranscriptBySemesterAction() {
        showTranscriptBySemester.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Your Transcript");
                frame.setSize(400, 400);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);

                DefaultListModel<String> listModel = new DefaultListModel<>();

                listModel.addElement(st.getStudentName());
                listModel.addElement(String.valueOf(st.getStudentID()));
                listModel.addElement("\n");

                String semester = JOptionPane.showInputDialog("Enter the semester you want to choose: ");
                for (Course next : st.getCourses()) {
                    if (semester.equals(next.getSemester())) {
                        listModel.addElement(next.toString());
                    }
                }
                for (Award next : st.getAwards()) {
                    listModel.addElement(next.toString());
                }

                JList<String> transcriptList = new JList<>(listModel);
                JScrollPane scrollPane = new JScrollPane(transcriptList);
                scrollPane.setPreferredSize(new Dimension(400, 600));

                frame.add(scrollPane);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    // EFFECTS: Assigns an action to the calculateOverallGpa button to display the overall GPA in a new JFrame
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void calculateOverallGpaAction() {
        calculateOverallGpa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Your Overall GPS");
                frame.setSize(400, 400);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);

                DefaultListModel<String> listModel = new DefaultListModel<>();

                listModel.addElement(st.getStudentName());
                listModel.addElement(String.valueOf(st.getStudentID()));
                listModel.addElement("\n");

                int allGrades = 0;
                int allCredits = 0;

                if (!st.getCourses().isEmpty()) {
                    for (Course next : st.getCourses()) {
                        allGrades += next.getGrade() * next.getCredits();
                        allCredits += next.getCredits();
                    }

                    double result = (double) allGrades / allCredits;
                    listModel.addElement(String.valueOf((int) (result + 0.5)));
                }

                JList<String> transcriptList = new JList<>(listModel);
                JScrollPane scrollPane = new JScrollPane(transcriptList);
                scrollPane.setPreferredSize(new Dimension(400, 600));

                frame.add(scrollPane);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    // EFFECTS: Assigns an action to the gpaBySemester button to
    // display the GPA for a specific semester in a new JFrame
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void gpaBySemesterAction() {
        gpaBySemester.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Your Overall GPA");
                frame.setSize(400, 400);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);

                DefaultListModel<String> listModel = new DefaultListModel<>();
                List<Course> bySemester = new ArrayList<>();

                listModel.addElement(st.getStudentName());
                listModel.addElement(String.valueOf(st.getStudentID()));
                listModel.addElement("\n");

                String semester = JOptionPane.showInputDialog("Enter the semester you want to choose: ");
                if (semester != null) {
                    for (Course next : st.getCourses()) {
                        if (semester.equals(next.getSemester())) {
                            bySemester.add(next);
                        }
                    }

                    int allGrades = 0;
                    int allCredits = 0;

                    if (!bySemester.isEmpty()) {
                        for (Course next : bySemester) {
                            allGrades += next.getGrade() * next.getCredits();
                            allCredits += next.getCredits();
                        }

                        double result = (double) allGrades / allCredits;
                        listModel.addElement(String.valueOf((int) (result + 0.5)));
                    }

                    JList<String> transcriptList = new JList<>(listModel);
                    JScrollPane scrollPane = new JScrollPane(transcriptList);
                    scrollPane.setPreferredSize(new Dimension(400, 600));

                    frame.add(scrollPane);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                }
            }
        });
    }

    // represents main
    public static void main(String[] args) {
        try {
            new StudentServiceUI();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                    null, "Unable to run application: file not found", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}