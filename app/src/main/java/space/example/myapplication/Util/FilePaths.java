package space.example.myapplication.Util;

import android.os.Environment;

public class FilePaths {

    //"storage/emulate/0"
    public String ROOT_DIR = Environment.getStorageDirectory().getPath();

    public String PICTURES = ROOT_DIR + "/Pictures";
    public String CAMERA = ROOT_DIR + "/DCIM/camera";

}
