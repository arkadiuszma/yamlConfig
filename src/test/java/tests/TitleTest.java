package tests;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@Slf4j
public class TitleTest extends BaseTest{
    @Test
    @DisplayName("Title test")
    public void shouldValidateCorrectTitle(){
        Assertions.assertThat(driver.getTitle()).isEqualTo(System.getProperty("title"));
    }
}
