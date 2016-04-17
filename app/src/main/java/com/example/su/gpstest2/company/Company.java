package com.example.su.gpstest2.company;

import android.location.Location;
import android.media.Image;
import android.provider.MediaStore;

import java.util.List;

/**
 * Created by Su on 2016/4/17.
 */
public class Company {
    private int id;
    private String name;
    private String address;
    private String description;
    private List<MediaStore.Audio> audios;
    private List<Image> imgs;
    private List<MediaStore.Video> videos;
    private Location loc;

    public Company(String name, String address, Location loc) {
        this.name = name;
        this.address = address;
        this.loc = loc;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }
}
