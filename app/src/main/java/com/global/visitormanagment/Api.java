package com.global.visitormanagment;

import com.global.visitormanagment.model.VisitorModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL_PREFIX = "http://www.gdgoenkaerp.online/";

    String BASE_URL = BASE_URL_PREFIX + "api/";


    // add new visitor
    @FormUrlEncoded
    @POST("visitor-store")
    Call<VisitorModel> addNewVisitor(@Field("name") String name,
                                     @Field("phone") String phone,
                                     @Field("purpose") String purpose,
                                     @Field("no_of_person") int no_of_person,
                                     @Field("date") String date,
                                     @Field("in_time") String in_time,
                                     @Field("file") String file,
                                     @Field("file1") String file1);

    // to mark out
    @FormUrlEncoded
    @POST("visitor-store")
    Call<VisitorModel> markOutVisitor(
            @Field("out_time") String out_time,
            @Field("id") int id);



    //show visitor
    @FormUrlEncoded
    @POST("visitor")
    Call<List<VisitorModel>> getVisitor(@Field("date") String date);
}
