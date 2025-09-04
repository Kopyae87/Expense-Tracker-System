package org.ace.java.web.authentication;

import javax.enterprise.context.RequestScoped;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.ace.accounting.common.validation.MessageId;
import org.ace.accounting.expense.Entity.ExpenseUser;
import org.ace.accounting.expense.Iservices.IExpenseUserService;
import org.ace.accounting.process.interfaces.IUserProcessService;
import org.ace.accounting.role.Role;
import org.ace.accounting.system.webPage.WebPage;
import org.ace.accounting.user.User;
import org.ace.accounting.user.service.interfaces.IUserService;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.ParamId;

@ManagedBean(name = "LoginBean")
@RequestScoped
public class LoginBean extends BaseBean {

	@ManagedProperty(value = "#{UserProcessService}")
	private IUserProcessService userProcessService;

	public void setUserProcessService(IUserProcessService userProcessService) {
		this.userProcessService = userProcessService;
	}

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	@ManagedProperty(value = "#{ExpenseUserService}")
	private IExpenseUserService expenseUserService;

	public void setExpenseUserService(IExpenseUserService expenseUserService) {
		this.expenseUserService = expenseUserService;
	}

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String authenticate() {
		//boolean authenticate = expenseUserService.loginCheck(username, password);
		boolean authenticate = userService.authenticate(username, password);
		if (authenticate) {
			User user = userService.findUser(username);
			putParam(ParamId.LOGIN_USER, user);
			userProcessService.registerUser(user);
			
//			ExpenseUser expenseuser = expenseUserService.findExpenseUser(username);
//			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("LoginUser", expenseuser);
//			putParam(ParamId.LOGIN_USER, expenseuser);
//			userProcessService.registerUser(expenseuser);
			return "home";
		} else {
			addInfoMessage(null, MessageId.LOGIN_FAILED);
			return null;
		}
	}

	public boolean hasPermission(String menu) {
		boolean permit = true;

		User user = new User();
		user = (User) getParam(ParamId.LOGIN_USER);
		// User user = userService.findUser(username);

		for (Role role : user.getRoles()) {
			if (role.getName().equals("Admin")) {
				permit = true;
				break;
			} else {
				permit = role.getName().contains(menu);
				if (permit)
					break;
			}
		}

		return permit;
//		boolean permit = true;
//
//		ExpenseUser user = new ExpenseUser();
//		user = (ExpenseUser) getParam(ParamId.LOGIN_USER);
//		// User user = userService.findUser(username);
//		return permit;
	}

	public boolean hasSubmenuPermission(String menu) {
		boolean subpermit = false;
		User user = new User();
		user = (User) getParam(ParamId.LOGIN_USER);
		// User user = userService.findUser(username);

		for (Role role : user.getRoles()) {

			for (WebPage wb : role.getWebpages()) {
				if (wb.getName().equalsIgnoreCase(menu)) {
					subpermit = true;
					break;
				}
			}
		}
		return subpermit;
//		boolean subpermit = true;
//		ExpenseUser user = new ExpenseUser();
//		user = (ExpenseUser) getParam(ParamId.LOGIN_USER);
//		// User user = userService.findUser(username);
//
//		return subpermit;
	}

	public String logout() {
		HttpSession session = (HttpSession) getFacesContext().getExternalContext().getSession(false);
		session.invalidate();
		return "login";
	}

	public String editUser() {
		putParam("key", "editUser");
		return "manageUser";

	}
}
