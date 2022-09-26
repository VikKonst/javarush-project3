package com.javarush.quest.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.quest.core.entity.QuestAction;
import com.javarush.quest.core.entity.QuestStep;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestInitializer {

    public Map<String, QuestStep> getQuestSteps() {
        File excel = retrieveQuestData("data.xlsx");
        File json = retrieveQuestData("quest.json");
        try {
           return createQuestSteps(excel, json);
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private File retrieveQuestData(String filename) {
        URL resource = getClass().getClassLoader().getResource(filename);
        if (resource == null) {
            throw new RuntimeException("Quest data is not found!");
        } else {
            return new File(resource.getFile());
        }
    }


    private Map<String, QuestStep> createQuestSteps(File fileExcel, File fileJson) throws IOException, InvalidFormatException {
        XSSFWorkbook workbook = new XSSFWorkbook(fileExcel);
        Map<String, QuestStep> questStepMap = new HashMap<>();
        for (QuestStep step : mapJson(fileJson)) {
            step = createDto(step, workbook);
            questStepMap.put(step.getId(), step);
        }
        return questStepMap;
    }

    private List<QuestStep> mapJson(File fileJson) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(fileJson, new TypeReference<>() {
        });
    }

    private QuestStep createDto(QuestStep step, XSSFWorkbook workbook) {
        step.setDescription(getCellValue(workbook, step.getDescription()));
        step.setId(getCellValue(workbook, step.getId()));
        for (QuestAction action : step.getActions()) {
            action.setTitle(getCellValue(workbook, action.getTitle()));
            action.setGoTo(getCellValue(workbook, action.getGoTo()));
        }
        return step;
    }

    private String getCellValue(XSSFWorkbook workbook, String cellName) {
        Pattern r = Pattern.compile("^([A-Z]+)([0-9]+)$");
        Matcher m = r.matcher(cellName);
        if (m.matches()) {
            String columnName = m.group(1);
            int rowNumber = Integer.parseInt(m.group(2));
            if (rowNumber > 0) {
                return workbook.getSheetAt(0).getRow(rowNumber - 1).getCell(CellReference.convertColStringToIndex(columnName)).getStringCellValue();
            }
        }
        return "";
    }


//    private void test() {
//        QuestStepRepository rep = new QuestStepRepository();
//        QuestStep questStep = new QuestStep();
//        rep.save(questStep.getId(), questStep);
//
//
//        UserRepository userRepository = new UserRepository();
//        User user = new User();
//        userRepository.save(user.getUsername(), user);
//    }
}

