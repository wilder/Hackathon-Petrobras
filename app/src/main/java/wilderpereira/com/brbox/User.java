package wilderpereira.com.brbox;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Wilder on 30/09/17.
 */

public class User implements Parcelable {

    String username;
    String email;
    String userId;
    Integer score;
    Integer level;
    String message;
    Double credits;

    public User(String username, String email, String userId, Integer score, Integer level, String message, Double credits) {
        this.username = username;
        this.email = email;
        this.userId = userId;
        this.score = score;
        this.level = level;
        this.message = message;
        this.credits = credits;
    }

    public User () {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.userId);
        dest.writeValue(this.score);
        dest.writeValue(this.level);
        dest.writeString(this.message);
        dest.writeValue(this.credits);
    }

    protected User(Parcel in) {
        this.username = in.readString();
        this.email = in.readString();
        this.userId = in.readString();
        this.score = (Integer) in.readValue(Integer.class.getClassLoader());
        this.level = (Integer) in.readValue(Integer.class.getClassLoader());
        this.message = in.readString();
        this.credits = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
