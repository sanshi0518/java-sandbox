package org.n3r.excel.reader.cell;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.n3r.validator.EasyValidator;
import org.n3r.validator.EasyValidatorInvoker;

import java.text.DecimalFormat;
import java.util.List;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-5 上午12:55
 */
public class StringCellReader implements ExcelCellReader {

    private final DecimalFormat decimalFormat = new DecimalFormat("###.#");

    @Override
    public Object read(Cell cell, boolean zeroIfNull) {
        if (cell == null) return zeroIfNull ? "0" : null;

        int cellType = cell.getCellType();

        if (cellType == Cell.CELL_TYPE_FORMULA) {
            int formulaResultType = cell.getCachedFormulaResultType();
            switch (formulaResultType) {
                case Cell.CELL_TYPE_NUMERIC:
                    return decimalFormat.format(cell.getNumericCellValue());
                case Cell.CELL_TYPE_STRING:
                    return cell.getRichStringCellValue().getString().trim();
                case Cell.CELL_TYPE_BOOLEAN:
                    return "" + cell.getBooleanCellValue();
                default:
                    return "";
            }
        }

        return cellType == Cell.CELL_TYPE_NUMERIC ? decimalFormat.format(cell.getNumericCellValue()) :
                cell.getRichStringCellValue().getString().trim();
    }

}
