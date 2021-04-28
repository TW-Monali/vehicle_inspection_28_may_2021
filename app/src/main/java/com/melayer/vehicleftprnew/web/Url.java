package com.melayer.vehicleftprnew.web;

/**
 * Created by melayer on 5/8/16.
 */
public interface Url {
    String PROTOCOL = "http";
    //String SERVER = "192.168.2.15"; //local DB
    String SERVER = "173.234.153.82";
    String PORT = "7373";
    String APP_NAME = "vehicleftp";
    String URL_BASE = PROTOCOL + "://" + SERVER + ":" + PORT + "/" + APP_NAME + "/";
    String URL_LOGIN = URL_BASE + "userLogin";
    String URL_REGISTER_VEHICLE  = URL_BASE + "saveVehicle";
    String URL_MODULE  = URL_BASE + "module";
    String URL_UPLOAD_MODULE = URL_BASE + "saveModule";
}
