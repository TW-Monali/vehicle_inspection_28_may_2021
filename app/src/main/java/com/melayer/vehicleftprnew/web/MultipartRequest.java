package com.melayer.vehicleftprnew.web;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.melayer.vehicleftprnew.domain.Module;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by melayer on 11/8/16.
 */
public class MultipartRequest extends Request<String> {

    private MultipartEntity entity = new MultipartEntity();

    private Response.Listener<String> mListener;



    public MultipartRequest(String url,Module module,Response.Listener<String> listener, Response.ErrorListener errorListener){

        super(Method.POST,url,errorListener);

        buildMultipartEntity(module);
    }

    private void buildMultipartEntity(Module module){
        try {

            entity.addPart("vehicleId", new StringBody(module.getVehicleId()));
            entity.addPart("vehicleNo", new StringBody(module.getVehicleNo()));
            entity.addPart("unit", new StringBody(module.getUnit().equals(null)?"":module.getUnit()));
            entity.addPart("checkPointId", new StringBody(module.getCheckPointId().toString()));
            entity.addPart("checkPointName", new StringBody(module.getCheckPointName()));
            entity.addPart("checkPointStatus", new StringBody(module.getCheckPointStatus()));

            entity.addPart("finalStatus", new StringBody(module.getFinalInspectionStatus()));
            entity.addPart("remarks", new StringBody(module.getRemarks().equals(null)?"":module.getRemarks()));
            entity.addPart("entryDate", new StringBody(module.getEntryDate()));
            entity.addPart("userId", new StringBody(module.getUserId().toString()));
            if (module.getImage()!=null) {
                String[] str =module.getImage().toString().split("/");
                entity.addPart("imagePath", new StringBody(str[str.length - 1]));
                entity.addPart("multipartFile", new FileBody(module.getImage()));
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
           // VolleyLog.e("UnsupportedEncodingException");
        }

    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return Response.success("Uploaded", getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}