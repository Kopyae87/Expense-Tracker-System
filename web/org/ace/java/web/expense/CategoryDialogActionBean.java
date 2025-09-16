package org.ace.java.web.expense;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "CategoryDialogActionBean")
@ViewScoped
public class CategoryDialogActionBean extends BaseBean{
	
	@ManagedProperty(value = "#{ExpenseService}")
	private IExpenseService expenseService;

	public void setExpenseService(IExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	
	private List<Category> categoryList;
	
	@PostConstruct
	public void init() {
		loadCategorys();
	}
	
	private void loadCategorys() {
		setCategoryList(expenseService.findAllCategory());
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
	public void selectCategory(Category cat) {
		PrimeFaces.current().dialog().closeDynamic(cat);
	}
	
}
