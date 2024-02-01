package com.uccwall.crefolder;

import org.apache.poi.xslf.usermodel.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.FileReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@SpringBootApplication
public class CrefolderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrefolderApplication.class, args);



        try {
            // JSON 파일 경로 설정
            String jsonFilePath = "C:\\Users\\user\\Desktop\\ExampleFolder\\jsonFile.json";

            // JSON 파일 읽기
            FileReader fileReader = new FileReader(jsonFilePath);
            StringBuilder jsonString = new StringBuilder();
            int character;

            while ((character = fileReader.read()) != -1) {
                jsonString.append((char) character);
            }

            // JSON 데이터 파싱
            JSONArray foldersArray = new JSONArray(jsonString.toString());

            // 각 폴더에 대해 반복
            for (int i = 0; i < foldersArray.length(); i++) {
                JSONObject folderObject = foldersArray.getJSONObject(i);

                // 각 폴더의 이름과 경로 가져오기
                String folderName = folderObject.getString("name");
                String folderPath = folderObject.getString("path");

                // 폴더 생성
                Path path = Paths.get(folderPath, folderName);
                Files.createDirectories(path);
                System.out.println("폴더 " + folderName + " 가 성공적으로 생성되었습니다.");

                // PPT 파일 생성 (폴더 이름과 동일한 이름 사용)
                createPptFile(path.toString(), folderName + ".pptx");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createPptFile(String folderPath, String pptFileName) {
        // PPT 파일 경로 설정
        String pptFilePath = Paths.get(folderPath, pptFileName).toString();

        // Apache POI를 사용하여 PPT 파일 생성
        try (XMLSlideShow ppt = new XMLSlideShow()) {
            // 빈 슬라이드 생성
            XSLFSlideMaster defaultMaster = ppt.createSlide().getSlideMaster();
            XSLFSlideLayout titleSlideLayout = defaultMaster.getLayout(SlideLayout.TITLE);
            XSLFSlide slide = ppt.createSlide(titleSlideLayout);

            // 제목과 내용 추가 (예시로 "제목"과 "내용"으로 설정)
            slide.getPlaceholder(0).setText("제목");
            slide.getPlaceholder(1).setText("내용");

            // PPT 파일 저장
            ppt.write(new java.io.FileOutputStream(pptFilePath));
            System.out.println("PPT 파일 " + pptFileName + " 가 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
