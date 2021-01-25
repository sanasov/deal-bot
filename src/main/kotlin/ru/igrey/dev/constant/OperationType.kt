package ru.igrey.dev.constant

enum class OperationType constructor(private val label: String, private val instruction: String) {
    DEAL_INFO("Информация по сдлеке", "Отправьте номер сделки"),
    DEALS_BY_PHONE_OR_CAS_ID("Инфо о клиенте. Его сделки", "Отправьте casId или номер телефона"),
    PHONES_BY_DEAL_ID("Список номеров телефонов по сделкам", "Отправьте номера сделок через пробел или ','"),
    PHONES_BY_CAS_ID("Список номеров по casId", "Отправьте список casId через пробел или ','"),
    PERSON_INFO_BY_FIO("Инфо клиента по ФИО", "Отправьте ФИО\nпример:\nИванов Иван Иванович"),
    PPL_IE_DOCS_BY_MONTH_AND_CAS_ID("ППЛ доки ИП за месяц", "Отправьте casId и месяц,\nпример:\n11152335 2020-06"),
    PPL_COMPANY_DOCS_BY_MONTH_AND_CAS_ID(
        "ППЛ доки компании за месяц", "Отправьте companyId и месяц,\nпример:\n238750 2020-12"
    );

    fun label(): String {
        return label
    }

    override fun toString(): String {
        return "Тип операции: $label\n$instruction"
    }
}
