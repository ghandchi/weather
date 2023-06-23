package be.inpress.weather.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

/**
 * Date & Time: 2023-06-22 20:34
 *
 * This component is considered for forming the error messages.
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@Slf4j
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    record ExceptionRule(Class<? extends WeatherException> exceptionClass, HttpStatus status) {
    }

    private final List<ExceptionRule> exceptionsRules = List.of(
            new ExceptionRule(UnknownException.class, HttpStatus.INTERNAL_SERVER_ERROR),
            new ExceptionRule(NotFoundException.class, HttpStatus.NOT_FOUND),
            new ExceptionRule(InvalidParameterException.class, HttpStatus.BAD_REQUEST),
            new ExceptionRule(CommunicationException.class, HttpStatus.BAD_GATEWAY)
    );

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        log.trace("Generating error attributes...");

        Throwable error = getError(request);

        Map<String, Object> map = super.getErrorAttributes(request, options);

        ExceptionRule exceptionRule = exceptionsRules.stream()
                .filter(rule -> rule.exceptionClass().isInstance(error))
                .findAny()
                .orElse(exceptionsRules.get(0));

        map.put("status", exceptionRule.status());
        map.put("message", error.getMessage());

        return map;
    }

    public Throwable getError(ServerRequest request) {
        Throwable error = super.getError(request);

        // Handling invalid JSON format and not supported urls
        if(error.getCause() instanceof DecodingException) {
            error = new InvalidParameterException("JSON format is invalid");
        } else if (error instanceof ResponseStatusException) {
            error = new NotFoundException("The requested resource (URL) is not handled currently");
        }

        return error;
    }
}