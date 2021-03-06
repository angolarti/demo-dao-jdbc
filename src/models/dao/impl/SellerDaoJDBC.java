package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import models.dao.SellerDao;
import models.dto.SellerDto;
import models.entities.Department;
import models.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, seller.getName());
			pstmt.setString(2, seller.getEmail());
			pstmt.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			pstmt.setDouble(4, seller.getBaseSalary());
			pstmt.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DBException("Unexpected error! No rows affected");
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(pstmt);
		}
	}
	
	@Override
	public void update(Seller seller) {
		
	}
	
	@Override
	public void deleteById(Integer id) {
		
	}
	
	@Override
	public Seller findById(Integer id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Department department = SellerDto.instantiateDepartment(rs);
				Seller seller = SellerDto.instantiateSeller(rs, department);
				return seller;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(pstmt);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");

			rs = pstmt.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department department = map.get(rs.getInt("DepartmentID"));
				if (department == null ) {					
					department = SellerDto.instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentID"), department);;
				}
				Seller seller = SellerDto.instantiateSeller(rs, department);
				list.add(seller);
			}
			return list;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(pstmt);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");

			pstmt.setInt(1, department.getId());
			rs = pstmt.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentID"));
				if (dep == null ) {					
					department = SellerDto.instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentID"), dep);;
				}
				Seller seller = SellerDto.instantiateSeller(rs, department);
				list.add(seller);
			}
			return list;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(pstmt);
		}
	}
}
