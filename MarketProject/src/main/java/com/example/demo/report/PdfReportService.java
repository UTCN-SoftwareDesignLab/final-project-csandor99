package com.example.demo.report;


import com.example.demo.activity.ActivityService;
import com.example.demo.activity.model.Activity;
import com.example.demo.activity.model.dto.ActivityDTO;
import com.example.demo.item.ItemService;
import com.example.demo.item.model.dto.ItemDTO;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.example.demo.report.ReportType.PDF;

@Service
@AllArgsConstructor
public class PdfReportService implements ReportService {

    private ItemService itemService;
    private ActivityService activityService;

    @Override
    public ByteArrayOutputStream export() {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        List<ItemDTO> items = itemService.outOfStock();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int count = 0;
        try {
            PDPageContentStream stream = new PDPageContentStream(doc,page);
            stream.setFont(PDType1Font.COURIER, 12);
            stream.beginText();
            stream.setLeading(14.5f);
            stream.newLineAtOffset(25, 700);
            stream.showText("Report: ");
            stream.newLine();
            stream.newLine();

            for(ItemDTO item: items) {
                if(count == 9){
                    stream.endText();
                    stream.close();
                    doc.addPage(page=new PDPage());
                    stream = new PDPageContentStream(doc,page);
                    stream.setFont(PDType1Font.COURIER, 12);
                    stream.beginText();
                    stream.setLeading(14.5f);
                    stream.newLineAtOffset(25, 700);
                    count = 0;
                }
                stream.showText("ID: " + item.getId());
                stream.newLine();
                stream.showText("Name: " + item.getName());
                stream.newLine();
                stream.showText("Barcode: " + item.getBarcode());
                stream.newLine();
                stream.showText("Price: " + item.getPrice());
                stream.newLine();
                stream.showText("####################################################################");
                stream.newLine();

                count ++;
            }

            stream.endText();
            stream.close();
            doc.save(byteArrayOutputStream);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream;
    }

    @Override
    public ByteArrayOutputStream exportActivity() {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        List<ActivityDTO> activities = activityService.findAll();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int count = 0;

        try {
            PDPageContentStream stream = new PDPageContentStream(doc,page);
            stream.setFont(PDType1Font.COURIER, 12);
            stream.beginText();
            stream.setLeading(14.5f);
            stream.newLineAtOffset(25, 700);
            stream.showText("Report: ");
            stream.newLine();
            stream.newLine();

            for(ActivityDTO activity: activities) {
                if(count == 11){
                    stream.endText();
                    stream.close();
                    doc.addPage(page=new PDPage());
                    stream = new PDPageContentStream(doc,page);
                    stream.setFont(PDType1Font.COURIER, 12);
                    stream.beginText();
                    stream.setLeading(14.5f);
                    stream.newLineAtOffset(25, 700);
                    count = 0;
                }
                stream.showText("Employee name: " + activity.getName());
                stream.newLine();
                stream.showText("Date: " + activity.getDate());
                stream.newLine();
                stream.showText("Total price: " + activity.getPrice());
                stream.newLine();
                stream.showText("####################################################################");
                stream.newLine();

                count ++;
            }

            stream.endText();
            stream.close();
            doc.save(byteArrayOutputStream);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream;
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
