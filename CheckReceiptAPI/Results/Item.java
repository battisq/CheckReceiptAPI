package CheckReceiptAPI.Results;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 *  Класс, представляющий позицию в чеке. В разных чеках параметры заполнены по разному.
 */
@Data
public class Item {

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
}
