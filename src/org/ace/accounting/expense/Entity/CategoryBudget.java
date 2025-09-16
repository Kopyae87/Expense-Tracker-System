package org.ace.accounting.expense.Entity;

public class CategoryBudget {
	private String categoryName;
	private double spent;
	private double limit;
	private int percentSpent;

	public CategoryBudget(String categoryName, double spent, double limit, int percentSpent) {
		this.categoryName = categoryName;
		this.spent = spent;
		this.limit = limit;
		this.percentSpent = percentSpent;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public double getSpent() {
		return spent;
	}

	public double getLimit() {
		return limit;
	}

	public int getPercentSpent() {
		return percentSpent;
	}

	
}
