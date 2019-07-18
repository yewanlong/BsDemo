package com.beisheng.mybslibary.utils.cache;


import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Base64;

import com.beisheng.mybslibary.BSDocTalkApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;



public class BSCacheHelper<T> {
	public BSDocTalkApplication app = (BSDocTalkApplication) BSDocTalkApplication.applicationContext;
	public SharedPreferences sharePreferences;

	public BSCacheHelper() {
		sharePreferences = app.getPublicPreference();
	}
	

	public synchronized void saveObject(final String key, final T object) {
		if(object == null) {
			removeObject(key);
			return;
		}
		Runnable run = new Runnable() {
			@Override
			public void run() {
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					new ObjectOutputStream(baos).writeObject(object);
					String objectBase64 = new String(Base64.encode(
							baos.toByteArray(), Base64.DEFAULT));
					sharePreferences.edit().putString(key, objectBase64)
							.commit();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				}
			}
		};
		BSMyThreadPool.getInstance().execute(run);
	}

	@SuppressWarnings("unchecked")
	public synchronized void openObject(final String key, final CacheListener<T> listener) {
		final Handler handler = new Handler( BSDocTalkApplication.applicationContext.getMainLooper());
		new Runnable() {
			@Override
			public void run() {
				try {
					String objectBase64 = sharePreferences.getString(key, null);
					if (objectBase64 == null) {
						handler.post(new UIRunnable(key, null, listener));
						return;
					}
					byte[] bytearray = Base64.decode(objectBase64, Base64.DEFAULT);
					ByteArrayInputStream bis = new ByteArrayInputStream(bytearray);
					T object = (T) new ObjectInputStream(bis).readObject();
					handler.post(new UIRunnable(key, object, listener));
					return;
				} catch (OptionalDataException e) {
					e.printStackTrace();
				} catch (StreamCorruptedException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				}catch(Exception e){
					e.printStackTrace();
				}
				handler.post(new UIRunnable(key, null, listener));
			}
		}.run();
	}
	
	public void removeObject(String key) {
		sharePreferences.edit().remove(key).commit();
	}
	
	private class UIRunnable implements Runnable {
		private String key;
		private T object;
		private CacheListener<T> listener;
		
		public UIRunnable(String key, T object, CacheListener<T> listener) {
			this.key = key;
			this.object = object;
			this.listener = listener;
		}

		@Override
		public void run() {
			try {
				listener.onRead(object);
			} catch (Exception e) {
				removeObject(key);
			}
		}
	}
	
	public interface CacheListener<T> {
		public void onRead(T object);
	}
}
