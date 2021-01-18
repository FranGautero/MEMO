package gautero.tuma.memo.ui.editStory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditStoryViewModel extends ViewModel{

    private MutableLiveData<String> mText;

    public EditStoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is edit fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
