package org.ace.accounting.expense.validator;

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
		int count = 0;

		if (budget == null) {
			result.addErrorMessage(formId, "Budget is required.");
			return result;
		}

		if (budget.getCategory() == null) {
			result.addErrorMessage(formId + ":categoryId", "Category is required");
			count += 1;
		}

		if (timevalue == null || timevalue.isEmpty()) {
			result.addErrorMessage(formId + ":period", "Period is required.");
			count += 1;
		}

		if (budget.getYearScope() == null) {
			result.addErrorMessage(formId + ":scopePanel", "Year in Time Scope is required.");
			count += 1;
		}

		if ("monthly".equals(timevalue)) {
			if (budget.getMonthScope() == null) {
				result.addErrorMessage(formId + ":monthSelect", "Month in Time Scope is required.");
				count += 1;
			}
			if (budget.getMonthlyLimit() == null) {
				result.addErrorMessage(formId + ":amountsPanel", "Monthly Budget is required.");
				count += 1;
			} else if (budget.getMonthlyLimit() <= 0) {
				result.addErrorMessage(formId + ":amountsPanel", "Monthly Budget must be greater than 0.");
				count += 1;
			}
		}

		if ("yearly".equals(timevalue)) {
			if (budget.getYearlyLimit() == null) {
				result.addErrorMessage(formId + ":amountsPanel", "Yearly Budget is required.");
				count += 1;
			} else if (budget.getYearlyLimit() <= 0) {
				result.addErrorMessage(formId + ":amountsPanel", "Yearly Budget must be greater than 0.");
				count += 1;
			}
		}
		// first check if fields are null or not
		if (count > 0) {
			return result;
		}

//		double totalCategoryMonthly = 0;
//		double totalCategoryYearly = 0;
		/*
		 * if ("monthly".equals(timevalue) || budget.getMonthScope() != null) {
		 * globalMonthlyLimit = categoryBudgetService.findMonthlyBudget(userid,
		 * budget.getMonthScope(), budget.getYearScope()); globalYearlyLimit =
		 * categoryBudgetService.findYearlyBudget(userid, budget.getYearScope()); }
		 * 
		 * if ("yearly".equals(timevalue)) { globalYearlyLimit =
		 * categoryBudgetService.findYearlyBudget(userid, budget.getYearScope()); }
		 */

		// --- Total existing category budget for this period ---

//		totalCategoryMonthly = expenseBudgetService.findTotalCategoryBudgetForMonth(userid, //
//				budget.getCategory().getId(), budget.getMonthScope(), budget.getYearScope());
//		totalCategoryYearly = expenseBudgetService.findTotalCategoryBudgetForYear(userid, //
//				budget.getCategory().getId(), budget.getYearScope());
		
		double globalMonthlyLimit = 0;
		double globalYearlyLimit =0;
		double totalAllCategoriesMonthly  = 0;
		double totalAllCategoriesYearly  = 0;
		
		// --- Fetch global limits ---
		if ("monthly".equals(timevalue)) {
		    globalMonthlyLimit = categoryBudgetService.findMonthlyBudget(userid, budget.getMonthScope(), budget.getYearScope());
		    totalAllCategoriesMonthly = expenseBudgetService.findTotalAllCategoryBudgetForMonth(userid, budget.getMonthScope(), budget.getYearScope());
		    totalAllCategoriesMonthly = expenseBudgetService.findTotalAllCategoryBudgetForMonth(userid, budget.getMonthScope(), budget.getYearScope());
		}
		globalYearlyLimit = categoryBudgetService.findYearlyBudget(userid, budget.getYearScope());

		// fetch total of all categories for that period 
		totalAllCategoriesYearly = expenseBudgetService.findTotalAllCategoryBudgetForYear(userid, budget.getYearScope());

		// if updating, subtract current budget amount so we don’t double count
		if (budget.getId() != null) {
		    totalAllCategoriesMonthly -= budget.getMonthlyLimit() != null ? budget.getMonthlyLimit() : 0;
		    totalAllCategoriesYearly -= budget.getYearlyLimit() != null ? budget.getYearlyLimit() : 0;
		}

		// --- validate against global limits
		if (globalMonthlyLimit > 0 && budget.getMonthlyLimit() != null
		        && (totalAllCategoriesMonthly + budget.getMonthlyLimit() > globalMonthlyLimit)) {
		    result.addErrorMessage(formId + ":amountsPanel",
		            "Monthly category budget exceeds global monthly budget limit (" + globalMonthlyLimit + " MMK)");
		}

		if (globalYearlyLimit > 0 && budget.getYearlyLimit() != null
		        && (totalAllCategoriesYearly + budget.getYearlyLimit() > globalYearlyLimit)) {
		    result.addErrorMessage(formId + ":amountsPanel",
		            "Yearly Category budget exceeds global yearly budget limit (" + globalYearlyLimit + " MMK)");
		}
		return result;
	}

	@Override
	public ValidationResult validate1(Budget budget, String timevalue, String userid) {
		ValidationResult result = new ValidationResult();
		String formId = "budgetAddForm";
		int count = 0;

		if (budget == null) {
			result.addErrorMessage(formId, "Budget is required.");
			return result;
		}

		if (budget.getCategory() == null) {
			result.addErrorMessage(formId + ":categoryId", "Category is required");
			count += 1;
		}

		if (timevalue == null || timevalue.isEmpty()) {
			result.addErrorMessage(formId + ":period", "Period is required.");
			count += 1;
		}

		if (budget.getYearScope() == null) {
			result.addErrorMessage(formId + ":scopePanel", "Year is required.");
			count += 1;
		}

		if ("monthly".equals(timevalue) || "both".equals(timevalue)) {
			if (budget.getMonthScope() == null) {
				result.addErrorMessage(formId + ":monthSelect", "Month scope is required.");
				count += 1;
			}
			if (budget.getMonthlyLimit() == null) {
				result.addErrorMessage(formId + ":amountsPanel", "Monthly Budget is required.");
				count += 1;
			} else if (budget.getMonthlyLimit() <= 0) {
				result.addErrorMessage(formId + ":amountsPanel", "Monthly Budget must be greater than 0.");
				count += 1;
			}
		}

		if ("yearly".equals(timevalue) || "both".equals(timevalue)) {
			if (budget.getYearlyLimit() == null) {
				result.addErrorMessage(formId + ":amountsPanel", "Yearly Budget is required.");
				count += 1;
			} else if (budget.getYearlyLimit() <= 0) {
				result.addErrorMessage(formId + ":amountsPanel", "Yearly Budget must be greater than 0.");
				count += 1;
			}
		}

		if (count > 0) {
			return result;
		}

		double globalMonthlyLimit = 0;
		double globalYearlyLimit = 0;
		double totalAllCategoriesMonthly = 0;
		double totalAllCategoriesYearly = 0;

		// Fetch global limits
		if ("monthly".equals(timevalue) || "both".equals(timevalue)) {
		    globalMonthlyLimit = categoryBudgetService.findMonthlyBudget(userid, budget.getMonthScope(), budget.getYearScope());
		    totalAllCategoriesMonthly = expenseBudgetService.findTotalAllCategoryBudgetForMonth(userid, budget.getMonthScope(), budget.getYearScope());
		}

		if ("yearly".equals(timevalue) || "both".equals(timevalue)) {
		    globalYearlyLimit = categoryBudgetService.findYearlyBudget(userid, budget.getYearScope());
		    totalAllCategoriesYearly = expenseBudgetService.findTotalAllCategoryBudgetForYear(userid, budget.getYearScope());
		}

		// If updating, subtract current budget so we don’t double-count it
		if (budget.getId() != null) {
		    if (budget.getMonthlyLimit() != null) {
		        totalAllCategoriesMonthly -= budget.getMonthlyLimit();
		    }
		    if (budget.getYearlyLimit() != null) {
		        totalAllCategoriesYearly -= budget.getYearlyLimit();
		    }
		}
		// Validate against global budget if it exists
		if (globalMonthlyLimit > 0 && budget.getMonthlyLimit() != null
		        && (totalAllCategoriesMonthly + budget.getMonthlyLimit() > globalMonthlyLimit)) {
		    result.addErrorMessage(formId + ":amountsPanel",
		            "Monthly budget exceeds global budget limit (" + globalMonthlyLimit + " MMK)");
		}

		if (globalYearlyLimit > 0 && budget.getYearlyLimit() != null
		        && (totalAllCategoriesYearly + budget.getYearlyLimit() > globalYearlyLimit)) {
		    result.addErrorMessage(formId + ":amountsPanel",
		            "Yearly budget exceeds global budget limit (" + globalYearlyLimit + " MMK)");
		}

//		Budget existBudget = expenseBudgetService.findIndenticalBudget(budget);
//		if (existBudget != null) {
//			result.addErrorMessage(formId, "The budget is already exist.");
//		}
		return result;
	}

}
