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
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.accounting.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.ParamId;

@ManagedBean(name = "ManageExpenseActionBean")
@ViewScoped
public class ManageExpenseActionBean extends BaseBean {

	@ManagedProperty(value = "#{ExpenseService}")
	private IExpenseService expenseService;

	public void setExpenseService(IExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	private List<Category> categoryList;
	private Date currentDate;
	private Date maxDate;
	private String selectedCategoryId;
	private Expense currentexpense;
	private List<Expense> expenseList;
	private String userId;
	private User currentUser;
	private Category cat;
	private Boolean iseditMode;

	@PostConstruct
	public void init() {
		getCategoryNames();
		setMaxDate();
		createNewExpense();
		getAllExpenses();
		currentUser = (User) getParam(ParamId.LOGIN_USER);
		userId = currentUser.getId();
	}

	public void createNewExpense() {
		this.currentexpense = new Expense();
		currentexpense.setExpense_date(new Date());
	}

	public void createNewExpenseList() {
		this.expenseList = new ArrayList<>();
	}

	public void setMaxDate() {
		maxDate = new Date();
	}

	private void getCategoryNames() {
		categoryList = expenseService.findAllCategory();
		System.out.println("in get");
		if (categoryList == null || categoryList.isEmpty()) {
			System.out.println("before set");
			setCategories();
		}
	}

	private void setCategories() {
		String[] defaultNames = { "Food", "Transport", "Shopping" };
		String[] defaultDescs = { "Meals,Snacks,Drinks,etc", "Bus,Taxi,Boat,etc", "Colthes,Funitures,etc" };
		for (int i = 0; i < defaultNames.length; i++) {
			Category c = new Category(defaultNames[i], defaultDescs[i]);
			expenseService.saveCategory(c);
		}
		categoryList = expenseService.findAllCategory();
	}

	public void saveExpense() {
		System.out.println("in the save Expense");
		changeCategoryIdToObject();
		currentexpense.setUser(currentUser);
		currentexpense.setCategory(cat);
		expenseService.saveExpense(currentexpense);
		createNewExpense();
		getAllExpenses();
//		resetExpenseForm();
	}

	public void updateExpense() {
		changeCategoryIdToObject();
//		currentexpense.setUser(currentUser);
		currentexpense.setCategory(cat);
		expenseService.updateExpense(currentexpense);
		cancelExpense();
		;
	}

	public void changeCategoryIdToObject() {
		cat = null;
		for (Category c : categoryList) {
			if (c.getId().equals(selectedCategoryId)) {
				cat = c;
				break;
			}
		}
	}

//	public void resetExpenseForm() {
//		 selectedCategoryId = null;
//		 create
//	}
	public void cancelExpense() {
		createNewExpense();
		iseditMode = false;
	}

	public void openeditExpense(Expense e) {
		currentexpense = e;
		selectedCategoryId = e.getCategory().getId();
		iseditMode = true;
	}

	public List<Expense> getAllExpenses() {
		expenseList = expenseService.findAllExpense(userId);
		return expenseList;
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

	public String getSelectedCategoryId() {
		return selectedCategoryId;
	}

	public void setSelectedCategoryId(String selectedCategory) {
		this.selectedCategoryId = selectedCategory;
	}

	public PaymentType[] getPaymenttypes() {
		return PaymentType.values();
	}

	public List<Expense> getExpenseList() {
		return expenseList;
	}

	public void setExpenseList(List<Expense> expenseList) {
		this.expenseList = expenseList;
	}

	public Expense getCurrentExpense() {
		return currentexpense;
	}

	public void setCurrentExpense(Expense expense) {
		this.currentexpense = expense;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public Category getCat() {
		return cat;
	}

	public void setCat(Category cat) {
		this.cat = cat;
	}

	public Boolean getIseditMode() {
		return iseditMode;
	}

	public void setIseditMode(Boolean iseditMode) {
		this.iseditMode = iseditMode;
	}

	public Expense getCurrentexpense() {
		return currentexpense;
	}

	public void setCurrentexpense(Expense currentexpense) {
		this.currentexpense = currentexpense;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	
	
}
