package jp.co.techfun.alarmclock;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// �A���[��STOP���Activity
public class AlarmOnOffActivity extends Activity {

	// ���f�B�A�v���C���[�I�u�W�F�N�g
	private MediaPlayer player;
	// �J�E���g
	private int count = 0;
	// ����
	int ran;
	// handler�擾
	Handler handler = new Handler();

	// �uSTOP�v�{�^���^�O
	static final String BTN_STOP0 = "0";
	static final String BTN_STOP1 = "1";
	static final String BTN_STOP2 = "2";
	static final String BTN_STOP3 = "3";
	static final String BTN_STOP4 = "4";

	// onCreate���\�b�h(��ʏ����\���C�x���g)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarmonoff);
		
		// ���[�������_�C�A���O�\��
		showdialog();

		// �C���e���g�擾
		Bundle bundle = getIntent().getExtras();
		// �A���[�������擾
		Uri uri = (Uri) bundle.get(AlarmClockSampleActivity.URI);
		// �A���[�������T�C�����g�̏ꍇ�̓f�t�H���g�A���[������ݒ�
		if (uri == null) {
			uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		}
		// �A���[�����̊J�n
		player = MediaPlayer.create(this, uri);
		player.setLooping(true);
		player.start();

		// �@���X�g�擾
		final LinkedList<Button> list = new LinkedList<>();

		// STOP�{�^���I�u�W�F�N�g�擾
		Button btnStop1 = (Button) findViewById(R.id.btn_stop0);
		btnStop1.setTag(BTN_STOP0);
		list.add(btnStop1);
		Button btnStop2 = (Button) findViewById(R.id.btn_stop1);
		btnStop2.setTag(BTN_STOP1);
		list.add(btnStop2);
		Button btnStop3 = (Button) findViewById(R.id.btn_stop2);
		btnStop3.setTag(BTN_STOP2);
		list.add(btnStop3);
		Button btnStop4 = (Button) findViewById(R.id.btn_stop3);
		btnStop4.setTag(BTN_STOP3);
		list.add(btnStop4);
		Button btnStop5 = (Button) findViewById(R.id.btn_stop4);
		btnStop5.setTag(BTN_STOP4);
		list.add(btnStop5);

		// STOP�{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
		btnStop1.setOnClickListener(btnStopClickListener);
		btnStop2.setOnClickListener(btnStopClickListener);
		btnStop3.setOnClickListener(btnStopClickListener);
		btnStop4.setOnClickListener(btnStopClickListener);
		btnStop5.setOnClickListener(btnStopClickListener);

		// �X���b�h�N��
		Timer timer = new Timer(false);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// �����擾
				ran = ((int) (Math.random() * 10)) % 5;

				// handler�ɏ�����n��
				handler.post(new Runnable() {
					@Override
					public void run() {
						for(int i = 0;i<list.size();i++){
							Button btn = list.get(i);
							btn.setBackgroundResource(R.drawable.btn_lightblue);
						}
						Button correctBtn = list.get(ran);
						correctBtn.setBackgroundResource(R.drawable.btn_blue_light);
					}
				});
			}
		}, 10, 400);
	}

	private void showdialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(AlarmOnOffActivity.this);
		dialog.setTitle("�A���[�����~�߂�I�I");
		dialog.setMessage("STOP�{�^����5��~�߂�I�I");
		dialog.create().show();
	}

	// STOP�{�^���N���b�N���X�i�[��`
	private OnClickListener btnStopClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// �^�O�̎擾
			String tag = (String) ((Button) v).getTag();

			// ���b�Z�[�W�\���p�e�L�X�g�I�u�W�F�N�g�擾
			TextView tvMessage = (TextView) findViewById(R.id.tv_message);
			// �J�E���g�\���p�e�L�X�g�I�u�W�F�N�g�擾
			TextView tvCount = (TextView) findViewById(R.id.tv_count);

			// �擾�����������^�O�Ɠ����ꍇ
			if (ran == Integer.parseInt(tag)) {
				// �u�����I�v���b�Z�[�W�\��
				tvMessage.setText(getResources().getString(R.string.tv_ok_message));
				// �J�E���g����
				count++;
				// �e�L�X�g�\��
				tvCount.setText(String.valueOf(count));
				// �J�E���g5�Œ�~
				if (count >= 5) {
					// �A���[�����̒�~
					player.stop();
					Toast.makeText(AlarmOnOffActivity.this, "�X�g�b�v�I�I", Toast.LENGTH_SHORT).show();
					finish();
				}
			} else {
				// �u�ԈႢ�I�v���b�Z�[�W�\��
				tvMessage.setText(getResources().getString(R.string.tv_ng_message));
			}
		}
	};
}
