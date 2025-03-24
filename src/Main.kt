package contacts

val contacts: MutableList<Contact> = mutableListOf()

fun checkNumber(number: String): String {
    val firstBracket = "(\\(\\w+\\)([- ]\\w{2,})*)"
    val secondBracket = "(\\w+[- ]\\(\\w{2,}\\)([- ]\\w{2,})*)"
    val noBracket = "(\\w+[- ]\\w{2,}([- ]\\w{2,})*)"
    val phoneNumberRegex = Regex("\\+?(\\w+|$firstBracket|$secondBracket|$noBracket)")
    return if (phoneNumberRegex.matches(number)) number else "".also { println("Wrong number format!") }
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
        contacts.removeAt(readln().toInt() - 1)
        println("The record removed!")
    }

    fun edit() {
        if (contacts.size < 1) {
            println("No records to edit!")
            return
        }
        list()
        println("Select a record:")
        val numRec = readln().toInt()
        println("Select a field (name, surname, number):")
        val field = readln()
        when (field) {
            "name" -> contacts[numRec].name = readln().also { println("Enter the name:") }
            "surname" -> contacts[numRec].surname = readln().also { println("Enter the surname:") }
            "number" -> contacts[numRec].number = readln().also { println("Enter the number:") }
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