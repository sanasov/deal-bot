package ru.igrey.dev.entity

data class RisasRequisitionEntity(
    val requisitionId: Long?,
    val state: String?
) {
    override fun toString(): String {
        return "requisitionId: $requisitionId, state: $state"
    }
}