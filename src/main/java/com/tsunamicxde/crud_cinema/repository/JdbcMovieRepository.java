package com.tsunamicxde.crud_cinema.repository;

import com.tsunamicxde.crud_cinema.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcMovieRepository implements MovieRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Movie> rowMapper = new RowMapper<>() {
        @Override
        public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Movie(rs.getInt("id"), rs.getString("title"),
                    rs.getString("genre"), rs.getInt("year"));
        }
    };

    @Override
    public List<Movie> findAll() {
        return jdbcTemplate.query("SELECT * FROM movies", rowMapper);
    }

    @Override
    public Movie findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM movies WHERE id = ?", new Object[]{id}, rowMapper);
    }

    @Override
    public int save(Movie movie) {
        return jdbcTemplate.update("INSERT INTO movies (title, genre, year) VALUES (?, ?, ?)",
                movie.getTitle(), movie.getGenre(), movie.getYear());
    }

    @Override
    public int update(Movie movie) {
        return jdbcTemplate.update("UPDATE movies SET title = ?, genre = ?, year = ? WHERE id = ?",
                movie.getTitle(), movie.getGenre(), movie.getYear(), movie.getId());
    }

    @Override
    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM movies WHERE id = ?", id);
    }
}
