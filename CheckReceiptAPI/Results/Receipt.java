package CheckReceiptAPI.Results;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Непосредственно сам чек. В разных чеках по разному заполнены параметры.
 */
@Data
public class Receipt {

    //region Money
    /**
     * Общая сумма по чеку, в копейках
     */
    private int totalSum;
    /**
     * Сумма, оплаченная наличными, в копейках
     */
    private int cashTotalSum;
    /**
     * Сумма, оплаченная безналичным способом оплаты, в копейках
     */
    private int ecashTotalSum;
    /**
     * Сумма НДС оплаченная по ставке 18%, в копейках
     */
    @SerializedName("nds18")
    private int totalNds18Sum;
    /**
     * Сумма НДС оплаченная по ставке 10%, в копейках
     */
    @SerializedName("nds10")
    private int totalNds10Sum;
    //endregion

    //region Cashbox
    /**
     * Фискальный признак документа, также известный как ФП, ФПД
     */
    private long fiscalSign;
    /**
     * Номер фискального документа
     */
    private int fiscalDocumentNumber;
    /**
     * Код чека
     */
    private int receiptCode;
    /**
     * Номер запроса
     */
    private int requestNumber;
    /**
     * Фискальный номер
     */
    @SerializedName("fiscalDriveNumber")
    private String fiscalNumber;
    /**
     * Что-то вроде зашифрованной информации о чеке
     */
    private String rawData;
    /**
     * Номер смены
     */
    private int shiftNumber;
    /**
     * Регистрационный номер ККТ
     */
    private String kktRegId;
    //endregion

    //region Store
    /**
     * ИНН продавца
     */
    @SerializedName("userInn")
    private String retailInn;
    /**
     * Адрес точки продажи
     */
    private String retailPlaceAddress;
    /**
     * Наименование продавца/магазина
     */
    @SerializedName("user")
    private String storeName;
    /**
     * Данные кассира, который пробил чек
     */
    @SerializedName("operator")
    private String cashier;
    /**
     * Адрес электронной почты организации, отправившей информацию по чеку в ФНС
     */
    @SerializedName("senderAddress")
    private String senderEmailAddress;
    //endregion

    //region Operation
    /**
     * Тип операции. Полагаю продажа, покупка и т.д.
     */
    private int operationType;
    /**
     * Дата совершения операции
     */
    @SerializedName("dateTime")
    private Date receiptDateTime;
    /**
     * Товары/услуги, участвующие в операции
     */
    private List<Item> items;
    //endregion

    //region Other
    /**
     * Тип системы налогообложения
     */
    private int taxationType;

    // Не понимаю что это
    private List<Object> stornoItems;
    // Не понимаю что это
    private List<Object> modifiers;
    // Не понимаю что это
    private List<Object> message;
    // Не понимаю что это
    private List<Object> properties;
    //endregion

    // region added for convenience

    /**
     * Общая сумма по чеку, в копейках
     */
    private double subunitSum;
    /**
     * Сумма, оплаченная наличными, в копейках
     */
    private double cashSubunitSum;
    /**
     * Сумма, оплаченная безналичным способом оплаты, в копейках
     */
    private double ecashSubunitSum;
    /**
     * Сумма НДС оплаченная по ставке 18%, в копейках
     */
    private double subunitNds18Sum;
    /**
     * Сумма НДС оплаченная по ставке 10%, в копейках
     */
    private double subunitNds10Sum;

    // endregion

    public void setSubunitSum(double subunitSum) {

        this.subunitSum = subunitSum;
    }

    public void setSubunitNds10Sum(double subunitNds10Sum) {

        this.subunitNds10Sum = subunitNds10Sum;
    }

    public void setSubunitNds18Sum(double subunitNds18Sum) {

        this.subunitNds18Sum = subunitNds18Sum;
    }

    public void setCashSubunitSum(double cashSubunitSum) {

        this.cashSubunitSum = cashSubunitSum;
    }

    public void setECashSubunitSum(double ecashSubunitSum) {

        this.ecashSubunitSum = ecashSubunitSum;
    }

    public int getTotalSum() {

        return totalSum;
    }

    public int getTotalNds10Sum() {

        return  totalNds10Sum;
    }

    public int getTotalNds18Sum() {

        return  totalNds18Sum;
    }

    public int getCashTotalSum() {

        return cashTotalSum;
    }

    public int getECashTotalSum() {

        return ecashTotalSum;
    }

    public List<Item> getItems() {

        return items;
    }
}
