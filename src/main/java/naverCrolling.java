import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class naverCrolling {

    private JPanel JPanel;
    private JLabel idLabel;
    private JLabel keyword;
    private JTextField idTextField;
    private JTextField filePath1;
    private JButton searchBtnAdmin;
    private JButton searchBtnCus;
    private JTextPane keywordArea;
    private JCheckBox viewChk;
    private JCheckBox viewBlogChk;
    private JCheckBox viewCafeChk;
    private JCheckBox videoChk;
    private JCheckBox allChk;
    private JButton fileBtn;
    private JScrollPane scrollText;
    private JLabel imageLabel;
    private String path;
    private String savedFilePath;
    private String pathStr;
    private String checkedStr;

    // 메인 실행
    public static void main(String[] args) throws IOException {

        // naverCrolling jFrame 세팅 및 실행
        JFrame frame = new JFrame("naverCrolling");
        String iconPath = System.getProperty("user.dir") + "\\jcIcon.png";
        String imageURL = iconPath;
        ImageIcon img = new ImageIcon(imageURL);
        frame.setIconImage(img.getImage());
        frame.setContentPane(new naverCrolling().JPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public naverCrolling() throws IOException {
        // 현재 작업중인 경로
        String dirPath = System.getProperty("user.dir");
        savedFilePath = dirPath + "\\pathSave";

        // 저장된 경로 가져오기
        filePathSearch(savedFilePath);

        // 이전 체크여부
        checkSearch(savedFilePath);

        // 검색시 이미지라벨 표출(검색 아닐경우 hide)
        imageLabel.setVisible(false);

        // keywordArea 사이즈 지정
        scrollText.setMinimumSize(new Dimension(200, 200));

        // 파일경로 표출
        filePath1.setEditable(false);

        // 파일 경로 eventListener
        fileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("naverCrolling");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(frame);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    path = file.getPath();
                    filePath1.setText(String.valueOf(path));
                }
            }
        });

        // search 버튼 eventListener
        searchBtnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IdSearchClass searchClass = new IdSearchClass();
                String vChk = "";  // VIEW 체크여부
                String vbChk = ""; // VIEW BLOG 체크여부
                String vcChk = ""; // VIEW CAFE 체크여부
                String viChk = ""; // 동영상 체크여부
                String alChk = ""; // 통합 체크여부
                String checkStr = ""; // 체크박스 체크 저장

                if(viewChk.isSelected()){
                    vChk = "chk";
                    checkStr += "vChk,";
                }
                if(viewBlogChk.isSelected()){
                    vbChk = "chk";
                    checkStr += "vbChk,";
                }
                if(viewCafeChk.isSelected()){
                    vcChk = "chk";
                    checkStr += "vcChk,";
                }
                if(videoChk.isSelected()){
                    viChk = "chk";
                    checkStr += "viChk,";
                }
                if(allChk.isSelected()){
                    alChk = "chk";
                    checkStr += "alChk,";
                }

                if(vChk.equals("") && vbChk.equals("") && vcChk.equals("") && viChk.equals("") && alChk.equals("")){
                    JOptionPane.showMessageDialog(null, "체크박스를 선택하세요.");
                    return;
                }

                if(idTextField.getText().equals("") || idTextField.getText() == null){
                    JOptionPane.showMessageDialog(null, "naver ID를 입력하세요.");
                    return;
                }

                if(keywordArea.getText().equals("") || keywordArea.getText() == null){
                    JOptionPane.showMessageDialog(null, "키워드를 입력하세요.");
                    return;
                }

                if(path == null || path.equals("")){
                    JOptionPane.showMessageDialog(null, "파일 저장경로를 설정하세요.");
                    return;
                }

                int completeNum = 0;

                if(completeNum == 0){
                    imageLabel.setVisible(true);
                    JOptionPane.showMessageDialog(null, "검색을 시작합니다.");
                }

                try {
                    completeNum = searchClass.idSearch(idTextField.getText(), keywordArea.getText(), vChk, vbChk, vcChk, viChk, alChk, "admin", path);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                if(completeNum == 1) {
                    imageLabel.setVisible(false);
                    JOptionPane.showMessageDialog(null, "excel 파일이 저장되었습니다.");
                    try {
                        if (checkStr.endsWith(",")) {
                            checkStr = checkStr.substring(0, checkStr.length() - 1);
                        }

                        filePathSave(filePath1.getText(), savedFilePath);
                        checkSave(checkStr, savedFilePath);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        searchBtnCus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IdSearchClass searchClass = new IdSearchClass();
                String vChk = "";  // VIEW 체크여부
                String vbChk = ""; // VIEW BLOG 체크여부
                String vcChk = ""; // VIEW CAFE 체크여부
                String viChk = ""; // 동영상 체크여부
                String alChk = ""; // 통합 체크여부
                String checkStr = ""; // 체크박스 체크 저장

                if(viewChk.isSelected()){
                    vChk = "chk";
                    checkStr += "vChk,";
                }
                if(viewBlogChk.isSelected()){
                    vbChk = "chk";
                    checkStr += "vbChk,";
                }
                if(viewCafeChk.isSelected()){
                    vcChk = "chk";
                    checkStr += "vcChk,";
                }
                if(videoChk.isSelected()){
                    viChk = "chk";
                    checkStr += "viChk,";
                }
                if(allChk.isSelected()){
                    alChk = "chk";
                    checkStr += "alChk,";
                }

                if(vChk.equals("") && vbChk.equals("") && vcChk.equals("") && viChk.equals("") && alChk.equals("")){
                    JOptionPane.showMessageDialog(null, "체크박스를 선택하세요.");
                    return;
                }

                if(idTextField.getText().equals("") || idTextField.getText() == null){
                    JOptionPane.showMessageDialog(null, "naver ID를 입력하세요.");
                    return;
                }

                if(keywordArea.getText().equals("") || keywordArea.getText() == null){
                    JOptionPane.showMessageDialog(null, "키워드를 입력하세요.");
                    return;
                }

                if(path == null || path.equals("")){
                    JOptionPane.showMessageDialog(null, "파일 저장경로를 설정하세요.");
                    return;
                }

                int completeNum = 0;

                if(completeNum == 0){
                    imageLabel.setVisible(true);
                    JOptionPane.showMessageDialog(null, "검색을 시작합니다.");
                }

                try {
                    completeNum = searchClass.idSearch(idTextField.getText(), keywordArea.getText(), vChk, vbChk, vcChk, viChk, alChk, "cus", path);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                if(completeNum == 1){
                    imageLabel.setVisible(false);
                    JOptionPane.showMessageDialog(null, "excel 파일이 저장되었습니다.");
                    try {
                        if (checkStr.endsWith(",")) {
                            checkStr = checkStr.substring(0, checkStr.length() - 1);
                        }

                        filePathSave(filePath1.getText(), savedFilePath);
                        checkSave(checkStr, savedFilePath);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

    }

    // 파일경로 위치
    public void filePathSearch(String savedPath) throws IOException {
        // 파일 존재 유무
        File file = new File(savedPath + "\\path.txt");
        boolean isExists = file.exists();

        if(isExists) {
            int nBuffer;
            BufferedReader reader = new BufferedReader(new FileReader(savedPath + "\\path.txt"));
            while ((nBuffer = reader.read()) != -1) {
                pathStr += (char)nBuffer;
            }

            if (pathStr == null || pathStr.equals("")){
                pathStr = "";
            }else{
                pathStr = pathStr.replaceAll("null", "");
            }
            System.out.println(pathStr);
            path = pathStr;
            filePath1.setText(String.valueOf(pathStr));
            reader.close();
        } else {
            try{
                file.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    // 검색 후 파일경로 저장
    public void filePathSave(String nowFilePath, String rootPath) throws IOException {
        File file = new File(rootPath + "\\path.txt");
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file));
        buffWrite.write(nowFilePath, 0, nowFilePath.length());
        buffWrite.flush();
        buffWrite.close();
    }

    // 체크여부
    public void checkSearch(String savedPath) throws IOException {
        // 파일 존재 유무
        File file = new File(savedPath + "\\check.txt");
        boolean isExists = file.exists();

        if(isExists) {
            int nBuffer;
            BufferedReader reader = new BufferedReader(new FileReader(savedPath + "\\check.txt"));
            while ((nBuffer = reader.read()) != -1) {
                checkedStr += (char)nBuffer;
            }

            if (checkedStr == null || checkedStr.equals("")){
                checkedStr = "";
            }else{
                checkedStr = checkedStr.replaceAll("null", "");
            }
            System.out.println(checkedStr);
            String[] chkArr = checkedStr.split(",");

            for(int i = 0; i < chkArr.length; i++){
                if(chkArr[i].equals("vChk")){
                    viewChk.setSelected(true);
                }else if(chkArr[i].equals("vbChk")){
                    viewBlogChk.setSelected(true);
                }else if(chkArr[i].equals("vcChk")){
                    viewCafeChk.setSelected(true);
                }else if(chkArr[i].equals("viChk")){
                    videoChk.setSelected(true);
                }else if(chkArr[i].equals("alChk")){
                    allChk.setSelected(true);
                }
            }
            reader.close();
        } else {
            try{
                file.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    // 검색 후 체크박스 저장
    public void checkSave(String checkStr, String rootPath) throws IOException {
        File file = new File(rootPath + "\\check.txt");
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file));
        buffWrite.write(checkStr, 0, checkStr.length());
        buffWrite.flush();
        buffWrite.close();
    }
}
