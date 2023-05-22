/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spring.LoginRegister.Master;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author siRe
 */

@Configuration
public class DefaultContentTypeInterceptor implements Interceptor {

    public DefaultContentTypeInterceptor(String string) {
    }

    DefaultContentTypeInterceptor(String contentType, String applicationjson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original=chain.request();
        Request requestWithUserAgent=original.newBuilder()
                .header("Content-Type", "application/json")
                .header("api-key", "eEJkSHptdGtTSmlDeFpzWk5hYU4")

                .build();

        return chain.proceed(requestWithUserAgent);
    }

    public DefaultContentTypeInterceptor() {
    }

}
