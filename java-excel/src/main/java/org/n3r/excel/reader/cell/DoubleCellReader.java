package org.n3r.excel.reader.cell;

import org.apache.poi.ss.usermodel.Cell;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-5 上午12:57
 */
public class DoubleCellReader implements ExcelCellReader {


    @Override
    public Object read(Cell cell, boolean zeroIfNull) {
        if (cell == null) return zeroIfNull ? 0d : null;

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
            case Cell.CELL_TYPE_FORMULA:
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_BLANK:
                return zeroIfNull ? 0d : null;
            default:
                // Invalid number format
                return cell.getStringCellValue();
        }
    }

}
