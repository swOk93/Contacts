package contacts

class Contact {
    var name: String = "Noname"
    var surname: String = ""
    private val firstBracket = "(\\(\\w+\\)([- ]\\w{2,})*)"
    private val secondBracket = "(\\w+[- ]\\(\\w{2,}\\)([- ]\\w{2,})*)"
    private val noBracket = "(\\w+[- ]\\w{2,}([- ]\\w{2,})*)"
    private val phoneNumberRegex = Regex("\\+?(\\w+|$firstBracket|$secondBracket|$noBracket)")
    var number: String = ""
        get() = field
        set(value) {
            field = if (phoneNumberRegex.matches(value)) value else ""
        }

}

fun main() {
    var contact = Contact()
    while (true) {
        println("Enter action (add, remove, edit, count, list, exit):")
        when (readln()) {
            "add" -> contact.add()
            "remove" -> contact.remove()
            "edit" -> contact.edit()
            "count" -> contact.count()
            "list" -> contact.list()
            "exit" -> break
        }
    }
}