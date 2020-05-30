package com.afnanulcoder.aspirant;

public class PostDetails
{
    String post, postWriter;
    int likes;

    public PostDetails() {
    }

    public PostDetails(String post, String postWriter, int likes) {
        this.post = post;
        this.postWriter = postWriter;
        this.likes = likes;
    }


    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPostWriter() {
        return postWriter;
    }

    public void setPostWriter(String postWriter) {
        this.postWriter = postWriter;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
