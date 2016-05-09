package pro.rgun.banktestapp.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rgun on 30.04.16.
 */
@Root(name = "BicCode", strict = false)
public class CbrBankModel implements Serializable{

    private static final long serialVersionUID = -3909987142343676698L;

    @ElementList(entry = "Record", inline = true)
    public List<Record> recordList;

}
