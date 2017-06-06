package com.kanzhun.okhttp.common;

import com.kanzhun.okhttp.bean.MusicBean;

import java.util.List;

import kz.ally.okhttp.client.AbsResponse;

/**
 * Author: ZhouYou
 * Date: 2017/5/22.
 */
public class GetMusicListResponse extends AbsResponse {

    public int count;

    public int start;

    public int total;

    public List<MusicBean> musics;

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

    public List<MusicBean> getMusicList() {
        return musics;
    }

    @Override
    public String toString() {
        return "GetMusicListResponse{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", musics=" + musics.toString() +
                '}';
    }
}
