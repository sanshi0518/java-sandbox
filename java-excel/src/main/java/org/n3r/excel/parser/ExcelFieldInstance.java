package org.n3r.excel.parser;

import org.n3r.validator.EasyValidator;

import java.lang.reflect.Field;
import java.util.List;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-5 下午4:56
 */
public class ExcelFieldInstance {

    private Field field;

    private int position;

    private boolean zeroIfNull;

    private List<EasyValidator> validators;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isZeroIfNull() {
        return zeroIfNull;
    }

    public void setZeroIfNull(boolean zeroIfNull) {
        this.zeroIfNull = zeroIfNull;
    }

    public List<EasyValidator> getValidators() {
        return validators;
    }

    public void setValidators(List<EasyValidator> validators) {
        this.validators = validators;
    }
}
