package com.ksc91u.youtube.dao

import com.ksc91u.youtube.dto.MemberLogDto
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException

interface MemberDao {
    fun addMemberLog(newRecord: MemberLogDto): MemberLogDto
    fun updateMemberLog(updated: MemberLogDto): MemberLogDto
    fun getByMemberDeviceId(memberId: String, deviceId: String): MemberLogDto?
    fun listByMember(memberId: String): List<MemberLogDto>
}

@Repository
class MemberDaoImpl(val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : MemberDao {
    override fun addMemberLog(newRecord: MemberLogDto): MemberLogDto {
        val sql = """
            INSERT INTO MemberLog(member_uuid, ip_address, ip_country, user_agent, device, os, device_id)
            VALUES (:member_uuid, :ip_address, :ip_country, :user_agent, :device, :os, :device_id)
        """.trimIndent()
        val params = mapOf(
            "member_uuid" to newRecord.memberId,
            "ip_address" to newRecord.ip,
            "ip_country" to newRecord.ipCountry,
            "user_agent" to newRecord.userAgent,
            "device" to newRecord.device,
            "os" to newRecord.os,
            "device_id" to newRecord.deviceId
        )

        namedParameterJdbcTemplate.execute(sql, params) { ps -> ps.execute() }
        return getByMemberDeviceId(newRecord.memberId, newRecord.deviceId)!!
    }

    override fun updateMemberLog(updated: MemberLogDto): MemberLogDto {
        TODO("Not yet implemented")
    }

    override fun getByMemberDeviceId(memberId: String, deviceId: String): MemberLogDto? {
        val params: Map<String, Any> = mapOf(
            "member_uuid" to memberId, "device_id" to deviceId
        )
        val sql = "SELECT * FROM MemberLog WHERE member_uuid = :member_uuid AND device_id = :device_id"
        return namedParameterJdbcTemplate.query(sql, params, MemberLogMapper()).firstOrNull()
    }

    override fun listByMember(memberId: String): List<MemberLogDto> {
        val params: Map<String, Any> = mapOf(
            "member_uuid" to memberId
        )
        val sql = "SELECT * FROM MemberLog WHERE member_uuid = :member_uuid"
        return namedParameterJdbcTemplate.query(sql, params, MemberLogMapper())
    }

    private class MemberLogMapper : RowMapper<MemberLogDto> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): MemberLogDto {
            return MemberLogDto.defaultInstance
        }
    }

}