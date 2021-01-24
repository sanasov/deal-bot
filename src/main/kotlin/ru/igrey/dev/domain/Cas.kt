package ru.igrey.dev.domain

import com.google.gson.annotations.SerializedName

data class Cas(
    @SerializedName(value = "casId")
    val id: Long,
    val phone: String?,
    val firstName: String?,
    val lastName: String?,
    val middleName: String?
) {
    private fun fio(): String {
        return this.lastName + " " + this.firstName + " " + this.middleName
    }

    override fun toString(): String {
        return "casId: " + this.id + ", phone: " + this.phone + ", " + fio()// + " authorities=" + authorities;
    }
}

data class CasUsers(
    private val casUsers: List<Cas> = emptyList()
) {

    override fun toString(): String {
        if (casUsers.isEmpty()) return "EMPTY"
        val sb = StringBuilder()
        for (cas in casUsers) {
            sb.append(cas.toString()).append("\n")
        }
        return sb.toString()
    }
}