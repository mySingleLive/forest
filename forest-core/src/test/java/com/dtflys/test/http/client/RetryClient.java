package com.dtflys.test.http.client;

import com.dtflys.forest.annotation.Request;

public interface RetryClient {

    @Request(
            url = "http://localhost:${port}/hello/user?username=foo",
            headers = {"Accept:text/plan"}
    )
    String simpleRetry();
}
