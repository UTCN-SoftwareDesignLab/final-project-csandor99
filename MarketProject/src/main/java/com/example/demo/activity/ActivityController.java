package com.example.demo.activity;

import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(ACTIVITY)
@RequiredArgsConstructor
public class ActivityController {

    private final ReportServiceFactory reportServiceFactory;
    private final ActivityService activityService;

    @GetMapping(PROMO + "/{name}")
    public int countEmployeeActivity(@PathVariable String name){
        return activityService.countEmployeeActivity(name);
    }

    @GetMapping(EXPORT_REPORT)
    public ResponseEntity<?> exportReport(@PathVariable ReportType type) {

        ByteArrayOutputStream bodyOutput = reportServiceFactory.getReportService(type).exportActivity();
        ByteArrayResource byteArrayResource = new ByteArrayResource(bodyOutput.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ActivityReport.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }

}
