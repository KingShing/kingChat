package sit.kingshing.kingchat;

import android.text.TextUtils;
import android.view.View;

public class Presenter implements  IPresenter{
    private IView mView ;

    public Presenter(IView view) {
        this.mView = view;
    }

    @Override
    public void search() {
        String str = mView.getInputString();
        if(TextUtils.isEmpty(str)){
            return;
        }

        String res = "Result:"+ str.hashCode();
        mView.setResultString(res);
    }
}
