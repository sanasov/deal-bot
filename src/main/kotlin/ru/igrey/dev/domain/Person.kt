package ru.igrey.dev.domain

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName(value = "casId")
    val id: Long,
    val confirmedPhone: String?,
    val firstName: String?,
    val lastName: String?,
    val middleName: String?
) {
    private fun fio(): String {
        return this.lastName + " " + this.firstName + " " + this.middleName
    }

    override fun toString(): String {
        return "casId: " + this.id + ", phone: " + this.confirmedPhone + ", " + fio()// + " authorities=" + authorities;
    }
}