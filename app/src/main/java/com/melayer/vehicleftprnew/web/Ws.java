package com.melayer.vehicleftprnew.web;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by melayer on 5/8/16.
 */
public class Ws {

    private static RequestQueue queue;

    public static RequestQueue getQueue(Context context){

        if(queue == null)
            queue = Volley.newRequestQueue(context);
        return queue;
    }

}
