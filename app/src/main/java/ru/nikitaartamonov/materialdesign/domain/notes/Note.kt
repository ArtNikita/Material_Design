package ru.nikitaartamonov.materialdesign.domain.notes

data class Note(
    val id: Int,
    var content: String
) {
    companion object {
        var id = 0
    }
}