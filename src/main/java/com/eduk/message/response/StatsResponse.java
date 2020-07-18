package com.eduk.message.response;

import com.eduk.model.Content;
import org.springframework.data.util.Pair;

import java.util.List;

public class StatsResponse {
    public List<?> views;
    public Long totalViews;
    public Long totalVotes;
    public Long totalComments;
    public Long totalContents;
    public Long avgRating;
    public String favSubject;

    public StatsResponse(){}

    public StatsResponse(List<?> views, Long totalViews, Long totalComments, Long totalVotes,
                         Long totalContents, Long avgRating, String favSubject){
        this.views = views;
        this.totalViews = totalViews;
        this.totalComments = totalComments;
        this.totalVotes = totalVotes;
        this.totalContents = totalContents;
        this.avgRating = avgRating;
        this.favSubject = favSubject;
    }
}
