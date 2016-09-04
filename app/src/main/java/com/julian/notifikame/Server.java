package com.julian.notifikame;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

/**
 * Created by Julian on 04/09/2016.
 */
public interface Server {
    DBDataConverter converter = new DBDataConverter();
    HttpClient httpclient =new DefaultHttpClient();
    HttpPost httppost = new HttpPost("http://notifk.gzpot.com/notifik/notifik.php"); // Url del Servidor
}
