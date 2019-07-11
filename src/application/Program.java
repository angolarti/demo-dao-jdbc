package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.dao.DaoFactory;
import models.dao.SellerDao;
import models.entities.Department;
import models.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Department department = new Department(1, "Books");
		
		Seller seller = new Seller(1, "Bob", "bob@gmail.com", new Date(sdf.parse("22/03/1987").getTime()), 
				3000.0, department);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println(seller);
	}
}
