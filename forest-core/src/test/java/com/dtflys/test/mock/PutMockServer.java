package com.dtflys.test.mock;

import org.apache.http.HttpHeaders;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.Header;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-05-17 16:08
 */
public class PutMockServer extends MockServerRule {

    public final static String EXPECTED = "{\"status\": \"ok\"}";

    public final static Integer port = 5010;

    public PutMockServer(Object target) {
        super(target, port);
    }

    public void initServer() {
        MockServerClient mockClient = new MockServerClient("localhost", port);
        mockClient.when(
                request()
                        .withPath("/hello")
                        .withMethod("PUT")
                        .withHeader(new Header(HttpHeaders.ACCEPT, "text/plain"))
                        .withBody("username=foo&password=123456")
        ).respond(
                response()
                        .withStatusCode(200)
                        .withBody(EXPECTED)
        );
    }

}
