package IO;


import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;
    private byte lastByte;
    private int counter;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
        this.lastByte = 0; // מתחילים עם 0
        this.counter = 0;
    }

    @Override
    public void write(int b) throws IOException {
        if (b == lastByte) {
            counter++;
            if (counter == 255) { // אם הגענו למקסימום שבייט יכול לייצג
                out.write(counter);
                counter = 0;
                out.write(0); // אפס הופעות של הערך ההפוך (כדי לשמור על הסדר)
            }
        } else {
            out.write(counter);
            lastByte = (byte) b;
            counter = 1;
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        // כתיבת המטא-דאטה (הממדים ומיקומי התחלה/סיום)
        for (int i = 0; i < 12; i++) {
            out.write(b[i]);
        }

        // דחיסת תוכן המבוך עצמו לפי השיטה שתוארה בעמוד 19-20
        lastByte = 0; // מתחילים עם 0
        counter = 0;

        for (int i = 12; i < b.length; i++) {
            byte currentBit = b[i];

            if (currentBit == lastByte) {
                counter++;
                // אם הגענו למקסימום ערך שבייט יכול לייצג (255)
                if (counter == 255) {
                    out.write(counter);
                    counter = 0;
                    // נמשיך עם אותו ערך, צריך להוציא 0 הופעות של הערך ההפוך
                    out.write(0);
                }
            } else {
                // כותבים את הספירה של הערך הקודם
                out.write(counter);
                // מחליפים את הערך ומאפסים את הספירה
                lastByte = currentBit;
                counter = 1;
            }
        }

        // כותבים את הספירה האחרונה
        if (counter > 0) {
            out.write(counter);
        }
    }
}
