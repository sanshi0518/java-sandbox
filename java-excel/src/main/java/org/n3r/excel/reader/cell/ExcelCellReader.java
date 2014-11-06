package org.n3r.excel.reader.cell;

import org.apache.poi.ss.usermodel.Cell;
import org.n3r.excel.annotation.ExcelField;
import org.n3r.validator.EasyValidator;

import java.util.List;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-5 上午12:50
 */
public interface ExcelCellReader {

    Object read(Cell cell, boolean zeroIfNull);
}
