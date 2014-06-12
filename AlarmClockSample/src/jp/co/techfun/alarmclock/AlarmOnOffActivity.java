package jp.co.techfun.alarmclock;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// アラームSTOP画面Activity
public class AlarmOnOffActivity extends Activity {

	// メディアプレイヤーオブジェクト
	private MediaPlayer player;

	// カウント
	private int count = 0;

	// 「STOP」ボタンタグ
	static final String BTN_STOP0 = "0";
	static final String BTN_STOP1 = "1";
	static final String BTN_STOP2 = "2";
	static final String BTN_STOP3 = "3";
	static final String BTN_STOP4 = "4";

	// onCreateメソッド(画面初期表示イベント)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarmonoff);

		// インテント取得
		Bundle bundle = getIntent().getExtras();

		// アラーム音を取得
		Uri uri = (Uri) bundle.get(AlarmClockSampleActivity.URI);

		// アラーム音がサイレントの場合はデフォルトアラーム音を設定
		if (uri == null) {
			uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		}

		// アラーム音の開始
		player = MediaPlayer.create(this, uri);
		player.setLooping(true);
		player.start();

		// STOPボタンオブジェクト取得
		Button btnStop1 = (Button) findViewById(R.id.btn_stop0);
		btnStop1.setTag(BTN_STOP0);
		Button btnStop2 = (Button) findViewById(R.id.btn_stop1);
		btnStop2.setTag(BTN_STOP1);
		Button btnStop3 = (Button) findViewById(R.id.btn_stop2);
		btnStop3.setTag(BTN_STOP2);
		Button btnStop4 = (Button) findViewById(R.id.btn_stop3);
		btnStop4.setTag(BTN_STOP3);
		Button btnStop5 = (Button) findViewById(R.id.btn_stop4);
		btnStop5.setTag(BTN_STOP4);

		// STOPボタンオブジェクトにクリックリスナー設定
		btnStop1.setOnClickListener(btnStopClickListener);
		btnStop2.setOnClickListener(btnStopClickListener);
		btnStop3.setOnClickListener(btnStopClickListener);
		btnStop4.setOnClickListener(btnStopClickListener);
		btnStop5.setOnClickListener(btnStopClickListener);

	}

	// STOPボタンクリックリスナー定義
	private OnClickListener btnStopClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// タグの取得
			String tag = (String) ((Button) v).getTag();

			// メッセージ表示用テキストオブジェクト取得
			TextView tvMessage = (TextView) findViewById(R.id.tv_message);
			// カウント表示用テキストオブジェクト取得
			TextView tvCount = (TextView) findViewById(R.id.tv_count);

			// 乱数取得
			int ran = (int) (Math.random() * 10);

			// 取得した乱数を5で割った余りがタグと同じ場合
			if (ran % 5 == Integer.parseInt(tag)) {
				// 「正解！」メッセージ表示
				tvMessage.setText(getResources().getString(R.string.tv_ok_message));
				// カウント増加
				count++;
				// テキスト表示
				tvCount.setText(String.valueOf(count));
				// カウント5で停止
				if (count >= 5) {
					// アラーム音の停止
					player.stop();
					Toast.makeText(AlarmOnOffActivity.this, "ストップ！！", Toast.LENGTH_SHORT).show();
					finish();
				}
			} else {
				// 「間違い！」メッセージ表示
				tvMessage.setText(getResources().getString(R.string.tv_ng_message));
			}
		}
	};
}
