package exam.nsj.com.exam.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import exam.nsj.com.exam.model.ExamModel;

public class ExcelHelper {

    private static final String[] HEADERS = {
        "ID", "\u9898\u76ee\u5185\u5bb9", "\u9898\u76ee\u7c7b\u578b",
        "\u9009\u98791", "\u9009\u98792", "\u9009\u98793",
        "\u9009\u98794", "\u9009\u98795", "\u9009\u98796", "\u7b54\u6848"
    };

    public static List<ExamModel> importFromStream(InputStream is) throws Exception {
        List<ExamModel> list = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            ExamModel exam = new ExamModel();
            exam.setIdOrig(getLongCell(row, 0));
            exam.setContent(getStringCell(row, 1));
            exam.setType(getStringCell(row, 2));
            exam.setOption1(getStringCell(row, 3));
            exam.setOption2(getStringCell(row, 4));
            exam.setOption3(getStringCell(row, 5));
            exam.setOption4(getStringCell(row, 6));
            exam.setOption5(getStringCell(row, 7));
            exam.setOption6(getStringCell(row, 8));
            exam.setAnswer(getStringCell(row, 9));
            if (exam.getContent() != null && !exam.getContent().isEmpty()) {
                list.add(exam);
            }
        }
        workbook.close();
        return list;
    }

    public static void exportToStream(List<ExamModel> exams, OutputStream os) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("\u9898\u5e93");

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
            cell.setCellStyle(headerStyle);
        }

        for (int i = 0; i < exams.size(); i++) {
            ExamModel exam = exams.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(exam.getIdOrig());
            row.createCell(1).setCellValue(nvl(exam.getContent()));
            row.createCell(2).setCellValue(nvl(exam.getType()));
            row.createCell(3).setCellValue(nvl(exam.getOption1()));
            row.createCell(4).setCellValue(nvl(exam.getOption2()));
            row.createCell(5).setCellValue(nvl(exam.getOption3()));
            row.createCell(6).setCellValue(nvl(exam.getOption4()));
            row.createCell(7).setCellValue(nvl(exam.getOption5()));
            row.createCell(8).setCellValue(nvl(exam.getOption6()));
            row.createCell(9).setCellValue(nvl(exam.getAnswer()));
        }

        for (int i = 0; i < HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(os);
        workbook.close();
    }

    private static String getStringCell(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue().trim();
            case NUMERIC:
                double val = cell.getNumericCellValue();
                if (val == Math.floor(val)) return String.valueOf((long) val);
                return String.valueOf(val);
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try { return cell.getStringCellValue().trim(); }
                catch (Exception e) { return String.valueOf(cell.getNumericCellValue()); }
            default: return "";
        }
    }

    private static long getLongCell(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return 0;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (long) cell.getNumericCellValue();
        }
        try { return Long.parseLong(cell.getStringCellValue().trim()); }
        catch (Exception e) { return 0; }
    }

    private static String nvl(String s) {
        return s == null ? "" : s;
    }
}
