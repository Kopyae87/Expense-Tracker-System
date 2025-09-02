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
@TableGenerator(name = "BUDGET_GEN", table = "ID_GEN" ,pkColumnValue = "GEN_NAME" , valueColumnName = "GEN_VAL" , allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class Budget {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BUDGET_GEN")
	private String id;
	
	@Column(name = "yearly")
	private Integer yearly;
	
	@Column(name = "monthly")
	private Integer monlthy;
	
	@Column(name = "daily")
	private Integer daily;
	
	private double amountLimit;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = true)
	private Category category;
	
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
		return monlthy;
	}

	public void setMonlthy(Integer monlthy) {
		this.monlthy = monlthy;
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

	
	
}
