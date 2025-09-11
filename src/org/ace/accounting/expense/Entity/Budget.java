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
@TableGenerator(name = "BUDGET_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "BUDGET_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class Budget {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BUDGET_GEN")
	private String id;

	@Column(name = "year_scope", nullable = false)
	private Integer yearScope;

	@Column(name = "yearly_limit")
	private Double yearlyLimit;

	@Column(name = "monthly_limit")
	private Double monthlyLimit;

//	@Column(name = "dailylimit")
//	private Double dailyLimit;

//	@Column(name = "amount_limit", nullable = false)
//	private double amountLimit;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

//	@ManyToOne
//	@JoinColumn(name = "user_id", nullable = false)
//	private ExpenseUser user;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "description")
	private String description;

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

	public Integer getYearScope() {
		return yearScope;
	}

	public void setYearScope(Integer yearScope) {
		this.yearScope = yearScope;
	}

	public Double getYearlyLimit() {
		return yearlyLimit;
	}

	public void setYearlyLimit(Double yearlyLimit) {
		this.yearlyLimit = yearlyLimit;
	}

	public Double getMonthlyLimit() {
		return monthlyLimit;
	}

	public void setMonthlyLimit(Double monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
