package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import model.ModelException;

public class MySQLCompanyDAO implements CompanyDAO{

	@Override
	public boolean save(Company company) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO companies VALUES "
				+ " (DEFAULT, ?, ?, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, company.getName());
		db.setString(2, company.getRole());
		db.setString(3, company.getStart());
		db.setString(4, company.getEnd());
		db.setString(5, company.getUser_id());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Company> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		
		List<Company> companies = new ArrayList<Company>();
			
		// Declara um instrução SQL
		String sqlQuery = "SELECT * FROM companies";
		
		db.createStatement();
	
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Company u = createCompany(db);
			companies.add(u);
		}
		
		return companies;
	}
	
	private Company createCompany(DBHandler db) throws ModelException {
		Company u = new Company(db.getInt("id"));
		u.setName(db.getString("nome"));
		u.setRole(db.getString("role"));
		u.setStart(db.getString("start"));
		u.setEnd(db.getString("end"));
		u.setUser_id("user_id");
		
		return u;
	}

	@Override
	public boolean update(Company company) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE posts "
						 + " SET content = ?,"
						 + " company_id = ?"
						 + " WHERE id = ?";
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, company.getName());
		db.setString(2, company.getRole());
		db.setString(3, company.getStart());
		db.setString(4, company.getEnd());
		db.setString(5, company.getUser_id());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Company company) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM companies "
		                 + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, company.getId());
		
		try {
			
			return db.executeUpdate() > 0;
			
		} catch (ModelException e) {
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				return false;
			}
			
			throw e; 
		}
	}

	@Override
	public Company findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sql = "SELECT * FROM companies WHERE id = ?";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Company u = null;
		while (db.next()) {
			u = createCompany(db);
			break;
		}
		
		return u;
	}
	
	
}
