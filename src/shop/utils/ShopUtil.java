package shop.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ShopUtil {

	// Stałe
	private static final String[] OPERATIONS = { "list products", "list products by category", "list categories",
			"add product", "delete product", "update product", "quit" };
	private static final String GET_ALL_CATEGORIES_QUERY = "SELECT * FROM Categories;";

	// Wyświetlenie możliwych operacji
	public static void listOperations() {
		System.out.println("Możliwe operacje:\n");
		for (int i = 0; i < OPERATIONS.length; i++) {
			System.out.println(String.format("%d.%s", i + 1, OPERATIONS[i]));
		}
		System.out.println("\nCo chcesz zrobić?");
	}

	// Znajdowanie wszystkich kategorii
	public static List<String> getAllCategories() {
		List<String> list = new ArrayList<>();
		try (Connection connection = DbUtil.getConn();
				PreparedStatement statement = connection.prepareStatement(GET_ALL_CATEGORIES_QUERY);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				list.add(resultSet.getString("name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cos sie nie powiodło");
		}
		return list;
	}

}
