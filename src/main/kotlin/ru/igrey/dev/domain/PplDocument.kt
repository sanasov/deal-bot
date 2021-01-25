package ru.igrey.dev.domain

enum class PplDocumentType {
    ACT, REESTR, PAYMENT;

    fun fileName(id: String, month: String) =
        when (this) {
            ACT -> "Акт ${id}_$month.pdf"
            REESTR -> "Реестр к акту ${id}_$month.pdf"
            PAYMENT -> "Счет на оплату ${id}_$month.pdf"
        }
}