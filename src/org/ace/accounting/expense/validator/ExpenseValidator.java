package org.ace.accounting.expense.validator;

import org.ace.accounting.common.validation.IDataValidator;
import org.ace.accounting.common.validation.ValidationResult;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.system.branch.Branch;
import org.springframework.stereotype.Service;

@Service(value = "ExpenseValidator")
public class ExpenseValidator implements IDataValidator<Expense> {

	@Override
	public ValidationResult validate(Expense expense, boolean transaction) {

		ValidationResult result = new ValidationResult();
		String formId = "addExpenseForm";

		if (expense == null) {
			result.addErrorMessage(formId, "Expense is required.");
			return result;
		}

		if (expense.getCategory() == null) {
			result.addErrorMessage(formId + ":category", "Category is required.");
		}

		if (expense.getExpenseDate() == null) {
			result.addErrorMessage(formId + ":expensedate", "Expense date is required.");
		}

		if (expense.getAmount() <= 0) {
			result.addErrorMessage(formId + ":amount", "Amount must be greater than 0.");
		}

		if (expense.getPaymentType() == null || expense.getPaymentType().trim().isEmpty()) {
			result.addErrorMessage(formId + ":paymenttype", "Payment type is required.");
		}

		if (expense.getDescription() == null || expense.getDescription().trim().isEmpty()) {
			result.addErrorMessage(formId + ":desc", "Description is required.");
		}

		return result;
	}

}
