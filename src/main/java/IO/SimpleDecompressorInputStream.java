package IO;


import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        // קריאת המטא-דאטה
        for (int i = 0; i < 12; i++) {
            b[i] = (byte) in.read();
        }

        int index = 12; // מתחילים אחרי המטא-דאטה
        byte value = 0; // מתחילים עם 0

        // כל עוד לא סיימנו למלא את המערך
        while (index < b.length) {
            int count = in.read(); // קוראים את מספר ההופעות
            if (count == -1) break; // אם הגענו לסוף הקלט

            // ממלאים את הערך הנוכחי מספר פעמים לפי הספירה
            for (int i = 0; i < count && index < b.length; i++) {
                b[index++] = value;
            }

            // מחליפים בין 0 ל-1
            value = (byte) (1 - value);
        }

        return b.length;
    }
}