package com.ksc91u.youtube.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

interface UserDao {
    fun findAll()
}

@Repository
class UserDaoImpl(val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : UserDao {

    override fun findAll() {
        val params: Map<String, Any> = HashMap()
        val sql = "SELECT * FROM ShareLink"
        val result = namedParameterJdbcTemplate.query(sql, params, UserMapper())
        println(">>>> $result")
    }


    private class UserMapper : RowMapper<String> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): String {
            return rs.getString("hashKey")
        }
    }
}