package org.ace.accounting.expense.validator;

import java.util.Calendar;

import javax.annotation.Resource;

import org.ace.accounting.common.validation.ValidationResult;
import org.ace.accounting.expense.Entity.Budget;
import org.ace.accounting.expense.Iservices.IExpenseBudgetService;
import org.ace.accounting.expense.Iservices.IExpenseDashBoardService;
import org.springframework.stereotype.Service;

@Service(value = "CategoryBudgetValidator")
public class CategoryBudgetValidator implements IBudgetValidator<Budget> {

	@Resource(name = "ExpenseBudgetService")
	private IExpenseBudgetService expenseBudgetService;

	@Resource(name = "ExpenseDashBoardService")
	private IExpenseDashBoardService categoryBudgetService;

	@Override
	public ValidationResult validate(Budget budget, String timevalue, String userid) {
		ValidationResult result = new ValidationResult();
		String formId = "budgetAddForm";

		if (budget == null) {
			result.addErrorMessage(formId, "Budget is required.");
			return result;
		}

		if (budget.getCategory() == null) {
			result.addErrorMessage(formId + ":categoryId", "Category is required");
		}

		if (timevalue == null || timevalue.isEmpty()) {
			result.addErrorMessage(formId + ":period", "Period is required.");

		}

		if (budget.getYearScope() == null) {
			result.addErrorMessage(formId + ":scopePanel", "Year is required.");
		}

		if ("monthly".equals(timevalue) || "both".equals(timevalue)) {
			if (budget.getMonthScope() == null) {
				result.addErrorMessage(formId + ":monthSelect", "Month scope is required.");
			}
			if (budget.getMonthlyLimit() == null) {
				result.addErrorMessage(formId + ":amountsPanel", "Monthly Budget is required.");
			} else if (budget.getMonthlyLimit() <= 0) {
				result.addErrorMessage(formId + ":amountsPanel", "Monthly Budget must be greater than 0.");
			}
		}

		if ("yearly".equals(timevalue) || "both".equals(timevalue)) {
			if (budget.getYearlyLimit() == null) {
				result.addErrorMessage(formId + ":amountsPanel", "Yearly Budget is required.");
			} else if (budget.getYearlyLimit() <= 0) {
				result.addErrorMessage(formId + ":amountsPanel", "Yearly Budget must be greater than 0.");
			}
		}

		if ("both".equals(timevalue)) {
			if (budget.getMonthlyLimit() > budget.getYearlyLimit()) {
				result.addErrorMessage(formId, "Monthly Budget can't larger than Yearly Budget");
			}

		}

		double globalMonthlyLimit = 0;
		double globalYearlyLimit = 0;
		double totalCategoryMonthly = 0;
		double totalCategoryYearly = 0;
		if ("monthly".equals(timevalue) || "both".equals(timevalue) && budget.getMonthScope() != null) {
			globalMonthlyLimit = categoryBudgetService.findMonthlyBudget(userid, budget.getMonthScope(),
					budget.getYearScope());
		}

		if ("yearly".equals(timevalue) || "both".equals(timevalue)) {
			globalYearlyLimit = categoryBudgetService.findYearlyBudget(userid, budget.getYearScope());
		}

		// --- Total existing category budget for this period ---
		
//		  totalCategoryMonthly =
//		  expenseBudgetService.findTotalCategoryBudgetForMonth(userid,
//		  budget.getCategory().getId(), budget.getMonthScope(), budget.getYearScope());
//		  totalCategoryYearly =
//		  expenseBudgetService.findTotalCategoryBudgetForYear(userid,
//		  budget.getCategory().getId(), budget.getYearScope());
		 

		if ("monthly".equals(timevalue) || "both".equals(timevalue)) {
			if (budget.getMonthScope() != null) {
				totalCategoryMonthly = expenseBudgetService.findTotalCategoryBudgetForMonth(userid,
						budget.getCategory().getId(), budget.getMonthScope(), budget.getYearScope());
			}
		}

		if ("yearly".equals(timevalue) || "both".equals(timevalue)) {
			totalCategoryYearly = expenseBudgetService.findTotalCategoryBudgetForYear(userid,
					budget.getCategory().getId(), budget.getYearScope());
		}

		// Validate against global budget if it exists
		if (globalMonthlyLimit > 0 && budget.getMonthlyLimit() != null
				&& (totalCategoryMonthly + budget.getMonthlyLimit() > globalMonthlyLimit)) {
			result.addErrorMessage(formId + ":amountsPanel",
					"Monthly budget exceeds global limit (" + globalMonthlyLimit + ")");
		}

		if (globalYearlyLimit > 0 && budget.getYearlyLimit() != null
				&& (totalCategoryYearly + budget.getYearlyLimit() > globalYearlyLimit)) {
			result.addErrorMessage(formId + ":amountsPanel",
					"Yearly budget exceeds global limit (" + globalYearlyLimit + ")");
		}

		return result;
	}
		
	@Override
	public ValidationResult validate1(Budget budget, String timevalue, String userid) {
		ValidationResult result = new ValidationResult();
		String formId = "budgetAddForm";

		if (budget == null) {
			result.addErrorMessage(formId, "Budget is required.");
			return result;
		}

		if (budget.getCategory() == null) {
			result.addErrorMessage(formId + ":categoryId", "Category is required");
		}

		if (timevalue == null || timevalue.isEmpty()) {
			result.addErrorMessage(formId + ":period", "Period is required.");

		}

		if (budget.getYearScope() == null) {
			result.addErrorMessage(formId + ":scopePanel", "Year is required.");
		}

		if ("monthly".equals(timevalue) || "both".equals(timevalue)) {
			if (budget.getMonthScope() == null) {
				result.addErrorMessage(formId + ":monthSelect", "Month scope is required.");
			}
			if (budget.getMonthlyLimit() == null) {
				result.addErrorMessage(formId + ":amountsPanel", "Monthly Budget is required.");
			} else if (budget.getMonthlyLimit() <= 0) {
				result.addErrorMessage(formId + ":amountsPanel", "Monthly Budget must be greater than 0.");
			}
		}

		if ("yearly".equals(timevalue) || "both".equals(timevalue)) {
			if (budget.getYearlyLimit() == null) {
				result.addErrorMessage(formId + ":amountsPanel", "Yearly Budget is required.");
			} else if (budget.getYearlyLimit() <= 0) {
				result.addErrorMessage(formId + ":amountsPanel", "Yearly Budget must be greater than 0.");
			}
		}

		if ("both".equals(timevalue)) {
			if (budget.getMonthlyLimit() > budget.getYearlyLimit()) {
				result.addErrorMessage(formId, "Monthly Budget can't larger than Yearly Budget");
			}

		}

		double globalMonthlyLimit = 0;
		double globalYearlyLimit = 0;
		double totalCategoryMonthly = 0;
		double totalCategoryYearly = 0;
		if ("monthly".equals(timevalue) || "both".equals(timevalue) && budget.getMonthScope() != null) {
			globalMonthlyLimit = categoryBudgetService.findMonthlyBudget(userid, budget.getMonthScope(),
					budget.getYearScope());
		}

		if ("yearly".equals(timevalue) || "both".equals(timevalue)) {
			globalYearlyLimit = categoryBudgetService.findYearlyBudget(userid, budget.getYearScope());
		}

		// --- Total existing category budget for this period ---
		
//		  totalCategoryMonthly =
//		  expenseBudgetService.findTotalCategoryBudgetForMonth(userid,
//		  budget.getCategory().getId(), budget.getMonthScope(), budget.getYearScope());
//		  totalCategoryYearly =
//		  expenseBudgetService.findTotalCategoryBudgetForYear(userid,
//		  budget.getCategory().getId(), budget.getYearScope());
		 

		if ("monthly".equals(timevalue) || "both".equals(timevalue)) {
			if (budget.getMonthScope() != null) {
				totalCategoryMonthly = budget.getMonthlyLimit();
			}
		}

		if ("yearly".equals(timevalue) || "both".equals(timevalue)) {
			totalCategoryYearly = budget.getYearlyLimit();
		}

		// Validate against global budget if it exists
		if (globalMonthlyLimit > 0 && budget.getMonthlyLimit() != null
				&& (totalCategoryMonthly + budget.getMonthlyLimit() > globalMonthlyLimit)) {
			result.addErrorMessage(formId + ":amountsPanel",
					"Monthly budget exceeds global limit (" + globalMonthlyLimit + ")");
		}

		if (globalYearlyLimit > 0 && budget.getYearlyLimit() != null
				&& (totalCategoryYearly + budget.getYearlyLimit() > globalYearlyLimit)) {
			result.addErrorMessage(formId + ":amountsPanel",
					"Yearly budget exceeds global limit (" + globalYearlyLimit + ")");
		}

		return result;
	}

}
