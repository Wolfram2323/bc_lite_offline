package com.bftcom;

import com.bftcom.dbtools.creation.DBCreation;

/**
 * Created by k.nikitin on 22.09.2016.
 */
public class BC_OffLine {
    public static void main(String[] args){
        DBCreation dbCreation = DBCreation.newInstance();
        dbCreation.createDB();
        System.exit(0);
    }
}
