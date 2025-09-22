package org.ace.java.web.expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ace.accounting.common.validation.ErrorMessage;
import org.ace.accounting.common.validation.IDataValidator;
import org.ace.accounting.common.validation.ValidationResult;
import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.Entity.PaymentType;
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.accounting.system.branch.Branch;
import org.ace.accounting.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.ParamId;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "ManageExpenseActionBean")
@ViewScoped
public class ManageExpenseActionBean extends BaseBean {

	@ManagedProperty(value = "#{ExpenseService}")
	private IExpenseService expenseService;

	public void setExpenseService(IExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	@ManagedProperty(value = "#{ExpenseValidator}")
	private IDataValidator<Expense> expenseValidator;

	public void setExpenseValidator(IDataValidator<Expense> expenseValidator) {
		this.expenseValidator = expenseValidator;
	}

	private List<Category> categoryList;
	private Date currentDate;
	private Date maxDate;
	private Expense currentexpense;
	private List<Expense> expenseList;
	public static String currenseUserId;
	private User currentUser;
	private Category cat;
	private Boolean iseditMode;

	@PostConstruct
	public void init() {
		currentUser = (User) getParam(ParamId.LOGIN_USER);
		currenseUserId = currentUser.getId();
		loadCategorys();
		setMaxDate();
		createNewExpense();
		createNewExpenseList();
		loadExpenses();
	}

	public void createNewExpense() {
		this.currentexpense = new Expense();
//		currentexpense.setExpense_date(new Date());
	}

	public void createNewExpenseList() {
		this.expenseList = new ArrayList<>();
	}

	public void setMaxDate() {
		maxDate = new Date();
	}

	public void loadExpenses() {
		expenseList = expenseService.findAllExpense(currenseUserId);
	}

	private void loadCategorys() {
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

	public void returnCategory(SelectEvent event) {
		cat = (Category) event.getObject();

	}

	public void saveExpense() {
		try {
			System.out.println("in the save Expense");
			currentexpense.setUser(currentUser);
			currentexpense.setCategory(cat);
			ValidationResult result = expenseValidator.validate(currentexpense, true);
			if (result.isVerified()) {
				System.out.println("in save");
				expenseService.saveExpense(currentexpense);
				addInfoMessage("Expense Added Successfully");
				resetForm();
			} else {
				System.out.println("in error ");
				for (ErrorMessage e : result.getErrorMeesages()) {
					addErrorMessage(null, e.getErrorcode(), e.getParams());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void updateExpense() {

		try {
			ValidationResult result = expenseValidator.validate(currentexpense, true);

			if (result.isVerified()) {
				currentexpense.setCategory(cat);
				expenseService.updateExpense(currentexpense);
				addInfoMessage("Expense Added Successfully");
				loadExpenses();
				cancelExpense();
			} else {
				for (ErrorMessage message : result.getErrorMeesages()) {
					addErrorMessage(null, message.getErrorcode(), message.getParams());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

//	public void changeCategoryIdToObject() {
//		cat = null;
//		for (Category c : categoryList) {
//			if (c.getId().equals(selectedCategoryId)) {
//				cat = c;
//				break;
//			}
//		}
//	}

	public void cancelExpense() {
		createNewExpense();
		cat = null;
		iseditMode = false;
	}

	public void deleteExpense(Expense expense) {
		try {
			if (expense == null) {
				System.out.println("expense is null");
				return;
			}
			expenseService.deleteExpense(expense);
			addInfoMessage("Expense Added Successfully");
			if (iseditMode && currentexpense.getId().equals(expense.getId())) {
				cancelExpense();
			}
			loadExpenses();
			System.out.println("delete succcessfully");
		} catch (Exception e) {
			// TODO: handle exception
			addErrorMessage("delete failed");
		}
	}

	public void openeditExpense(Expense expense) {
		if (expense == null) {
			addErrorMessage("Cannot edit: expense is null");
			return;
		}
		currentexpense = expense;
		cat = expense.getCategory();
		if (cat == null) {
			addErrorMessage("This expense has no category. Please select one.");
		}
		iseditMode = true;
	}

	public void resetForm() {
		createNewExpense();
		clearSelectedCategory();
		loadExpenses();
	}

	public void clearSelectedCategory() {
		cat = null;
	}

	public List<Expense> getAllExpenses() {
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
		return currenseUserId;
	}

	public void setUserId(String userId) {
		ManageExpenseActionBean.currenseUserId = userId;
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

	public static String getCurrenseUserId() {
		return currenseUserId;
	}

	public static void setCurrenseUserId(String currenseUserId) {
		ManageExpenseActionBean.currenseUserId = currenseUserId;
	}

}
