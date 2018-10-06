package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.Scanner;
import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
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

  private void launch() throws SQLException {
    Scanner input = new Scanner(System.in);
    System.out.println("Choose one: ");
    System.out.println("1. Look up a film by its id.");  
    System.out.println("2. Look up a film by a search keyword.");
    System.out.println("3. Exit the application.");
    
//    Film film = db.getFilmById(input.nextInt());
//    System.out.println(film);
    
    switch(input.nextInt()) {
    	case 1: searchById();
    		    break;
    	case 2: System.out.println("Movies by keywords");
    	        break;
    	case 3: System.out.println("Goodbye!");
    	        break;
    	default: System.out.println("Invalid");
    	
    }
    
    input.close();
  }

  private void searchById() throws SQLException {
	  Scanner sc = new Scanner(System.in);
	  System.out.println("Enter Film ID: ");
	  int choice = sc.nextInt();
			if (choice <= 1000) {
				Film film = db.getFilmById(choice);
				System.out.println(film);			
			}else {
				System.out.println("Film not found");
			}

		sc.close();
  }

}
