package org.ace.accounting.expense.Entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = "User")
@TableGenerator(name = "USER_GEN", table = "ID_GEN" ,pkColumnValue = "GEN_NAME" , valueColumnName = "GEN_VAL" , allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class ExpenseUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "USER_GEN")
	private String id;
}
