package org.n3r.excel;

import org.n3r.excel.annotation.ExcelField;
import org.n3r.excel.annotation.ExcelObject;
import org.n3r.excel.annotation.ParseType;
import org.n3r.validator.impl.RequiredValidator;

import java.util.regex.Pattern;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-5 上午10:19
 */
@ExcelObject(parseType = ParseType.ROW, start = 2, ignoreAllZerosOrNullRows = false)
public class Agent {

    @ExcelField(position = 1, validator = "@required @len(10, 100)")
    private String developerId;

    @ExcelField(position = 2, validator = "@required @len(2)")
    private String provinceCode;

    @ExcelField(position = 3, validator = "@required @len(3)")
    private String eparchyCode;

    @ExcelField(position = 4, validator = "@enum(0,1)")
    private int sex;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getEparchyCode() {
        return eparchyCode;
    }

    public void setEparchyCode(String eparchyCode) {
        this.eparchyCode = eparchyCode;
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)[dD]?$");
        String str = "-120";
        System.out.println(pattern.matcher(str).matches());

        RequiredValidator validator = new RequiredValidator();
        validator.validate(1); // toString
    }
}
