package org.ace.accounting.web.system;

import java.sql.Time;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.accounting.expense.Entity.Budget;
import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Iservices.IExpenseBudgetService;
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.accounting.system.branch.Branch;
import org.ace.accounting.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.ParamId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "ManageExpenseBudgetActionBean")
@ViewScoped
public class ManageExpenseBudgetActionBean extends BaseBean {

//	@ManagedProperty(value = "#{ExpenseService}")
//	private IExpenseService expenseService;

//	public void setExpenseService(IExpenseService expenseService) {
//		this.expenseService = expenseService;
//	}

	@ManagedProperty(value = "#{ExpenseBudgetService}")
	private IExpenseBudgetService expenseBudgetService;

	public void setExpenseBudgetService(IExpenseBudgetService expenseBudgetService) {
		this.expenseBudgetService = expenseBudgetService;
	}

	private String timevalue;
	private Budget currentbudget;
	private boolean iseditMode;
	private boolean showOverwriteDialog;
	private double budgetAmount;
	private int timescope;
	private String currentUserId;
	private List<Budget> budgetsList;
	private User user;

	@PostConstruct
	public void init() {
		user = (User) getParam(ParamId.LOGIN_USER);
		setCurrentUserId(user.getId());
		createNewBudget();
		loadBudgets();
		showOverwriteDialog = false;
	}

	private void createNewBudget() {
		currentbudget = new Budget();
	}

	public void saveBudget() {
		boolean isExist = expenseBudgetService.findCategory(currentbudget.getCategory().getId(), timevalue, currentUserId, timescope);
		if (isExist) {
			PrimeFaces.current().executeScript("PF('overwriteDialog').show()");
		} else {
			actualSaveBudget();
		}
	}

	public void updateBudget() {

	}

	public void cancelBudget() {
		resetForm();
		iseditMode = false;
	}
	
	public void openEditBudget(Budget b) {
		this.currentbudget = b;
		iseditMode = true;
	}
	

	private void loadBudgets() {
		budgetsList = expenseBudgetService.fineAllBudgets(currentUserId);
	}

	public void confirmOverwrite() {
		actualSaveBudget();
		showOverwriteDialog = false;
	}

	public void cancelOverwrite() {
		showOverwriteDialog = false;
	}

	public void actualSaveBudget() {
		System.out.println("in actual save method");
		if("monthly".equals(timevalue)) {
			currentbudget.setMonthlyLimit(budgetAmount);
		}else if("yearly".equals(timevalue)) {
			currentbudget.setYearlyLimit(budgetAmount);
		}
		currentbudget.setYearScope(timescope);
		currentbudget.setUser(user);
		expenseBudgetService.saveBudget(currentbudget, timevalue);
		loadBudgets();
	}

	public void deleteBudget() {
		
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

	public double getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

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

	public int getTimescope() {
		return timescope;
	}

	public void setTimescope(int timescope) {
		this.timescope = timescope;
	}

}
