package pro.rgun.banktestapp;

import java.io.Serializable;

/**
 * Created by rgun on 30.04.16.
 */
public class CbrBankModel implements Serializable {

    private static final long serialVersionUID = 1071065845676403402L;

    public Integer RecordId;
    public String RecordDu;
    public String ShortName;
    public String Bic;

    public boolean isExpanded = false;

}
