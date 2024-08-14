package nior_near.server.global.util;

import org.springframework.beans.factory.annotation.Value;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

@Configuration
public class SmsService {
//    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
    private DefaultMessageService messageService;

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    @PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    public void sendMessage() {
        Message message = new Message();
        message.setFrom("010");
        message.setTo("010");
        message.setText("니어니어 테스트 문자 전송");
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }

}
