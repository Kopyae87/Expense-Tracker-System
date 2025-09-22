package org.ace.accounting.expense.validator;

import org.ace.accounting.common.validation.ValidationResult;
import org.ace.accounting.expense.Entity.GlobalBudget;
import org.springframework.stereotype.Service;

@Service(value = "GlobalBudgetValidator")
public class GlobalBudgetValidator implements IGlobalBudgetValidator<GlobalBudget>{

	@Override
	public ValidationResult validate(GlobalBudget budget, String timevalue, String userid) {
		ValidationResult result = new ValidationResult();
		String formId = "budgetAddForm";

		if (budget == null) {
			result.addErrorMessage(formId, "Budget is required.");
			return result;
		}

		if(timevalue == null || timevalue.isEmpty()) {
			result.addErrorMessage(formId + ":period", "Period is required.");

		}
		
		if (budget.getYearScope() == null) {
			result.addErrorMessage(formId + ":scopePanel", "Year is required.");
		}
		
		if ("yearly".equals(timevalue) || "both".equals(timevalue)) {
	        if (budget.getYearlyLimit() == null) {
	            result.addErrorMessage(formId + ":amountsPanel", "Yearly Budget is required.");
	        } else if (budget.getYearlyLimit() <= 0) {
	            result.addErrorMessage(formId + ":amountsPanel", "Yearly Budget must be greater than 0.");
	        }
	    }
		
	    if ("monthly".equals(timevalue) || "both".equals(timevalue)) {
	        if (budget.getMonthScope() == null) {
	            result.addErrorMessage(formId + ":monthSelect", "Month is required.");
	        }
	        if (budget.getMonthlyLimit() == null) {
	            result.addErrorMessage(formId + ":amountsPanel", "Monthly Budget is required.");
	        } else if (budget.getMonthlyLimit() <= 0) {
	            result.addErrorMessage(formId + ":amountsPanel", "Monthly Budget must be greater than 0.");
	        }
	    }

	    if("both".equals(timevalue)) {
	    	if(budget.getMonthlyLimit() > budget.getYearlyLimit()) {
	    		result.addErrorMessage(formId, "Monthly Budget can't larger than Yearly Budget");
	    	}
	    	
	    }
	   
		

		return result;
	}

	

}
