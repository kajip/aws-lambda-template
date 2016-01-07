package org.kajip.lambda.hello;

import com.amazonaws.services.lambda.runtime.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {

    Logger logger = LoggerFactory.getLogger(getClass());

    public String handler(String name, Context context) {

        logger.debug("received: " + name);

        return "Hello, " + name;
    }
}
