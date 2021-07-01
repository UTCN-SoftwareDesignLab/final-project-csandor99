package com.example.demo.report;

import com.example.demo.activity.ActivityService;
import com.example.demo.activity.model.Activity;
import com.example.demo.activity.model.dto.ActivityDTO;
import com.example.demo.item.ItemService;
import com.example.demo.item.model.dto.ItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.example.demo.report.ReportType.CSV;

@Service
@AllArgsConstructor
public class CSVReportService implements ReportService {

    private final ItemService itemService;
    private final ActivityService activityService;

    @Override
    public ByteArrayOutputStream export() {
//        List<ItemDTO> items = itemService.outOfStock();
//        try{
//            FileWriter fw = new FileWriter("ReportCSV.csv");
//            for(ItemDTO item: items){
//                fw.append(item.getId().toString());
//                fw.append(",");
//                fw.append(item.getName());
//                fw.append(",");
//                fw.append(item.getBarcode());
//                fw.append(",");
//                fw.append(String.valueOf(item.getPrice()));
//                fw.append("\n");
//            }
//            fw.close();
//        }catch(IOException e){
//            return "Error";
//        }
//        return "I am a CSV reporter.";
        return new ByteArrayOutputStream();
    }

    public ByteArrayOutputStream exportActivity() {
//        List<ActivityDTO> activities = activityService.findAll();
//        try{
//            FileWriter fw = new FileWriter("ActivityReportCSV.csv");
//            for(ActivityDTO activity: activities){
//                fw.append(activity.getId().toString());
//                fw.append(",");
//                fw.append(activity.getName());
//                fw.append(",");
//                fw.append(activity.getDate());
//                fw.append(",");
//                fw.append(String.valueOf(activity.getPrice()));
//                fw.append("\n");
//            }
//            fw.close();
//        }catch(IOException e){
//            return "Error";
//        }
//        return "I am a CSV reporter.";
        return new ByteArrayOutputStream();
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
