package shop.app;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import shop.dao.ProductDao;
import shop.entity.Product;
import shop.utils.ShopUtil;

public class ManagerShopProducts {

	public static void main(String[] args) {
		// Potrzebne zmienne
		ProductDao productDao = new ProductDao();
		List<String> listCategories = ShopUtil.getAllCategories();
		boolean work = true;
		// Aplikacja
		try (Scanner scan = new Scanner(System.in)) {
			while (work) {
				ShopUtil.listOperations();
				switch (scan.nextLine()) {
				// Operacja wyświetlenia wszystkich produktów
				case "1":
				case "1.list products":
				case "list products":
					List<Product> listProducts = productDao.findAllProducts(listCategories);
					for (Product product : listProducts) {
						System.out.println(String.format("%s\n", product));
					}
					break;
				// Operacja wyświetlenia produktów o podanej kategorii
				case "2":
				case "2.list product by category":
				case "list product by category":
					System.out.println("Podaj nazwę kategorii");
					String line = scan.nextLine();
					if (listCategories.contains(line)) {
						List<Product> listProductsByCategory = productDao.findAllProductsByCategory(line);
						for (Product product : listProductsByCategory) {
							System.out.println(String.format("%s\n", product));
						}
					} else {
						System.out.println("Nie odnaleziono kategorii");
					}
					break;
				// Operacja wyświetlenia wszystkich kategorii
				case "3":
				case "3.list categories":
				case "list categories":
					for (int i = 0; i < listCategories.size(); i++) {
						System.out.println(String.format("%d.%s", i + 1, listCategories.get(i)));
					}
					break;
				// Operacja dodania produktu
				case "4":
				case "4.add product":
				case "add product":
					Product productToAdd = new Product();
					// Dodawanie nazwy produktu
					System.out.println("Podaj nazwę produktu");
					productToAdd.setName(scan.nextLine());
					// Sprawdzanie i dodawanie kategorii produktu
					System.out.println("Podaj kategorię produktu");
					String category = scan.nextLine();
					if (!listCategories.contains(category)) {
						System.out.println("Nieistniejąca kategoria, przerywam dodawanie produktu");
						return;
					}
					productToAdd.setCategory(category);
					// Dodawanie opisu produktu
					System.out.println("Podaj opis produktu");
					productToAdd.setDescription(scan.nextLine());
					// Sprawdzanie i dodawanie oceny produktu
					System.out.println("Podaj ocene produktu (Dopuszczalny zakres oceny: 0 - 5)");
					int evaluation = 0;
					try {
						evaluation = scan.nextInt();
					} catch (InputMismatchException e) {
						System.out.println("Błędna dana, przerywam dodanie produktu");
						return;
					}
					if (evaluation > 5 || evaluation < 0) {
						System.out.println("Niepoprawna ocena, przekroczono dopuszczalny zakres");
						return;
					}
					productToAdd.setEvaluation(evaluation);
					// Sprawdzanie i dodawanie ceny prduktu
					System.out.println("Podaj cenę produktu (Dopuszczalny zakres ceny: 0,01 - 99999,99)");
					double price = 0.0;
					try {
						price = scan.nextDouble();
					} catch (InputMismatchException e) {
						System.out.println("Błędna dana, przerywam dodanie produktu");
						return;
					}
					if (price < 0.01 || price > 99999.99) {
						System.out.println("Niepoprawna ocena, przekroczono dopuszczalny zakres");
						return;
					}
					productToAdd.setPrice(price);
					// Sprawdzanie i dodawanie ilości produktów
					System.out.println("Podaj ilość produktów (Dopuszczalny zakres ilości: 0 - 2147483647)");
					int quantity = 0;
					try {
						quantity = scan.nextInt();
					} catch (InputMismatchException e) {
						System.out.println("Błędna dana, przerywam dodanie produktu");
						return;
					}
					if (quantity < 0 || quantity > 2147483647) {
						System.out.println("Niepoprawna ocena, przekroczono dopuszczalny zakres");
						return;
					}
					// Dodawanie produktu
					productToAdd.setQuantitu(quantity);
					productDao.addProduct(productToAdd, listCategories);
					scan.nextLine();
					break;
				// Operacja usuwania produktu
				case "5":
				case "5.delete product":
				case "delete product":
					System.out.println("Podaj id produktu do usunięcia");
					int index = 0;
					try {
						index = scan.nextInt();
					} catch (InputMismatchException e) {
						System.out.println("Niepoprawny index, przerywam usuwanie produktu");
						scan.nextLine();
						break;
					}
					productDao.deleteProduct(index);
					scan.nextLine();
					break;
				// Operacja aktualizacji produktu
				case "6":
				case "6.update product":
				case "update product":
					System.out.println("Podaj id produktu do aktualizacji");
					int indexUpdate = 0;
					try {
						indexUpdate = scan.nextInt();
					} catch (InputMismatchException e) {
						System.out.println("Błędny id produktu, przerywam aktualizację produktu");
						break;
					}
					scan.nextLine();
					Product productToUpdate = new Product();
					// Dodawanie nazwy produktu
					System.out.println("Podaj nazwę produktu");
					productToUpdate.setName(scan.nextLine());
					// Sprawdzanie i dodawanie kategorii produktu
					System.out.println("Podaj kategorię produktu");
					String categoryUpdate = scan.nextLine();
					if (!listCategories.contains(categoryUpdate)) {
						System.out.println("Nieistniejąca kategoria, przerywam dodawanie produktu");
						break;
					}
					productToUpdate.setCategory(categoryUpdate);
					// Dodawanie opisu produktu
					System.out.println("Podaj opis produktu");
					productToUpdate.setDescription(scan.nextLine());
					// Sprawdzanie i dodawanie oceny produktu
					System.out.println("Podaj ocene produktu (Dopuszczalny zakres oceny: 0 - 5)");
					int evaluationUpdate = 0;
					try {
						evaluationUpdate = scan.nextInt();
					} catch (InputMismatchException e) {
						System.out.println("Błędna dana, przerywam dodanie produktu");
						break;
					}
					if (evaluationUpdate > 5 || evaluationUpdate < 0) {
						System.out.println("Niepoprawna ocena, przekroczono dopuszczalny zakres");
						break;
					}
					productToUpdate.setEvaluation(evaluationUpdate);
					// Sprawdzanie i dodawanie ceny prduktu
					System.out.println("Podaj cenę produktu (Dopuszczalny zakres ceny: 0,01 - 99999,99)");
					double priceUpdate = 0.0;
					try {
						priceUpdate = scan.nextDouble();
					} catch (InputMismatchException e) {
						System.out.println("Błędna dana, przerywam dodanie produktu");
						break;
					}
					if (priceUpdate < 0.01 || priceUpdate > 99999.99) {
						System.out.println("Niepoprawna ocena, przekroczono dopuszczalny zakres");
						break;
					}
					productToUpdate.setPrice(priceUpdate);
					// Sprawdzanie i dodawanie ilości produktów
					System.out.println("Podaj ilość produktów (Dopuszczalny zakres ilości: 0 - 2147483647)");
					int quantityUpdate = 0;
					try {
						quantityUpdate = scan.nextInt();
					} catch (InputMismatchException e) {
						System.out.println("Błędna dana, przerywam dodanie produktu");
						break;
					}
					if (quantityUpdate < 0 || quantityUpdate > 2147483647) {
						System.out.println("Niepoprawna ocena, przekroczono dopuszczalny zakres");
						break;
					}
					// Aktualizacja produktu
					productToUpdate.setQuantitu(quantityUpdate);
					productDao.updateProduct(indexUpdate, productToUpdate, listCategories);
					scan.nextLine();
					break;
				// Operacja zamknięcia aplikacji
				case "7":
				case "7.quit":
				case "quit":
					work = false;
					System.out.println("Kończę działanie programu");
					break;
				// Inne komendy
				default:
					System.out.println("Nieznana komenda");
				}

			}
		}
	}

}
