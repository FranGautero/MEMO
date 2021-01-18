package gautero.tuma.memo.ui.storys;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is story fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}