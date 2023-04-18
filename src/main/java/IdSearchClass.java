import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IdSearchClass {
    // 엑셀에 필요한 dataList
    private List<String> dataList = new ArrayList<String>();
    // No 카운트
    private int noCnt = 0;

    public int idSearch(String idText, String keyword, String vChk, String vbChk, String vcChk, String viChk, String alChk, String btnType, String path) throws IOException {
        List<String> keywordList = new ArrayList<String>();
        String url = "";

        dataList.clear();

        if(keyword.contains("\n")){
            keywordList = Arrays.asList(keyword.split("\\n"));
        }else{
            keywordList = Arrays.asList(keyword);
        }

        if(keyword.equals("")){
            keywordList = null;
        }

        if(keywordList.size() > 1){
            for (int i = 0; i < keywordList.size(); i++){
                String chkType = "";
                String resultStr = "";
                resultStr += keywordList.get(i).toString() + "/";

                if(alChk.equals("chk")){
                    chkType = "alChk";
                    url = chkUrlMethod(chkType, keywordList.get(i).toString());
                    resultStr += crollingMethod(url, idText, keywordList.get(i).toString(), chkType, btnType);
                }
                if(vChk.equals("chk")){
                    chkType = "vChk";
                    url = chkUrlMethod(chkType, keywordList.get(i).toString());
                    resultStr += crollingMethod(url, idText, keywordList.get(i).toString(), chkType, btnType);
                }
                if(vbChk.equals("chk")){
                    chkType = "vbChk";
                    url = chkUrlMethod(chkType, keywordList.get(i).toString());
                    resultStr += crollingMethod(url, idText, keywordList.get(i).toString(), chkType, btnType);
                }
                if(vcChk.equals("chk")){
                    chkType = "vcChk";
                    url = chkUrlMethod(chkType, keywordList.get(i).toString());
                    resultStr += crollingMethod(url, idText, keywordList.get(i).toString(), chkType, btnType);
                }
                if(viChk.equals("chk")){
                    chkType = "viChk";
                    url = chkUrlMethod(chkType, keywordList.get(i).toString());
                    resultStr += crollingMethod(url, idText, keywordList.get(i).toString(), chkType, btnType);
                }

                dataList.add(resultStr);
            }
        }else{
            String chkType = "";
            String resultStr = "";
            resultStr += keywordList.get(0).toString() + "/";

            if(alChk.equals("chk")){
                chkType = "alChk";
                url = chkUrlMethod(chkType, keywordList.get(0).toString());
                resultStr += crollingMethod(url, idText, keywordList.get(0).toString(), chkType, btnType);
            }
            if(vChk.equals("chk")){
                chkType = "vChk";
                url = chkUrlMethod(chkType, keywordList.get(0).toString());
                resultStr += crollingMethod(url, idText, keywordList.get(0).toString(), chkType, btnType);
            }
            if(vbChk.equals("chk")){
                chkType = "vbChk";
                url = chkUrlMethod(chkType, keywordList.get(0).toString());
                resultStr += crollingMethod(url, idText, keywordList.get(0).toString(), chkType, btnType);
            }
            if(vcChk.equals("chk")){
                chkType = "vcChk";
                url = chkUrlMethod(chkType, keywordList.get(0).toString());
                resultStr += crollingMethod(url, idText, keywordList.get(0).toString(), chkType, btnType);
            }
            if(viChk.equals("chk")){
                chkType = "viChk";
                url = chkUrlMethod(chkType, keywordList.get(0).toString());
                resultStr += crollingMethod(url, idText, keywordList.get(0).toString(), chkType, btnType);
            }

            dataList.add(resultStr);
        }

        excelDown(dataList, path);

        return 1;
    }

    public String chkUrlMethod(String chkType, String keyword){
        String url = "";

        if(chkType.equals("alChk")){
            url = "https://search.naver.com/search.naver?where=nexearch&sm=tab_jum&query=" + keyword;
        }
        if(chkType.equals("vChk")){
            url = "https://search.naver.com/search.naver?where=view&sm=tab_jum&query=" + keyword;
        }
        if(chkType.equals("vbChk")){
            url = "https://search.naver.com/search.naver?query=" + keyword + "&nso=&where=blog&sm=tab_opt";
        }
        if(chkType.equals("vcChk")){
            url = "https://search.naver.com/search.naver?where=article&sm=tab_viw.blog&query=" + keyword + "&nso=";
        }
        if(chkType.equals("viChk")){
            url = "https://search.naver.com/search.naver?where=video&sm=tab_jum&query=" + keyword;
        }

        return url;
    }


    public String crollingMethod(String url, String idText, String keyword, String chkType, String btnType) throws IOException {
        Document doc = null;
        String grade = "";
        int cnt = 0;
        String chkString = "";
        url = url.replaceAll("\r", "");
        Map<String, Object> paramMap = new HashMap<String, Object>();

        try {
            doc = Jsoup.connect(url).get();
        }catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("===========================================");

        Elements element = null;
        List<Element> ie1 = null;
        List<Element> ie2 = null;

        if(chkType.equals("viChk")){
            element = doc.select("div.list_inner");
            ie1 = element.select("a.info_title");
            ie2 = element.select("a[href$=https://blog.naver.com/" + idText + "].link");
        }else{
            element = doc.select("ul.lst_total");
            ie1 = element.select("a.sub_txt");
            ie2 = element.select("a[href$=https://blog.naver.com/" + idText + "].sub_txt");
        }



        if(btnType.equals("admin")){ // 관리자용 버튼
            for(int i = 0; i < ie1.size(); i++){
                if(ie1.get(i).toString().contains(idText)){
                    grade += (i+1) + ",";
                }
            }

            for(int i = 0; i < ie2.size(); i++){
                cnt++;
            }
        }else if(btnType.equals("cus")){ // 고객용 버튼
            for(int i = 0; i < ie1.size(); i++){
                if(i < 10){
                    if(ie1.get(i).toString().contains(idText)){
                        grade += (i+1) + ",";
                        cnt++;
                        break;
                    }
                }else{
                    grade = "";
                    break;
                }
            }

            for(int i = 0; i < ie2.size(); i++){
                cnt++;
            }
        }

        if(chkType.equals("alChk")){
            chkString = "통합";
        }
        if(chkType.equals("vChk")){
            chkString = "VIEW";
        }
        if(chkType.equals("vbChk")){
            chkString = "VIEW(블로그)";
        }
        if(chkType.equals("vcChk")){
            chkString = "VIEW(카페)";
        }
        if(chkType.equals("viChk")){
            chkString = "동영상";
        }

        if(grade.equals("")){
            grade = "0";
        }else{
            grade = grade.substring(0, grade.length()-1);
        }

        System.out.println("===========================================");
        System.out.println("키워드 : " + keyword + " " + chkString + " -- " + " 순위 : " + grade + " 노출 : " + cnt);
        String result = chkType + "." + cnt + "." + grade + "." + btnType + "$";

        return result;
    }

    public void excelDown(List<String> dataList, String path) throws IOException {
        ExcelUtil excelUtil = new ExcelUtil();
        excelUtil.createExcelToFile(dataList, path);
    }
}
