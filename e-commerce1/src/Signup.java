import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Signup extends JDialog {
    private JTextField fullnameTF;
    private JTextField phoneNumberfld;
    private JTextField addressfld;
    private JTextField emailfld;
    private JTextField usernamefld;
    private JPasswordField ConfirmpasswordFld;
    private JPasswordField passwordFld;
    private JButton SIGNUPButton;
    private JLabel fullnamefld;
    private JButton backbtn;
    private JTextField IDfld;
    private JPanel SignupPannel;

    public Signup(JFrame parent) {
        super(parent);
        setTitle("SIGN UP");
        setContentPane(SignupPannel);
        setMinimumSize(new Dimension(750, 600));
        setModal(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        pack();
        SIGNUPButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = IDfld.getText();
                String fullname = fullnameTF.getText();
                String username = usernamefld.getText();
                String email = emailfld.getText();
                String phoneNumber = phoneNumberfld.getText();
                String address = addressfld.getText();
                String password = String.valueOf(passwordFld.getPassword());
                String confirmpassword = String.valueOf(ConfirmpasswordFld.getPassword());

                if (ID.isEmpty() || fullname.isEmpty() || username.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!password.equals(confirmpassword)) {
                    JOptionPane.showMessageDialog(null,
                            "confirm password does not match",
                            "try again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyclick", "root", "");
                    String sql = "INSERT INTO users (ID, fullname, username, email, phoneNumber, address, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, ID);
                    preparedStatement.setString(2, fullname);
                    preparedStatement.setString(3, username);
                    preparedStatement.setString(4, email);
                    preparedStatement.setString(5, phoneNumber);
                    preparedStatement.setString(6, address);
                    preparedStatement.setString(7, password);

                    int addedRows = preparedStatement.executeUpdate();

                    if (addedRows > 0) {
                        JOptionPane.showMessageDialog(null, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Clear the fields after successful registration
                        IDfld.setText("");
                        fullnameTF.setText("");
                        usernamefld.setText("");
                        emailfld.setText("");
                        phoneNumberfld.setText("");
                        addressfld.setText("");
                        passwordFld.setText("");
                    }
                    preparedStatement.close();
                    conn.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while registering.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
        backbtn.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                goTologin();
            }

            private void goTologin() {
                // Assuming you have a class for your login page, e.g., LoginPage
                login loginPage = new login(null);

                // Assuming you want to close the current window
                Window window = SwingUtilities.getWindowAncestor(Signup.this);
                window.dispose();

                // Set the login page visible
                loginPage.setVisible(true);
            }
        });
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Signup signup = new Signup(null);
            signup.setVisible(true);
        });
        System.out.println("true");
    }
}


