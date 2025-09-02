package org.ace.accounting.expense.Entity;

public enum PaymentType {
	CASH("Cash"),CARD("Card"),Online("Online"),BANK_TRANSFER("Bank Transfer");
	
	private String label;

	private PaymentType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
