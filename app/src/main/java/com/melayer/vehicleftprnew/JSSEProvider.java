package com.melayer.vehicleftprnew;

import android.util.Log;

import java.security.AccessController;
import java.security.Provider;

/**
 * Created by twtech on 5/7/17.
 */

public class JSSEProvider extends Provider
{

    public JSSEProvider()
    {
        super("HarmonyJSSE", 1.0, "Harmony JSSE Provider");

        Log.v("JSSEProvider report", "JSSEProvider called");
        AccessController.doPrivileged(new java.security.PrivilegedAction<Void>() {
            public Void run()
            {
                put("SSLContext.TLS", "org.apache.harmony.xnet.provider.jsse.SSLContextImpl");
                put("Alg.Alias.SSLContext.TLSv1", "TLS");
                put("KeyManagerFactory.X509", "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl");
                put("TrustManagerFactory.X509", "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl");
                return null;
            }
        });
    }
}