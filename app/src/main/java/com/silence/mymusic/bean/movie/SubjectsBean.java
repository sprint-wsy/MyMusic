package com.silence.mymusic.bean.movie;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wushiyu on 2017/7/6.
 */

public class SubjectsBean implements Parcelable{
    /**
     * rating : {"max":10,"average":6.9,"stars":"35","min":0}
     * genres : ["剧情","喜剧"]
     * title : 我不是潘金莲
     * casts : [{"alt":"https://movie.douban.com/celebrity/1050059/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1691.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1691.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1691.jpg"},"name":"范冰冰","id":"1050059"},{"alt":"https://movie.douban.com/celebrity/1274274/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/39703.jpg","large":"https://img3.doubanio.com/img/celebrity/large/39703.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/39703.jpg"},"name":"郭涛","id":"1274274"},{"alt":"https://movie.douban.com/celebrity/1324043/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/58870.jpg","large":"https://img3.doubanio.com/img/celebrity/large/58870.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/58870.jpg"},"name":"大鹏","id":"1324043"}]
     * （多少人评分）collect_count : 56325
     * （原名）original_title : 我不是潘金莲
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1274255/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/45667.jpg","large":"https://img1.doubanio.com/img/celebrity/large/45667.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/45667.jpg"},"name":"冯小刚","id":"1274255"}]
     * year : 2016
     * images : {"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2378133884.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2378133884.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p2378133884.jpg"}
     * （更多信息）alt : https://movie.douban.com/subject/26630781/
     * id : 26630781
     */
    private RatingBean rating;
    private String title;
    private int collect_count;
    private String original_title;
    private String subtype;
    private String year;
    private ImageBean images;
    private String alt;
    private String id;
    private List<String> genres;
    private List<PersonBean> casts;
    private List<PersonBean> directors;

    protected SubjectsBean(Parcel in) {
        rating = in.readParcelable(RatingBean.class.getClassLoader());
        title = in.readString();
        collect_count = in.readInt();
        original_title = in.readString();
        subtype = in.readString();
        year = in.readString();
        images = in.readParcelable(ImageBean.class.getClassLoader());
        alt = in.readString();
        id = in.readString();
        genres = in.createStringArrayList();
        casts = in.createTypedArrayList(PersonBean.CREATOR);
        directors = in.createTypedArrayList(PersonBean.CREATOR);
    }

    public static final Creator<SubjectsBean> CREATOR = new Creator<SubjectsBean>() {
        @Override
        public SubjectsBean createFromParcel(Parcel in) {
            return new SubjectsBean(in);
        }

        @Override
        public SubjectsBean[] newArray(int size) {
            return new SubjectsBean[size];
        }
    };

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ImageBean getImages() {
        return images;
    }

    public void setImages(ImageBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<PersonBean> getCasts() {
        return casts;
    }

    public void setCasts(List<PersonBean> casts) {
        this.casts = casts;
    }

    public List<PersonBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<PersonBean> directors) {
        this.directors = directors;
    }

    @Override
    public String toString() {
        return  "SubjectsBean{" +
                "directors=" + directors +
                ", casts=" + casts +
                ", genres=" + genres +
                ", id='" + id + '\'' +
                ", alt='" + alt + '\'' +
                ", images=" + images +
                ", year='" + year + '\'' +
                ", subtype='" + subtype + '\'' +
                ", original_title='" + original_title + '\'' +
                ", collect_count=" + collect_count +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(rating, flags);
        dest.writeString(title);
        dest.writeInt(collect_count);
        dest.writeString(original_title);
        dest.writeString(subtype);
        dest.writeString(year);
        dest.writeParcelable(images, flags);
        dest.writeString(alt);
        dest.writeString(id);
        dest.writeStringList(genres);
        dest.writeTypedList(casts);
        dest.writeTypedList(directors);
    }
}
