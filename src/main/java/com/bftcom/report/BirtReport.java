package com.bftcom.report;

import com.bftcom.gui.utils.Message;
import org.apache.xerces.impl.dv.util.Base64;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Collection;

import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by k.nikitin on 21.11.2016.
 */
public class BirtReport {
    private static final Logger log = LoggerFactory.getLogger(BirtReport.class);

    public static final Locale RUSSIAN_LOCALE = new Locale("ru_RU");

    private static IReportEngine engine = null;

    public static synchronized IReportEngine getEngine() {
        if (engine == null) {
            try {
                EngineConfig config = new EngineConfig();
                config.setEngineHome(new File("./src/main/lib/birt-report-engine").exists() ? "./src/main/lib/birt-report-engine"
                        : "./lib/birt-report-engine");
                config.setLogConfig("", Level.OFF);



                Platform.startup(config);

                IReportEngineFactory factory = (IReportEngineFactory) Platform
                        .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);

                engine = factory.createReportEngine(config);
            } catch (Exception ex) {
                ex.printStackTrace();
                Message.throwExceptionForJavaFX(ex, "Ошибка загрузки генератора отчетов", "", false);
            }
        }

        return engine;
    }

    public static void generate(String output_format, InputStream in, OutputStream out, Map<String, Object> params, Connection con) {
        IReportEngine engine = BirtReport.getEngine();
        try {
            processDesign(engine.openReportDesign(in), output_format, out, params, con);
        } catch (Exception e) {
            e.printStackTrace();
            Message.throwExceptionForJavaFX(e, "Ошибка генерации отчета", "", false);
        }
    }

    private static void processDesign(IReportRunnable design, String output_format, OutputStream out, Map<String, Object> params, Connection con) {
        IRunAndRenderTask task = null;

        try {


            task = engine.createRunAndRenderTask(design);
            task.setLocale(RUSSIAN_LOCALE);


            task.getAppContext().put("OdaJDBCDriverPassInConnection", con);


            //пробуем заполнить все параметры отчета
            IGetParameterDefinitionTask paramTask = engine.createGetParameterDefinitionTask(design);
            Collection parameterDefns = paramTask.getParameterDefns(false);
            for (Object o : parameterDefns) {
                IParameterDefnBase rptParam = (IParameterDefnBase) o;
                if (rptParam.getParameterType() != IParameterDefnBase.SCALAR_PARAMETER) {
                    continue;         //only process scalar parameters
                }
                String paramName = rptParam.getName();
                String paramType = ((IScalarParameterDefn) rptParam).getScalarParameterType();

                Object value = params.get(paramName);
                if (value != null) {
                    if ("multi-value".equals(paramType)) {
                        task.setParameterValue(paramName, ((String) value).split(","));
                    } else {
                        task.setParameterValue(paramName, value);
                    }
                }
            }

            //тип отчета, файл отчета, эмиттер (и вообще все options)
            RenderOption options = ("html".equals(output_format) ? new HTMLRenderOption() : new RenderOption());
            options.setOutputStream(out);
            options.setOutputFormat(output_format);
            if ("pdf".equals(output_format)) {
                options.setOption(IPDFRenderOption.PDF_HYPHENATION, true);
            }
            if ("html".equals(output_format)) {
                HTMLRenderOption htmlOptions = (HTMLRenderOption) options;
                htmlOptions.setEnableAgentStyleEngine(true); //bug эклипса - см. https://bugs.eclipse.org/bugs/show_bug.cgi?id=321144

//         final String IMAGE_DIR = (System.getProperty("birt.image.dir") != null ? System.getProperty("birt.image.dir") : System.getProperty("java.io.tmpdir"));
                final String IMAGE_DIR = System.getProperty("java.io.tmpdir");
                htmlOptions.setImageDirectory(IMAGE_DIR + "/birt_report_img");

                htmlOptions.setBaseImageURL("birt_report_img");
                htmlOptions.setImageHandler(new HTMLServerImageHandler());
                if (Boolean.getBoolean("birt.report.encodeBase64")) {
                    htmlOptions.setSupportedImageFormats("PNG");
                    htmlOptions.setImageHandler(new HTMLServerImageHandler() {
                        @Override
                        protected String handleImage(IImage image, Object context, String prefix, boolean needMap) {
                            String embeddedImage = null;
                            embeddedImage = Base64.encode(image.getImageData());
                            return "data:image/png;base64," + embeddedImage;
                        }
                    });
                }
            }
            task.setRenderOption(options);
            task.run();
        } catch (Exception e) {
            e.printStackTrace();
            Message.throwExceptionForJavaFX(e, "Ошибка генерации отчета", "", false);
        } finally {
            if (task != null) task.close();
        }

    }

}
