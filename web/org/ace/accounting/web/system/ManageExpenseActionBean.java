package org.ace.accounting.web.system;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.Entity.PaymentType;
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.accounting.expense.services.ExpenseService;
import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "ManageExpenseActionBean")
@ViewScoped
public class ManageExpenseActionBean extends BaseBean{
	
	@ManagedProperty(value = "#{ExpenseService}")
	private IExpenseService expenseService;

	public void setExpenseService(IExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	private List<Category> categoryList;
	private Date currentDate;
	private Category selectedCategory;
	private double amount;
	private Date expenseDate;
	private String description;
	private PaymentType paymenttype;
	
	@PostConstruct
	public void init() {
		getCategoryNames();
		setMaxDate();
		expenseDate = new Date();// this is for default expense date when appear 
	}
	
	public void setMaxDate() {
		currentDate = new Date();
	}
	
	private void getCategoryNames() {
		categoryList = expenseService.findAllCategory();
		System.out.println("in get");
		if(categoryList == null || categoryList.isEmpty()) {
			System.out.println("before set");
			setCategories();
		}
	}

	private void setCategories() {
		String[] defaultNames = {"Food","Transport","Shopping"};
		String[] defaultDescs = {"Meals,Snacks,Drinks,etc","Bus,Taxi,Boat,etc","Colthes,Funitures,etc"};
		for(int i = 0; i<defaultNames.length; i ++) {
			Category c = new Category(defaultNames[i],defaultDescs[i]);
			expenseService.saveCategory(c);
		}
		categoryList = expenseService.findAllCategory();
	}

	private void saveExpense() {
		Expense expense = new Expense();
		expense.setCategory(selectedCategory);
		expense.setExpense_date(expenseDate);
		expense.setAmount(amount);
		expense.setDescription(description);
		expense.setPaymenttype(paymenttype);
	}
	
	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryNamesList) {
		this.categoryList = categoryNamesList;
	}

	public IExpenseService getExpenseService() {
		return expenseService;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Category getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PaymentType[] getPaymenttype() {
		return PaymentType.values();
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	
	
}
