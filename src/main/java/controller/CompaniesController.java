package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.ModelException;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/companies"})
public class CompaniesController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
			case "/crud-manager/company/form": {
				listCompanies(req);
				req.setAttribute("action", "insert");
				ControllerUtil.forward(req, resp, "/form-company.jsp");
				break;
			}
			case "/crud-manager/company/update": {
				listCompanies(req);
				Company company = loadCompany(req);
				req.setAttribute("company", company);
				req.setAttribute("action", "update");
				ControllerUtil.forward(req, resp, "/form-company.jsp");
				break;
			}
			default:
				listCompanies(req);
				
				ControllerUtil.transferSessionMessagesToRequest(req);
			
				ControllerUtil.forward(req, resp, "/companies.jsp");
			}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		if (action == null || action.equals("") ) {
			ControllerUtil.forward(req, resp, "/index.jsp");
			return;
		}
		
		switch (action) {
			case "/crud-manager/company/delete":
				deleteCompany(req, resp);
				break;	
			case "/crud-manager/company/insert": {
				insertCompany(req, resp);
				break;
			}
			case "/crud-manager/company/update": {
				updateCompany(req, resp);
				break;
			}
			default:
				System.out.println("URL inválida " + action);
				break;
			}
				
			ControllerUtil.redirect(resp, req.getContextPath() + "/companies");
	}
	
	private Company loadCompany(HttpServletRequest req) {
		String companyIdParameter = req.getParameter("companyId");
		
		int companyId = Integer.parseInt(companyIdParameter);
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		try {
			Company company = dao.findById(companyId);
			
			if (company == null)
				throw new ModelException("Companhia não encontrado para alteração");
			
			return company;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}
	
	private void listCompanies(HttpServletRequest req) {
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		List<Company> companies = null;
		try {
			companies = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (companies != null)
			req.setAttribute("companies", companies);
	}

	
	private void insertCompany(HttpServletRequest req, HttpServletResponse resp) {
		String CompanyName = req.getParameter("name");
		String CompanyRole = req.getParameter("role");
		String CompanyStart = req.getParameter("start");
		String CompanyEnd = req.getParameter("end");
		
		Company company = new Company();
		company.setName(CompanyName);
		company.setRole(CompanyRole);
		company.setStart(CompanyStart);
		company.setEnd(CompanyEnd);
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		try {
			if (dao.save(company)) {
				ControllerUtil.sucessMessage(req, "Companhia '" + company.getName() + "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Companhia '" + company.getName() + "' não pode ser salvo.");
			}
				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void updateCompany(HttpServletRequest req, HttpServletResponse resp) {
		String CompanyName = req.getParameter("name");
		String CompanyRole = req.getParameter("role");
		String CompanyStart = req.getParameter("start");
		String CompanyEnd = req.getParameter("end");
		
		Company company = new Company();
		company.setName(CompanyName);
		company.setRole(CompanyRole);
		company.setStart(CompanyStart);
		company.setEnd(CompanyEnd);
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		try {
			if (dao.update(company)) {
				ControllerUtil.sucessMessage(req, "Companhia '" + company.getName() + "' atualizado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Companhia '" + company.getName() + "' não pode ser atualizado.");
			}
				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void deleteCompany(HttpServletRequest req, HttpServletResponse resp) {
		String companyIdParameter = req.getParameter("id");
		
		int companyId = Integer.parseInt(companyIdParameter);
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		try {
			Company company = dao.findById(companyId);
			
			if (company == null)
				throw new ModelException("Companhia não encontrado para deleção.");
			
			if (dao.delete(company)) {
				ControllerUtil.sucessMessage(req, "Companhia '" + company.getName() + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Companhia '" + company.getName() + "' não pode ser deletado. Há dados relacionados a companhia.");
			}
		} catch (ModelException e) {
			// log no servidor
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	

}
