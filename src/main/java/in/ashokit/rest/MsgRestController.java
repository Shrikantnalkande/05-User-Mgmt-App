package in.ashokit.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgRestController {

    private Logger logger = LoggerFactory.getLogger(MsgRestController.class);

    @GetMapping("/welcome")
    public String getWelcomeMsg(){
        logger.debug("started");
        logger.info("executed");
        String msg = "Welcome to ashok IT";
        logger.debug("completed");
        logger.warn("warning msg");
        logger.error("error msg");
        return msg;
    }

    public String getGreetMsg(){
        logger.debug("getGreetMsg() - started");
        logger.info("getGreetMsg() - executed");
        String msg = "Good Mrng";
        logger.debug("getGreetMsg()- completed");
        logger.warn("getGreetMsg()- warning msg");
        logger.error("getGreetMsg()- error msg");
        return msg;
    }
}
