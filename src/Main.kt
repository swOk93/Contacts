package contacts

val contacts: MutableList<Contact> = mutableListOf()

fun isContactsEmpty(action: String) = if (contacts.size < 1) {
    println("No records to $action!")
    true
} else false

fun checkNumber(number: String): String {
    val firstBracket = """\(\w+\)([- ]\w{2,})*"""
    val secondBracket = """\w+[- ]\(\w{2,}\)([- ]\w{2,})*"""
    val noBracket = """\w+[- ]\w{2,}([- ]\w{2,})*"""
    val phoneNumberRegex = Regex("""^\+?(\w+|$firstBracket|$secondBracket|$noBracket)$""")
    return if (phoneNumberRegex.matches(number)) number else "[no number]".also { println("Wrong number format!") }
}

fun numOrNull(input: String): Int? {
    val num = input.toIntOrNull()    // if this is a number
    return if (num != null && num in 1..contacts.size) num
    else {
        println("Wrong number")
        null
    }
}

class Contact(var name: String, var surname: String, number: String) {
    var number: String = number
        set(value) {
            field = checkNumber(value)
        }

    init {
        this.number = number
    }
}



object Actions {
    fun add() {
        val name: String = println("Enter the name:").run { readln() }
        val surname: String = println("Enter the surname:").run { readln() }
        val number: String = println("Enter the number:").run { readln() }
        contacts.add(Contact(name, surname, number))
    }

    fun remove() {
        if (isContactsEmpty("remove")) return
        list()
        println("Select a record:")
        val numRec = numOrNull(readln()) ?: return
        contacts.removeAt(numRec - 1)
        println("The record removed!")
    }

    fun edit() {
        if (isContactsEmpty("edit")) return
        list()
        println("Select a record:")
        val numRec = numOrNull(readln()) ?: return
        println("Select a field (name, surname, number):")
        val field = readln()
        when (field) {
            "name" -> println("Enter the name:").also { contacts[numRec - 1].name = readln() }
            "surname" -> println("Enter the surname:").also { contacts[numRec - 1].surname = readln() }
            "number" -> println("Enter the number:").also { contacts[numRec - 1].number = readln() }
            else -> println("Wrong field!")
        }
        println("The record updated!")
    }

    fun count() {
        println("The Phone Book has ${contacts.count()} records.")
    }

    private fun list() {
        for (i in 0..<contacts.size) {
            println("${i+1}. ${contacts[i].name} ${contacts[i].surname}, ${contacts[i].number}")
        }
    }

    fun info() {
        if (isContactsEmpty("list")) return else list()
        println("Enter index to show info:")
    }

}

fun main() {
    while (true) {
        println("Enter action (add, remove, edit, count, list, exit):")
        when (readln()) {
            "add" -> Actions.add()
            "remove" -> Actions.remove()
            "edit" -> Actions.edit()
            "count" -> Actions.count()
            "info" -> Actions.info()
            "exit" -> break
            else -> println("Unknown command!")
        }
    }
}