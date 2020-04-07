package com.ksc91u.youtube.dao

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.ksc91u.youtube.dto.ShareLinkDto
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.security.SecureRandom
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

interface UserDao {
    fun findAll(): MutableList<ShareLinkDto>
    fun addMapping(routeUrl: String, title: String, content: String, imgUrl: String)
    fun getMapping(hashId: String): ShareLinkDto
}

@Repository
class UserDaoImpl(val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : UserDao {

    private val random = SecureRandom()
    //private val chars = "123456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray()
    private val chars = "123456789abcdefghijkmnpqrstuvwxyz".toCharArray()

    override fun findAll(): MutableList<ShareLinkDto> {
        val params: Map<String, Any> = HashMap()
        val sql = "SELECT * FROM ShareLink ORDER BY id DESC"
        return namedParameterJdbcTemplate.query<ShareLinkDto>(sql, params, ShareLinkMapper())
    }

    override fun getMapping(hashId: String): ShareLinkDto {
        val params: Map<String, Any> = mapOf(
                Pair("hashKey", hashId))
        val sql = "SELECT * FROM ShareLink WHERE hashKey = :hashKey"
        return namedParameterJdbcTemplate.query<ShareLinkDto>(sql, params, ShareLinkMapper()).first()
    }

    override fun addMapping(routeUrl: String, title: String, content: String, imgUrl: String) {
        val sql = "INSERT INTO ShareLink(hashKey, routeUrl, title, content, imgUrl) " +
                "VALUES (:hashKey, :routeUrl, :title, :content, :imgUrl)"

        val nanoId = NanoIdUtils.randomNanoId(random, chars, 8)
        val params = mapOf(
                Pair("hashKey", nanoId),
                Pair("routeUrl", routeUrl),
                Pair("title", title),
                Pair("content", content),
                Pair("imgUrl", imgUrl)
        )

        namedParameterJdbcTemplate.execute(sql, params) { ps -> ps.execute() }

    }

    private class ShareLinkMapper : RowMapper<ShareLinkDto> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): ShareLinkDto {
            return ShareLinkDto(
                    rs.getInt("id"),
                    rs.getString("hashKey"),
                    rs.getString("routeUrl"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("imgUrl")
            )
        }
    }
}