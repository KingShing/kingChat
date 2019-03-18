package sit.kingshing.common.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class Fragment extends androidx.fragment.app.Fragment {

    private View mRoot;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       initArgs(getArguments());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot==null) {
            int layId = getContentLayoutId();

            View root = inflater.inflate(layId,container,false);
            initWidget(root);
            mRoot = root;

        }else{
            if (mRoot.getParent()!=null) {
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }



        return mRoot;


    }

    /**
     *
     * @param bundle
     * @return
     */
    protected void initArgs(Bundle bundle){

    }

    /**
     *  得到当前界面的资源id
     * @return 资源id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget(View root){

    }

    /**
     * 初始化数据
     */
    protected void initData(){

    }

    /**
     *  返回按键触发时调用
     * @return 返回true，代表我已处理返回逻辑，返回false，表示我没处理
     */
    public boolean onBackPressed(){
        return false;
    }
}