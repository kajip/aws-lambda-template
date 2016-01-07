package org.kajip.lambda.hello;

import org.junit.Test;
import com.amazonaws.services.lambda.runtime.Context;
import org.kajip.lambda.test.ContextMockBuilder;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class HelloWorldTest {

    HelloWorld helloWorld = new HelloWorld();

    @Test
    public void handlerのテスト() throws Exception {

        String name = "Test";
        Context contextMock = ContextMockBuilder.build();

        String result = helloWorld.handler(name, contextMock);

        assertThat(result, is("Hello, " + name));
    }
}
