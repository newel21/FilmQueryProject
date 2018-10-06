package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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

  private void launch() throws SQLException {
    Scanner input = new Scanner(System.in);
    System.out.println("Choose one: ");
    System.out.println("1. Look up a film by its id.");  
    System.out.println("2. Look up a film by a search keyword.");
    System.out.println("3. Exit the application.");
    
    switch(input.nextInt()) {
    	case 1: searchById();
    		    break;
    	case 2: searchByKeyword();
    	        break;
    	case 3: System.out.println("Goodbye!");
    	        break;
    	default: System.out.println("Invalid");
    	
    }
    
    input.close();
  }

  private void searchById() throws SQLException, InputMismatchException{
	  Scanner sc = new Scanner(System.in);
	  System.out.println("Enter Film ID: ");
	  try {
	  int choice = sc.nextInt();
			if (choice <= 1000) {
				Film film = db.getFilmById(choice);
				System.out.println(film);
				printActors();
			} else {
				System.out.println("Film not found");
			}
		  }catch (InputMismatchException e) {
			  System.out.println("Invalid input, try again");
			  System.out.println();
			  searchById();		  
	  }

		sc.close();
  }
  
  private void searchByKeyword() throws SQLException {
	  Scanner sc = new Scanner(System.in);
	  System.out.println("Enter keyword: ");
	  String choice = sc.nextLine();
	  List<Film> film = db.getFilmByKeyword(choice);  
	  	System.out.println(film);			

	   
	  
	  sc.close();
  }
  
  private void printActors() {
	  List<Actor> actor = new ArrayList<>();
	  for (Actor a : actor) {
		actor.add(a);
		System.out.println(a.getFirstName() + " " + a.getLastName());
	}
  }

}


