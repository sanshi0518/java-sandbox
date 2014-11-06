package org.n3r.excel.reader.cell;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Date;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-5 上午2:47
 */
public class CellReaderContext {

    private ExcelCellReader cellReader = null;

    public <T> CellReaderContext(Class<T> clazz) {

        if (clazz.equals(String.class))
            cellReader = new StringCellReader();
        else if (clazz.equals(Date.class))
            cellReader = new DateCellReader();
        else if (clazz.equals(Integer.class) || clazz.equals(Long.class) || clazz.equals(Double.class))
            cellReader = new DoubleCellReader();
        else
            throw new RuntimeException(clazz.getName() + " Data type not supported for reading.");
    }

    public Object read(Cell cell, boolean zeroIfNull) {
        return cellReader.read(cell, zeroIfNull);
    }

}
