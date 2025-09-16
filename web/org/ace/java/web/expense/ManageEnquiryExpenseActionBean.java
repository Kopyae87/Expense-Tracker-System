package org.ace.java.web.expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.Entity.PaymentType;
import org.ace.accounting.expense.Iservices.IEnquiryExpenseService;
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.accounting.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.ParamId;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "ManageEnquiryExpenseActionBean")
@ViewScoped
public class ManageEnquiryExpenseActionBean extends BaseBean{
	
	@ManagedProperty(value = "#{ExpenseService}")
	private IExpenseService expenseService;

	@ManagedProperty(value = "#{EnquiryExpenseService}")
	private IEnquiryExpenseService enquiryExpenseService;
	
	public void setExpenseService(IExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	public void setEnquiryExpenseService(IEnquiryExpenseService enquiryExpenseService) {
		this.enquiryExpenseService = enquiryExpenseService;
	}
	
	private String categoryId;
	private String paymentType;
	private Date startDate;
	private Date endDate;
	private List<Category> categoryList;
	private List<Expense> expenseList;
	private Category category;
	private String userid;
	private User currentUser;

	@PostConstruct
	public void init() {
		currentUser = (User) getParam(ParamId.LOGIN_USER);
		userid = currentUser.getId();
		createNewCategoriesList();
		loadCategories();
		createNewExpenseList();
		loadExpenses();
	}
	
	public void createNewCategoriesList() {
		categoryList = new ArrayList<>();
	}
	
	public void loadCategories(){
		categoryList = expenseService.findAllCategory();
	}
	
	public void createNewExpenseList() {
		expenseList = new ArrayList<>();
	}
	
	public List<Expense> search(){
		System.out.println("paymentype:" + paymentType);
		createNewExpenseList();
		expenseList = enquiryExpenseService.find(startDate, endDate, categoryId, paymentType, userid);
		if(expenseList != null && !expenseList.isEmpty()) {
			System.out.println("success");
		}
		return expenseList;
	}

	public void cancel() {
		categoryId = null;
		startDate = null;
		endDate = null;
		paymentType = "";
	}
	
	public void returnCategory(SelectEvent event) {
		category = (Category) event.getObject();
		categoryId = category.getId();
	}
	
	public void deleteExpense(Expense expense) {
		try {
			if(expense == null) {
				System.out.println("expense is null");
				return;
			}
			expenseService.deleteExpense(expense);
			
			expenseList = expenseService.findAllExpense(userid);
			System.out.println("delete succcessfully");
		} catch (Exception e) {
			// TODO: handle exception
			addErrorMessage("delete failed");
		}
	}
	
	public void exportToExcel() {
		try {
			ExcelExport.exportExpensesToExcel(expenseList, "expenses.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadExpenses() {
		expenseList = expenseService.findAllExpense(userid);
	}
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryName) {
		this.categoryId = categoryName;
		category = null;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Expense> getExpenseList() {
		return expenseList;
	}

	public void setExpenseList(List<Expense> expenseList) {
		this.expenseList = expenseList;
	}

	public IExpenseService getExpenseService() {
		return expenseService;
	}

	public IEnquiryExpenseService getEnquiryExpenseService() {
		return enquiryExpenseService;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
	public PaymentType[] getPaymentTypes() {
		return PaymentType.values();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	
}
