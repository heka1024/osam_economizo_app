package com.example.tooth;

public class Ledger {
    private int current_money = 0;
    private int used_money = 0;
    private boolean can_use_money = true;

    public int getMoney() {
        return this.current_money;
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

