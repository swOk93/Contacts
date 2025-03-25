package contacts

val contacts: MutableList<Contact> = mutableListOf()

fun isContactsEmpty(action: String) = if (contacts.size < 1) {
    println("No records to $action!")
    true
} else false


fun numOrNull(input: String): Int? {
    val num = input.toIntOrNull()    // if this is a number
    return if (num != null && num in 1..contacts.size) num
    else {
        println("Wrong number")
        null
    }
}

open class Contact(var name: String, var number: String) {

    open fun getName(): String {
        return "$name"
    }

    open fun getInfo(): String {
        return "$name"
    }

    companion object {
        fun checkNumber(number: String): String {
            val firstBracket = """\(\w+\)([- ]\w{2,})*"""
            val secondBracket = """\w+[- ]\(\w{2,}\)([- ]\w{2,})*"""
            val noBracket = """\w+[- ]\w{2,}([- ]\w{2,})*"""
            val phoneNumberRegex = Regex("""^\+?(\w+|$firstBracket|$secondBracket|$noBracket)$""")
            return if (phoneNumberRegex.matches(number)) number else "[no number]".also { println("Wrong number format!") }
        }
    }
}

class Person(name: String, var surname: String, var birthDate: String, var gender: String, number: String) : Contact(name, number) {
//    var birthDate: String = number 
//        set(value) {
//            field = checkBirthDate(value)
//        }
//    var gender: String = gender
//        set(value) {
//            field = checkGender(value)
//        }

    override fun getName(): String {
        return "$name, $surname"
    }

    companion object {
        fun checkBirthDate(str: String): String {
            val birthDateRegex = Regex("^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")
            return if (birthDateRegex.matches(str)) str else "[no data]".also { println("Bad birth date!") }
        }
        fun checkGender(str: String): String {
            return if (str == "M" || str == "F") str else "[no data]".also { println("Bad gender!") }
        }
    }
}

class Organization(name: String, address: String, number: String) : Contact(name, number) 

object PhoneBookManager {
    private fun addOrganization() {
        val name: String = println("Enter the organization name:").run { readln() }
        val address: String = println("Enter the address:").run { readln() }
        val number: String = println("Enter the number:").run { Contact.checkNumber(readln()) }
        contacts.add(Organization(name, address, number))
        println("The record added.")
    }

    private fun addPerson() {
        val name: String = println("Enter the name:").run { readln() }
        val surname: String = println("Enter the surname:").run { readln() }
        val birthDate: String = println("Enter the birth date:").run { Person.checkBirthDate(readln()) }
        val gender: String = println("Enter the gender (M, F):").run { Person.checkGender(readln()) }
        val number: String = println("Enter the number:").run { Contact.checkNumber(readln()) }
        contacts.add(Person(name, surname, birthDate, gender, number))
        println("The record added.")
    }

    fun add() {
        println("Enter the type (person, organization):")
        when (readln()) {
            "person" -> addPerson()
            "organization" -> addOrganization()
            else -> println("Wrong type of organization")
        }
//        val name: String = println("Enter the name:").run { readln() }
//        val surname: String = println("Enter the surname:").run { readln() }
//        val number: String = println("Enter the number:").run { readln() }
//        contacts.add(Contact(name, surname, number))
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
            println("${i + 1}. ${contacts[i].getName()}")
        }
    }

    fun info() {
        if (isContactsEmpty("list")) return else list()
        println("Enter index to show info:")
    }

}

fun main() {
    while (true) {
        println("Enter action (add, remove, edit, count, list, exit):\n")
        when (readln()) {
            "add" -> PhoneBookManager.add()
            "remove" -> PhoneBookManager.remove()
            "edit" -> PhoneBookManager.edit()
            "count" -> PhoneBookManager.count()
            "info" -> PhoneBookManager.info()
            "exit" -> break
            else -> println("Unknown command!")
        }
    }
}