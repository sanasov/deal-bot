package ru.igrey.dev.entity

import java.time.LocalDateTime

data class DealEntity(
    val id: Long,
    val authorId: Long,
    val dealStatusId: Long,
    val mikId: Long,
    val officeId: Long,
    val typeId: Long,
    val archived: Boolean,
    val createdTime: LocalDateTime
)