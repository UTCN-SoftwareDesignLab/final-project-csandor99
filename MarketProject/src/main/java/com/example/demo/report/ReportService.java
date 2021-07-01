package com.example.demo.report;

import java.io.ByteArrayOutputStream;

public interface ReportService {
    ByteArrayOutputStream export();

    ByteArrayOutputStream exportActivity();

    ReportType getType();
}
