package org.ace.accounting.expense.Entity;

public enum Monthly {
	January(1), February(2), March(3), April(4), May(5), June(6), July(7), August(8), September(9), October(10), November(11), December(12);

	private int value;

	private Monthly(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}	
//	January("January"), February("February"), March("March"), April("April"), May("May"), June("June"), July("July"), August("August"), September("September"), October("October"), November("November"), December("December");
//
//	private String value;
//
//	private Monthly(String value) {
//		this.value = value;
//	}
//
//	public String getValue() {
//		return value;
//	}
//
//	public void setValue(String value) {
//		this.value = value;
//	}
//}

