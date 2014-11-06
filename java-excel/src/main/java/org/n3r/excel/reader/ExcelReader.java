package org.n3r.excel.reader;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.n3r.core.joor.Reflect;
import org.n3r.core.lang.RClass;
import org.n3r.excel.annotation.ExcelObject;
import org.n3r.excel.annotation.NestedExcelObject;
import org.n3r.excel.annotation.ParseType;
import org.n3r.excel.parser.ExcelFieldInstance;
import org.n3r.excel.parser.ExcelObjectParser;
import org.n3r.excel.reader.cell.CellReaderContext;
import org.n3r.validator.EasyValidator;
import org.n3r.validator.EasyValidatorInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-4 下午3:23
 */
public class ExcelReader {
    private static final Logger LOG = LoggerFactory.getLogger(ExcelReader.class);
    private File file;
    private int sheetIndex;
    private String sheetName;
    private Sheet sheet;

    // TODO: WhenException?

    public ExcelReader file(File file) {
        this.file = file;
        return this;
    }

    public ExcelReader sheet(int sheetIndex) {
        this.sheetIndex = sheetIndex;
        return this;
    }

    public ExcelReader sheet(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    private void loadSheet() {
        if (file == null)
            throw new RuntimeException("Excel file is required!");

        if (sheet != null) return;

        try {
            Workbook workbook = WorkbookFactory.create(file);
            // sheetName is prior to sheetIndex
            sheet = StringUtils.isEmpty(sheetName) ? workbook.getSheetAt(sheetIndex) :
                    workbook.getSheet(sheetName);
        } catch (Exception e) {
            LOG.error("Loading excel sheet error!", e);
            throw new RuntimeException("Loading excel sheet error!", e);
        }
    }

    private <T> ExcelObject getExcelObjectAnnotation(Class<T> clazz) {
        ExcelObject excelObject = clazz.getAnnotation(ExcelObject.class);

        if (excelObject == null) throw new RuntimeException("Class must be annotated by ExcelObject");

        return excelObject;
    }

    private int tuningEndBoundary(ExcelObject excelObject) {
        int end = excelObject.end();

        if (excelObject.parseType() == ParseType.ROW) {
            int lastRowNum = sheet.getLastRowNum();
            return (end >= 0 && end <= lastRowNum) ? end : lastRowNum;
        }

        if (end < 0) throw new RuntimeException("End boundary should be greater than zero when ParseType is COLUMN");
        return end;
    }

    public <T> List<T> read(Class<T> clazz) {
        loadSheet();

        ExcelObject excelObject = getExcelObjectAnnotation(clazz);

        int endBoundary = tuningEndBoundary(excelObject);

        List<T> result = Lists.newArrayList();

        for (int cursor = excelObject.start(); cursor < endBoundary; cursor++) {
            T excelBean = instantiateExcelBean(clazz, excelObject, cursor);

            instantiateNestedExcelBean(clazz, excelBean);

            result.add(excelBean);
        }

        return result;
    }

    private Cell getCell(int rowIndex, int columnIndex) {
        Row row = sheet.getRow(rowIndex);
        return row == null ? null : row.getCell(columnIndex);
    }

    private <T> T instantiateExcelBean(Class<T> clazz, ExcelObject excelObject, int cursor) {
        T excelBean = Reflect.on(clazz).get();

        List<ExcelFieldInstance> excelFieldInstances = ExcelObjectParser.parse(clazz);

        for (ExcelFieldInstance fieldInstance : excelFieldInstances) {
            int position = fieldInstance.getPosition();
            boolean zeroIfNull = fieldInstance.isZeroIfNull();
            Field field = fieldInstance.getField();
            List<EasyValidator> validators = fieldInstance.getValidators();

            Cell cell = ParseType.ROW == excelObject.parseType() ? getCell(cursor, position) :
                    getCell(position, cursor);

            Object cellValue = new CellReaderContext(field.getType()).read(cell, zeroIfNull);

            EasyValidatorInvoker.invoke(cellValue, validators);

            setExcelFieldValue(field, excelBean, cellValue);
        }

        return excelBean;
    }

    private <T> void instantiateNestedExcelBean(Class<T> clazz, T excelBean) {
        List<Field> nestedFields = getNestedExcelObjectFields(clazz);
        for (Field nestedField : nestedFields) {
            Class<?> nestedFieldType = nestedField.getType();

            List<?> nestedExcelBeans = read(RClass.isAssignable(nestedFieldType, List.class) ?
                    getGenericType(nestedField) : nestedFieldType);

            if (RClass.isAssignable(nestedFieldType, List.class))
                setExcelFieldValue(nestedField, excelBean, nestedExcelBeans);
            else if (CollectionUtils.isNotEmpty(nestedExcelBeans))
                setExcelFieldValue(nestedField, excelBean, nestedExcelBeans.get(0));
        }
    }

    private <T> void setExcelFieldValue(Field field, T excelBean, Object cellValue) {
        try {
            field.set(excelBean, cellValue);
        } catch (Exception e) {
            LOG.error("Exception occured while setting field value", e);
            throw new RuntimeException("Exception occured while setting field value ", e);
        }
    }

    private <T> List<Field> getNestedExcelObjectFields(Class<T> clazz) {
        List<Field> fields = Lists.newArrayList();
        for (Field field : clazz.getDeclaredFields()) {
            NestedExcelObject nestedExcelObject = field.getAnnotation(NestedExcelObject.class);
            if (nestedExcelObject != null) {
                field.setAccessible(true);
                fields.add(field);
            }
        }
        return fields;
    }

    private Class<?> getGenericType(Field field) {
        Type type = field.getGenericType();
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            return (Class<?>) pt.getActualTypeArguments()[0];
        }

        return null;
    }

}
