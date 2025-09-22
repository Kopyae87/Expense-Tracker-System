package org.ace.java.web.expense;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.CategoryBudget;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.Iservices.IExpenseDashBoardService;
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.accounting.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.ParamId;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean(name = "ManageExpenseDashBoardActionBean")
@ViewScoped
public class ManageExpenseDashBoardActionBean extends BaseBean {

	@ManagedProperty(value = "#{ExpenseService}")
	private IExpenseService expenseService;

	public void setExpenseService(IExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	@ManagedProperty(value = "#{ExpenseDashBoardService}")
	private IExpenseDashBoardService dashBoardService;
	
	public void setDashBoardService(IExpenseDashBoardService dashBoardService) {
		this.dashBoardService = dashBoardService;
	}

	private double monthlyExpense;
	private double yearlyExpense;
	private double remainingBudget;
	private long totalTransactions;
	private List<Expense> recentExpenses;
	private List<CategoryBudget> categoryBudgetsMonthly;
	private List<CategoryBudget> categoryBudgetsYearly;
	private BarChartModel categoryChart;
	private LineChartModel monthlyTrendChart;
	private Calendar cal;
	private User currentUser;
	private String userId;
	
	private double monthlyBudget; // Example fixed monthly budget
	private double yearlyBudget; // Example fixed yearly budget
	private double monthlyPercent;
	private double yearlyPercent;
	private String topCategoryName;
	private double topCategoryExpense;
	private int currentmonth;
	private int currentyear;

	@PostConstruct
	public void init() {
		currentUser = (User) getParam(ParamId.LOGIN_USER);
		setUserId(currentUser.getId());
		getCurrentDate();
        loadDashboardData();
        createCategoryChart();
        createMonthlyTrendChart();
	}

	public void getCurrentDate() {
		 cal = Calendar.getInstance();
		 currentmonth = cal.get(Calendar.MONTH) + 1;
		 currentyear = cal.get(Calendar.YEAR);
	}
	
	// === Load Dashboard Data ===
	private void loadDashboardData() {
		// Replace with real service methods
        monthlyExpense = dashBoardService.findTotalExpenseForMonth(userId, currentmonth , currentyear);
        yearlyExpense = dashBoardService.findTotalExpenseForYear(userId, currentyear);
        totalTransactions = dashBoardService.countExpenses(userId);

		monthlyBudget = dashBoardService.findMonthlyBudget(userId, currentmonth, currentyear);
		yearlyBudget = dashBoardService.findYearlyBudget(userId, currentyear);
		remainingBudget = monthlyBudget - monthlyExpense;

	    monthlyPercent = monthlyBudget == 0 ? 0 : (double) ((monthlyExpense / monthlyBudget) * 100);
	    yearlyPercent = yearlyBudget == 0 ? 0 : (double) ((yearlyExpense / yearlyBudget) * 100);
	    
	    monthlyPercent = Math.round(monthlyPercent * 10.0) / 10.0;
	    yearlyPercent = Math.round(yearlyPercent * 10.0) / 10.0;
	    
        // load recent 10 expenses
        recentExpenses = expenseService.findLatestTenExpenses(userId);

        // load category budgets per-category utilization)
        categoryBudgetsMonthly = new ArrayList<>();
        List<Category> categories = expenseService.findAllCategory();
        for (Category c : categories) {
            double spent = dashBoardService.findTotalExpenseByCategoryForMonth(userId, c.getId(), currentmonth);
            double limit = dashBoardService.findBudgetByCategoryForMonth(userId, c.getId(), currentmonth, currentyear); // Example fixed per-category limit
            int percentSpent = limit == 0 ? 0 : (int) ((spent / limit) * 100);
            categoryBudgetsMonthly.add(new CategoryBudget(c.getName(), spent, limit, percentSpent));
        }
        
        // load category budgets by Yearly
        categoryBudgetsYearly = new ArrayList<>();
        for (Category c : categories) {
            double spent = dashBoardService.findTotalExpenseByCategoryForYear(userId, c.getId(), currentyear);
            double limit = dashBoardService.findBudgetByCategoryForYear(userId, c.getId(), currentyear);
            int percentSpent = limit == 0 ? 0 : (int) ((spent / limit) * 100);
            categoryBudgetsYearly.add(new CategoryBudget(c.getName(), spent, limit, percentSpent));
        }
        
        Optional<CategoryBudget> top = categoryBudgetsMonthly.stream()
                .max(Comparator.comparingDouble(CategoryBudget::getSpent));
        if (top.isPresent()) {
            topCategoryName = top.get().getCategoryName();
            topCategoryExpense = top.get().getSpent();
        } else {
            topCategoryName = "N/A";
            topCategoryExpense = 0;
        }
	}

	// === Chart: Category Bar ===
	private void createCategoryChart() {
		categoryChart = new BarChartModel();
		ChartSeries series = new ChartSeries();
		series.setLabel("Expenses");

		for (CategoryBudget cb : categoryBudgetsMonthly) {
			series.set(cb.getCategoryName(), cb.getSpent());
		}

		categoryChart.addSeries(series);
		categoryChart.setLegendPosition("ne");
		categoryChart.setTitle("Category Expenses");
		categoryChart.setAnimate(true);
	}

	// === Chart: Monthly Trend ===
	private void createMonthlyTrendChart() {
		monthlyTrendChart = new LineChartModel();
		LineChartSeries series = new LineChartSeries();
		series.setLabel("Monthly Expenses");
	

		Map<Integer, Double> monthData = dashBoardService.findMonthlyTrend(userId, currentyear);
		
		for (int i = 1; i <= 12; i++) {
		    series.set(i, monthData.getOrDefault(i, 0.0));
		}
		 
		monthlyTrendChart.addSeries(series);
		monthlyTrendChart.setTitle("Expense Trend");
		monthlyTrendChart.setLegendPosition("ne");
		monthlyTrendChart.setAnimate(true);
		
		  // Configure X-axis explicitly
	    Axis xAxis = monthlyTrendChart.getAxis(AxisType.X);
	    xAxis.setLabel("Month");
	    xAxis.setMin(0);
	    xAxis.setMax(13);
	    xAxis.setTickInterval("1"); // ensures ticks at every month

	    // Optional: configure Y-axis
	    Axis yAxis = monthlyTrendChart.getAxis(AxisType.Y);
	    yAxis.setLabel("Amount (MMK)");
	    yAxis.setMin(0); // start from zero
	}

	public double getMonthlyExpense() {
		return monthlyExpense;
	}

	public void setMonthlyExpense(double monthlyExpense) {
		this.monthlyExpense = monthlyExpense;
	}

	public double getYearlyExpense() {
		return yearlyExpense;
	}

	public void setYearlyExpense(double yearlyExpense) {
		this.yearlyExpense = yearlyExpense;
	}

	public double getRemainingBudget() {
		return remainingBudget;
	}

	public void setRemainingBudget(double remainingBudget) {
		this.remainingBudget = remainingBudget;
	}

	public long getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(long totalTransactions) {
		this.totalTransactions = totalTransactions;
	}

	public List<Expense> getRecentExpenses() {
		return recentExpenses;
	}

	public void setRecentExpenses(List<Expense> recentExpenses) {
		this.recentExpenses = recentExpenses;
	}

	public List<CategoryBudget> getCategoryBudgetsMonthly() {
		return categoryBudgetsMonthly;
	}

	public void setCategoryBudgetsMonthly(List<CategoryBudget> categoryBudgetsMonthly) {
		this.categoryBudgetsMonthly = categoryBudgetsMonthly;
	}

	public BarChartModel getCategoryChart() {
		return categoryChart;
	}

	public void setCategoryChart(BarChartModel categoryChart) {
		this.categoryChart = categoryChart;
	}

	public LineChartModel getMonthlyTrendChart() {
		return monthlyTrendChart;
	}

	public void setMonthlyTrendChart(LineChartModel monthlyTrendChart) {
		this.monthlyTrendChart = monthlyTrendChart;
	}

	public Calendar getCal() {
		return cal;
	}

	public void setCal(Calendar cal) {
		this.cal = cal;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getMonthlyBudget() {
		return monthlyBudget;
	}

	public void setMonthlyBudget(double monthlyBudget) {
		this.monthlyBudget = monthlyBudget;
	}

	public double getYearlyBudget() {
		return yearlyBudget;
	}

	public void setYearlyBudget(double yearlyBudget) {
		this.yearlyBudget = yearlyBudget;
	}

	public double getMonthlyPercent() {
		return monthlyPercent;
	}

	public void setMonthlyPercent(double monthlyPercent) {
		this.monthlyPercent = monthlyPercent;
	}

	public double getYearlyPercent() {
		return yearlyPercent;
	}

	public void setYearlyPercent(double yearlyPercent) {
		this.yearlyPercent = yearlyPercent;
	}

	public String getTopCategoryName() {
		return topCategoryName;
	}

	public void setTopCategoryName(String topCategoryName) {
		this.topCategoryName = topCategoryName;
	}

	public double getTopCategoryExpense() {
		return topCategoryExpense;
	}

	public void setTopCategoryExpense(double topCategoryExpense) {
		this.topCategoryExpense = topCategoryExpense;
	}

	public List<CategoryBudget> getCategoryBudgetsYearly() {
		return categoryBudgetsYearly;
	}

	public void setCategoryBudgetsYearly(List<CategoryBudget> categoryBudgetsYearly) {
		this.categoryBudgetsYearly = categoryBudgetsYearly;
	}
	
	
}
