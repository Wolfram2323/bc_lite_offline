package com.bftcom.context;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by k.nikitin on 17.11.2016.
 */
public class OfflineVersion {
    private static Integer major;
    private static Integer minor;
    private static Integer realease;
    private static Integer buildNumber;

    static{
        InputStream is = OfflineVersion.class.getResourceAsStream("/version.txt");
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(is, writer, "UTF-8");
            String version = writer.toString().substring("v.".length());
            List<Integer> verList = new LinkedList<>();
            Pattern pattern =Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(version);
            while(matcher.find()){
                verList.add(Integer.valueOf(matcher.group()));
            }
            major = verList.get(0);
            minor = verList.get(1);
            realease = verList.get(2);
            buildNumber = verList.get(3);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Field[] fields = this.getClass().getDeclaredFields();
        try{
            for(Field field : fields){

                sb.append(field.get(this).toString()).append(".");

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        sb.delete(sb.lastIndexOf("."),sb.length());
        return sb.toString();
    }
}
