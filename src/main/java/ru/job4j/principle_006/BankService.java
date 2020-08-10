package ru.job4j.principle_006;
import java.util.ArrayList;
import java.util.HashMap;

public class BankService {
    private final HashMap<User, ArrayList<Account>> users = new HashMap<>();

    public void addUser(User user) {
        users.putIfAbsent(user, new ArrayList<>());
    }

    public Account findByRequisite(String passport, String requisite)
            throws NotFoundUserException {
        User user = findByPassport(passport);
        if (user == null) {
            return null;
        }
        return users.get(user).stream()
                .filter(account -> account.getRequisite().equals(requisite))
                .findFirst()
                .orElse(null);
    }

    public void addAccount(String passport, Account account)
            throws NotFoundUserException {
        User user = findByPassport(passport);
        if (user == null) {
            return;
        }
        users.get(user).add(account);
    }


    public User findByPassport(String passport) throws NotFoundUserException {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        throw new NotFoundUserException("User not found. passport " + passport);
    }

    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport, String descRequisite, double amount)
            throws NotFoundUserException {
        var source = findByRequisite(srcPassport, srcRequisite);
        var dest = findByRequisite(destPassport, descRequisite);
        boolean rsl = source != null && dest != null;
        if (rsl) {
            source.setBalance(source.getBalance() - amount);
            dest.setBalance(dest.getBalance() + amount);
        }
        return rsl;
    }

    public static void main(String[] args) throws NotFoundUserException {
        var bank = new BankService();
        bank.addUser(new User("321", "Petr Arsentev"));
        var user = bank.findByPassport("3211");
        System.out.println(user != null ? user.getUsername() : null);
        user = bank.findByPassport("321");
        System.out.println(user != null ? user.getUsername() : null);
    }
}