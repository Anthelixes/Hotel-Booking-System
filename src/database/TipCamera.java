package database;

public class TipCamera {
	private int tipId;
	private String denumire;
	
	public TipCamera(int tipId, String denumire) {
		setTipId(tipId);
		setDenumire(denumire);
	}
	
	public int getTipId() {
		return tipId;
	}
	public void setTipId(int tipId) {
		this.tipId = tipId;
	}
	public String getDenumire() {
		return denumire;
	}
	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}
	
	
}
