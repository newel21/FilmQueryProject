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
		String sql = "SELECT film.id, film.title, film.release_year, film.rating, film.description, ";
		sql += "language.name FROM film JOIN language ON film.language_id = language.id WHERE film.id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();
		if (filmResult.next()) {
			film = new Film(); 
			film.setId(filmResult.getInt("film.id"));
			film.setTitle(filmResult.getString("film.title"));
			film.setReleaseYear(filmResult.getInt("film.release_year"));
			film.setRating(filmResult.getString("film.rating"));
			film.setDescription(filmResult.getString("film.description"));
			film.setLanguageName(filmResult.getString("language.name"));
			film.setActors(getActorsByFilmId(filmId)); 
		}
		filmResult.close();
		stmt.close();
		conn.close();
		return film;
	}

	@Override
	public List<Actor> getActorsByFilmId(int filmID) {
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
//		                          1       2          3   
			String sql = "SELECT actor.first_name, actor.last_name\n"
					+ "FROM actor JOIN film_actor ON film_actor.actor_id = actor.id\n"
					+ "JOIN film ON film_actor.film_id = film.id WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Actor actor = new Actor();
				actor.setFirstName(rs.getString(1));
				actor.setLastName(rs.getString(2));
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

	@Override
	public List<Film> getFilmByKeyword(String filmKeyword) throws SQLException {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.id, film.title, film.release_year, film.rating, film.description, ";
			sql += "language.name FROM film JOIN language ON film.language_id = language.id WHERE film.title LIKE ? OR film.description LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + filmKeyword + "%");
			stmt.setString(2, "%" + filmKeyword + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Film film = new Film();
				film.setId(rs.getInt("film.id"));
				film.setTitle(rs.getString("film.title"));
				film.setReleaseYear(rs.getInt("film.release_year"));
				film.setRating(rs.getString("film.rating"));
				film.setDescription(rs.getString("film.description"));
				film.setLanguageName(rs.getString("language.name"));
				film.setActors(getActorsByFilmId(rs.getInt("id"))); 
				films.add(film);
			}
			
			if (films.isEmpty()) {
				System.out.println("Not found on file. Try again.");
			}
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

}
