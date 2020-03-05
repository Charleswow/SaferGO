package com.memorandum.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;


import com.jack.isafety.R;
import com.memorandum.EditActivity;

import java.io.IOException;

public class AlertDialogActivity extends Activity implements OnClickListener {

	public static AlertDialogActivity context = null;
	private MediaPlayer player = new MediaPlayer();
	WakeLock mWakelock;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//��Ҫ��AndroidManifest��������Ȩ�ޣ�������Ļ
		PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
		mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, "AlertDialog");
		        mWakelock.acquire();
//		final Window win = getWindow();
//		win.addFlags( WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
//		        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
//		        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//��Ļ��������Ҫ����Ȩ��
		KeyguardManager keyguardManager = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
		KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("AlertDialog");
		keyguardLock.disableKeyguard();  
		context = this;
		try{
			Uri localUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM);
			if((player != null) && (localUri != null))
			{
					player.setDataSource(context,localUri);
					player.prepare();
					player.setLooping(false);
					player.start();
			}
			
			AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
			localBuilder.setTitle(R.string.alertName);
			localBuilder.setMessage(getIntent().getStringExtra("content"));
			localBuilder.setPositiveButton(R.string.positiveButton,this);
			localBuilder.setNegativeButton(R.string.negativeButton,this);
			localBuilder.show();
			
		}catch (IllegalArgumentException localIllegalArgumentException)
	    {
		      localIllegalArgumentException.printStackTrace();
		    }
		    catch (SecurityException localSecurityException)
		    {
		      localSecurityException.printStackTrace();
		    }
		    catch (IllegalStateException localIllegalStateException)
		    {
		      localIllegalStateException.printStackTrace();
		    } catch (IOException e)
		    {
				e.printStackTrace();
		    }
		}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		//TODO Auto-generated method stub
		switch(which){
		case DialogInterface.BUTTON1:
		{
			Intent intent = new Intent(AlertDialogActivity.this, EditActivity.class);
			Bundle b = new Bundle();
			b.putString("datetime",getIntent().getStringExtra("datetime"));
			b.putString("content", getIntent().getStringExtra("content"));
			b.putString("alerttime",getIntent().getStringExtra("alerttime"));
			intent.putExtra("android.intent.extra.INTENT", b);
			startActivity(intent);                                //����ת����Activity
			finish();
		}
		case DialogInterface.BUTTON2:
		{
//			mWakelock.release();
			player.stop();
			finish();
		}
	  }
	}
}