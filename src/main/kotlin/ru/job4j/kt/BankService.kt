package ru.job4j.kt

import ru.job4j.principle_006.Account
import ru.job4j.principle_006.User
import java.util.*

class BankService {
    private val users = HashMap<User, ArrayList<Account>>()

    fun addUser(user: User): Boolean =
            users.putIfAbsent(user, ArrayList()) == null

    fun findByPassport(passport: String): User? =
            users.keys.find { it.passport == passport }

    fun findAccounts(passport: String): ArrayList<Account>? {
        val user = findByPassport(passport)
        return user?.let { users[user] }
    }

    fun addAccount(passport: String, account: Account): Boolean {
        return findAccounts(passport)?.add(account) ?: false
    }

    fun findByRequisite(passport: String, requisite: String): Account? =
        findByPassport(passport)?.let { user ->
            users[user]?.find { it.requisite == requisite }
        }

    fun transferMoney(srcPassport: String, srcRequisite: String,
                      destPassport: String, destRequisite: String, amount: Double): Boolean {
        val source = findByRequisite(srcPassport, srcRequisite)
        val dest = findByRequisite(destPassport, destRequisite)
        val rsl = source != null && dest != null && source.balance > amount;
        if (rsl) {
            source!!.balance -= amount
            dest!!.balance += amount
        }
        return rsl
    }
}

fun main() {
    val bank = BankService()
    bank.findByPassport("123")
}