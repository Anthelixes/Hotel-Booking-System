package database;

public class Camera extends TipCamera {
	int cameraId;
	float pret;
	
	public Camera(int tipId, String denumire) {
		super(tipId, denumire);
		// TODO Auto-generated constructor stub
	}
	public Camera() {
		super(0, "");
	}
	public Camera(int tipId, String denumire, int cameraId, float pret) {
		super(tipId, denumire);
		setCameraId(cameraId);
		setPret(pret);
	}
	public int getCameraId() {
		return cameraId;
	}
	public void setCameraId(int cameraId) {
		this.cameraId = cameraId;
	}
	public float getPret() {
		return pret;
	}
	public void setPret(float pret) {
		this.pret = pret;
	}
}
