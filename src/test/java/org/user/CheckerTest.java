package org.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.user.util.HTTPChecker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

class CheckerTest {

    private static final String BASE = "http://httpstat.us";

    @Test
    void testStatus200() {
        int status = HTTPChecker.getStatusCode(BASE + "/200");
        assertEquals(200, status, "Deveria retornar 200 para /200");  // HEAD retorna 200
    }

    @Test
    void testStatus404() {
        int status = HTTPChecker.getStatusCode(BASE + "/404");
        assertEquals(404, status, "Deveria retornar 404 para /404");
    }

    @Test
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    void testTimeout() {
        // sleep=6000 ms faz o servidor demorar mais que nosso timeout de 5s
        int status = HTTPChecker.getStatusCode(BASE + "/200?sleep=6000");
        assertEquals(-1, status, "Deveria retornar -1 em caso de timeout");  // timeout detectado
    }
}
