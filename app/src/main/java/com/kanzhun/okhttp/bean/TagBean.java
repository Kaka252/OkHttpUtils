package com.kanzhun.okhttp.bean;

/**
 * Author: ZhouYou
 * Date: 2017/6/6.
 */
public class TagBean {
    /**
     * count : 732
     * name : OST
     */

    private int count;
    private String name;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TagBean{" +
                "count=" + count +
                ", name='" + name + '\'' +
                '}';
    }
}
