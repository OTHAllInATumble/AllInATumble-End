package com.example.su.gpstest2.product;

import android.media.Image;
import android.provider.MediaStore;

import com.example.su.gpstest2.company.Company;

import java.util.List;

/**
 * Created by Su on 2016/4/17.
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private Company company;
    private List<MediaStore.Audio> audios;
    private List<Image> imgs;
    private List<MediaStore.Video> videos;

    public Product(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<MediaStore.Audio> getAudios() {
        return audios;
    }

    public void setAudios(List<MediaStore.Audio> audios) {
        this.audios = audios;
    }

    public List<Image> getImgs() {
        return imgs;
    }

    public void setImgs(List<Image> imgs) {
        this.imgs = imgs;
    }

    public List<MediaStore.Video> getVideos() {
        return videos;
    }

    public void setVideos(List<MediaStore.Video> videos) {
        this.videos = videos;
    }
}
