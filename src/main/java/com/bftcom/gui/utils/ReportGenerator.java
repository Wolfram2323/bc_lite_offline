package com.bftcom.gui.utils;

import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.report.BirtReport;
import javafx.concurrent.Task;


import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by k.nikitin on 11.12.2017.
 */
public class ReportGenerator<T> extends Task<T> {

    private String outputFormat;
    private String reportTemplateName;
    private String url;
    private Map<String, Object> params;
    private final static long max = 100;

    public ReportGenerator(String outputFormat, String reportTemplate, Map<String, Object> params, String url) {
        this.outputFormat = outputFormat;
        this.reportTemplateName = reportTemplate;
        this.params = params;
        this.url = url;
    }

    @Override
    public void run() {
        try {
            File report = File.createTempFile("birt", "." + outputFormat);
            updateProgress(5, max);
            InputStream template = getClass().getResourceAsStream(reportTemplateName);
            FileOutputStream reportStream = new FileOutputStream(report);
            updateProgress(15, max);
            Connection connection = DriverManager.getConnection(url + ";user=" + HibernateUtils.DERBY_USER_NAME
                    + ";password=" + HibernateUtils.DERBY_PSWD);
            connection.setAutoCommit(true);
            updateProgress(20, max);
            BirtReport.generate(outputFormat, template, reportStream, params, connection);
            updateProgress(90, max);
            template.close();
            reportStream.close();
            connection.close();
            updateProgress(100, max);
            Desktop.getDesktop().open(report);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            Message.throwExceptionForJavaFX(e, "Ошибка генерации отчета", "", false);
        }
    }

    @Override
    protected T call() throws Exception {
        run();
        return null;
    }
}
