package org.ace.accounting.expense.validator;

import javax.annotation.Resource;

import org.ace.accounting.common.validation.ValidationResult;
import org.ace.accounting.expense.Entity.Budget;
import org.ace.accounting.expense.Entity.GlobalBudget;
import org.ace.accounting.expense.Iservices.IExpenseBudgetService;
import org.springframework.stereotype.Service;

@Service(value = "GlobalBudgetValidator")
public class GlobalBudgetValidator implements IGlobalBudgetValidator<GlobalBudget> {

	@Resource(name = "ExpenseBudgetService")
	private IExpenseBudgetService expenseBudgetService;

	@Override
	public ValidationResult validate(GlobalBudget globalBudget, String timevalue, String userid) {
		ValidationResult result = new ValidationResult();
		String formId = "budgetAddForm";

		if (globalBudget == null) {
			result.addErrorMessage(formId, "Global Budget is required.");
			return result;
		}

		if (timevalue == null || timevalue.isEmpty()) {
			result.addErrorMessage(formId + ":period", "Period is required.");
		}

		if (globalBudget.getYearScope() == null) {
			result.addErrorMessage(formId + ":scopePanel", "Year is required.");
		}

//		    <!-- Validation for monthly global budget -->
		if ("monthly".equals(timevalue)) {
			if (globalBudget.getMonthScope() == null) {
				result.addErrorMessage(formId + ":monthSelect", "Month is required.");
			}
			if (globalBudget.getMonthlyLimit() == null) {
				result.addErrorMessage(formId + ":amountsPanel", "Monthly Global Budget is required.");
			} else if (globalBudget.getMonthlyLimit() <= 0) {
				result.addErrorMessage(formId + ":amountsPanel", "Monthly Global Budget must be greater than 0.");
			} else {
				// --- Find total of all category budgets in that month ---
				double totalCategoryMonthly = expenseBudgetService.findTotalAllCategoryBudgetForMonth(userid,
						globalBudget.getMonthScope(), globalBudget.getYearScope());
				if (globalBudget.getMonthlyLimit() < totalCategoryMonthly) {
					result.addErrorMessage(formId + ":amountsPanel",
							"Monthly Global Budget (" + globalBudget.getMonthlyLimit() + " MMK) "
									+ "must be greater than total category budgets (" + totalCategoryMonthly
									+ " MMK).");
				}
			}
		}

		// ---- Validation for yearly global budget ----
		if ("yearly".equals(timevalue)) {
			if (globalBudget.getYearlyLimit() == null) {
				result.addErrorMessage(formId + ":amountsPanel", "Yearly Global Budget is required.");
			} else if (globalBudget.getYearlyLimit() <= 0) {
				result.addErrorMessage(formId + ":amountsPanel", "Yearly Global Budget must be greater than 0.");
			} else {
				// --- Find total of all category budgets in that year ---
				double totalCategoryYearly = expenseBudgetService.findTotalAllCategoryBudgetForYear(userid,
						globalBudget.getYearScope());
				if (globalBudget.getYearlyLimit() < totalCategoryYearly) {
					result.addErrorMessage(formId + ":amountsPanel",
							"Yearly Global Budget (" + globalBudget.getYearlyLimit() + " MMK) "
									+ "must be greater than total category budgets (" + totalCategoryYearly + " MMK).");
				}
			}
		}

//		GlobalBudget existBudget = expenseBudgetService.findIndenticalGlobalBudget(globalBudget);
//		if (existBudget != null) {
//			result.addErrorMessage(formId, "The global budget is already exist.");
//		}
		return result;
	}

	@Override
	public ValidationResult validate1(GlobalBudget globalBudget, String timevalue, String userid) {
		ValidationResult result = new ValidationResult();
		String formId = "budgetAddForm";

		if (globalBudget == null) {
			result.addErrorMessage(formId, "Global Budget is required.");
			return result;
		}

		if (timevalue == null || timevalue.isEmpty()) {
			result.addErrorMessage(formId + ":period", "Period is required.");
		}

		if (globalBudget.getYearScope() == null) {
			result.addErrorMessage(formId + ":scopePanel", "Year is required.");
		}

//		    <!-- Validation for monthly global budget -->
		if ("monthly".equals(timevalue)) {
			if (globalBudget.getMonthScope() == null) {
				result.addErrorMessage(formId + ":monthSelect", "Month is required.");
			}
			if (globalBudget.getMonthlyLimit() == null) {
				result.addErrorMessage(formId + ":amountsPanel", "Monthly Global Budget is required.");
			} else if (globalBudget.getMonthlyLimit() <= 0) {
				result.addErrorMessage(formId + ":amountsPanel", "Monthly Global Budget must be greater than 0.");
			} else {
				// --- Find total of all category budgets in that month ---
				double totalCategoryMonthly = expenseBudgetService.findTotalAllCategoryBudgetForMonth(userid,
						globalBudget.getMonthScope(), globalBudget.getYearScope());
				if (globalBudget.getMonthlyLimit() < totalCategoryMonthly) {
					result.addErrorMessage(formId + ":amountsPanel",
							"Monthly Global Budget (" + globalBudget.getMonthlyLimit() + " MMK) "
									+ "must be greater than total category budgets (" + totalCategoryMonthly
									+ " MMK).");
				}
			}
		}

		// ---- Validation for yearly global budget ----
		if ("yearly".equals(timevalue)) {
			if (globalBudget.getYearlyLimit() == null) {
				result.addErrorMessage(formId + ":amountsPanel", "Yearly Global Budget is required.");
			} else if (globalBudget.getYearlyLimit() <= 0) {
				result.addErrorMessage(formId + ":amountsPanel", "Yearly Global Budget must be greater than 0.");
			} else {
				// --- Find total of all category budgets in that year ---
				double totalCategoryYearly = expenseBudgetService.findTotalAllCategoryBudgetForYear(userid,
						globalBudget.getYearScope());
				if (globalBudget.getYearlyLimit() < totalCategoryYearly) {
					result.addErrorMessage(formId + ":amountsPanel",
							"Yearly Global Budget (" + globalBudget.getYearlyLimit() + " MMK) "
									+ "must be greater than total category budgets (" + totalCategoryYearly + " MMK).");
				}
			}
		}


//		GlobalBudget existBudget = expenseBudgetService.findIndenticalGlobalBudget(globalBudget);
//		if (existBudget != null) {
//			result.addErrorMessage(formId, "The global budget is already exist.");
//		}
//		
		return result;
	}

}
