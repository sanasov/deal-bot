package ru.igrey.dev.constant;

public enum OperationType {
    DEAL_INFO("Информация по сдлеке", "Отправьте номер сделки"),
    DEALS_BY_PHONE_OR_CAS_ID("Инфо о клиенте. Его сделки", "Отправьте casId или номер телефона"),
    PHONES_BY_DEAL_ID("Список номеров телефонов по сделкам", "Отправьте номера сделок через пробел или ','"),
    PHONES_BY_CAS_ID("Список номеров по casId", "Отправьте список casId через пробел или ','"),
    PPL_DOCS_BY_MONTH_AND_CAS_ID("ППЛ доки за месяц", "Отправьте casId и месяц,\nпример:\n238750 2020-12"),
    PERSON_INFO_BY_FIO("Инфо клиента по ФИО", "Отправьте ФИО\nпример:\nИванов Иван Иванович");

    OperationType(String label, String instruction) {
        this.label = label;
        this.instruction = instruction;
    }

    private final String label;
    private final String instruction;

    public String label() {
        return label;
    }

    public String toString() {
        return "Тип операции: " + label + "\n" + instruction;
    }

}
