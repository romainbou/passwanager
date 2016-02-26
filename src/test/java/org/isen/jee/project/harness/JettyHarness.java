package org.isen.jee.project.harness;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.reflections.Reflections;

public class JettyHarness {

    private static final int BASE_PORT = 9090;
    private static final int MAX_PORT_DEVIATION = 0;
    private static final String CONTEXT_PATH = "app";

    private HttpClient httpClient;
    private static Server server;

    private static int port = BASE_PORT + (int) (Math.random() * MAX_PORT_DEVIATION);

    protected static final String SERVLET_PATH = "/passwanager";
    protected static final String SERVLET_URI = getBaseUri() + SERVLET_PATH;

    @BeforeClass
    public static void startServer() throws Exception {
        server = new Server(port);
        server.setStopAtShutdown(true);

        WebAppContext context = new WebAppContext();

        String wardir = "src/main/webapp";
        context.setResourceBase(wardir);
        context.setDescriptor(wardir + "WEB-INF/web.xml");
        context.setContextPath("/" + CONTEXT_PATH);
        context.setParentLoaderPriority(true);

        Reflections reflections = new Reflections("org.isen.jee");

        Set<Class<?>> servlets = reflections.getTypesAnnotatedWith(WebServlet.class);

        for (Class<?> servletClass : servlets) {

            WebServlet annotation = servletClass.getAnnotation(WebServlet.class);
            if (annotation != null) {
                for (String pattern : annotation.value()) {
                    context.addServlet(new ServletHolder((Servlet) servletClass.newInstance()), pattern);
                }
            }

        }

        server.setHandler(context);
        server.start();

    }

    public static String getBaseUri() {
        return "http://localhost:" + port + "/" + CONTEXT_PATH;
    }

    @AfterClass
    public static void stopServer() throws Exception {
        server.stop();

    }

    @Before
    public void doBefore() throws Exception {
        httpClient = new DefaultHttpClient();
    }

    @After
    public void doAfter() throws Exception {
        httpClient.getConnectionManager()
                .closeIdleConnections(0, TimeUnit.SECONDS);
    }
    
    public String get(String uri) throws IOException, HttpException {
    	HttpGet get = new HttpGet(uri);
    	return executeAndReturnResult(get);
    }

    public int getAndGetStatusCode(String uri) throws IOException, HttpException {
    	HttpGet get = new HttpGet(uri);
		try{
        	HttpResponse response = httpClient.execute(get);
        	int responseCode = response.getStatusLine().getStatusCode();
        	return responseCode;
	    } catch (IOException e) {
	        throw new WebRuntimeException(500, e.getMessage());
	    } finally {
	        get.releaseConnection();
	    }
    }

    public String getWithParams(String uri, Map<String, String> params) {
    	
    	StringBuilder requestUrl = new StringBuilder(uri);
    	
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
    	for (Entry<String, String> paramEntry : params.entrySet()) {
    		nameValuePairs.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
    	}    		
    	
    	String querystring = URLEncodedUtils.format(nameValuePairs, "utf-8");
    	requestUrl.append("?");
    	requestUrl.append(querystring);
    	
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpGet get = new HttpGet(requestUrl.toString());
    	return executeAndReturnResult(get);
    	
    }
    
    public int getWithParamsAndGetStatusCode(String uri, Map<String, String> params) {
    	    	
    	StringBuilder requestUrl = new StringBuilder(uri);
    	
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
    	for (Entry<String, String> paramEntry : params.entrySet()) {
    		nameValuePairs.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
    	}    		
    		
		String querystring = URLEncodedUtils.format(nameValuePairs, "utf-8");
		requestUrl.append("?");
		requestUrl.append(querystring);
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet get = new HttpGet(requestUrl.toString());
		try{
        	HttpResponse response = httpClient.execute(get);
        	int responseCode = response.getStatusLine().getStatusCode();
        	return responseCode;
	    } catch (IOException e) {
	        throw new WebRuntimeException(500, e.getMessage());
	    } finally {
	        get.releaseConnection();
	    }

    }
    
    public String delete(String uri) {
        HttpDelete delete = new HttpDelete(uri);
        return executeAndReturnResult(delete);
    }

    public String post(String uri, Map<String, String> params) {

        HttpPost post = new HttpPost(uri);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        for (Entry<String, String> paramEntry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return executeAndReturnResult(post);
    }
    
    public int postAndGetStatusCode(String uri, Map<String, String> params){
        HttpPost post = new HttpPost(uri);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        for (Entry<String, String> paramEntry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        try{
        	HttpResponse response = httpClient.execute(post);
        	int responseCode = response.getStatusLine().getStatusCode();
        	return responseCode;
	    } catch (IOException e) {
	        throw new WebRuntimeException(500, e.getMessage());
	    } finally {
	        post.releaseConnection();
	    }
    }


    public String put(String uri, Map<String, String> params) {

        HttpPut put = new HttpPut(uri);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        for (Entry<String, String> paramEntry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
        }
        try {
            put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return executeAndReturnResult(put);
    }


    private String executeAndReturnResult(HttpRequestBase method) {
        try {
            HttpResponse response = httpClient.execute(method);
            int responseCode = response.getStatusLine()
                    .getStatusCode();
            if (responseCode >= 500) {
                throw new WebRuntimeException(responseCode, "Bad request code " + responseCode);
            }
            InputStream content = response.getEntity()
                    .getContent();
            return IOUtils.toString(content, "UTF-8");
        } catch (IOException e) {
            throw new WebRuntimeException(500, e.getMessage());
        } finally {
            method.releaseConnection();
        }
    }
}
