package gautero.tuma.memo.notifications;

import android.app.NotificationManager;

import gautero.tuma.memo.model.Comment;

public class NotificationSender {
    public Data data;
    public String to;

    public NotificationSender(Data d, String t){
        this.data = d;
        this.to = t;
    }

    public NotificationSender(){

    }

}
