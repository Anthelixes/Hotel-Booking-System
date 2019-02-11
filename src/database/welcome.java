package database;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JScrollPane;
import com.toedter.calendar.JDateChooser;
import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;
import javax.swing.UIManager;
import javax.swing.JCheckBox;

public class welcome extends JFrame implements ActionListener{
	
	Connection conn;
	Statement stmt;
	PreparedStatement ps;
	ResultSet rs = null;
	private JButton btnInsert, btnSearch, btnAfisareClienti, btnAdaugareClient;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JCheckBox chckbxParcare, chckbxRoomService, chckbxSpalatorie, chckbxFaxcopiator;
	private JDateChooser dateCheckIn, dateCheckOut;
	private JLabel lblNumeClient, lblPrenumeClient, lblCamera, lblCnp, lblTelefon;
	private JPanel contentPane, panel, panelBeneficii;
	private JRadioButton rdbtnSingle, rdbtnTwin;
	private JScrollPane scrollPane, scrollPaneClient;
	private JTable table, tableClient;
	private JTextField txtCamera, textFieldPren, textFieldNume, textFieldCnp, textFieldTelefon;
	
	public welcome() {
		connection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(312, 13, 535, 249);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tip", "Nr Camera", "Pret"
			}
		));
		
		dateCheckOut = new JDateChooser();
		dateCheckOut.setBounds(26, 152, 252, 22);
		contentPane.add(dateCheckOut);
		
		dateCheckIn = new JDateChooser();
		dateCheckIn.setBounds(26, 117, 252, 22);
		contentPane.add(dateCheckIn);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				ArrayList<Camera> data = new ArrayList<Camera>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				tableModel.setRowCount(0);
				String query = "SELECT DISTINCT C.tipID, C.cameraID, C.pret\r\n" + 
						"FROM camera C\r\n" + 
						"WHERE C.tipID LIKE '" + (rdbtnSingle.isSelected() ? "1" : (rdbtnTwin.isSelected() ? "2" : "")) + "' \r\n" + 
						"AND C.cameraID NOT IN (SELECT f.cameraID\r\n" + 
						"FROM factura F INNER JOIN camera A \r\n" + 
						" ON f.cameraID = a.cameraID\r\n" + 
						"WHERE (f.checkIn <= '" + sdf.format(dateCheckIn.getDate()) + "' AND f.checkOut >= '" + sdf.format(dateCheckIn.getDate()) + "')\r\n" + 
						"	OR (f.checkIn < '" + sdf.format(dateCheckOut.getDate()) + "' AND f.checkOut >= '" + sdf.format(dateCheckOut.getDate()) + "')\r\n" + 
						"	OR ('" + sdf.format(dateCheckIn.getDate()) + "' <= f.checkIn AND f.checkOut >= '" + sdf.format(dateCheckIn.getDate()) + "')\r\n" + 
						")";
				try {
					PreparedStatement ps = conn.prepareStatement(query);
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						Camera camera = new Camera();
						camera.setCameraId(rs.getInt("cameraID"));
						camera.setTipId(rs.getInt("tipID"));
						camera.setPret(rs.getFloat("pret"));
						data.add(camera);
						
						tableModel.addRow(new Object[] {camera.getTipId(), camera.getCameraId(), camera.getPret()});
					}
				}catch(Exception ex) {
					System.out.println(ex);
				}
				scrollPane.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int row = table.getSelectedRow();
						txtCamera.setText(Integer.toString(data.get(row).getCameraId()));
						visibleItems(data.get(row).getCameraId());
					}
				});
			}
		});
		btnSearch.setBounds(106, 187, 97, 25);
		contentPane.add(btnSearch);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Tip camera", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(26, 21, 252, 83);
		contentPane.add(panel);
		
		rdbtnSingle = new JRadioButton("Single");
		rdbtnSingle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(rdbtnSingle);
		panel.add(rdbtnSingle);
		
		rdbtnTwin = new JRadioButton("Twin");
		rdbtnTwin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(rdbtnTwin);
		panel.add(rdbtnTwin);
		
		txtCamera = new JTextField();
		txtCamera.setBounds(92, 380, 111, 22);
		contentPane.add(txtCamera);
		txtCamera.setColumns(10);
		
		lblCamera = new JLabel("Camera:");
		lblCamera.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCamera.setBounds(26, 373, 77, 34);
		contentPane.add(lblCamera);
		
		btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//INSERT TO DABABASE
				int facturaId = 0;
				int idAux = 0;
				String query = "SELECT * FROM client WHERE CNP LIKE '" + textFieldCnp.getText() + "'";
				try {
					PreparedStatement ps = conn.prepareStatement(query);
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						idAux = rs.getInt("clientID");
					}
				}catch(Exception ex) {
					System.out.println(ex);
				}
				query = "INSERT INTO factura (cameraID, clientID, checkIn, checkOut, nrNopti) VALUES (?,?,?,?,?)";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setInt(1,(int)Integer.parseInt(txtCamera.getText()));
					ps.setInt(2, idAux);
					
					ps.setString(3, sdf.format(dateCheckIn.getDate()));
					ps.setString(4, sdf.format(dateCheckOut.getDate()));
					
					java.sql.Date sqldate1 = new java.sql.Date(dateCheckIn.getDate().getTime());
					java.sql.Date sqldate2 = new java.sql.Date(dateCheckOut.getDate().getTime());
					ps.setLong(5, getDateDiff(sqldate1, sqldate2,TimeUnit.DAYS));
					ps.executeUpdate();
					query = "SELECT facturaID FROM factura WHERE checkIn = '" + sdf.format(dateCheckIn.getDate()) +"' AND checkOut = '" + sdf.format(dateCheckOut.getDate()) + "' ";
					ps = conn.prepareStatement(query);
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						facturaId = rs.getInt("facturaID");
					}
					JOptionPane.showMessageDialog(null, "Inserare adaugata cu succes!");
				}catch(Exception ex) {
					System.out.println(ex);
				}
				
				try {
					if(facturaId != 0) {
						//adaugare beneficii
						checkBoxItem(facturaId);
					}
					else
						JOptionPane.showMessageDialog(null, "Eroare la factura id!");
				} catch(Exception ex) {
					System.out.println(ex);
				}
			}
		});
		btnInsert.setBounds(92, 415, 111, 25);
		contentPane.add(btnInsert);
		
		lblNumeClient = new JLabel("Nume client:");
		lblNumeClient.setBounds(26, 243, 85, 16);
		contentPane.add(lblNumeClient);
		
		lblPrenumeClient = new JLabel("Prenume client:");
		lblPrenumeClient.setBounds(26, 276, 97, 16);
		contentPane.add(lblPrenumeClient);
		
		textFieldPren = new JTextField();
		textFieldPren.setBounds(162, 275, 116, 22);
		contentPane.add(textFieldPren);
		textFieldPren.setColumns(10);
		
		textFieldNume = new JTextField();
		textFieldNume.setBounds(162, 240, 116, 22);
		contentPane.add(textFieldNume);
		textFieldNume.setColumns(10);
		
		scrollPaneClient = new JScrollPane();
		scrollPaneClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableClient.getSelectedRow();
				showItem(index);
			}
		});
		scrollPaneClient.setBounds(312, 280, 535, 122);
		contentPane.add(scrollPaneClient);
		
		tableClient = new JTable();
		scrollPaneClient.setViewportView(tableClient);
		tableClient.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nume", "Prenume", "CNP", "Telefon"
			}
		));
		
		lblCnp = new JLabel("CNP:");
		lblCnp.setBounds(26, 313, 56, 16);
		contentPane.add(lblCnp);
		
		textFieldCnp = new JTextField();
		textFieldCnp.setBounds(162, 310, 116, 22);
		contentPane.add(textFieldCnp);
		textFieldCnp.setColumns(10);
		
		lblTelefon = new JLabel("Telefon:");
		lblTelefon.setBounds(26, 344, 56, 16);
		contentPane.add(lblTelefon);
		
		textFieldTelefon = new JTextField();
		textFieldTelefon.setBounds(162, 345, 116, 22);
		contentPane.add(textFieldTelefon);
		textFieldTelefon.setColumns(10);
		
		btnAfisareClienti = new JButton("Afisare clienti");
		btnAfisareClienti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					showClientTable();
				}catch(Exception e) {
					System.out.println(e);
				}
			}
		});
		btnAfisareClienti.setBounds(521, 415, 121, 25);
		contentPane.add(btnAfisareClienti);
		
		btnAdaugareClient = new JButton("Adaugare client");
		btnAdaugareClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = "INSERT INTO client (nume, prenume, CNP, telefon) VALUES (?,?,?,?)";
				try {
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1,textFieldNume.getText());
					ps.setString(2, textFieldPren.getText());
					if(textFieldCnp.getText().length() == 13)
						ps.setString(3, textFieldCnp.getText());
					else
						JOptionPane.showMessageDialog(null, "Trebuie introdus un CNP valid!");
					ps.setString(4, textFieldTelefon.getText());
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Inserare client adaugata cu succes!");
				}catch(Exception ex) {
					System.out.println(ex);
				}
			}
		});
		btnAdaugareClient.setBounds(312, 415, 142, 25);
		contentPane.add(btnAdaugareClient);
		
		panelBeneficii = new JPanel();
		panelBeneficii.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Beneficii camera", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBeneficii.setBounds(859, 13, 191, 249);
		contentPane.add(panelBeneficii);
		panelBeneficii.setLayout(null);
		
		chckbxParcare = new JCheckBox("Parcare");
		chckbxParcare.setBounds(8, 23, 73, 25);
		panelBeneficii.add(chckbxParcare);
		chckbxParcare.setVisible(false);
		
		chckbxRoomService = new JCheckBox("Room service");
		chckbxRoomService.setBounds(8, 57, 105, 25);
		panelBeneficii.add(chckbxRoomService);
		chckbxRoomService.setVisible(false);
		
		chckbxSpalatorie = new JCheckBox("Spalatorie");
		chckbxSpalatorie.setBounds(8, 87, 87, 25);
		panelBeneficii.add(chckbxSpalatorie);
		chckbxSpalatorie.setVisible(false);
		
		chckbxFaxcopiator = new JCheckBox("Fax/Copiator");
		chckbxFaxcopiator.setBounds(8, 117, 101, 25);
		panelBeneficii.add(chckbxFaxcopiator);
		chckbxFaxcopiator.setVisible(false);
	}
	public void checkBoxItem(int nrFactura) {
		connection();
		ArrayList<Beneficiu> beneficii = new ArrayList<Beneficiu>();
		
		if(chckbxRoomService.isSelected()) {
			Beneficiu beneficiu = new Beneficiu();
			beneficiu.setBeneficiuId(1);
			beneficiu.setDescriere(chckbxRoomService.getText());
			System.out.println(beneficiu.getDescriere());
			beneficii.add(beneficiu);
		}
		if(chckbxSpalatorie.isSelected()) {
			Beneficiu beneficiu = new Beneficiu();
			beneficiu.setBeneficiuId(2);
			beneficiu.setDescriere(chckbxSpalatorie.getText());
			System.out.println(beneficiu.getDescriere());
			beneficii.add(beneficiu);
		}
		if(chckbxFaxcopiator.isSelected()) {
			Beneficiu beneficiu = new Beneficiu();
			beneficiu.setBeneficiuId(3);
			beneficiu.setDescriere(chckbxFaxcopiator.getText());
			System.out.println(beneficiu.getDescriere());
			beneficii.add(beneficiu);
		}
		if(chckbxParcare.isSelected()) {
			Beneficiu beneficiu = new Beneficiu();
			beneficiu.setBeneficiuId(4);
			beneficiu.setDescriere(chckbxParcare.getText());
			System.out.println(beneficiu.getDescriere());
			beneficii.add(beneficiu);
		}
		
		String query = "INSERT INTO facturabeneficii (facturaID, beneficiuID, Info) VALUES (?,?,?)";
		for(int i = 0; i < beneficii.size(); i++) {
			try {
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setInt(1, nrFactura);
				ps.setInt(2, beneficii.get(i).getBeneficiuId());
				ps.setString(3, null);
				ps.executeUpdate();
			}catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	public void visibleItems(int nrCamera) {
		connection();
		PreparedStatement ps;
		ResultSet rs;
		
		chckbxParcare.setVisible(false);
		chckbxRoomService.setVisible(false);
		chckbxSpalatorie.setVisible(false);
		chckbxFaxcopiator.setVisible(false);
		
		try {
			String query = "SELECT A.beneficiuID FROM beneficiicamera A WHERE A.cameraID = " + nrCamera + "";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getInt("beneficiuID") == 1) {
					chckbxRoomService.setVisible(true);
				}
				if(rs.getInt("beneficiuID") == 2) {
					chckbxSpalatorie.setVisible(true);
				}
				if(rs.getInt("beneficiuID") == 3) {
					chckbxFaxcopiator.setVisible(true);
				}
				if(rs.getInt("beneficiuID") == 4) {
					chckbxParcare.setVisible(true);
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	//Display date in Client Table
		//Fill array with data
	public ArrayList<Client> getClientList(){
		ArrayList<Client> clientList = new ArrayList<Client>();
		
		connection();
		String query = "SELECT * FROM client";
		Statement st;
		ResultSet rs;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			Client client;
			
			while(rs.next()) {
				client = new Client(rs.getInt("clientID"), rs.getString("nume"), rs.getString("prenume"), rs.getString("CNP"), rs.getString("telefon"));
				clientList.add(client);
			}
		}catch(SQLException ex) {
			System.out.println(ex);
		}
		return clientList;
	}
	
	public ArrayList<Camera> getCameraList(){
		ArrayList<Camera> cameraList = new ArrayList<Camera>();
		
		connection();
		String query = "SELECT * FROM camera";
		Statement st;
		ResultSet rs;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			Camera camera;
			
			while(rs.next()) {
				camera = new Camera(rs.getInt("cameraID"), "", rs.getInt("tipID"), rs.getFloat("pret"));
				cameraList.add(camera);
			}
		} catch (Exception e){
			System.out.println(e);
		}
		
		return cameraList;
	}
	//Populate the Client Table
	public void showClientTable() {
		connection();
		String query = "SELECT * FROM client";
		Statement st;
		ResultSet rs;
		ArrayList<Client> clientList = new ArrayList<Client>();
		DefaultTableModel model = (DefaultTableModel)tableClient.getModel();
		model.setRowCount(0);
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			Client client;
			
			while(rs.next()) {
				client = new Client(rs.getInt("clientID"), rs.getString("nume"), rs.getString("prenume"), rs.getString("CNP"), rs.getString("telefon"));
				clientList.add(client);
				model.addRow(new Object[] {client.getNume(), client.getPrenume(), client.getCNP(), client.getTelefon()});
			}
		}catch(SQLException ex) {
			System.out.println(ex);
		}
	}
	//Show data in inputs
	public void showItem(int index) {
		textFieldNume.setText(getClientList().get(index).getNume());
		textFieldPren.setText(getClientList().get(index).getPrenume());
		textFieldCnp.setText(getClientList().get(index).getCNP());
		textFieldTelefon.setText(getClientList().get(index).getTelefon());
	}
	
	public class Function{
		Connection conn;
		Statement stmt;
		PreparedStatement ps;
		ResultSet rs = null;
		public ResultSet find(String s) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbhotel", "root", "");
				stmt = conn.createStatement();
				ps = conn.prepareStatement("SELECT cameraID FROM camera WHERE tipID = 1");
				ps.setString(1, s);
				rs = ps.executeQuery();
			}catch(Exception e) {
				System.out.println(e);
			}
			return rs;
		}
	}
	public void connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbhotel", "root", "");
			stmt = conn.createStatement();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					welcome frame = new welcome();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}