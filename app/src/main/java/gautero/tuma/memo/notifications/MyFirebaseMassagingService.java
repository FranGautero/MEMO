package gautero.tuma.memo.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import gautero.tuma.memo.R;
import gautero.tuma.memo.model.Usuario;

public class MyFirebaseMassagingService extends FirebaseMessagingService {
    private String sender;
    private static String TAG = "messaging";
    private FirebaseUser mUser;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference rootNode = db.getReference().child("LoggedUsers");

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sender = remoteMessage.getData().get("Message");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "PushNotificationChannel";
            String desc = "canal creado";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel;
            channel = new NotificationChannel("CommentNotify", name, importance);
            channel.setDescription(desc);

            NotificationManager notificationManager = getApplication().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        CharSequence name2 = "Haz recibido un comentario";
        CharSequence sequence = "De: " + sender;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication(), "CommentNotify")
                .setSmallIcon(R.mipmap.memo_launcher)
                .setContentTitle(name2)
                .setContentText(sequence)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());
        notificationManager.notify(200, builder.build());
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    public void sendRegistrationToServer(String token) {
        //Mando el usuario con su token a la db o lo sobreescribo
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        Usuario usuario = new Usuario();
        usuario.setToken(token);
        usuario.setUsuario(mUser.getEmail());
        rootNode.child(mUser.getUid()).setValue(usuario);
    }

}
