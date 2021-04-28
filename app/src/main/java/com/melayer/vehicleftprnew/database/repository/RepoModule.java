package com.melayer.vehicleftprnew.database.repository;

import com.melayer.vehicleftprnew.domain.Module;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 25/8/16.
 */
public interface RepoModule {
    void saveModule(Module module) throws Exception;
    void saveLocalObj(Collection<Module> modules) throws Exception;
    void updateFinalStatus(String vehicleNo) throws Exception;
    void updateFlag(String vehicleNo, String checkPointName) throws Exception;
    void deleteTableData()throws Exception;
    void deleteTableData(String vehicleNo) throws Exception;
    Set<Module> uploadModuleData(String userId) throws Exception;
    Integer checkDataInspections() throws Exception;
    List<String> getLocalVehicleList()throws Exception;
    List<Module> uploadData(String userId)throws Exception;
    Set<Module> selectAll()throws Exception;
}
