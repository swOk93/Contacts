package contacts

val contacts: MutableList<Contact> = mutableListOf()

fun checkNumber(number: String): String {
    val firstBracket = "(\\(\\w+\\)([- ]\\w{2,})*)"
    val secondBracket = "(\\w+[- ]\\(\\w{2,}\\)([- ]\\w{2,})*)"
    val noBracket = "(\\w+[- ]\\w{2,}([- ]\\w{2,})*)"
    val phoneNumberRegex = Regex("\\+?(\\w+|$firstBracket|$secondBracket|$noBracket)")
    return if (phoneNumberRegex.matches(number)) number else "[no number]".also { println("Wrong number format!") }
}

fun numOrNull(): Int? {
    val num = readln().toIntOrNull()    // if this is a number
    if (num in 1.. contacts.size) return num// in right range
    println("Wrong number")
    return null
}

class Contact(var name: String, var surname: String, number: String) {
    private var _number: String = ""

    var number: String
        get() = _number
        set(value) {
            _number = checkNumber(value)
        }
}

object Actions {
    fun add() {
        println("Enter the name:")
        val name: String = readln()
        println("Enter the surname:")
        val surname: String = readln()
        println("Enter the number:")
        val number: String = readln()
        contacts.add(Contact(name, surname, number))
    }

    fun remove() {
        if (contacts.size < 1) {
            println("No records to remove!")
            return
        }
        list()
        println("Select a record:")
        val numRec = numOrNull() ?: return
        contacts.removeAt(numRec - 1)
        println("The record removed!")
    }

    fun edit() {
        if (contacts.size < 1) {
            println("No records to edit!")
            return
        }
        list()
        println("Select a record:")
        val numRec = numOrNull() ?: return
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

    fun list() {
        for (i in 0..<contacts.size) {
            println("${i+1}. ${contacts[i].name} ${contacts[i].surname}, ${contacts[i].number}")
        }
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
            "list" -> Actions.list()
            "exit" -> break
        }
    }
}