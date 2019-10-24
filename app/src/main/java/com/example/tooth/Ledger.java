package com.example.tooth;

public class Ledger {
    private static Ledger ledger = null;
    private int current_money;
    private int used_money;
    private boolean can_use_money;

    private Ledger() {
        this.current_money = 0;
        this.used_money = 0;
        this.can_use_money = false;
    }

    public static Ledger getLedger() {
        if (ledger == null) {
            ledger = new Ledger();
            return ledger;
        } else {
            return ledger;
        }
    }

    public boolean canUseMoney() {
        return can_use_money;
    }

    public int getMoney() {
        return this.current_money;
    }

    public int getUsedMoney() {
        return this.used_money;
    }

    public void setMoney(int new_val) {
        this.current_money = new_val;
        this.can_use_money = true;
    }

    public void useMoney(int use_val) {
        if (can_use_money) {
            this.current_money -= use_val;
            this.used_money += use_val;

            if (this.current_money <= 0) {
                can_use_money = false;
                current_money = 0;
            }
        }
    }
}

