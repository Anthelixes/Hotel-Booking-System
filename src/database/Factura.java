package database;

import java.sql.Date;

public class Factura {
	Client client;
	Camera camera;
	Date checkIn;
	Date checkOut;
	private int facturaId;
	private int nrNopti;
	
	Factura(Client client, Camera camera, Date checkIn, Date dataFactura, int facturaId, int nrNopti){
		setClient(client);
		setCamera(camera);
		setCheckIn(checkIn);
		setCheckOut(checkOut);
		setFacturaId(facturaId);
		setNrNopti(nrNopti);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		boolean test = checkOut.after(checkIn);
		if(test)
			this.checkOut = checkOut;
	}

	public int getFacturaId() {
		return facturaId;
	}

	public void setFacturaId(int facturaId) {
		this.facturaId = facturaId;
	}

	public int getNrNopti() {
		return nrNopti;
	}

	public void setNrNopti(int nrNopti) {
		this.nrNopti = nrNopti;
	}
	
	
}
