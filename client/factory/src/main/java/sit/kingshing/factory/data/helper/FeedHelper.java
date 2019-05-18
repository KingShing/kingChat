package sit.kingshing.factory.data.helper;

import android.os.SystemClock;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sit.kingshing.common.Common;
import sit.kingshing.common.app.Application;
import sit.kingshing.factory.Factory;
import sit.kingshing.factory.R;
import sit.kingshing.factory.data.DataSource;
import sit.kingshing.factory.model.api.RspModel;
import sit.kingshing.factory.model.api.moment.FeedCreateModel;
import sit.kingshing.factory.model.card.FeedCard;
import sit.kingshing.factory.model.db.Feed;
import sit.kingshing.factory.model.db.Feed_Table;
import sit.kingshing.factory.net.Network;
import sit.kingshing.factory.net.RemoteService;
import sit.kingshing.factory.net.UploadHelper;
import sit.kingshing.utils.CollectionUtil;
import sit.kingshing.utils.PicturesCompressor;
import sit.kingshing.utils.StreamUtil;


public class FeedHelper {
    // 异步上传图片
    public static void uploadAsynFeedPicture(final List<String> pictures, final String content, final DataSource.Callback<FeedCard> callback) {
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                List<String> ossPaths = new ArrayList<>();
                if (pictures != null){
                    for (String picture : pictures) {
                        if (picture == null) {
                            continue;
                        }
                        ossPaths.add(uploadPicture(picture));
                    }
                }
                String[] array = ossPaths.toArray(new String[0]);
                String jsonString = Factory.getGson().toJson(array, String[].class);
                Log.d("FeedHelper", "run: " + jsonString);
                FeedCreateModel model = new FeedCreateModel(content, jsonString);
                FeedHelper.create(model, callback);
            }
        });
    }


    // 上传图片
    private static String uploadPicture(String path) {
        File file = null;
        try {
            // 通过Glide的缓存区间解决了图片外部权限的问题
            file = Glide.with(Factory.app())
                    .load(path)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (file != null) {
            // 进行压缩
            String cacheDir = Application.getCacheDirFile().getAbsolutePath();
            String tempFile = String.format("%s/image/Cache_%s.png", cacheDir, SystemClock.uptimeMillis());

            try {
                // 压缩工具类
                if (PicturesCompressor.compressImage(file.getAbsolutePath(), tempFile,
                        Common.Constance.MAX_UPLOAD_IMAGE_LENGTH)) {
                    // 上传
                    String ossPath = UploadHelper.uploadFeedPicture(tempFile);
                    // 清理缓存
                    StreamUtil.delete(tempFile);
                    return ossPath;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // 从本地获取可见feed
    public static List<Feed> findVisibleFeedFromLocal() {
        return SQLite.select()
                .from(Feed.class)
                .orderBy(Feed_Table.createDate, false)
                .queryList();
    }

    // 从本地获取最新的feed time
    public static String getLastTime() {
//        List<Feed> feeds = SQLite.select()
//                .from(Feed.class)
//                .orderBy(Feed_Table.createDate, false)
//                .queryList();
//
//        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
//        if (feeds.size() > 0) {
//            return format.format(feeds.get(0).getCreateDate());
//        }
        return format.format(new Date(10, 1, 1, 0, 0, 0));
    }


    //从网络获取可见feed
    //不需要callback，直接存储到数据库,和数据对比，差异更新界面
    public static void freshVisibleFeed() {
        RemoteService service = Network.remote();
        service.feedVisible(getLastTime())
                .enqueue(new Callback<RspModel<List<FeedCard>>>() {
                    @Override
                    public void onResponse(Call<RspModel<List<FeedCard>>> call, Response<RspModel<List<FeedCard>>> response) {
                        RspModel<List<FeedCard>> rspModel = response.body();
                        if (rspModel.success()) {
                            List<FeedCard> feedCards = rspModel.getResult();
                            // 唤起进行保存的操作
                            Factory.getFeedCenter().dispatch(CollectionUtil.toArray(feedCards, FeedCard.class));
                            // 返回数据
                        } else {
                            Factory.decodeRspCode(rspModel, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<List<FeedCard>>> call, Throwable t) {
                        //no thing
                    }
                });
    }

    // feed的创建
    public static void create(FeedCreateModel model, final DataSource.Callback<FeedCard> callback) {
        RemoteService service = Network.remote();
        service.feedCreate(model)
                .enqueue(new Callback<RspModel<FeedCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<FeedCard>> call, Response<RspModel<FeedCard>> response) {
                        RspModel<FeedCard> rspModel = response.body();
                        if (rspModel.success()) {
                            FeedCard feedCard = rspModel.getResult();
                            // 唤起进行保存的操作
                            Factory.getFeedCenter().dispatch(feedCard);
                            //添加到自己的FTL中
                            //TODO 好像不需要
                            ////插入到到自己联系人的ftl中
                            // insert2FriendFTL(UserFactory.contacts(pubUser), feed);
                            // 返回数据
                            callback.onDataLoaded(feedCard);
                        } else {
                            Factory.decodeRspCode(rspModel, callback);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<FeedCard>> call, Throwable t) {
                        callback.onDataNotAvailable(R.string.data_network_error);
                    }
                });
    }


}
