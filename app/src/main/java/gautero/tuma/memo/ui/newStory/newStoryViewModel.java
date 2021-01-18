package gautero.tuma.memo.ui.newStory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class newStoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public newStoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new story fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}