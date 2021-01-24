package ru.igrey.dev.entity

data class DealHistoryEntity(
    val id: Long,
    val dealId: Long,
    val userId: Long,
    val oldStatusId: Long,
    val newStatusId: Long,
    val comment: String,
    val event: String,
    val sourceTokenId: Long
) {
    override fun toString() = "STI:$sourceTokenId; userId: $userId;\n$oldStatusId -->$event--> $newStatusId"
}