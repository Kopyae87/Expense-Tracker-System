package org.ace.java.web.expense;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.ace.accounting.expense.Entity.BudgetDTO;
import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.Entity.PaymentType;
import org.ace.accounting.expense.Iservices.IEnquiryExpenseService;
import org.ace.accounting.expense.Iservices.IExpenseBudgetService;
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.accounting.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.ParamId;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.SelectEvent;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@ManagedBean(name = "ManageEnquiryExpenseActionBean")
@ViewScoped
public class ManageEnquiryExpenseActionBean extends BaseBean {

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

	@ManagedProperty(value = "#{ExpenseBudgetService}")
	private IExpenseBudgetService expenseBudgetService;

	public void setExpenseBudgetService(IExpenseBudgetService expenseBudgetService) {
		this.expenseBudgetService = expenseBudgetService;
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

	/* details */
	private Expense selectedExpense;
	private Category selectedCategory;
	private BudgetDTO correspondingBudgets;

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

	public void loadCategories() {
		categoryList = expenseService.findAllCategory();
	}

	public void createNewExpenseList() {
		expenseList = new ArrayList<>();
	}

	public List<Expense> search() {
		System.out.println("paymentype:" + paymentType);
		createNewExpenseList();
		expenseList = enquiryExpenseService.find(startDate, endDate, categoryId, paymentType, userid);
		if (expenseList != null && !expenseList.isEmpty()) {
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

//	public void deleteExpense(Expense expense) {
//		try {
//			if(expense == null) {
//				System.out.println("expense is null");
//				return;
//			}
//			expenseService.deleteExpense(expense);
//			
//			expenseList = expenseService.findAllExpense(userid);
//			System.out.println("delete succcessfully");
//		} catch (Exception e) {
//			// TODO: handle exception
//			addErrorMessage("delete failed");
//		}
//	}

	public void exportToExcel() {
		try {
			ExcelExport.exportExpensesToExcel(expenseList, "expenses.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewExpenseDetails(Expense expense) {
		if (expense != null && expense.getId() != null) {
			try {
				this.selectedExpense = expense;

				// fetch category details
				this.selectedCategory = expense.getCategory();

				// fetch budget for this category & date
				correspondingBudgets = expenseBudgetService.findBudgetByCategoryAndDate(expense.getCategory().getId(),
						expense.getExpenseDate(), userid);
				/* this.selectedExpense = expenseService.findByExpenseId(expense.getId()); */
				System.out.println("Expense loaded: " + selectedExpense.getDescription());
			} catch (Exception e) {
				e.printStackTrace();
				addErrorMessage(null, "Failed to load expense details");
			}
		}
	}

	public void generateExpenseReport() {

		if (expenseList == null || expenseList.isEmpty()) {
			addErrorMessage(null, "No expenses to generate report");
			return;
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

//	    String dirPath = System.getProperty("user.home") + "/ExpenseReports/";
		String dirPath = "D:/reports/";
		String fileName = "ExpenseReport_" + new SimpleDateFormat("MMdd_HHmm").format(new Date());
		String pdfFilePath = dirPath + fileName + ".pdf";
		System.out.println("generateExpenseReport: Writing PDF to " + pdfFilePath);

		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("Expense-Tracker.jrxml")) {

			if (inputStream == null) {
				addErrorMessage(null, "Report design file not found");
				return;
			}
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("ReportTitle", "Monthly Expense Report"); // optional
			parameters.put("GeneratedDate", new Date()); // optional

			// Use your expenseList as data source
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(this.expenseList);

			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

			/*
			 * File pdfFile = new File(pdfFilePath);
			 * FileUtils.forceMkdir(pdfFile.getParentFile());
			 */

			// JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFilePath);

			response.reset();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + ".pdf\"");

			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			facesContext.responseComplete();
			addInfoMessage(null, "Expense Report generated successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(null, "Expense Report Generation Failed: " + e.getMessage());
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

	public Expense getSelectedExpense() {
		return selectedExpense;
	}

	public void setSelectedExpense(Expense selectedExpense) {
		this.selectedExpense = selectedExpense;
	}

	public Category getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public BudgetDTO getCorrespondingBudgets() {
		return correspondingBudgets;
	}

	public void setCorrespondingBudgets(BudgetDTO correspondingBudgets) {
		this.correspondingBudgets = correspondingBudgets;
	}

	
}
