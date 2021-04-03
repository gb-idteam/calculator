package ru.systemairac.calculator.service.allimplement;

import be.quodlibet.boxable.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.service.allinterface.PDDocumentService;

import java.io.File;
import java.io.IOException;

public class PDDocumentServiceImpl implements PDDocumentService {

    private static final float fontSizeSmall = 10;
    private static final float fontSizeNormal = 12;
    private static final float fontSizeBig = 14;

    private static final String PATH_TO_FONT_FILE = "src/main/resources/arial.ttf";


    // TODO: refactor
    @Override
    public PDDocument toPDDocument(User user, ProjectDto projectDto, TechDataDto techDataDto) throws IOException {

        PDPage myPage = new PDPage(PDRectangle.A4);
        PDDocument mainDocument = new PDDocument();
        PDFont font = PDType0Font.load(mainDocument, new File(PATH_TO_FONT_FILE));
        mainDocument.addPage(myPage);
        PDPageContentStream contentStream = new PDPageContentStream(mainDocument, myPage);
        contentStream.setFont(font, 22);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText("Проект");
        contentStream.endText();

        float margin = 50;
        float yStartNewPage = myPage.getMediaBox().getHeight() - (2 * margin);
        float tableWidth = myPage.getMediaBox().getWidth() - (2 * margin);

        boolean drawContent = true;
        float bottomMargin = 70;
        float yPosition = 720;

        BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, mainDocument, myPage, false, drawContent);

        Row<PDPage> row;
        Cell<PDPage> cell;

        row = table.createRow(12);
        cell = row.createCell(100, "Наименование проекта: " + projectDto.getTitle());
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        row = table.createRow(12);
        cell = row.createCell(100, "Адрес проекта: " + projectDto.getAddress());
        cell.setFont(font);
        cell.setFontSize(fontSizeSmall);
        row = table.createRow(12);
        cell = row.createCell(100, "Заказчик/клиент");
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell.setAlign(HorizontalAlignment.CENTER);
        row = table.createRow(12);
        cell = row.createCell(100, "Компания: " + user.getNameCompany());
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        row = table.createRow(12);
        cell = row.createCell(100, "Имя: " + user.getFullName());
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        row = table.createRow(12);
        cell = row.createCell(100, "Телефон: " + user.getPhone());
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        row = table.createRow(12);
        cell = row.createCell(100, "email: " + user.getEmail());
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);

        table.draw();

        yPosition -= table.getHeaderAndDataHeight() + 20;
        table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, mainDocument, myPage, false, drawContent);

        row = table.createRow(12);
        cell = row.createCell(25, "");
        cell = row.createCell(50/2f, "Вход");
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell.setAlign(HorizontalAlignment.CENTER);
        cell = row.createCell(50/2f, "Выход");
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell.setAlign(HorizontalAlignment.CENTER);

        row = table.createRow(12);
        cell = row.createCell(25, "Температура (°C)");
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell = row.createCell(50/2f, String.format("%.1f", techDataDto.getTempIn()));
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell.setAlign(HorizontalAlignment.CENTER);
        cell = row.createCell(50/2f, String.format("%.1f", techDataDto.getTempIn()));
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell.setAlign(HorizontalAlignment.CENTER);


        row = table.createRow(12);
        cell = row.createCell(25, "RH (%)");
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell = row.createCell(50/2f, String.format("%.0f", techDataDto.getHumIn()));
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell.setAlign(HorizontalAlignment.CENTER);
        cell = row.createCell(50/2f, String.format("%.0f", techDataDto.getHumOut()));
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell.setAlign(HorizontalAlignment.CENTER);

        table.draw();
        yPosition -= table.getHeaderAndDataHeight() + 20;

        String name = "Распыление";
        String[][] data = {
                {"Установка в:", techDataDto.getTypeMontage().getTxt()},
                {"Ширина (мм)", String.valueOf(techDataDto.getWidth())},
                {"Высота (мм)", String.valueOf(techDataDto.getLength())}
        };

        float yPositionLeft = yPosition;
        float yPositionRight = yPosition;
        table = new BaseTable( yPositionLeft, yStartNewPage, bottomMargin, tableWidth / 2f, margin, mainDocument, myPage, false, true);

        drawTable(table, font, name, data);
        yPositionLeft -= table.getHeaderAndDataHeight() + 20;

        name = "Информация по увлажнению";
        data = new String[][]{
                {"Темература увлажнения (°С)", String.format("%.1f", techDataDto.getTempIn())},
                {"Суммарный расход воздуха (м3/ч)", String.format("%d", techDataDto.getAirFlow())},
                {"Скорость воздуха (м/с)", "n/a"},
                {"Нагрузка по пару (кг/ч)", String.format("%.1f", techDataDto.getCalcCapacity())},
        };

        table = new BaseTable( yPositionRight, yStartNewPage, bottomMargin, tableWidth / 2f, margin + table.getWidth(), mainDocument, myPage, false, true);

        drawTable(table, font, name, data);
        yPositionRight -= table.getHeaderAndDataHeight() + 20;

        name = "Подбор увлажнителя";
        data = new String[][]{
                {"Тип увлажнения", techDataDto.getEnumHumidifierType().getTxt()},
                {"Электропитание", techDataDto.getPhase() + " фаз" + (techDataDto.getPhase() == 1 ? "а" : "ы") + ", " +
                        techDataDto.getVoltage() + "V"}
        };

        table = new BaseTable( yPositionLeft, yStartNewPage, bottomMargin, tableWidth / 2f, margin, mainDocument, myPage, false, true);

        drawTable(table, font, name, data);
        yPositionLeft -= table.getHeaderAndDataHeight() + 20;

        yPosition = Math.min(yPositionLeft, yPositionRight);

        name = "Результаты расчета увлажнения";
        data = new String[][]{
                {"Тип увлажнения", "1 x EHU 751-1, 1.0 kg/h, 220V 0.75kW/h"},
                {"Тип распыления", "1 Дисперсионные трубки*ø:25mm L:290mm"}
        };

        table = new BaseTable( yPosition, yStartNewPage, bottomMargin, tableWidth, margin, mainDocument, myPage, false, true);

        drawTable(table, font, name, data, new float[]{40f, 60f});
        yPosition -= table.getHeaderAndDataHeight() + 20;

        contentStream.close();
        return mainDocument;
    }

    private static void drawTable(BaseTable table, PDFont font, String name, String[][] data) throws IOException {

        Row<PDPage> row;
        Cell<PDPage> cell;
        row = table.createRow(12);
        cell = row.createCell(100, name);
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell.setAlign(HorizontalAlignment.CENTER);
        for (int i = 0; i < data.length; i++) {
            row = table.createRow(0);
            for (int j = 0; j < data[i].length; j++) {
                cell = row.createCell(100f / data[i].length, data[i][j]);
                cell.setFont(font);
                cell.setFontSize(fontSizeNormal);
                cell.setValign(VerticalAlignment.MIDDLE);
            }
        }

        table.draw();
    }

    private static void drawTable(BaseTable table, PDFont font, String name, String[][] data, float[] columnWidth) throws IOException {
        Row<PDPage> row;
        Cell<PDPage> cell;
        row = table.createRow(12);
        cell = row.createCell(100, name);
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell.setAlign(HorizontalAlignment.CENTER);
        for (int i = 0; i < data.length; i++) {
            row = table.createRow(0);
            for (int j = 0; j < data[i].length; j++) {
                cell = row.createCell(columnWidth[j], data[i][j]);
                cell.setFont(font);
                cell.setFontSize(fontSizeNormal);
                cell.setValign(VerticalAlignment.MIDDLE);
            }
        }

        table.draw();
    }

}


