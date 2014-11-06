package org.n3r.excel.reader.cell;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-5 上午12:55
 */
public class DateCellReader implements ExcelCellReader {

    @Override
    public Object read(Cell cell, boolean zeroIfNull) {
        return DateUtil.isCellDateFormatted(cell) ?
                DateUtil.getJavaDate(cell.getNumericCellValue()) : cell.getStringCellValue(); // invalid date format
    }

}
