import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login extends JDialog {
    private JPanel panel11;
    private JTextField emailfld;
    private JButton loginbtn;
    private JPasswordField passwordfld;
    private JLabel username;
    private JLabel password;
    private JButton registerbtn;

    public login(JFrame parent) {
        super(parent);
        setTitle("LOGIN");
        setContentPane(panel11);
        setMinimumSize(new Dimension(550, 400));
        setModal(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);


        loginbtn.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailfld.getText();
                String password = String.valueOf(passwordfld.getPassword());
                // Assuming you have a method to validate the login
                boolean loginSuccessful = validateLogin(email, password);
                if (loginSuccessful) {
                    home homePage = new home();

                    // Close the current window (assuming it's a JFrame)
                    Window window = SwingUtilities.getWindowAncestor(login.this);
                    window.dispose();

                    home.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Invalid email or password",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        registerbtn.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
// Get the current window
                Window currentWindow = SwingUtilities.windowForComponent(registerbtn);

                // Close the current window
                currentWindow.dispose();

                // Open the sign-up window
                Signup signUpFrame = new Signup(null);
                signUpFrame.setVisible(true);
            }


        });

    }
    private boolean validateLogin(String email, String password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyclick", "root", "");
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            boolean loginSuccessful = resultSet.next();

            resultSet.close();
            preparedStatement.close();
            conn.close();

            return loginSuccessful;

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while registering.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            login loginPage = new login(null);
            loginPage.setVisible(true);
        });
    }
}


