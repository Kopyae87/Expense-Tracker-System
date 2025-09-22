package org.ace.java.web.expense;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.accounting.common.validation.ErrorMessage;
import org.ace.accounting.common.validation.IDataValidator;
import org.ace.accounting.common.validation.ValidationResult;
import org.ace.accounting.expense.Entity.Budget;
import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.Entity.GlobalBudget;
import org.ace.accounting.expense.Iservices.IExpenseBudgetService;
import org.ace.accounting.expense.validator.IBudgetValidator;
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

	@ManagedProperty(value = "#{GlobalBudgetValidator}")
	private IBudgetValidator<GlobalBudget> globalBudgetValidator;

	public void setGlobalBudgetValidator(IBudgetValidator<GlobalBudget> globalBudgetValidator) {
		this.globalBudgetValidator = globalBudgetValidator;
	}

	@ManagedProperty(value = "#{CategoryBudgetValidator}")
	private IBudgetValidator<Budget> budgetValidator;

	public void setBudgetValidator(IBudgetValidator<Budget> budgetValidator) {
		this.budgetValidator = budgetValidator;
	}

	private String timevalue;
	private Budget currentbudget;
	private boolean iseditMode;
	private boolean showOverwriteDialog;
//	private double budgetAmount;
	private String currentUserId;
	private List<Budget> categoryBudgetsList;
	private List<Integer> yearsScopeList;
	private Budget existBudgetForOverwrite;
	private User user;

	/* global budget */
	private GlobalBudget globalBudget;
	private String globalTimeValue;
	private List<GlobalBudget> globalBudgetsList;
	private boolean isGlobalEditMode;
	private GlobalBudget existGlobalBudgetForOverwrite;

	@PostConstruct
	public void init() {
		user = (User) getParam(ParamId.LOGIN_USER);
		setCurrentUserId(user.getId());
		createNewBudget();
		loadBudgets();
		loadGlobalBudgets();
		loadYearsAndMonthsList();
		showOverwriteDialog = false;
		createNewGlobalBudget();
	}

	private void createNewBudget() {
		currentbudget = new Budget();
	}

	private void createNewGlobalBudget() {
		globalBudget = new GlobalBudget();
	}

	public void saveBudget() {
		try {
			System.out.println("in the update budget");
			ValidationResult result = budgetValidator.validate(currentbudget, timevalue);
			if (result.isVerified()) {
				accordingToTimeValue();
				currentbudget.setUser(user);
				Budget existBudget = expenseBudgetService.findIndenticalBudget(currentbudget);
				if (existBudget != null) {
					existBudgetForOverwrite = existBudget;
					PrimeFaces.current().executeScript("PF('overwriteDlg').show()");
				} else {
					actualSaveBudget();
				}
			} else {
				System.out.println("in error ");
				for (ErrorMessage e : result.getErrorMeesages()) {
					addErrorMessage(null, e.getErrorcode(), e.getParams());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		try {
			System.out.println("in the update budget");
			ValidationResult result = budgetValidator.validate(currentbudget, timevalue);
			if (result.isVerified()) {
				expenseBudgetService.updateBudget(currentbudget);
				resetForm();
				timevalue = "";
				addInfoMessage("Budget Update Successfully");
			} else {
				System.out.println("in error ");
				for (ErrorMessage e : result.getErrorMeesages()) {
					addErrorMessage(null, e.getErrorcode(), e.getParams());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteBudget(Budget budget) {
		try {
			expenseBudgetService.deleteBudget(budget);
			if (iseditMode && currentbudget.getId().equals(budget.getId())) {
				cancelBudget();
			}
			loadBudgets();
			System.out.println("delete succcessfully");
			addInfoMessage("Budget Delete Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void cancelBudget() {
		resetForm();
		iseditMode = false;
	}

	public void openEditBudget(Budget b) {
		this.currentbudget = b;
		if (b.getYearScope() != null && b.getMonthScope() == null) {
			timevalue = "yearly";
		} else if (b.getMonthScope() != null && b.getYearScope() == null) {
			timevalue = "monthly";
		} else if (b.getMonthScope() != null && b.getYearScope() != null) {
			timevalue = "both";
		}
		iseditMode = true;
	}

	private void loadBudgets() {
		categoryBudgetsList = new ArrayList<>();
		categoryBudgetsList = expenseBudgetService.fineAllBudgets(currentUserId);
	}

	private void loadGlobalBudgets() {
		globalBudgetsList = new ArrayList<>();
		globalBudgetsList = expenseBudgetService.fineAllGlobalBudgets(currentUserId);
	}

	public void confirmOverwrite() {
		if (existBudgetForOverwrite != null) {
			currentbudget.setId(existBudgetForOverwrite.getId());
			expenseBudgetService.updateBudget(currentbudget);
			existBudgetForOverwrite = null;
		}
		loadBudgets();
		resetForm();
		showOverwriteDialog = false;
	}

	public void cancelOverwrite() {
		currentbudget.setId(null);
		existBudgetForOverwrite = null;
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
		/*
		 * try { System.out.println("in the save budget"); ValidationResult result =
		 * budgetValidator.validate(currentbudget, timevalue); if (result.isVerified())
		 * {
		 */
		expenseBudgetService.saveBudget(currentbudget);
		loadBudgets();
		addInfoMessage("Budget Added Successfully");
		resetForm();
		/*
		 * } else { System.out.println("in error "); for (ErrorMessage e :
		 * result.getErrorMeesages()) { addErrorMessage(null, e.getErrorcode(),
		 * e.getParams()); } } } catch (Exception e) { e.printStackTrace(); }
		 */
	}

	public void resetForm() {
		createNewBudget();
		timevalue = "";
		existBudgetForOverwrite = null;
	}

	/* global budget */
	public void saveGlobalBudget() {
		try {
			System.out.println("in the update budget");
			ValidationResult result = globalBudgetValidator.validate(globalBudget, globalTimeValue);
			if (result.isVerified()) {
				accordingToGlobalTimeValue();
				globalBudget.setUser(user);
				System.out.println("globalbudget: " + globalBudget.getMonthScope() + globalBudget.getYearScope());
				GlobalBudget existBudget = expenseBudgetService.findIndenticalGlobalBudget(globalBudget);
				if (existBudget != null) {
					existGlobalBudgetForOverwrite = existBudget;
					PrimeFaces.current().executeScript("PF('globalOverwriteDlg').show()");
				} else {
					actualSaveGlobalBudget();
				}
			}else {
				for (ErrorMessage e : result.getErrorMeesages()) {
					addErrorMessage(null, e.getErrorcode(), e.getParams());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void updateGlobalBudget() {
		expenseBudgetService.updateGlobalBudget(globalBudget);
		resetGlobalForm();
		;
	}

	public void cancelGlobalBudget() {
		resetGlobalForm();
		isGlobalEditMode = false;
	}

	public void resetGlobalForm() {
		createNewGlobalBudget();
		globalTimeValue = "";
		isGlobalEditMode = false;
	}

	public void openEditGlobalBudget(GlobalBudget globalBudget) {
		this.globalBudget = globalBudget;
		if (globalBudget.getYearScope() != null && globalBudget.getMonthScope() == null) {
			globalTimeValue = "yearly";
		} else if (globalBudget.getMonthScope() != null && globalBudget.getYearScope() == null) {
			globalTimeValue = "monthly";
		} else if (globalBudget.getMonthScope() != null && globalBudget.getYearScope() != null) {
			globalTimeValue = "both";
		}
		isGlobalEditMode = true;
	}

	public void deleteGlobalBudget(GlobalBudget globalBudget) {
		expenseBudgetService.deleteGlobalBudget(globalBudget);

		if (iseditMode && currentbudget.getId().equals(globalBudget.getId())) {
			cancelGlobalBudget();
		}
		loadGlobalBudgets();
		System.out.println("global budget delete succcessfully");
	}

	public void actualSaveGlobalBudget() {
		expenseBudgetService.saveGlobalBudget(globalBudget);
		loadGlobalBudgets();
		resetGlobalForm();
	}

	public void confirmOverwriteGlobalBudget() {
		if (existGlobalBudgetForOverwrite != null) {
			globalBudget.setId(existGlobalBudgetForOverwrite.getId());
			expenseBudgetService.updateGlobalBudget(globalBudget);
			existGlobalBudgetForOverwrite = null;
		}
		loadGlobalBudgets();
		resetGlobalForm();
	}

	public void cancelOverwriteGlobalBudget() {
		globalBudget.setId(null);
		existGlobalBudgetForOverwrite = null;
		showOverwriteDialog = false;
	}

	public void accordingToGlobalTimeValue() {
		if ("yearly".equals(globalTimeValue)) {
			if (globalBudget.getYearScope() == null) {
				addErrorMessage(null, "Year is required for yearly budget");
				return;
			}
			globalBudget.setMonthScope(null); // clear month
		} else if ("monthly".equals(globalTimeValue)) {
			if (globalBudget.getYearScope() == null) {
				addErrorMessage(null, "Year is required for monthly budget");
				return;
			}
			if (globalBudget.getMonthScope() == null) {
				addErrorMessage(null, "Month is required for monthly budget");
				return;
			}
		} else if ("both".equals(globalTimeValue)) {
			if (globalBudget.getYearScope() == null || globalBudget.getMonthScope() == null) {
				addErrorMessage(null, "Year and Month are required for both");
				return;
			}
		}
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

	public List<Budget> getCategoryBudgetsList() {
		return categoryBudgetsList;
	}

	public void setCategoryBudgetsList(List<Budget> categorybudgetsList) {
		this.categoryBudgetsList = categorybudgetsList;
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

	public List<GlobalBudget> getGlobalBudgetsList() {
		return globalBudgetsList;
	}

	public void setGlobalBudgetsList(List<GlobalBudget> globalBudgetsList) {
		this.globalBudgetsList = globalBudgetsList;
	}

	public boolean isGlobalEditMode() {
		return isGlobalEditMode;
	}

	public void setGlobalEditMode(boolean isGlobalEditMode) {
		this.isGlobalEditMode = isGlobalEditMode;
	}

}
