package xin.skingorz.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    //个人信息列表
    public static final int TYPE_MY = 0x01;
    //好友列表
    public static final int TYPE_FRIENDS = 0x02;

    @Id(autoincrement = true)
    private  Long id;

    @Unique
    private String email;           //邮箱地址
    private String username;        //用户名

    @Property
    private String user;            //好友所属关系
    private String created_at;      //账号创建时间
    private String mobilePhone;     //手机号
    private String imageUrl;        //头像地址
    private String individuality;   //个性签名
    private String birthPlace;      //出生地
    private String sex;             //性别
    private String age;             //年龄
    private String livePlace;       //常住地
    private String emergencyPhone;  //紧急联系人
    @Generated(hash = 1561920211)
    public User(Long id, String email, String username, String user,
            String created_at, String mobilePhone, String imageUrl,
            String individuality, String birthPlace, String sex, String age,
            String livePlace, String emergencyPhone) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.user = user;
        this.created_at = created_at;
        this.mobilePhone = mobilePhone;
        this.imageUrl = imageUrl;
        this.individuality = individuality;
        this.birthPlace = birthPlace;
        this.sex = sex;
        this.age = age;
        this.livePlace = livePlace;
        this.emergencyPhone = emergencyPhone;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getCreated_at() {
        return this.created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getMobilePhone() {
        return this.mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getIndividuality() {
        return this.individuality;
    }
    public void setIndividuality(String individuality) {
        this.individuality = individuality;
    }
    public String getBirthPlace() {
        return this.birthPlace;
    }
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
    public String getLivePlace() {
        return this.livePlace;
    }
    public void setLivePlace(String livePlace) {
        this.livePlace = livePlace;
    }
    public String getEmergencyPhone() {
        return this.emergencyPhone;
    }
    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getUser() {
        return this.user;
    }
    public void setUser(String user) {
        this.user = user;
    }

}
