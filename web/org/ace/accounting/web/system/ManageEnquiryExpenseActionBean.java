package org.ace.accounting.web.system;

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
	
	private String categoryName;
	private String paymentType;
	private Date startDate;
	private Date endDate;
	private List<Category> categoryList;
	private List<Expense> expenseList;
	private String userid;

	@PostConstruct
	public void init() {
		userid = ManageExpenseActionBean.currenseUserId;
		createNewCategoriesList();
		loadCategories();
	}
	
	public void createNewCategoriesList() {
		categoryList = new ArrayList<>();
	}
	
	public void loadCategories(){
		categoryList = expenseService.findAllCategory();
	}
	
	public List<Expense> search(){
		expenseList = enquiryExpenseService.find(startDate, endDate, categoryName, paymentType, userid);
		if(expenseList != null) {
			System.out.println("success");
		}
		return expenseList;
	}

	public void cancel() {
		categoryName = "";
		startDate = null;
		endDate = null;
		paymentType = "";
	}
	
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	
	
}
