package co.pickcake.messages;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

@SpringBootTest
public class MessagesSourceTest {

    @Autowired
    private  MessageSource messageSource;

    @Test
    public void helloMessages() {
        String hello = messageSource.getMessage("test.hello", null, Locale.ENGLISH);
        Assertions.assertThat(hello).isEqualTo("hello");

        String hello2 = messageSource.getMessage("test.hello", null, Locale.KOREAN);
        Assertions.assertThat(hello2).isEqualTo("안녕");
    }
    @Test
    public void notFoundMessages(){
        Assertions.assertThatThrownBy(() -> messageSource.getMessage("noMessage", null, Locale.ENGLISH))
                .isInstanceOf(NoSuchMessageException.class);
    }
}
