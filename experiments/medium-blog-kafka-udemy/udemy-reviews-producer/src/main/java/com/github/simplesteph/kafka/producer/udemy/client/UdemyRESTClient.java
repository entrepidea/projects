package com.github.simplesteph.kafka.producer.udemy.client;

import avro.shaded.com.google.common.annotations.VisibleForTesting;
import com.github.simplesteph.avro.udemy.Course;
import com.github.simplesteph.avro.udemy.Review;
import com.github.simplesteph.avro.udemy.User;
import com.github.simplesteph.kafka.producer.udemy.model.ReviewApiResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpException;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class UdemyRESTClient {

    private Integer count;
    private String courseId;
    private Integer nextPage;
    private final Integer pageSize;

    public UdemyRESTClient(String courseId, Integer pageSize) {
        this.pageSize = pageSize;
        this.courseId = courseId;
    }

    private void init() throws HttpException {
        count = reviewApi(1, 1).getCount();
        // we fetch from the last page
        nextPage = count / pageSize + 1;
    }

    public List<Review> getNextReviews() throws HttpException {
        if (nextPage == null) init();
        int page=1;
        if (nextPage >= 1) {
            List<Review> result = reviewApi(pageSize, page++).getReviewList();
            nextPage -= 1;

            return result;
        }

        return Collections.emptyList();
    }

    public ReviewApiResponse reviewApi(Integer pageSize, Integer page) throws HttpException {
        String url = "https://www.udemy.com/api-2.0/courses/" + courseId + "/reviews";
        HttpResponse<JsonNode> jsonResponse = null;
        try {

            //create basic authentication and put it as a field in HTTP header
            String clientId =  "G7elOJy7K8ULS3fOCODXXeBLPoMwauYGJrx9GseW";
            String clientSec = "oRttVDnke3d39yqDGEVH7mB5v3U4JLtgYa0L5nATAIrswWqduVlduSXC6u3IlRG5Aim95L5oMhJlM54d89S7dU5CrDaPqxjRKneZYG6FK25qBXtaTK42fSnOaZSu01c6";
            String plainClientCredentials=clientId+":"+clientSec;
            String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
            base64ClientCredentials = "Basic "+base64ClientCredentials;

            Map<String, String> headers = new HashMap<>();
            headers.put("accept", "application/json");
            headers.put("Authorization", base64ClientCredentials);
            jsonResponse = Unirest.get(url)
                    .headers(headers)
                    .queryString("page", page)
                    .queryString("page_size", pageSize)
                    .queryString("fields[course_review]", "title,content,rating,created,modified,user_modified,user,course")
                    .asJson();
        } catch (UnirestException e) {
            throw new HttpException(e.getMessage());
        }

        if (jsonResponse.getStatus() == 200) {
            JSONObject body = jsonResponse.getBody().getObject();
            Integer count = body.getInt("count");
            String next = body.optString("next");
            String previous = body.optString("previous");
            List<Review> reviews = this.convertResults(body.getJSONArray("results"));
            ReviewApiResponse reviewApiResponse = new ReviewApiResponse(count, next, previous, reviews);

            return reviewApiResponse;
        }

        throw new HttpException("Udemy API Unavailable");
    }

    public List<Review> convertResults(JSONArray resultsJsonArray) {
        List<Review> results = new ArrayList<>();
        for (int i = 0; i < resultsJsonArray.length(); i++) {
            JSONObject reviewJson = resultsJsonArray.getJSONObject(i);
            Review review = jsonToReview(reviewJson);
            results.add(review);
        }
        results.sort(Comparator.comparing(Review::getCreated));
        return results;
    }

    public Review jsonToReview(JSONObject reviewJson) {
        Review.Builder reviewBuilder = Review.newBuilder();
        reviewBuilder.setContent(reviewJson.getString("content"));
        reviewBuilder.setId(reviewJson.getLong("id"));
        reviewBuilder.setRating(reviewJson.getBigDecimal("rating").toPlainString());
        reviewBuilder.setTitle(reviewJson.getString("content"));
        reviewBuilder.setCreated(DateTime.parse(reviewJson.getString("created")));
        reviewBuilder.setModified(DateTime.parse(reviewJson.getString("modified")));
        reviewBuilder.setUser(jsonToUser(reviewJson.getJSONObject("user")));
        reviewBuilder.setCourse(jsonToCourse(reviewJson.getJSONObject("course")));
        return reviewBuilder.build();
    }

    public User jsonToUser(JSONObject userJson) {
        User.Builder userBuilder = User.newBuilder();
        userBuilder.setTitle(userJson.getString("title"));
        userBuilder.setName(userJson.getString("name"));
        userBuilder.setDisplayName(userJson.getString("display_name"));

        return userBuilder.build();
    }

    public Course jsonToCourse(JSONObject courseJson) {
        Course.Builder courseBuilder = Course.newBuilder();
        courseBuilder.setId(courseJson.getLong("id"));
        courseBuilder.setTitle(courseJson.getString("title"));
        courseBuilder.setUrl(courseJson.getString("url"));

        return courseBuilder.build();
    }

    public void close() {
        try {
            Unirest.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
