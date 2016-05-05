package pro.rgun.banktestapp;

import java.io.Serializable;

/**
 * Created by rgun on 05.05.16.
 */
public class ListItemBankModel implements Serializable{

    private static final long serialVersionUID = -8006070426484949986L;

    public String ShortName;
    public String Bic;

    public boolean isExpanded = false;

    public State state = State.SHORT;

    public enum State {
        SHORT,
        FULL,
        IN_PROGRESS,
        REPEAT
    }
}
