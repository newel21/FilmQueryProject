package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private final String user = "student";
	private final String pass = "student";



	@Override
	public Film getFilmById(int filmId) throws SQLException {
		Film film = null;
		Connection conn = DriverManager.getConnection(URL, user, pass);
//                            1     2         3          4         5
		String sql = "SELECT id, title, release_year, rating, description FROM film WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();
		if (filmResult.next()) {
			film = new Film(); // Create the object
			film.setId(filmResult.getInt(1));
			film.setTitle(filmResult.getString(2));
			film.setReleaseYear(filmResult.getInt(3));
			film.setRating(filmResult.getString(4));
			film.setDescription(filmResult.getString(5));
			film.setActors(getActorsByFilmId(filmId)); // A Film has Actors
		}
		
		return film;
	}

	@Override
	public Actor getActorById(int actorId) throws SQLException {
		Actor actor = null;
		Connection conn = DriverManager.getConnection(URL, user, pass);   
//		                      1       2          3
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor(); // Create the object
			// Here is our mapping of query columns to our object fields:
			actor.setId(actorResult.getInt(1));
			actor.setFirstName(actorResult.getString(2));
			actor.setLastName(actorResult.getString(3));
		}
		return actor;
	}

	@Override
	public List<Actor> getActorsByFilmId(int filmID) {
		List<Actor> actors = new ArrayList<>();
		  try {
		    Connection conn = DriverManager.getConnection(URL, user, pass);
//		                          1       2          3   
		    String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		    PreparedStatement stmt = conn.prepareStatement(sql);
		    stmt.setInt(1, filmID);
		    ResultSet rs = stmt.executeQuery();
		    while (rs.next()) {
		      int actorId = rs.getInt(1);
		      String firstName = rs.getString(2);
		      String lastName = rs.getString(3);
		      Actor actor = new Actor(actorId, firstName, lastName, null);
		      actors.add(actor);
		    }
		    rs.close();
		    stmt.close();
		    conn.close();
		  } catch (SQLException e) {
		    e.printStackTrace();
		  }
		  return actors;
	}

	
}
