package org.kajip.lambda.hello;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class HelloWorld {

    public String handler(String name, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("received: " + name);

        return "Hello, " + name;
    }
}
