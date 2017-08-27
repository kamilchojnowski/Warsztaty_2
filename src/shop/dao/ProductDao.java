package shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shop.entity.Product;
import shop.utils.DbUtil;

public class ProductDao {

	// Zapytania SQL
	private static final String FIND_ALL_PRODUCTS_QUERY = "SELECT * FROM Products;";
	private static final String FIND_PRODUCT_BY_CATEGORY_QUERY = "SELECT * FROM Products WHERE category_id = (SELECT id FROM Categories WHERE name = ?);";
	private static final String ADD_PRODUCT_QUERY = "INSERT into Products (category_id, name, description, evaluation, price, quantity) values (?, ?, ?, ?, ?, ?);";
	private static final String DELETE_PRODUCT_QUERY = "DELETE FROM Products WHERE id = ?;";
	private static final String UPDATE_PRODUCT_QUERY = "UPDATE Products SET category_id = ?, name = ? , description = ?, evaluation = ?, price = ?, quantity =? WHERE id = ?;";

	// Konstruktor
	public ProductDao() {

	}

	// Lista wszystkich produktów
	public List<Product> findAllProducts(List<String> listCategories) {
		List<Product> listProducts = new ArrayList<>();
		try (Connection connection = DbUtil.getConn();
				PreparedStatement statement = connection.prepareStatement(FIND_ALL_PRODUCTS_QUERY);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Product productToAdd = new Product();
				productToAdd.setId(resultSet.getInt("id"));
				productToAdd.setName(resultSet.getString("name"));
				productToAdd.setCategory(listCategories.get(resultSet.getInt("category_id") - 1));
				productToAdd.setDescription(resultSet.getString("description"));
				productToAdd.setEvaluation(resultSet.getInt("evaluation"));
				productToAdd.setPrice(resultSet.getDouble("price"));
				productToAdd.setQuantitu(resultSet.getInt("quantity"));
				listProducts.add(productToAdd);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cos sie nie powiodło");
		}
		return listProducts;
	}

	// Lista produktów z podanej kategorii
	public List<Product> findAllProductsByCategory(String name) {
		List<Product> listProducts = new ArrayList<>();
		try (Connection connection = DbUtil.getConn();
				PreparedStatement statement = connection.prepareStatement(FIND_PRODUCT_BY_CATEGORY_QUERY);) {
			statement.setString(1, name);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Product productToAdd = new Product();
					productToAdd.setId(resultSet.getInt("id"));
					productToAdd.setName(resultSet.getString("name"));
					productToAdd.setCategory(name);
					productToAdd.setDescription(resultSet.getString("description"));
					productToAdd.setEvaluation(resultSet.getInt("evaluation"));
					productToAdd.setPrice(resultSet.getDouble("price"));
					productToAdd.setQuantitu(resultSet.getInt("quantity"));
					listProducts.add(productToAdd);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cos sie nie powiodło");
		}
		return listProducts;
	}

	// Dodawanie produktu
	public Product addProduct(Product productToAdd, List<String> listCategories) {

		// Znajdowanie id kategorii
		int index = 0;
		for (int i = 0; i < listCategories.size(); i++) {
			if (listCategories.get(i).equals(productToAdd.getCategory())) {
				index = i + 1;
			}
		}
		// Dodawanie produktu
		try (Connection connection = DbUtil.getConn();
				PreparedStatement statement = connection.prepareStatement(ADD_PRODUCT_QUERY,
						PreparedStatement.RETURN_GENERATED_KEYS);) {
			statement.setInt(1, index);
			statement.setString(2, productToAdd.getName());
			statement.setString(3, productToAdd.getDescription());
			statement.setInt(4, productToAdd.getEvaluation());
			statement.setDouble(5, productToAdd.getPrice());
			statement.setInt(6, productToAdd.getQuantity());
			int result = statement.executeUpdate();
			if (result != 1) {
				throw new RuntimeException("Execute update returned " + result);
			}
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.first()) {
					productToAdd.setId(generatedKeys.getInt(1));
					return productToAdd;
				} else {
					throw new RuntimeException("Generated key was not found");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cos sie nie powiodło");
		}
		return null;
	}

	// Usuwanie produktu
	public void deleteProduct(int index) {
		try (Connection connection = DbUtil.getConn();
				PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_QUERY);) {
			statement.setInt(1, index);
			int result = statement.executeUpdate();
			if (result == 1) {
				System.out.println("Produkt usunięty poprawnie");
			} else {
				System.out.println("Błąd usuwania produktu, prawdopodobnie nie znaleziono produktu");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cos sie nie powiodło");
		}
	}

	// Aktualizacja produktu
	public void updateProduct(int indexUpdate, Product productToUpdate, List<String> listCategories) {

		// Znajdowanie id kategorii
		int index = 0;
		for (int i = 0; i < listCategories.size(); i++) {
			if (listCategories.get(i).equals(productToUpdate.getCategory())) {
				index = i + 1;
			}
		}

		// Aktualizacja produktu
		try (Connection connection = DbUtil.getConn();
				PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_QUERY);) {
			statement.setInt(1, index);
			statement.setString(2, productToUpdate.getName());
			statement.setString(3, productToUpdate.getDescription());
			statement.setInt(4, productToUpdate.getEvaluation());
			statement.setDouble(5, productToUpdate.getPrice());
			statement.setInt(6, productToUpdate.getQuantity());
			statement.setInt(7, indexUpdate);
			int result = statement.executeUpdate();
			if (result == 1) {
				System.out.println("Produkt uaktualniony poprawnie");
			} else {
				System.out.println("Błąd aktualizacji produktu, prawdopodobnie nie znaleziono produktu");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cos sie nie powiodło");
		}
	}

}
