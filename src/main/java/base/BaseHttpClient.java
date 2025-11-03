package base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

public  class BaseHttpClient {

    public RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("https://stellarburgers.education-services.ru/api/")
            .addHeader("Content-Type", "application/json")
            .setRelaxedHTTPSValidation()
            .addFilter(new AllureRestAssured())
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .addFilter(new ErrorLoggingFilter())
            .build();

}