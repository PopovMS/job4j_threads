package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean result = false;
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            result = true;
        }
        return result;
    }

    public synchronized boolean update(Account account) {
        boolean result = false;
        if (accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            result = true;
        }
        return result;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);

    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> srcAcc = getById(fromId);
        Optional<Account> destAcc = getById(toId);
        if (srcAcc.isPresent() && srcAcc.get().amount() >= amount) {
            if (destAcc.isPresent()) {
                update(new Account(toId, destAcc.get().amount() + amount));
                update(new Account(fromId, srcAcc.get().amount() - amount));
            }
        }
        return srcAcc.isPresent() && destAcc.isPresent();
    }
}

