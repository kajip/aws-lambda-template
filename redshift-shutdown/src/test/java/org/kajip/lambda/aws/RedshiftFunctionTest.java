package org.kajip.lambda.aws;

import com.amazonaws.services.redshift.model.Cluster;
import org.junit.Test;
import com.amazonaws.services.lambda.runtime.Context;
import org.kajip.lambda.test.ContextMockBuilder;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class RedshiftFunctionTest {

    RedshiftFunction redshiftFunction = new RedshiftFunction();

//    @Test
    public void handlerのテスト() throws Exception {

        String name = "Test";
        Context contextMock = ContextMockBuilder.build();

        Cluster cluster = redshiftFunction.restore(contextMock);

//        assertThat(result, is("Hello, " + name));
    }
}
