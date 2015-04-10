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

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import davidepatrizi.com.scadenzarioauto.R;

/**
 * Created by Bobaldo on 28/03/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private AlarmManager am;
    private Intent intentAssicurazione;
    private PendingIntent pendingIntentAssicurazione;
    private Notification notification = null;
    private NotificationCompat.Builder builder = null;
    private NotificationManager nm = null;

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("PD: ", "receive");
        Bundle extras = intent.getExtras();
        if (extras != null) {
            int tipoAlarm = extras.getInt(Constant.TIPO_ALARM);
            String scadenza = extras.getString(Constant.SCADENZA);
            String targa = extras.getString(Constant.TARGA);

            Log.w("PD: ", "tipoAlarm: " + tipoAlarm);
            Log.w("PD: ", "scadenza: " + scadenza);
            Log.w("PD: ", "targa: " + targa);

            Resources res = context.getResources();
            switch (tipoAlarm) {
                case Constant.ALARM_SCADENZA_ASSICURAZIONE:
                    builder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.icona)
                            .setContentTitle(res.getString(R.string.ita_scadenza_assicurazione))
                            .setColor(Color.RED)
                            .setContentText(String.format(res.getString(R.string.ita_notifica_scadenza_assicurazione), targa.toUpperCase(), scadenza));
                    notification = builder.build();
                    nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    nm.notify(Constant.NOTIFICA_SCADENZA_ASSICURAZIONE, notification);
                    Log.w("PD: ", "notifica send: ");
                    break;
                case Constant.ALARM_SCADENZA_BOLLO:
                    builder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.icona)
                            .setSmallIcon(R.drawable.abc_btn_radio_material)
                            .setContentTitle(res.getString(R.string.ita_scadenza_bollo))
                            .setColor(Color.RED)
                            .setContentText(String.format(res.getString(R.string.ita_notifica_scadenza_bollo), targa.toUpperCase(), scadenza));
                    notification = builder.build();
                    nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    nm.notify(Constant.NOTIFICA_SCADENZA_BOLLO, notification);
                    break;
            }
        }
    }

    public void setAlarm(Context context, int tipoAlarm, String _scadenza, String targa) throws ParseException {
        //ASSUNZIONE: l'alert viene inviato il mese prima della scadenza intorno alle 10:10
        Date date = Constant.formatterYYYYMMDD.parse(_scadenza);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 10);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month - 1);

        Log.w("PD", "scadenza: " + _scadenza + " allarme: " + calendar.getTime().toString());
        intentAssicurazione = new Intent(context, AlarmReceiver.class);
        intentAssicurazione.putExtra(Constant.TIPO_ALARM, tipoAlarm);
        intentAssicurazione.putExtra(Constant.SCADENZA, calendar.getTime().toString());
        intentAssicurazione.putExtra(Constant.TARGA, targa);
        pendingIntentAssicurazione = PendingIntent.getBroadcast(context, 0, intentAssicurazione, 0);
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentAssicurazione);
        Log.w("PD", "Alarm setted");
    }

    public void cancelAlarm(Context context, int tipoAlarm, String scadenza, String targa) {
        intentAssicurazione = new Intent(context, AlarmReceiver.class);
        intentAssicurazione.putExtra(Constant.TIPO_ALARM, tipoAlarm);
        intentAssicurazione.putExtra(Constant.SCADENZA, scadenza);
        intentAssicurazione.putExtra(Constant.TARGA, targa);
        pendingIntentAssicurazione = PendingIntent.getBroadcast(context, 0, intentAssicurazione, 0);
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntentAssicurazione);
        Log.w("PD", "Alarm cancelled");
    }
}