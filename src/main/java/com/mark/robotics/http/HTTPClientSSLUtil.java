package com.mark.robotics.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.mark.common.StringUtil;



public class HTTPClientSSLUtil {

  public final static boolean TRUST_ALL_SSL_CERTIFICATES = false;
  public final static boolean TRUST_ALL_SSL_HOSTNAMES = false;
  public final static String SSL_CONTEXT_NAME = "SSL";
  public final static String SSL_SCHEMA_NAME = "https";
  public final static int SSL_SCHEMA_PORT = 443;

  public final static String ACTION_REGISTER_SSL_FROM_KEYSTORE = "registerSSLFromKeyStore";

  private String keyStorePath;
  private String keyStorePassword;
  private boolean trustAllSSLCertificates = TRUST_ALL_SSL_CERTIFICATES;
  private boolean trustAllSSLHostnames = TRUST_ALL_SSL_HOSTNAMES;
  private String schemaName = SSL_SCHEMA_NAME;
  private int schemaPort = SSL_SCHEMA_PORT;
  private Logger logger;

  private KeyStore keyStore;

  public String getKeyStorePath() {
    return keyStorePath;
  }

  public void setKeyStorePath(String keyStorePath) {
    this.keyStorePath = keyStorePath;
  }

  public String getKeyStorePassword() {
    return keyStorePassword;
  }

  public void setKeyStorePassword(String keyStorePassword) {
    this.keyStorePassword = keyStorePassword;
  }

  public boolean isTrustAllSSLCertificates() {
    return trustAllSSLCertificates;
  }

  public void setTrustAllSSLCertificates(boolean trustAllSSLCertificates) {
    this.trustAllSSLCertificates = trustAllSSLCertificates;
  }

  public boolean isTrustAllSSLHostnames() {
    return trustAllSSLHostnames;
  }

  public void setTrustAllSSLHostnames(boolean trustAllSSLHostnames) {
    this.trustAllSSLHostnames = trustAllSSLHostnames;
  }

  public String getSchemaName() {
    return schemaName;
  }

  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  public int getSchemaPort() {
    return schemaPort;
  }

  public void setSchemaPort(int schemaPort) {
    this.schemaPort = schemaPort;
  }

  public Logger getLogger() {
    return logger;
  }

  public void setLogger(Logger logger) {
    this.logger = logger;
  }

  public void registerSSL(DefaultHttpClient httpClient) {
    httpClient.getConnectionManager().getSchemeRegistry().register(getSSLSchema());
  }

  public boolean isSSLSchemaForSpecificURL(String url) {
    if (StringUtil.isNullOrEmpty(schemaName) || StringUtil.isNullOrEmpty(url)) {
      return false;
    }
    return StringUtil.prefixMatch(schemaName, url);
  }

  private Scheme getSSLSchema() {
    Scheme sslSchema = null;
    try {
      SSLSocketFactory socketFactory = getSSLSocketFactory();
      if (trustAllSSLHostnames) {
        socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      }
      sslSchema = new Scheme(schemaName, socketFactory, schemaPort);
    } catch (Exception e) {
      logger.error(ACTION_REGISTER_SSL_FROM_KEYSTORE, e);
    }

    return sslSchema;
  }

  private SSLSocketFactory getSSLSocketFactory() throws Exception {
    if (trustAllSSLCertificates) {
      return new SSLSocketFactory(getSSLConextToFakeTrust());
    } else {
      return new SSLSocketFactory(getKeyStore());
    }
  }

  private SSLContext getSSLConextToFakeTrust() throws Exception {
    SSLContext sslContext = SSLContext.getInstance(SSL_CONTEXT_NAME);
    TrustManager[] trustManagers = new TrustManager[] { new FakeX509TrustManager() };
    sslContext.init(null, trustManagers, new SecureRandom());
    return sslContext;
  }

  private KeyStore getKeyStore() throws IOException {
    if (null == keyStore) {
      createKeyStore();
    }
    return keyStore;
  }

  private synchronized void createKeyStore() throws IOException {
    if (null != keyStore) {
      return;
    }

    FileInputStream instream = null;
    try {
      keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      if (StringUtil.isNotNullNorEmpty(keyStorePath) && StringUtil.isNotNullNorEmpty(keyStorePassword)) {
        instream = new FileInputStream(new File(keyStorePath));
        keyStore.load(instream, keyStorePassword.toCharArray());
      }
    } catch (Exception e) {
      logger.error(ACTION_REGISTER_SSL_FROM_KEYSTORE, e);
    } finally {
      if (null != instream)
        instream.close();
    }
  }

  public class FakeX509TrustManager implements X509TrustManager {

    private final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

    public void checkClientTrusted(X509Certificate[] chain, String authType) {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }

    public X509Certificate[] getAcceptedIssuers() {
      return (_AcceptedIssuers);
    }
  }
}
