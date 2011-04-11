package org.reber.twitpicviewer;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

public class ViewPic extends Activity {
	
	private Bitmap drawable;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent caller = getIntent();
		
		final Uri uri = caller.getData();
		Log.d("VIEW_PIC", uri.toString());
		
		final ProgressDialog prompt = ProgressDialog.show(this, "", "Loading " + uri.toString(), true);

		final Handler handler = new Handler();
		new Thread() {
			@Override
			public void run() {
				String url = uri.toString();
				if (url.contains("twitpic")) {
					url = url.replace("http://twitpic.com/", "");
					url = "http://twitpic.com/show/large/" + url;
				} else if (url.contains("yfrog")) {
					url += ":iphone";
				} else if (url.contains("plixi")) {
					url = "http://api.plixi.com/api/tpapi.svc/imagefromurl?url=" + url;
				}

				try {
					drawable = getImage(ViewPic.this, url.trim(), "img.jpg");
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				handler.post(new Runnable() {
					@Override
					public void run() {
						ImageView iv = (ImageView) findViewById(R.id.imageview);
						iv.setImageBitmap(drawable);
						
						prompt.dismiss();
					}
				});
			}
		}.start();
		prompt.show();
	}

	private Bitmap getImage(Context ctx, String url, String saveFilename) throws MalformedURLException, IOException {
		Bitmap x;

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestProperty("User-agent","Mozilla/4.0");
		HttpURLConnection.setFollowRedirects(true);
		connection.connect();

		Log.d("VIEW_PIC", connection.getContentType());
		Log.d("VIEW_PIC", connection.getContentLength() + "");
		InputStream input = connection.getInputStream();

		x = BitmapFactory.decodeStream(new PatchInputStream(input));
		Log.d("VIEW_PIC", "Finished Decoding Stream " + x);
		return x;
	}

	public class PatchInputStream extends FilterInputStream {
		public PatchInputStream(InputStream in) {
			super(in);
		}
		public long skip(long n) throws IOException {
			long m = 0L;
			while (m < n) {
				long _m = in.skip(n-m);
				if (_m == 0L) break;
				m += _m;
			}
			return m;
		}
	}
}