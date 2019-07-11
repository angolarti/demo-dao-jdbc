package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DBException;
import models.dao.SellerDao;
import models.dto.SellerDto;
import models.entities.Department;
import models.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private static final String ResultSet = null;
	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		
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
		}
	}

	@Override
	public List<Seller> findAll() {
		return null;
	}
}
