package pro.rgun.banktestapp.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rgun on 30.04.16.
 *
 * <p>Модель для сохранения списка {@link Record}</p>
 *
 * @see <a href="http://www.cbr.ru/scripts/XML_bic.asp">http://www.cbr.ru/scripts/XML_bic.asp</a>
 */
@Root(name = "BicCode", strict = false)
public class CbrBankModel implements Serializable{

    private static final long serialVersionUID = -3909987142343676698L;

    /**
     * Список {@link Record}
     */
    @ElementList(entry = "Record", inline = true)
    public List<Record> recordList;

}
