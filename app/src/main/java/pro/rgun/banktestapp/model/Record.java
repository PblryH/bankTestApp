package pro.rgun.banktestapp.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by rgun on 09.05.16.
 *
 * <p>Модель элемента списка {@link CbrBankModel}</p>
 *
 * @see <a href="http://www.cbr.ru/scripts/XML_bic.asp">http://www.cbr.ru/scripts/XML_bic.asp</a>
 */
@Root(strict = false)
public class Record implements Serializable {

    private static final long serialVersionUID = 5525181811901512213L;

    @Attribute(name = "ID", required = false)
    public String RecordId;

    @Attribute(name = "DU", required = false)
    public String RecordDu;

    /**
     * Короткое имя банка
     */
    @Element(name = "ShortName")
    public String ShortName;

    /**
     * БИК банка
     */
    @Element(name = "Bic")
    public String Bic;

}
