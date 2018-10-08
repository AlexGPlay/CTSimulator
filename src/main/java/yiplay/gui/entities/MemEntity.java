package yiplay.gui.entities;

public class MemEntity {

	private int address;
	private int status;
	private String hexAddress;
	
	public MemEntity() {
		this(0,0);
	}
	
	public MemEntity(int direccion) {
		this(direccion,0);
	}
	
	public MemEntity(int direccion, int estado) {
		this.address = direccion;
		this.status = estado;
		this.hexAddress = Integer.toHexString(direccion).toUpperCase();
		while(hexAddress.length()<4)
			hexAddress = '0' + hexAddress;
		this.hexAddress = "0x" + hexAddress;
	}
	
	public void freeCell() {
		this.status = 0;
	}
	
	public void fillCell() {
		this.status = 1;
	}
	
	public boolean isFilled() {
		return this.status == 1;
	}
	
	public int getAddress() {
		return this.address;
	}
	
	public int getStatus() {
		return this.status;
	}

	public String getHexAddress() {
		return hexAddress;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
}
