package jp.niro.jimcon.tutorial;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    public LocalDate unmarshal(String v) throws Exception {
        return DateUtil.parse(v);
    }

    public String marshal(LocalDate v) throws Exception {
        return DateUtil.format(v);
    }
}
