package app;

import io.micronaut.http.annotation.*;

@Controller("/bankApi")
public class BankApiController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}