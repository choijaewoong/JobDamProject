//package com.example.androidchoi.jobdam.Manager;
//
//import android.content.Context;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//public abstract class NetworkRequest<T> implements Runnable {
//
//	public static final String METHOD_GET = "GET";
//	public static final String METHOD_POST = "POST";
//
//	public static final int DEFAULT_TIMEOUT = 10000;
//	public static final int DEFAULT_RETRY = 3;
//	NetworkManager manager;
//	NetworkManager.OnResultListener<T> mListener;
//	Context mContext;
//	int connectionTimeout = DEFAULT_TIMEOUT;
//	int readTimeout = DEFAULT_TIMEOUT;
//	int retry = DEFAULT_RETRY;
//
//	T result;
//	int errorCode = -1;
//
//	//데이터를 얻어오기 위한 URL
//	abstract public URL getURL() throws MalformedURLException;
//	//얻어온 데이터를 처리하는 메소드
//	abstract public T parse(InputStream is) throws IOException;
//	public void setOnResultListener(NetworkManager.OnResultListener<T> listener) {
//		mListener = listener;
//	}
//	public void setNetworkManager(NetworkManager manager) {
//		this.manager = manager;
//	}
//
//	public void setContext(Context context) {
//		mContext = context;
//	}
//	public Context getContext() {
//		return mContext;
//	}
//
//	// 네트워크 요청시 서버로 전달할 값을 정의하기 위한 메소드
//	public String getRequestMethod() {
//		return METHOD_GET;
//	}
//	public void setRequestHeader(HttpURLConnection conn) { }
//	public void setConfiguration(HttpURLConnection conn) { }
//	public void writeOutput(OutputStream conn) { }
//
//	private boolean isCanceled = false;
//	public void cancel() {
//		isCanceled = true;
//		if (mConn != null) {
//			try {
//			    mConn.disconnect();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		manager.processCancel(this);
//	}
//	public boolean isCanceled() {
//		return isCanceled;
//	}
//
//	HttpURLConnection mConn;
//	@Override
//	public void run() {
//		int retryCount = retry;
//		while (retryCount > 0 && !isCanceled) {
//			try {
//				URL url = getURL();
//				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//				conn.setConnectTimeout(connectionTimeout);
//				conn.setReadTimeout(readTimeout);
//
//				String requestMethod = getRequestMethod();
//				if (requestMethod.equals(METHOD_POST)) {
//					conn.setDoOutput(true); //default : false
//				}
//				conn.setRequestMethod(METHOD_GET);
//				conn.setDoInput(true); //default : true
////				setConfiguration(conn);
//				setRequestHeader(conn);
////				if (conn.getDoOutput()) {
////					writeOutput(conn.getOutputStream());
////				}
////				if (isCanceled) continue;
//				int responseCode = conn.getResponseCode();
////				if (isCanceled) continue;
//				mConn = conn;
//				if (responseCode == HttpURLConnection.HTTP_OK) {
//					InputStream is = conn.getInputStream();
//					result = parse(is);
//					manager.sendSuccess(this);
//					mConn = null;
//					return;
//				} else {
//					retryCount = 0;
//				}
//				mConn = null;
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//				retryCount = 0;
//			} catch (IOException e) {
//				e.printStackTrace();
//				retryCount--;
//			}
//		}
//		manager.sendFail(this);
//	}
//
//	public void sendSuccess() {
//		if (!isCanceled) {
//			if (mListener != null) {
//				mListener.onSuccess(this, result);
//			}
//		} else {
//			manager.processCancel(this);
//		}
//	}
//
//	public void sendFail() {
//		if (!isCanceled) {
//			if (mListener != null) {
//				mListener.onFail(this, errorCode);
//			}
//		} else {
//			manager.processCancel(this);
//		}
//	}
//}
