package org.kajip.lambda.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import static org.mockito.Mockito.*;

public class ContextMockBuilder {

    public static Context build() {

        Context contextMock = mock(Context.class);
        LambdaLogger logger = mock(LambdaLogger.class);

        when(contextMock.getLogger()).thenReturn(logger);

        return contextMock;
    }
}
