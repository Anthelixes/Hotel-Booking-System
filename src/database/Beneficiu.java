package database;

public class Beneficiu {
	private int beneficiuId;
	private String descriere;
	
	public Beneficiu() {
		
	}
	public Beneficiu(int beneficiuId, String descriere) {
		setBeneficiuId(beneficiuId);
		setDescriere(descriere);
	}

	public int getBeneficiuId() {
		return beneficiuId;
	}

	public void setBeneficiuId(int beneficiuId) {
		this.beneficiuId = beneficiuId;
	}

	public String getDescriere() {
		return descriere;
	}

	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}
	
}
