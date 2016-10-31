package dbtools;

import com.bftcom.dbtools.creation.DBCreation;
import com.bftcom.dbtools.utils.DataSynchronizer;
import com.bftcom.dbtools.utils.DataUploader;

import java.io.File;
import java.math.BigInteger;

/**
 * Created by k.nikitin on 19.10.2016.
 */
public class DBUtilsTest {
    public static void main(String[] args){
//        DBCreation.createDB();
//        DBCreation.getUploadSql();
//        DBCreation.importCsvDataFiles(new File("D:\\Repositaries\\bc_lite_offline\\csv_data"), null);
//        DBCreation.importCsvDataFilesTest();
//        DataUploader dataUploader = new DataUploader();
//        dataUploader.fullExport(BigInteger.valueOf(1000000267));
//        dataUploader.exportForOtherOffline();
        DataSynchronizer  dataSynchronizer = new DataSynchronizer();
        dataSynchronizer.synchronizeData("D:\\Repositaries\\bc_lite_offline\\synchronize\\synchronizeQustion.xml");
        System.exit(0);
}
        }
