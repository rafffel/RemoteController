package hide92795.android.remotecontroller.activity;

import hide92795.android.remotecontroller.GoogleAnalyticsUtil;
import hide92795.android.remotecontroller.R;
import hide92795.android.remotecontroller.Session;
import hide92795.android.remotecontroller.receivedata.DynmapData;
import hide92795.android.remotecontroller.ui.dialog.CircleProgressDialogFragment.OnCancelListener;
import hide92795.android.remotecontroller.util.LogUtil;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DynmapActivity extends ActionBarActivity implements OnCancelListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.d("DynmapActivity#onCreate()");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dynmap);
		startLoad();
	}

	@Override
	protected void onStart() {
		super.onStart();
		LogUtil.d("DynmapActivity#onCreate()");
		GoogleAnalyticsUtil.startActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogUtil.d("DynmapActivity#onResume()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtil.d("DynmapActivity#onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		LogUtil.d("DynmapActivity#onStop()");
		GoogleAnalyticsUtil.stopActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtil.d("DynmapActivity#onDestroy()");
		WebView webview = (WebView) findViewById(R.id.web_dynmap_web);
		webview.stopLoading();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void startLoad() {
		DynmapData data = ((Session) getApplication()).getServerInfo().getDynmapData();
		if (data != null) {
			final String dynmap_address = data.getAddress();

			WebView webview = (WebView) findViewById(R.id.web_dynmap_web);
			webview.getSettings().setJavaScriptEnabled(true);
			webview.getSettings().setBuiltInZoomControls(true);
			webview.getSettings().setAppCacheEnabled(false);
			webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
			webview.setHorizontalScrollbarOverlay(true);
			webview.setHorizontalScrollBarEnabled(false);
			webview.setVerticalScrollbarOverlay(true);
			webview.setVerticalScrollBarEnabled(false);
			webview.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					return url.equals(dynmap_address);
				}

				@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon) {
					((Session) getApplication()).showProgressDialog(DynmapActivity.this, true, DynmapActivity.this);
				}

				@Override
				public void onPageFinished(WebView view, String url) {
					((Session) getApplication()).dismissProgressDialog();
				}
			});
			webview.loadUrl(dynmap_address);
		}
	}

	@Override
	public void onCancel() {
		finish();
	}
}
