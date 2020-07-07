package CheckReceiptAPI.Results;

import org.apache.http.StatusLine;

/**
 * Класс, используемый для представления ответа, полученного от ФНС
 */
public class Result {

    /**
     * Информация об ответе с сервера
     */
    private StatusLine statusLine;
    private ResultStatusTypes resultStatusTypes;

    public StatusLine getStatusLine () {
        return statusLine;
    }

    public void setStatusLine (StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public Result () {

    }

    public Result (StatusLine statusLine, ResultStatusTypes resultStatusTypes) {
        this.statusLine = statusLine;
        this.resultStatusTypes = resultStatusTypes;
    }

    public ResultStatusTypes getResultStatusTypes() {
        return resultStatusTypes;
    }

    public void setResultStatusTypes (ResultStatusTypes typeResults) {
        this.resultStatusTypes = typeResults;
    }
}
