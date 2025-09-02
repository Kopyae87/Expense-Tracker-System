package org.ace.accounting.web.system;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "ManageExpenseActionBean")
@ViewScoped
public class ManageExpenseActionBean extends BaseBean{
	
	@ManagedProperty(value = "#{ExpenseService}")
	private IExpenseService expenseService;

	public void setExpenseService(IExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	private String expense_name;
	
	
	@PostConstruct
	public void init() {
		expense_name = "It is test";
	}

	public void save() {
		
	}
	
	
	
	
	
	
	public String getExpense_name() {
		return expense_name;
	}


	public void setExpense_name(String expense_name) {
		this.expense_name = expense_name;
	}
	
}
