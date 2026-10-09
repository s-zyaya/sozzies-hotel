package hotel.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${app.api.key}")
    private String apiKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        // Пропускаємо сам Swagger UI та документацію
        if (path.contains("/swagger-ui") || path.contains("/v3/api-docs")) {
            return true;
        }

        // Беремо ключ із заголовка запиту
        String requestKey = request.getHeader("X-API-KEY");

        // 🚨 НАШІ ЛОГИ ДЛЯ ШАМАНСТВА В LIVE METRICS:
        System.out.println("=== SWAGGER AUTH CHECK ===");
        System.out.println("Очікуваний ключ (той, що в налаштуваннях): [" + apiKey + "]");
        System.out.println("Отриманий ключ від користувача: [" + requestKey + "]");

        // Якщо в налаштуваннях Azure чомусь пусто, або ключі не збігаються
        if (apiKey == null || apiKey.isEmpty() || !apiKey.equals(requestKey)) {
            System.out.println("РЕЗУЛЬТАТ: Доступ ЗАБЛОКОВАНО (401)");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid API Key!");
            return false;
        }

        System.out.println("РЕЗУЛЬТАТ: Доступ ДОЗВОЛЕНО (200)");
        return true;
    }
}