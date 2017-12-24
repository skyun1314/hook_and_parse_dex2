package com.binpang.apiMonitor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;


public class NetWorkHook extends ApiMonitorHook {

	@Override
	public void startHook() {
		// TODO Auto-generated method stub
		// HttpURLConnection
		Method openConnectionMethod =  FindMethod.findMethod("java.net.URL", ClassLoader.getSystemClassLoader(), "openConnection");
		hookhelper.hookMethod(openConnectionMethod, new AbstractBahaviorHookCallBack() {
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				URL url = (URL) param.thisObject;
				Logger.logI("Connect to URL ->");
				Logger.logI("The URL = " + url.toString());
			}
		});

		Method executeRequest =  FindMethod.findMethod("org.apache.http.impl.client.AbstractHttpClient", ClassLoader.getSystemClassLoader(),
				"execute", HttpHost.class, HttpRequest.class, HttpContext.class);

		hookhelper.hookMethod(executeRequest, new AbstractBahaviorHookCallBack() {
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Connect to URL ->");
				HttpHost host = (HttpHost) param.args[0];
				HttpRequest request = (HttpRequest) param.args[1];
				if (request instanceof HttpGet) {
					HttpGet httpGet = (HttpGet) request;
					Logger.logI("HttpGet Method : " + httpGet.getMethod());
					Logger.logI("HttpGet URL : " + httpGet.getURI().toString());

					if (httpGet.getURI().toString().contains("miosapi.54nb.com")){
						new Exception("wodelog").printStackTrace();
					}

					Header[] headers = request.getAllHeaders();
					if (headers != null) {
						for (int i = 0; i < headers.length; i++) {
							Logger.logI(headers[i].getName() + ":" + headers[i].getName());
						}
					}
				} else if (request instanceof HttpPost) {
					HttpPost httpPost = (HttpPost) request;
					Logger.logI("HttpPost Method : " + httpPost.getMethod());
					Logger.logI("HttpPost URL : " + httpPost.getURI().toString());
					if (httpPost.getURI().toString().contains("miosapi.54nb.com")){
						new Exception("wodelog").printStackTrace();
					}
					Header[] headers = request.getAllHeaders();
					if (headers != null) {
						for (int i = 0; i < headers.length; i++) {
							Logger.logI(headers[i].getName() + ":" + headers[i].getValue());
						}
					}
					HttpEntity entity = httpPost.getEntity();
					String contentType = null;
					if (entity.getContentType() != null) {
						contentType = entity.getContentType().getValue();
						if (URLEncodedUtils.CONTENT_TYPE.equals(contentType)) {

							try {
								byte[] data = new byte[(int) entity.getContentLength()];
								entity.getContent().read(data);
								String content = new String(data, HTTP.DEFAULT_CONTENT_CHARSET);
								Logger.logI("HTTP POST Content : " + content);
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (contentType.startsWith(HTTP.DEFAULT_CONTENT_TYPE)) {
							try {
								byte[] data = new byte[(int) entity.getContentLength()];
								entity.getContent().read(data);
								String content = new String(data, contentType.substring(contentType.lastIndexOf("=") + 1));
								Logger.logI("HTTP POST Content : " + content);
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}else{
						byte[] data = new byte[(int) entity.getContentLength()];
						try {
							entity.getContent().read(data);
							String content = new String(data, HTTP.DEFAULT_CONTENT_CHARSET);
							Logger.logI("HTTP POST Content : " + content);
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}
			}

			@Override
			public void afterHookedMethod(HookParam param) {
				// TODO Auto-generated method stub
				super.afterHookedMethod(param);
				HttpResponse resp = (HttpResponse) param.getResult();
				if (resp != null) {
					Logger.logI("Status Code = " + resp.getStatusLine().getStatusCode());
					Header[] headers = resp.getAllHeaders();
					if (headers != null) {
						for (int i = 0; i < headers.length; i++) {
							Logger.logI(headers[i].getName() + ":" + headers[i].getValue());
						}
					}

				}
			}
		});
	}

}
