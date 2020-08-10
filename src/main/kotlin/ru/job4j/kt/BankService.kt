package ru.job4j.kt

import ru.job4j.principle_006.Account
import ru.job4j.principle_006.User
import java.util.*

class BankService {
    private val users = HashMap<User, ArrayList<Account>>()

    fun addUser(user: User): Boolean =
            users.putIfAbsent(user, ArrayList()) == null

    fun addAccount(passport: String, account: Account): Boolean =
            findAccounts(passport).add(account)

    fun findByPassport(passport: String): User =
            users.keys.first { it.passport == passport }

    fun findAccounts(passport: String): ArrayList<Account> =
            users.getOrElse(
                    findByPassport(passport),
                    { throw NoSuchElementException() }
            )

    fun findByRequisite(passport: String, requisite: String): Account =
            findAccounts(passport).first { it.requisite == requisite }

    fun transferMoney(srcPassport: String, srcRequisite: String,
                      destPassport: String, destRequisite: String, amount: Double): Boolean {
        val source = findByRequisite(srcPassport, srcRequisite)
        val dest = findByRequisite(destPassport, destRequisite)
        source.balance -= amount
        dest.balance += amount
        return true
    }
}

fun main() {
    val bank = BankService()
    bank.findByPassport("123")
}