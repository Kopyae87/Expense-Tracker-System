package org.ace.accounting.web.system;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.accounting.expense.Entity.Budget;
import org.ace.accounting.expense.Iservices.IExpenseBudgetService;
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "ManageExpenseBudgetActionBean")
@ViewScoped
public class ManageExpenseBudgetActionBean extends BaseBean {

	@ManagedProperty(value = "#{ExpenseService}")
	private IExpenseService expenseService;

	public void setExpenseService(IExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	@ManagedProperty(value = "#{ExpenseBudgetService}")
	private IExpenseBudgetService expenseBudgetService;

	public void setExpenseBudgetService(IExpenseBudgetService expenseBudgetService) {
		this.expenseBudgetService = expenseBudgetService;
	}

	private String timevalue;
	private Budget currentbudget;

	@PostConstruct
	public void init() {

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

}
