package gautero.tuma.memo.notifications;

public class Data {

    public String Title;
    public String Message;

    public Data(String t, String m){
        this.Title=t;
        this.Message=m;
    }
    public Data(){}

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
