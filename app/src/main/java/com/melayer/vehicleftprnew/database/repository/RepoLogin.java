package com.melayer.vehicleftprnew.database.repository;

/**
 * Created by root on 25/8/16.
 */
public interface RepoLogin {
    void saveUserCredentials(String userName, Integer userId) throws Exception;

    String getUserName() throws Exception;

    String getUserId() throws Exception;
}
