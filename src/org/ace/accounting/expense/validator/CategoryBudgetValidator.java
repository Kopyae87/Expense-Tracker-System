package org.ace.accounting.expense.validator;

import org.ace.accounting.common.validation.ValidationResult;
import org.ace.accounting.expense.Entity.Budget;
import org.springframework.stereotype.Service;

@Service(value = "CategoryBudgetValidator")
public class CategoryBudgetValidator implements IBudgetValidator<Budget> {

	@Override
	public ValidationResult validate(Budget budget, String timevalue) {
		ValidationResult result = new ValidationResult();
		String formId = "budgetAddForm";

		if (budget == null) {
			result.addErrorMessage(formId, "Budget is required.");
			return result;
		}

		if(timevalue == null || timevalue.isEmpty()) {
			result.addErrorMessage(formId + ":period", "Period is required.");

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

		if (budget.getYearScope() == null) {
			result.addErrorMessage(formId + ":scopePanel", "Year scope is required.");
		}

		/*
		 * if (budget.getMonthScope() == null) { result.addErrorMessage(formId +
		 * ":monthSelect", "Month scope is required."); }
		 */

		return result;
	}

}
