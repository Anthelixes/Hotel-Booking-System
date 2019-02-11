package database;

import java.io.Serializable;
import java.io.*;
import java.util.*;


import java.sql.*;

public class Client implements Serializable{
	private int clientID;
	private String nume;
	private String prenume;
	private String CNP;
	private String telefon;
	
	public Client() {}
	public Client(int id, String nume, String prenume, String CNP, String telefon) {
		this.clientID = id;
		this.nume = nume;
		this.prenume = prenume;
		this.CNP = CNP;
		this.telefon = telefon;
	}
	public int getClientID() {
		return clientID;
	}
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public String getPrenume() {
		return prenume;
	}
	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}
	public String getCNP() {
		return CNP;
	}
	public void setCNP(String cNP) {
		CNP = cNP;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}	
}