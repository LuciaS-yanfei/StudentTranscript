//package ui;
//
//import javax.swing.*;\
//
//public class JOptionPaneMultiInput {
//    private JTextField studentName = new JTextField(5);
//    private JTextField studentId = new JTextField(5);
//    private JLabel nameLabel = new JLabel("Your name");
//    private JLabel idLabel = new JLabel("Your student ID:");
//
//    private JPanel infoPanel = new JPanel();
//    infoPanel.add(nameLabel);
//    infoPanel.add(studentName);
//    infoPanel.add(Box.createHorizontalStrut(15)); // a spacer
//    infoPanel.add(new JLabel("Your student ID:"));
//    infoPanel.add(studentId);
//
//    int result = JOptionPane.showConfirmDialog(null, myPanel,
//            "Student Information", JOptionPane.OK_CANCEL_OPTION);
//        if (result == JOptionPane.OK_OPTION) {
//        System.out.println("student name: " + studentName.getText());
//        System.out.println("student ID: " + studentId.getText());
//    }
//}
