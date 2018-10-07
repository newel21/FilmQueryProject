package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public void DatabaseAccessorObject() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
	}

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

//  private void test() throws SQLException {
//    Film film = db.getFilmById(1);
//    System.out.println(film);
//  }
	
	// prompts user to search film by either ID or keyword
	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);
		int choice;
		System.out.println("Choose one: ");
		System.out.println("1. Look up a film by its id.");
		System.out.println("2. Look up a film by a search keyword.");
		System.out.println("3. Exit the application.");
		choice = input.nextInt();

		switch (choice) {
		case 1:
			searchById();
			break;
		case 2:
			searchByKeyword();
			break;
		case 3:
			System.out.println("Goodbye!");
			break;
		default:
			System.out.println("Invalid");
		}

		input.close();
	}

	// Option 1: ask user to input ID (integer)
	private void searchById() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Film ID: ");
		try {
			int choice = sc.nextInt();
			System.out.println();
			if (choice <= 1000) {
				Film film = db.getFilmById(choice);
				System.out.println(film);
			} else {
				System.out.println("Film not found");
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input, try again");
			System.out.println();
			searchById();
		} finally {
			sc.close();
		}
	}

	// Option 2: ask user to input keyword (string)
	private void searchByKeyword() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter keyword: ");
		try {
			String choice = sc.nextLine();
			System.out.println();
			List<Film> film = db.getFilmByKeyword(choice);
			for (Film f : film) {
				System.out.println(f);
			}
		} catch (NoSuchElementException e) {
			searchByKeyword();
		} finally {
			sc.close();
		}
	}

}
