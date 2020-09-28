package ru.job4j.principle_006;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class BankService {
    private final HashMap<User, ArrayList<Account>> users = new HashMap<>();

    public void addUser(User user) {
        users.putIfAbsent(user, new ArrayList<>());
    }

    public Account findByRequisite(String passport, String requisite) {
        User user = findByPassport(passport);
        if (user == null) {
            return null;
        }
        return users.get(user).stream()
                .filter(account -> account.getRequisite().equals(requisite))
                .findFirst()
                .orElse(null);
    }

    public void addAccount(String passport, Account account) {
        User user = findByPassport(passport);
        if (user == null) {
            return;
        }
        users.get(user).add(account);
    }

    public User findByPassportIfNullThrow(String passport) throws NotFoundUserException {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        throw new NotFoundUserException("User with passport " + passport + "not found");
    }

    public User findByPassportIfNullRuntime(String passport) {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        throw new NullPointerException("User with passport " + passport + "not found");
    }

    @Nullable
    public User findByPassportCheckByCompile(String passport) {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        return null;
    }

    public Optional<User> findByPassportIfNullOptional(String passport) {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public User findByPassport(String passport) {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        return null;
    }

    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport, String descRequisite, double amount) {
        var source = findByRequisite(srcPassport, srcRequisite);
        var dest = findByRequisite(destPassport, descRequisite);
        boolean rsl = source != null && dest != null;
        if (rsl) {
            source.setBalance(source.getBalance() - amount);
            dest.setBalance(dest.getBalance() + amount);
        }
        return rsl;
    }

    public static void main(String[] args) {
        var bank = new BankService();
        bank.addUser(new User("321", "Petr Arsentev"));
        try {
            var user = bank.findByPassportIfNullThrow("123");
            System.out.println(user.getUsername());
        } catch (NotFoundUserException e) {
            System.out.println("User not found.");
        }

        var user = bank.findByPassportCheckByCompile("123");
        System.out.println(user.getUsername());
    }
}