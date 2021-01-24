package ru.igrey.dev.entity

data class DocumentEntity(
    val id: Long,
    val fileId: Long,
    val documentTypeId: Long,
    val filename: String,
    val sourceTokenId: Long,
    val archived: Boolean = false
) {
    override fun toString(): String {
        val archivedStr = if (archived) "(archived) " else ""
        return (archivedStr + documentTypeId + ": '" + filename + "' \n"
                + "fileId:" + fileId + ", sourceTokenId:" + sourceTokenId)
    }
}