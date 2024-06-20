package br.com.jonataalbuquerque.sprout;

import br.com.jonataalbuquerque.sprout.annotations.Controller;
import br.com.jonataalbuquerque.sprout.annotations.HttpBody;
import br.com.jonataalbuquerque.sprout.annotations.Post;
import br.com.jonataalbuquerque.sprout.domain.Teste;

@Controller
public class TestController {

    @Post(path = "/test")
    public Teste test(@HttpBody Teste requestHeader) {
        return requestHeader;
    }
}
