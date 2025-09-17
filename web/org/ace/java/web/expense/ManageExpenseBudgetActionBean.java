package org.ace.java.web.expense;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.accounting.expense.Entity.Budget;
import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.GlobalBudget;
import org.ace.accounting.expense.Iservices.IExpenseBudgetService;
import org.ace.accounting.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.ParamId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "ManageExpenseBudgetActionBean")
@ViewScoped
public class ManageExpenseBudgetActionBean extends BaseBean {

	@ManagedProperty(value = "#{ExpenseBudgetService}")
	private IExpenseBudgetService expenseBudgetService;

	public void setExpenseBudgetService(IExpenseBudgetService expenseBudgetService) {
		this.expenseBudgetService = expenseBudgetService;
	}

	private String timevalue;
	private Budget currentbudget;
	private boolean iseditMode;
	private boolean showOverwriteDialog;
//	private double budgetAmount;
	private String currentUserId;
	private List<Budget> budgetsList;
	private List<Integer> yearsScopeList;
	private User user;

	/* global budget */
	private GlobalBudget globalBudget;
	private String globalTimeValue;
	
	@PostConstruct
	public void init() {
		user = (User) getParam(ParamId.LOGIN_USER);
		setCurrentUserId(user.getId());
		createNewBudget();
		loadBudgets();
		loadYearsAndMonthsList();
		showOverwriteDialog = false;
		globalBudget = new GlobalBudget();
	}

	private void createNewBudget() {
		currentbudget = new Budget();
	}

	public void saveBudget() {
		accordingToTimeValue();
		currentbudget.setUser(user);
		Budget existBudget = expenseBudgetService.findIndenticalBudget(currentbudget);
		if (existBudget != null) {
			currentbudget.setId(existBudget.getId());
			PrimeFaces.current().executeScript("PF('overwriteDialog').show()");
		} else {
			actualSaveBudget();
		}
	}

	public void loadYearsAndMonthsList() {
		yearsScopeList = new ArrayList<>();
		int currentyear = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = (currentyear - 24); i <= currentyear; i++) {
			yearsScopeList.add(i);
		}
		for (int j = (currentyear + 1); j <= (currentyear + 24); j++) {
			yearsScopeList.add(j);
		}
	}

	public void updateBudget() {
		expenseBudgetService.updateBudget(currentbudget);
		resetForm();
		timevalue = "";
	}

	public void deleteBudget(Budget budget) {
		expenseBudgetService.deleteBudget(budget);

		if (iseditMode && currentbudget.getId().equals(budget.getId())) {
			cancelBudget();
		}
		loadBudgets();
		System.out.println("delete succcessfully");
	}

	public void cancelBudget() {
		resetForm();
		iseditMode = false;
	}

	public void openEditBudget(Budget b) {
		this.currentbudget = b;
		if(b.getYearScope() != null && b.getMonthScope() == null) {
			timevalue = "yearly";
		}else if(b.getMonthScope() != null && b.getYearScope() == null) {
			timevalue = "monthly";
		}else if(b.getMonthScope() != null && b.getYearScope() != null) {
			timevalue = "both";
		}
		iseditMode = true;
	}

	private void loadBudgets() {
		budgetsList = new ArrayList<>();
		budgetsList = expenseBudgetService.fineAllBudgets(currentUserId);
	}

	public void confirmOverwrite() {
		expenseBudgetService.updateBudget(currentbudget);
		loadBudgets();
		resetForm();
		showOverwriteDialog = false;
	}

	public void cancelOverwrite() {
		showOverwriteDialog = false;
	}

	public void accordingToTimeValue() {
		System.out.println("in actual save method1");
//		if ("monthly".equals(timevalue)) {
//			currentbudget.setMonthlyLimit(budgetAmount);
//		} else if ("yearly".equals(timevalue)) {
//			currentbudget.setYearlyLimit(budgetAmount);
//		}
	}

	public void actualSaveBudget() {
		expenseBudgetService.saveBudget(currentbudget);
		loadBudgets();
		resetForm();
	}

	public void resetForm() {
		createNewBudget();
		timevalue = "";
	}

	public void returnCategory(SelectEvent event) {
		Category cat = (Category) event.getObject();
		currentbudget.setCategory(cat);
	}

	public String getTimevalue() {
		return timevalue;
	}

	public void setTimevalue(String timevalue) {
		this.timevalue = timevalue;
	}

	public Budget getCurrentbudget() {
		return currentbudget;
	}

	public void setCurrentbudget(Budget currentbudget) {
		this.currentbudget = currentbudget;
	}

	public Boolean getIseditMode() {
		return iseditMode;
	}

	public void setIseditMode(Boolean iseditMode) {
		this.iseditMode = iseditMode;
	}

	public Boolean getShowOverwriteDialog() {
		return showOverwriteDialog;
	}

	public void setShowOverwriteDialog(Boolean showOverwriteDialog) {
		this.showOverwriteDialog = showOverwriteDialog;
	}

//	public double getBudgetAmount() {
//		return budgetAmount;
//	}
//
//	public void setBudgetAmount(double budgetAmount) {
//		this.budgetAmount = budgetAmount;
//	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public List<Budget> getBudgetsList() {
		return budgetsList;
	}

	public void setBudgetsList(List<Budget> budgetsList) {
		this.budgetsList = budgetsList;
	}

	public void setIseditMode(boolean iseditMode) {
		this.iseditMode = iseditMode;
	}

	public void setShowOverwriteDialog(boolean showOverwriteDialog) {
		this.showOverwriteDialog = showOverwriteDialog;
	}

	public List<Integer> getYearsScopeList() {
		return yearsScopeList;
	}

	public void setYearsScopeList(List<Integer> yearsScopeList) {
		this.yearsScopeList = yearsScopeList;
	}

	
	public Month[] getMonths() {
		return Month.values();
	}

	public GlobalBudget getGlobalBudget() {
		return globalBudget;
	}

	public void setGlobalBudget(GlobalBudget globalBudget) {
		this.globalBudget = globalBudget;
	}

	public String getGlobalTimeValue() {
		return globalTimeValue;
	}

	public void setGlobalTimeValue(String globalTimeValue) {
		this.globalTimeValue = globalTimeValue;
	}

}
