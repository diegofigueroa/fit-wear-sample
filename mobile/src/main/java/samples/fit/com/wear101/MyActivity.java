package samples.fit.com.wear101;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.view.Menu;
import android.view.MenuItem;

public class MyActivity extends Activity {
    private final String EXTRA_EVENT_ID = "event_id";
    private final String EXTRA_VOICE_REPLY = "extra_voice_reply";
    private final static String GROUP_KEY_EMAILS = "group_key_emails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        createSimpleNotification(1, "Test", "Do you see this on your watch?");
        createNotificationWithWearOnlyAction(2, "Test", "Hey look for some actions!");
        createNotificationBigStyle(3, "Test", "This is a big style notification, you should see this by default. How cool is that, right? Adding more text, because why not?");
        createNotificationWithVoiceAction(4, "Test", "Are you there? Want to say something?");
        createNotificationsGroup(5, "Test group", "I'm a nice and cool notification!", "Hey I'm a cool notification too!");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createSimpleNotification(int eventId, String eventTitle, String eventLocation) {
        final int notificationId = 001;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, MyActivity.class);
        viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(eventTitle)
                        .setContentText(eventLocation)
                        .setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private void createNotificationWithAction(int eventId, String eventTitle, String eventLocation) {
        final int notificationId = 002;
        // Build an intent for an action to view a map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=");
        mapIntent.setData(geoUri);

        PendingIntent mapPendingIntent = PendingIntent.getActivity(this, 0, mapIntent, 0);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MyActivity.class), 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(eventTitle)
                        .setContentText(eventLocation)
                        .setContentIntent(viewPendingIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_launcher, getString(R.string.map), mapPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private void createNotificationWithWearOnlyAction(int eventId, String eventTitle, String eventLocation) {
        final int notificationId = 003;
        // Build an intent for an action to view a map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=");
        mapIntent.setData(geoUri);

        PendingIntent mapPendingIntent = PendingIntent.getActivity(this, 0, mapIntent, 0);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MyActivity.class), 0);

        // Create the action
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_launcher, getString(R.string.view), mapPendingIntent).build();

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(eventTitle)
                        .setContentText(eventLocation)
                        .setContentIntent(viewPendingIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_launcher, getString(R.string.map), mapPendingIntent)
                        .extend(new NotificationCompat.WearableExtender().addAction(action));

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private void createNotificationBigStyle(int eventId, String eventTitle, String eventDescription) {
        final int notificationId = 004;
        // Build an intent for an action to view a map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=");
        mapIntent.setData(geoUri);

        // Specify the 'big view' content to display the long
        // event description that may not fit the normal content text.
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.setBigContentTitle("New Event!");
        bigStyle.bigText(eventDescription);

        PendingIntent mapPendingIntent = PendingIntent.getActivity(this, 0, mapIntent, 0);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MyActivity.class), 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(eventTitle)
                        .setContentIntent(viewPendingIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_launcher, getString(R.string.map), mapPendingIntent)
                        .setStyle(bigStyle);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private void createNotificationWithVoiceAction(int eventId, String eventTitle, String eventDescription) {
        final int notificationId = 005;
        String replyLabel = getString(R.string.say);

        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .build();

        // Create an intent for the reply action
        Intent replyIntent = new Intent(this, MyActivity.class);
        PendingIntent replyPendingIntent = PendingIntent.getActivity(this, 0, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the reply action and add the remote input
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_launcher, getString(R.string.say), replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        // Build the notification and add the action via WearableExtender
        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(eventTitle)
                        .setContentText(eventDescription)
                        .extend(new NotificationCompat.WearableExtender().addAction(action))
                        .build();

        // Issue the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, notification);
    }

    private void createNotificationsGroup(int eventId, String eventTitle, String eventDescription, String otherEventDescription) {
        final int notificationId = 006;

        // Build a notification, setting the group appropriately
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(eventTitle)
                .setContentText(eventDescription)
                .setSmallIcon(R.drawable.ic_launcher)
                .setGroup(GROUP_KEY_EMAILS)
                .build();

        Notification notification2 = new NotificationCompat.Builder(this)
                .setContentTitle("Another event!")
                .setContentText(otherEventDescription)
                .setSmallIcon(R.drawable.ic_launcher)
                .setGroup(GROUP_KEY_EMAILS)
                .build();


        // Create an InboxStyle notification
        Notification summary = new NotificationCompat.Builder(this)
                .setContentTitle("2 new messages")
                .setSmallIcon(R.drawable.ic_launcher)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Alex Faaborg   Check this out")
                        .addLine("Jeff Chang   Launch Party")
                        .setBigContentTitle("2 new messages")
                        .setSummaryText("johndoe@gmail.com"))
                .setGroup(GROUP_KEY_EMAILS)
                .setGroupSummary(true)
                .build();

        // Issue the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(notificationId, notification);
        notificationManager.notify(notificationId+1, notification2);
        notificationManager.notify(notificationId+2, summary);
    }
}
