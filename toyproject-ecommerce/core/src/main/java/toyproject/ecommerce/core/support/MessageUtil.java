package toyproject.ecommerce.core.support;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class MessageUtil {

    private final MessageSource messageSource;

    public String getMessage(String messageKey) {
        return getMessage(messageKey, null, Locale.getDefault());
    }

    public String getMessage(String messageKey, Object[] args, Locale locale) {
        return messageSource.getMessage(messageKey, args, locale);
    }
}
