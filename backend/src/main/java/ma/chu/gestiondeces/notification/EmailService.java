package ma.chu.gestiondeces.notification;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void envoyerEmail(
            String destinataire,
            String sujet,
            String contenu
    ) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(destinataire);
        message.setSubject(sujet);
        message.setText(contenu);

        mailSender.send(message);
    }
}