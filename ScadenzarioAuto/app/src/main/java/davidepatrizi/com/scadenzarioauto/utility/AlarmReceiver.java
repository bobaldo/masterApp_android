package davidepatrizi.com.scadenzarioauto.utility;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

import davidepatrizi.com.scadenzarioauto.R;

/**
 * Created by Bobaldo on 28/03/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private AlarmManager am;
    private Intent intentAssicurazione;
    private PendingIntent pendingIntentAssicurazione;

    public AlarmReceiver(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("PD: ", "receive");
        int tipoAlarm;
        String scadenza;
        String targa;
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Notification notification = null;
            NotificationCompat.Builder builder = null;
            tipoAlarm = extras.getInt(Constant.TIPO_ALARM);
            scadenza = extras.getString(Constant.SCADENZA);
            targa = extras.getString(Constant.TARGA);
            Resources res = context.getResources();
            switch (tipoAlarm) {
                case Constant.ALARM_SCADENZA_ASSICURAZIONE:
                    builder = new NotificationCompat.Builder(context)
                            //.setSmallIcon(R.drawable.abc_btn_radio_material)
                            .setContentTitle(res.getString(R.string.ita_scadenza_assicurazione))
                            .setColor(Color.RED)
                            .setContentText(res.getString(R.string.ita_notifica_assicurazione).format(targa, scadenza));
                    notification = builder.build();
                    break;
                case Constant.ALARM_SCADENZA_BOLLO:
                    /*builder =new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.abc_btn_radio_material)
                            .setContentTitle("Scadenza Assicurazione")
                            .setContentText("Il tuo bollo scade il %s");
                    notification = builder.build();*/
                    break;
            }

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(tipoAlarm, notification);
        }
    }

    public void setAlarm(Context context, int tipoAlarm, String _scadenza, String targa) {
        intentAssicurazione = new Intent(context, AlarmReceiver.class);
        pendingIntentAssicurazione = PendingIntent.getBroadcast(context, 0, intentAssicurazione, 0);
        Calendar calendar = Calendar.getInstance();
        //calendar.setTime(new Date(_scadenza));
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 8);

        Log.w("PD", "scadenza: " + calendar.getTime().toString());

        intentAssicurazione.putExtra(Constant.TIPO_ALARM, tipoAlarm);
        intentAssicurazione.putExtra(Constant.SCADENZA, _scadenza);
        intentAssicurazione.putExtra(Constant.TARGA, targa);

        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentAssicurazione);

        Log.w("PD", "Alarm setted");
    }

    public void cancelAlarm(Context context, int tipoAlarm, String scadenza, String targa) {
        intentAssicurazione = new Intent(context, AlarmReceiver.class);
        pendingIntentAssicurazione = PendingIntent.getBroadcast(context, 0, intentAssicurazione, 0);
        intentAssicurazione.putExtra(Constant.TIPO_ALARM, tipoAlarm);
        intentAssicurazione.putExtra(Constant.SCADENZA, scadenza);
        intentAssicurazione.putExtra(Constant.TARGA, targa);

        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntentAssicurazione);
        Log.w("PD", "Alarm cancelled");
    }
}