package com.sample.vacation.dao;

import com.sample.vacation.entity.User;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Iryna Subota on 21.10.2016.
 */
@Repository
public class UserDao extends AbstractDao {

    private static final RowMapper USER_MAPPER = new RowMapper() {
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            return User.create(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getDate("birthday"),
                    rs.getString("position")
            );
        }
    };

    public User getById(long id) {
        return (User) jdbcTemplate.queryForObject("select * from user where id=?",
                new Object[]{id},
                USER_MAPPER);
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from user", USER_MAPPER);
    }

    public long insert(final User user) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("insert into user (first_name, last_name,birthday,position) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getSecondName());
                ps.setDate(3, new Date(user.getBirthDay().getTime()));
                ps.setString(4, user.getPosition());
                return ps;
            }
        }, key);
        return key.getKey().intValue();
    }

    public long update(final User user) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("update user set first_name=?, last_name=?,birthday=?,position=? where id=?");
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getSecondName());
                ps.setDate(3, new Date(user.getBirthDay().getTime()));
                ps.setString(4, user.getPosition());
                ps.setLong(5, user.getId());
                return ps;
            }
        });
        return user.getId();
    }

    public void delete(final int id) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("delete from user where id=?");
                ps.setInt(1, id);
                return ps;
            }
        });
    }

}
