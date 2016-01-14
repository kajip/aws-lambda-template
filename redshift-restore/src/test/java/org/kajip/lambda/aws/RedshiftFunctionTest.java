package org.kajip.lambda.aws;

import com.amazonaws.services.redshift.model.Cluster;
import org.junit.Ignore;
import org.junit.Test;
import com.amazonaws.services.lambda.runtime.Context;
import org.kajip.lambda.test.ContextMockBuilder;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class RedshiftFunctionTest {

    RedshiftFunction redshiftFunction = new RedshiftFunction();

    @Test
    @Ignore
    public void handlerのテスト() throws Exception {
        Context contextMock = ContextMockBuilder.build();

        Cluster cluster = redshiftFunction.handler(contextMock);
    }
}
