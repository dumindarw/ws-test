package ws.com.loits.wstest;

import android.content.Context;
import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsServiceConnectionSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by dumindaw on 09/09/2015.
 */
public class SoapClient {

    public  SoapPrimitive getResponse(String nameSpace, String url, String method, String action, Hashtable params, String username, String pass, Context ctx) {
        SoapObject request = new SoapObject(nameSpace, method);

        Enumeration values;
        values = params.keys();
        while (values.hasMoreElements()) {
            String key = (String) values.nextElement();
            String value = params.get(key).toString();
            Log.d("Key,Value=> ", key + " : " + value);
            request.addProperty(key, value);
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        //add custom header for WS-Reliable Messaging
        envelope.headerOut = addHeaders(username,pass);

        //envelope.implicitTypes = false;

        //HttpTransportSE httpTransport = new HttpTransportSE(url);

        //allowAllSSL();

        HttpsTransportSE httpsTransport = new HttpsTransportSE("203.189.65.69", 8246, "/services/imageService?wsdl", 20000);

        String headerAuthStr = username + ":" + pass;

        List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
        headerList.add(new HeaderProperty("Authorization", "Basic " + org.kobjects.base64.Base64.encode(headerAuthStr.getBytes())));

        SoapPrimitive response = null;

        try {
            httpsTransport.debug = true;

            ((HttpsServiceConnectionSE) httpsTransport.getServiceConnection()).setSSLSocketFactory(newSslSocketFactory(ctx));



            httpsTransport.call(action, envelope, headerList);

            String requestDump = httpsTransport.requestDump;
            String responseDump = httpsTransport.responseDump;

            Log.i("", "Requeste: " + requestDump);
            Log.i("", "Response: " + responseDump);

            response = (SoapPrimitive) envelope.getResponse();
        } catch (SoapFault e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private static TrustManager[] trustManagers;

    public static class _FakeX509TrustManager implements
            javax.net.ssl.X509TrustManager {
        private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public boolean isClientTrusted(X509Certificate[] chain) {
            return (true);
        }

        public boolean isServerTrusted(X509Certificate[] chain) {
            return (true);
        }

        public X509Certificate[] getAcceptedIssuers() {
            return (_AcceptedIssuers);
        }
    }

    public static void allowAllSSL() {

        javax.net.ssl.HttpsURLConnection
                .setDefaultHostnameVerifier(new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

        javax.net.ssl.SSLContext context = null;

        if (trustManagers == null) {
            trustManagers = new javax.net.ssl.TrustManager[] { new _FakeX509TrustManager() };
        }

        try {
            context = javax.net.ssl.SSLContext.getInstance("TLS");
            context.init(null, trustManagers, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            Log.e("allowAllSSL", e.toString());
        } catch (KeyManagementException e) {
            Log.e("allowAllSSL", e.toString());
        }
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(context
                .getSocketFactory());
    }

    private static SSLSocketFactory newSslSocketFactory(Context context) {
        try {

            TrustManagerFactory originalTrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            originalTrustManagerFactory.init((KeyStore) null);

            // Get an instance of the Bouncy Castle KeyStore format
            KeyStore trusted = KeyStore.getInstance(KeyStore.getDefaultType());
            // Get the raw resource, which contains the keystore with
            // your trusted certificates (root and any intermediate certs)

            InputStream in = context.getResources().openRawResource(R.raw.clienttruststore2);
            try {
                // Initialize the keystore with the provided trusted certificates
                // Also provide the password of the keystore
                trusted.load(in, "wso2carbon".toCharArray());
            } finally {
                in.close();
            }

            originalTrustManagerFactory.init(trusted);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(/*keyManagerFactory.getKeyManagers()*/null, originalTrustManagerFactory.getTrustManagers()/*new TrustManager[]{tm}*/, /*new java.security.SecureRandom()*/null);

            return sc.getSocketFactory();
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static Element[] addHeaders(String userName, String pw)
    {
        Element[] header = new Element[1];
        header[0] = new Element().createElement("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd","Security");
        header[0].setAttribute(null, "mustUnderstand","1");
        header[0].setAttribute(null, "xmlns:wsu","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

        Element timestampElem = new Element().createElement(null, "wsu:Timestamp");
        Element usernameToken = new Element().createElement("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:dd.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        // This is for TimeStamp element value
        java.util.Date create = new java.util.Date();
        java.util.Date expires = new java.util.Date(create.getTime()
                + (5l * 60l * 1000000l));

        Element createdElem = new Element().createElement(null, "wsu:Created");
        Element expiresElem = new Element().createElement(null, "wsu:Expires");

        createdElem.addChild(Node.TEXT, formatter.format(create));
        expiresElem.addChild(Node.TEXT, formatter.format(expires));

        timestampElem.setAttribute(null, "wsu:Id", "TS-1EB4A2A52467EB9373141362942343119");
        timestampElem.addChild(Node.ELEMENT, expiresElem);
        timestampElem.addChild(Node.ELEMENT, createdElem);

        usernameToken.setAttribute("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", "UsernameToken-4");

        header[0].addChild(Node.ELEMENT, timestampElem);
        header[0].addChild(Node.ELEMENT, usernameToken);

        Element username = new Element().createElement(null, "n0:Username");
        username.addChild(Node.TEXT, userName);
        usernameToken.addChild(Node.ELEMENT, username);
        Element pass = new Element().createElement(null,"n0:Password");
        pass.setAttribute(null, "Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
        pass.addChild(Node.TEXT, pw);

        usernameToken.addChild(Node.ELEMENT, pass);
        return header;
    }
}
