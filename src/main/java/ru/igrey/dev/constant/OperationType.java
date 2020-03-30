package ru.igrey.dev.constant;

public enum OperationType {
    DEAL_INFO("Информация по сдлеке", "Отправьте номер сделки"),
    DEALS_BY_CAS_ID("Все сделки клиента", "Отправьте casId"),
    PHONES_BY_DEAL_ID("Список номеров телефонов по сделкам", "Отправьте номера сделок через пробел или ','"),
    PHONES_BY_CAS_ID("Список номеров по casId","Отправьте список casId через пробел или ','");

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
