package pro.rgun.banktestapp.screen.main;

import java.io.Serializable;

/**
 * Created by rgun on 05.05.16.
 */
public class ListItemBankModel implements Serializable{

    private static final long serialVersionUID = -8006070426484949986L;

    public String shortName;
    public String bic;
    public String ks;
    public String city;
    public String address;
    public String tel;

    public boolean isExpanded = false;

    public State state = State.SHORT;

    public enum State {
        SHORT,
        FULL,
        IN_PROGRESS,
        REPEAT
    }
}
