package org.ace.accounting.expense.Entity;

public class BudgetDTO {
	private Integer yearScope;
	private Integer monthScope;
	private String categoryId;
	private String categoryName;
	private Double categoryMonthlyLimit;
	private Double categoryYearlyLimit;
	private Double globalMonthlyLimit;
	private Double globalYearlyLimit;
	private boolean hasCategoryBudget;
	private boolean hasGlobalBudget;

	public BudgetDTO(Integer yearScope, Integer monthScope, String categoryId, String categoryName,
			Double categoryMonthlyLimit, Double categoryYearlyLimit, Double globalMonthlyLimit,
			Double globalYearlyLimit, boolean hasCategoryBudget, boolean hasGlobalBudget) {
		super();
		this.yearScope = yearScope;
		this.monthScope = monthScope;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryMonthlyLimit = categoryMonthlyLimit;
		this.categoryYearlyLimit = categoryYearlyLimit;
		this.globalMonthlyLimit = globalMonthlyLimit;
		this.globalYearlyLimit = globalYearlyLimit;
		this.hasCategoryBudget = hasCategoryBudget;
		this.hasGlobalBudget = hasGlobalBudget;
	}

	public Integer getYearScope() {
		return yearScope;
	}

	public void setYearScope(Integer yearScope) {
		this.yearScope = yearScope;
	}

	public Integer getMonthScope() {
		return monthScope;
	}

	public void setMonthScope(Integer monthScope) {
		this.monthScope = monthScope;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Double getCategoryMonthlyLimit() {
		return categoryMonthlyLimit;
	}

	public void setCategoryMonthlyLimit(Double categoryMonthlyLimit) {
		this.categoryMonthlyLimit = categoryMonthlyLimit;
	}

	public Double getCategoryYearlyLimit() {
		return categoryYearlyLimit;
	}

	public void setCategoryYearlyLimit(Double categoryYearlyLimit) {
		this.categoryYearlyLimit = categoryYearlyLimit;
	}

	public Double getGlobalMonthlyLimit() {
		return globalMonthlyLimit;
	}

	public void setGlobalMonthlyLimit(Double globalMonthlyLimit) {
		this.globalMonthlyLimit = globalMonthlyLimit;
	}

	public Double getGlobalYearlyLimit() {
		return globalYearlyLimit;
	}

	public void setGlobalYearlyLimit(Double globalYearlyLimit) {
		this.globalYearlyLimit = globalYearlyLimit;
	}

	public boolean isHasCategoryBudget() {
		return hasCategoryBudget;
	}

	public void setHasCategoryBudget(boolean hasCategoryBudget) {
		this.hasCategoryBudget = hasCategoryBudget;
	}

	public boolean isHasGlobalBudget() {
		return hasGlobalBudget;
	}

	public void setHasGlobalBudget(boolean hasGlobalBudget) {
		this.hasGlobalBudget = hasGlobalBudget;
	}

}
