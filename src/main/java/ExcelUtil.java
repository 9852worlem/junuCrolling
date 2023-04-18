import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExcelUtil {
    private int rowNum = 0;
    public String btnTypeStr = "";

    //File로 만들 경우
    public void createExcelToFile(List<String> dataList, String path) throws FileNotFoundException, IOException {
        Workbook workbook = new SXSSFWorkbook(); // 성능 개선 버전
        Sheet sheet = workbook.createSheet("데이터");

        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMddHHmmss"; //hhmmss로 시간,분,초만 뽑기도 가능
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        String formatDate = formatter.format(today);

        rowNum = 0;

        createExcel(sheet, dataList);
        String[] strArr1 = dataList.get(0).toString().split("/"); // 0: 키워드 1: 데이터
        String[] strArr2 = strArr1[1].split("\\$"); // 체크박스에 따른 리스트 수
        String[] strArr3 = strArr2[0].split("\\."); // 체크타입, 노출, 순위 순으로 배열생성

        btnTypeStr = strArr3[3].toString();
        if(btnTypeStr.equals("admin")){
            btnTypeStr = "관리자용";
        }else if (btnTypeStr.equals("cus")){
            btnTypeStr = "고객용";
        }

        String filepath = path + "\\" + formatDate + "_" + btnTypeStr + ".xlsx";

        FileOutputStream fos = new FileOutputStream(new File(filepath));
        workbook.write(fos);
        fos.flush();
        fos.close();

    }

    //엑셀 생성
    private void createExcel(Sheet sheet, List<String> dataList) {
        Workbook workbook = new SXSSFWorkbook(); // 성능 개선 버전
        Row row = null;
        Cell cell = null;
        int rowCount = 0;
        int cellCount = 0;

        // 테이블 헤더용 스타일
        CellStyle backgroundStyle = getTitleCellStyle(sheet.getWorkbook());
        CellStyle datagroundStyle = getDataCellStyle(sheet.getWorkbook());


        //열 생성
        row = sheet.createRow(rowCount++);
        cellCount = 0; //셀 카운트 초기화

        // 셀 병합
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0)); // No 행합
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 1, 2)); // 키워드 행합

        // 통합
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 4));

        // VIEW
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 10));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 6));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 8));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 9, 10));

        // 동영상
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 11, 12));

        // No
        cell = row.createCell(0);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("No");

        // 키워드
        cell = row.createCell(1);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("키워드");

        // 통합, VIEW, 동영상
        cell = row.createCell(3);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("통합");

        cell = row.createCell(5);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("VIEW");

        cell = row.createCell(11);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("동영상");

        // row = 1
        row = sheet.createRow(1);
        cell = row.createCell(3);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("VIEW");

        cell = row.createCell(5);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("전체");

        cell = row.createCell(7);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("블로그");

        cell = row.createCell(9);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("카페");

        cell = row.createCell(11);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("관련도");

        // row = 2
        row = sheet.createRow(2);
        cell = row.createCell(3);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("노출");

        cell = row.createCell(4);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("순위");

        cell = row.createCell(5);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("노출");

        cell = row.createCell(6);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("순위");

        cell = row.createCell(7);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("노출");

        cell = row.createCell(8);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("순위");

        cell = row.createCell(9);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("노출");

        cell = row.createCell(10);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("순위");

        cell = row.createCell(11);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("노출");

        cell = row.createCell(12);
        cell.setCellStyle(backgroundStyle);
        cell.setCellValue("순위");


        rowCount = 3;
        cellCount = 9;


        //데이를 한개씩 조회해서 한개의 행으로 만든다.
        for (int i = 0; i < dataList.size(); i++){
            String[] strArr1 = dataList.get(i).toString().split("/"); // 0: 키워드 1: 데이터
            String[] strArr2 = strArr1[1].split("\\$"); // 체크박스에 따른 리스트 수
            //String[] strArr3 = strArr2[0].split("\\."); // 체크타입, 노출, 순위 순으로 배열생성
            String keyword = strArr1[0].toString();

            cellCount = 0;
            sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 1, 2)); // 키워드 행합
            row = sheet.createRow(rowCount++);

            // No
            cell = row.createCell(cellCount++);
            cell.setCellValue("" + (i + 1));
            cell.setCellStyle(datagroundStyle);

            // 키워드
            cell = row.createCell(cellCount++);
            cell.setCellValue(keyword);
            cell.setCellStyle(datagroundStyle);


            int arrCntAl = 0;
            for(int j = 0; j < strArr2.length; j++){
                if(strArr2[j].toString().contains("alChk")){
                    String[] strArr3 = strArr2[j].split("\\."); // 체크타입, 노출, 순위 순으로 배열생성

                    // 통합 - 노출
                    cellCount++;
                    cell = row.createCell(cellCount++);
                    cell.setCellValue(Integer.parseInt(strArr3[1].toString()));
                    cell.setCellStyle(datagroundStyle);

                    // 통합 - 순위
                    cell = row.createCell(cellCount++);
                    cell.setCellValue(strArr3[2].toString());
                    cell.setCellStyle(datagroundStyle);
                    break;
                }else{
                    arrCntAl++;
                }

                if(arrCntAl == strArr2.length){
                    // 통합 - 노출
                    cellCount++;
                    cell = row.createCell(cellCount++);
                    cell.setCellValue("-");
                    cell.setCellStyle(datagroundStyle);

                    // 통합 - 순위
                    cell = row.createCell(cellCount++);
                    cell.setCellValue("-");
                    cell.setCellStyle(datagroundStyle);
                }
            }

            int arrCntV = 0;
            for(int j = 0; j < strArr2.length; j++){
                if(strArr2[j].toString().contains("vChk")){
                    String[] strArr3 = strArr2[j].split("\\."); // 체크타입, 노출, 순위 순으로 배열생성
                    // VIEW - 전체 - 노출
                    cell = row.createCell(cellCount++);
                    cell.setCellValue(Integer.parseInt(strArr3[1].toString()));
                    cell.setCellStyle(datagroundStyle);

                    // VIEW - 전체 - 순위
                    cell = row.createCell(cellCount++);
                    cell.setCellValue(strArr3[2].toString());
                    cell.setCellStyle(datagroundStyle);
                    break;
                }else{
                    arrCntV++;
                }

                if (arrCntV == strArr2.length){
                    // VIEW - 전체 - 노출
                    cell = row.createCell(cellCount++);
                    cell.setCellValue("-");
                    cell.setCellStyle(datagroundStyle);

                    // VIEW - 전체 - 순위
                    cell = row.createCell(cellCount++);
                    cell.setCellValue("-");
                    cell.setCellStyle(datagroundStyle);
                }
            }

            int arrCntVb = 0;
            for(int j = 0; j < strArr2.length; j++){
                if(strArr2[j].toString().contains("vbChk")){
                    String[] strArr3 = strArr2[j].split("\\."); // 체크타입, 노출, 순위 순으로 배열생성
                    // VIEW - 블로그 - 노출
                    cell = row.createCell(cellCount++);
                    cell.setCellValue(Integer.parseInt(strArr3[1].toString()));
                    cell.setCellStyle(datagroundStyle);

                    // VIEW - 블로그 - 순위
                    cell = row.createCell(cellCount++);
                    cell.setCellValue(strArr3[2].toString());
                    cell.setCellStyle(datagroundStyle);
                    break;
                }else{
                    arrCntVb++;
                }

                if(arrCntVb == strArr2.length){
                    // VIEW - 블로그 - 노출
                    cell = row.createCell(cellCount++);
                    cell.setCellValue("-");
                    cell.setCellStyle(datagroundStyle);

                    // VIEW - 블로그 - 순위
                    cell = row.createCell(cellCount++);
                    cell.setCellValue("-");
                    cell.setCellStyle(datagroundStyle);
                }
            }


            int arrCntVc = 0;
            for(int j = 0; j < strArr2.length; j++){
                if(strArr2[j].toString().contains("vcChk")){
                    String[] strArr3 = strArr2[j].split("\\."); // 체크타입, 노출, 순위 순으로 배열생성
                    // VIEW - 카페 - 노출
                    cell = row.createCell(cellCount++);
                    cell.setCellValue(Integer.parseInt(strArr3[1].toString()));
                    cell.setCellStyle(datagroundStyle);

                    // VIEW - 카페 - 순위
                    cell = row.createCell(cellCount++);
                    cell.setCellValue(strArr3[2].toString());
                    cell.setCellStyle(datagroundStyle);
                    break;
                }else{
                    arrCntVc++;
                }

                if(arrCntVc == strArr2.length){
                    // VIEW - 카페 - 노출
                    cell = row.createCell(cellCount++);
                    cell.setCellValue("-");
                    cell.setCellStyle(datagroundStyle);

                    // VIEW - 카페 - 순위
                    cell = row.createCell(cellCount++);
                    cell.setCellValue("-");
                    cell.setCellStyle(datagroundStyle);
                }
            }


            int arrCntVi = 0;
            for(int j = 0; j < strArr2.length; j++){
                if(strArr2[j].toString().contains("viChk")){
                    String[] strArr3 = strArr2[j].split("\\."); // 체크타입, 노출, 순위 순으로 배열생성
                    // 동영상 - 노출
                    cell = row.createCell(cellCount++);
                    cell.setCellValue(Integer.parseInt(strArr3[1].toString()));
                    cell.setCellStyle(datagroundStyle);

                    // 동영상 - 순위
                    cell = row.createCell(cellCount++);
                    cell.setCellValue(strArr3[2].toString());
                    cell.setCellStyle(datagroundStyle);
                    break;
                }else{
                    arrCntVi++;
                }

                if(arrCntVi == strArr2.length){
                    // 동영상 - 노출
                    cell = row.createCell(cellCount++);
                    cell.setCellValue("-");
                    cell.setCellStyle(datagroundStyle);

                    // 동영상 - 순위
                    cell = row.createCell(cellCount++);
                    cell.setCellValue("-");
                    cell.setCellStyle(datagroundStyle);
                }
            }
        }
    }


    public static CellStyle getTitleCellStyle(Workbook workbook) {
        CellStyle backgroundStyle = workbook.createCellStyle();

        backgroundStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        backgroundStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        backgroundStyle.setAlignment(CellStyle.ALIGN_CENTER); // 가운데 정렬
        backgroundStyle.setBorderBottom(CellStyle.BORDER_THIN); // 테두리 두껍게
        backgroundStyle.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        return backgroundStyle;
    }

    public static CellStyle getDataCellStyle(Workbook workbook) {
        CellStyle backgroundStyle = workbook.createCellStyle();

        backgroundStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        backgroundStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        backgroundStyle.setAlignment(CellStyle.ALIGN_CENTER); // 가운데 정렬
        backgroundStyle.setBorderBottom(CellStyle.BORDER_THIN); // 테두리 두껍게
        backgroundStyle.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle.setBorderTop(CellStyle.BORDER_THIN);

        return backgroundStyle;
    }
}
