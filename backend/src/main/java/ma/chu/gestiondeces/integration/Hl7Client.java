/*package ma.chu.gestiondeces.integration;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Component
public class Hl7Client {

    private final Hl7Config config;

    public Hl7Client(Hl7Config config) {
        this.config = config;
    }

    public String sendMessage(String message)
            throws Exception {

        Socket socket =
                new Socket(
                        config.getHost(),
                        config.getPort()
                );

        OutputStream out =
                socket.getOutputStream();

        out.write(message.getBytes());

        out.flush();

        InputStream in =
                socket.getInputStream();

        byte[] buffer =
                new byte[4096];

        int len =
                in.read(buffer);

        socket.close();

        return new String(
                buffer,
                0,
                len
        );
    }
}*/