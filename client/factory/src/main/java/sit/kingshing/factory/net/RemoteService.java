package sit.kingshing.factory.net;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import sit.kingshing.factory.model.api.RspModel;
import sit.kingshing.factory.model.api.account.AccountRspModel;
import sit.kingshing.factory.model.api.account.LoginModel;
import sit.kingshing.factory.model.api.account.RegisterModel;
import sit.kingshing.factory.model.api.group.GroupCreateModel;
import sit.kingshing.factory.model.api.group.GroupMemberAddModel;
import sit.kingshing.factory.model.api.message.MsgCreateModel;
import sit.kingshing.factory.model.api.moment.FeedCreateModel;
import sit.kingshing.factory.model.api.user.UserUpdateModel;
import sit.kingshing.factory.model.card.FeedCard;
import sit.kingshing.factory.model.card.GroupCard;
import sit.kingshing.factory.model.card.GroupMemberCard;
import sit.kingshing.factory.model.card.MessageCard;
import sit.kingshing.factory.model.card.UserCard;

/**
 * 网络请求的所有的接口
 */
public interface RemoteService {

    /*
     *  用户
     */

    //注册接口
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    //登录接口
    @POST("account/login")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    //绑定设备Id
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);

    // 用户更新的接口
    @PUT("user")
    Call<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);

    // 用户搜索的接口
    @GET("user/search/{name}")
    Call<RspModel<List<UserCard>>> userSearch(@Path("name") String name);

    // 用户关注接口
    @PUT("user/follow/{userId}")
    Call<RspModel<UserCard>> userFollow(@Path("userId") String userId);

    // 获取联系人列表
    @GET("user/contact")
    Call<RspModel<List<UserCard>>> userContacts();

    // 查询某人的信息
    @GET("user/{userId}")
    Call<RspModel<UserCard>> userFind(@Path("userId") String userId);


    /*
     * 发送消息
     */

    // 发送消息的接口
    @POST("msg")
    Call<RspModel<MessageCard>> msgPush(@Body MsgCreateModel model);


    /*
     * 群
     */


    // 创建群
    @POST("group")
    Call<RspModel<GroupCard>> groupCreate(@Body GroupCreateModel model);

    // 拉取群信息
    @GET("group/{groupId}")
    Call<RspModel<GroupCard>> groupFind(@Path("groupId") String groupId);

    // 群搜索的接口
    @GET("group/search/{name}")
    Call<RspModel<List<GroupCard>>> groupSearch(@Path(value = "name", encoded = true) String name);

    // 我的群列表
    @GET("group/list/{date}")
    Call<RspModel<List<GroupCard>>> groups(@Path(value = "date", encoded = true) String date);

    // 我的群的成员列表
    @GET("group/{groupId}/member")
    Call<RspModel<List<GroupMemberCard>>> groupMembers(@Path("groupId") String groupId);

    // 给群添加成员
    @POST("group/{groupId}/member")
    Call<RspModel<List<GroupMemberCard>>> groupMemberAdd(@Path("groupId") String groupId,
                                                         @Body GroupMemberAddModel model);

    /**
     * 朋友圈
     */

    //创建一条动态feed
    @POST("feed/publish")
    Call<RspModel<FeedCard>> feedCreate(@Body FeedCreateModel model);
    //获取指定时间之后可见的feeds

    //搜索一条feed
    @GET("feed/{feedId}")
    Call<RspModel<FeedCard>> feedSearch(@Path("feedId") String feedId);


    //获取指定时间之后所有可见的feed
    @GET("feed/visible/{lastUpdateTime}")
    Call<RspModel<List<FeedCard>>> feedVisible(@Path("lastUpdateTime") String lastUpdateTime);


    //获取指定时间之后自己创建的feed
    @GET("feed/self")
    Call<RspModel<List<FeedCard>>> feedSelf();
}
