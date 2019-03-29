package sit.kingshing.kingchat.fragment.search;


import java.util.List;

import sit.kingshing.common.app.PresenterFragment;
import sit.kingshing.factory.model.card.GroupCard;
import sit.kingshing.factory.search.SearchContract;
import sit.kingshing.factory.search.SearchGroupPresenter;
import sit.kingshing.kingchat.R;
import sit.kingshing.kingchat.activities.SearchActivity;


public class SearchGroupFragment extends PresenterFragment<SearchContract.Presenter>
        implements SearchContract.GroupView, SearchActivity.SearchFragment {


    public SearchGroupFragment() {

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    public void search(String content) {
        mPresenter.search(content);
    }


    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchGroupPresenter(this);
    }

    @Override
    public void onSearchDone(List<GroupCard> groupCards) {

    }
}
