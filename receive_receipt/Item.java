package com.our_company.comon.libs.receive_receipt;

import com.google.gson.annotations.SerializedName;

/**
 *  Класс, представляющий позицию в чеке. В разных чеках параметры заполнены по разному.
 */
public class Item {

//    @PrimaryKey(autoGenerate = true)
//    private int id = 0;

    // region В копейках
    /**
     * Сумма по позиции, в копейках
     */
    @SerializedName("sum")
    private int totalSum;
    /**
     *   Сумма НДС, оплаченная по ставке 10%, в копейках
     */
    @SerializedName("nds10")
    private int ndsTotal10Sum;
    /**
     *   Сумма НДС, оплаченная по ставке 18%, в копейках
     */
    @SerializedName("nds18")
    private int ndsTotal18Sum;

    // endregion

    // region added for convenience

    /**
     *  Сумма по позиции, в рублях
     */
    private double subunitSum;
    /**
     *  Сумма НДС, оплаченная по ставке 10%, в рублях
     */
    private double ndsSubunit10Sum;
    /**
     *   Сумма НДС, оплаченная по ставке 18%, в рублях
     */
    private double ndsSubunit18Sum;
    // endregion

    /**
     * Количество
     */
    private double quantity;
    /**
     *   Цена позиции, в копейках
     */
    private int price;
    /**
     *   Наименование позиции
     */
    private String name;

    public void setSubunitSum(double subunitSum) {
        this.subunitSum = subunitSum;
    }

    public void setNdsSubunit10Sum(double ndsSubunit10Sum) {
        this.ndsSubunit10Sum = ndsSubunit10Sum;
    }

    public void setNdsSubunit18Sum(double ndsSubunit18Sum) {
        this.ndsSubunit18Sum = ndsSubunit18Sum;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public int getNdsTotal10Sum() {
        return ndsTotal10Sum;
    }

    public int getNdsTotal18Sum() {
        return  ndsTotal18Sum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    public void setNdsTotal10Sum(int ndsTotal10Sum) {
        this.ndsTotal10Sum = ndsTotal10Sum;
    }

    public void setNdsTotal18Sum(int ndsTotal18Sum) {
        this.ndsTotal18Sum = ndsTotal18Sum;
    }

    public double getSubunitSum() {
        return subunitSum;
    }

    public double getNdsSubunit10Sum() {
        return ndsSubunit10Sum;
    }

    public double getNdsSubunit18Sum() {
        return ndsSubunit18Sum;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
