package org.user.scheduler;

import org.openqa.selenium.WebDriver;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.user.service.MonitorLogDAO;
import org.user.util.HTTPChecker;
import org.user.util.WebDriverFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorJob implements Job {
    private final MonitorLogDAO logDAO = new MonitorLogDAO();

    // Lista de URLs a monitorar
    private static final List<String> URLS = Arrays.asList(
            "https://www.google.com/",
            "https://www.wikipedia.org/",
            "https://httpstat.us/400",
            "https://httpstat.us/404"
    );

    // Mensagens de err
    private static final Map<Integer, String> STATUS_ERROR_MESSAGES = new HashMap<>();
    static {
        STATUS_ERROR_MESSAGES.put(400, "Bad Request - Requisição inválida.");
        STATUS_ERROR_MESSAGES.put(401, "Unauthorized - Requer autenticação.");
        STATUS_ERROR_MESSAGES.put(403, "Forbidden - Acesso negado.");
        STATUS_ERROR_MESSAGES.put(404, "Not Found - Recurso não encontrado.");
        STATUS_ERROR_MESSAGES.put(500, "Internal Server Error - Erro interno no servidor.");
        STATUS_ERROR_MESSAGES.put(502, "Bad Gateway - Gateway inválido.");
        STATUS_ERROR_MESSAGES.put(503, "Service Unavailable - Serviço indisponível.");
        STATUS_ERROR_MESSAGES.put(504, "Gateway Timeout - Tempo limite no gateway.");
    }

    @Override
    public void execute(JobExecutionContext context) {
        for (String alvo : URLS) {
            long start = System.currentTimeMillis();
            WebDriver driver = null;
            String errorMessage = null;

            try {
                driver = WebDriverFactory.createHeadless();
                driver.get(alvo);
            } catch (Exception e) {
                errorMessage = e.getMessage();
            } finally {
                if (driver != null) {
                    driver.quit();
                }
            }

            long loadTime = System.currentTimeMillis() - start;
            int status = HTTPChecker.getStatusCode(alvo);


            if (errorMessage == null && STATUS_ERROR_MESSAGES.containsKey(status)) {
                errorMessage = STATUS_ERROR_MESSAGES.get(status);
            }

            logDAO.save(alvo, new Date(), status, errorMessage);
        }
    }
}
