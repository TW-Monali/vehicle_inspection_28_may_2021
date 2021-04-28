package com.melayer.vehicleftprnew.database;

import com.melayer.vehicleftprnew.database.repository.RepoImplLogin;
import com.melayer.vehicleftprnew.database.repository.RepoLogin;

/**
 * Created by root on 25/8/16.
 */
public final class RepoFactory {

    public static RepoLogin getLoginRepository(Helper helper) {

        return new RepoImplLogin(helper);
    }


}
