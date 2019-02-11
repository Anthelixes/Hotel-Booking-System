package database;

import java.sql.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class logIn extends JFrame {
	private welcome welcome;
	private JPanel contentPane;
	private JTextField user;
	private JPasswordField pass;
	Connection conn;
	Statement stmt;

	static logIn frame;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new logIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	public void setWelcome(welcome welcome) {
		this.welcome = welcome;
	}

	public JTextField getUser() {
		return user;
	}
	public void setUser(JTextField user) {
		this.user = user;
	}
	public logIn() {
		connect();
		frame();
	}
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbhotel", "root", "");
			stmt = conn.createStatement();
		}catch(Exception e) {
			System.out.println(e);
			}		
	}
	
	public void frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 466, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLoginPage = new JLabel("Login page");
		lblLoginPage.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblLoginPage.setBounds(148, 28, 243, 41);
		contentPane.add(lblLoginPage);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(186, 106, 93, 21);
		contentPane.add(lblUsername);
		
		user = new JTextField();
		user.setBounds(148, 153, 131, 36);
		contentPane.add(user);
		user.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(184, 202, 83, 22);
		contentPane.add(lblPassword);
		
		pass = new JPasswordField();
		pass.setBounds(148, 235, 131, 36);
		contentPane.add(pass);
		
		JButton btnLog = new JButton("Login");
		btnLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String sql = "SELECT * FROM users WHERE username = '" + user.getText() + "' AND password = '" + pass.getText().toString()+"'";
					ResultSet rs = stmt.executeQuery(sql);
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, "Login Successfully! Welcome, " + user.getText());
						welcome w1 = new welcome();
						w1.setVisible(true);
						frame.setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(null, "Parola sau nume incorect!");
					}
					conn.close();
				}catch(Exception e) {
					System.out.println(e);
					}
			}
		});
		btnLog.setBounds(170, 313, 97, 25);
		contentPane.add(btnLog);
	}
}