package logic.entity;

public enum PaymentMethod {
	TRANSFER, PAYPAL, DEBIT;

	public String toLowerCase() {
		return this.name().toLowerCase();
	}
}
