package models.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import models.entities.Department;
import models.entities.Seller;

public class SellerDto {

	private Integer id;
	private String name;
	private String email;
	private Date birthDate;
	private Double baseSalary;

	private Department department;
	
	public static Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
		Seller seller = new Seller(
				rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), 
				rs.getDouble("BaseSalary"), department);
		return seller;
	}

	public static Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department department = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
		return department;
	}
}
