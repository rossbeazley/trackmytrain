package uk.co.rossbeazley.trackmytrain.wsclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LBDAuth {
    public static String TOKEN_VALUE;

    static {
        try {
            TOKEN_VALUE = new Scanner(new File("/etc/ldb.token")).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            TOKEN_VALUE = "";
        }
    }
}
