package pro.rgun.banktestapp.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by rgun on 09.05.16.
 */
@Root(strict = false)
public class Record implements Serializable {

    private static final long serialVersionUID = 5525181811901512213L;

    @Attribute(name = "ID", required = false)
    public String RecordId;

    @Attribute(name = "DU", required = false)
    public String RecordDu;

    @Element(name = "ShortName")
    public String ShortName;

    @Element(name = "Bic")
    public Integer Bic;

}
