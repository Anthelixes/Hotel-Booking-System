package database;

public class BeneficiuCamera extends Beneficiu {
	Client client;
	float cost;
	
	BeneficiuCamera(){
		
	}
	BeneficiuCamera(Client client, float cost){
		setClient(client);
		setCost(cost);
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	
}
