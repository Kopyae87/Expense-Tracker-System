package org.ace.accounting.expense.Entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.accounting.common.BasicEntity;
import org.ace.accounting.user.User;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = "Budget")
@TableGenerator(name = "BUDGET_GEN", table = "ID_GEN" , pkColumnName = "GEN_NAME" , valueColumnName = "GEN_VAL" , pkColumnValue = "BUDGET_GEN" ,allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class Budget {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BUDGET_GEN")
	private String id;
	
	@Column(name = "yearly", nullable = false)
	private Integer yearly;
	
	@Column(name = "monthly")
	private Integer monthly;
	
	@Column(name = "daily")
	private Integer daily;
	
	@Column(name = "amount_limit", nullable = false)
	private double amountLimit;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = true)
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private ExpenseUser user;
	
	@Version
	private int version;
	
	@Embedded
	private BasicEntity basicEntity;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getYearly() {
		return yearly;
	}

	public void setYearly(Integer yearly) {
		this.yearly = yearly;
	}

	public Integer getMonlthy() {
		return getMonlthy();
	}

	public void setMonlthy(Integer monlthy) {
		this.monthly = monlthy;
	}

	public double getAmountLimit() {
		return amountLimit;
	}

	public void setAmountLimit(double amountLimit) {
		this.amountLimit = amountLimit;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public BasicEntity getBasicEntity() {
		return basicEntity;
	}

	public void setBasicEntity(BasicEntity basicEntity) {
		this.basicEntity = basicEntity;
	}

	public Integer getMonthly() {
		return monthly;
	}

	public void setMonthly(Integer monthly) {
		this.monthly = monthly;
	}

	public Integer getDaily() {
		return daily;
	}

	public void setDaily(Integer daily) {
		this.daily = daily;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public ExpenseUser getUser() {
		return user;
	}

	public void setUser(ExpenseUser user) {
		this.user = user;
	}

	
	
}
