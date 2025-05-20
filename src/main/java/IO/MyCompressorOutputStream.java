//
//
//package IO;
//
//import java.io.IOException;
//import java.io.OutputStream;
//
//public class MyCompressorOutputStream extends OutputStream {
//    private OutputStream out;
//
//    public MyCompressorOutputStream(OutputStream out) {
//        this.out = out;
//    }
//
//    @Override
//    public void write(int b) throws IOException {
//        out.write(b);
//    }
//
//    @Override
//    public void write(byte[] b) throws IOException {
//        if (b == null || b.length == 0) {
//            return;
//        }
//
//        // כתיבת המטא-דאטה (12 בתים ראשונים)
//        for (int i = 0; i < 12; i++) {
//            out.write(b[i]);
//        }
//
//        // אלגוריתם דחיסה שמגביל את הגודל הסופי לפחות מ-1000 בתים
//
//        // שלב 1: דחיסת ביטים - 8 תאים בכל בייט
//        // נדחוס 8 תאים לתוך בייט אחד (כי במבוך יש רק 0 ו-1)
//        int totalCells = b.length - 12;
//        int compressedSize = (totalCells + 7) / 8; // כמה בתים נדרשים לייצג את כל התאים
//
//        // מערך לאחסון הנתונים הדחוסים
//        byte[] bitCompressedData = new byte[compressedSize];
//
//        // דחיסת כל תא במבוך לביט יחיד
//        for (int i = 12, bitIndex = 0, byteIndex = 0; i < b.length; i++) {
//            if (b[i] == 1) {
//                // מדליק את הביט המתאים
//                bitCompressedData[byteIndex] |= (1 << (7 - bitIndex % 8));
//            }
//
//            bitIndex++;
//            if (bitIndex % 8 == 0) {
//                byteIndex++;
//            }
//        }
//
//        // שלב 2: דחיסת RLE על המידע שכבר נדחס בביטים
//        int maxCompSize = 500; // מגביל לחצי מהגודל המקסימלי של המערך בטסט
//        byte[] rleCompressed = new byte[maxCompSize];
//        int rleIndex = 0;
//
//        // מעקב אחרי רצפים של בתים זהים
//        if (bitCompressedData.length > 0) {
//            byte currentByte = bitCompressedData[0];
//            int runLength = 1;
//
//            for (int i = 1; i < bitCompressedData.length; i++) {
//                if (bitCompressedData[i] == currentByte && runLength < 255) {
//                    runLength++;
//                } else {
//                    // כתיבת הבייט ומספר החזרות שלו למערך RLE
//                    if (rleIndex + 2 <= maxCompSize) {
//                        rleCompressed[rleIndex++] = currentByte;
//                        rleCompressed[rleIndex++] = (byte) runLength;
//                    }
//
//                    currentByte = bitCompressedData[i];
//                    runLength = 1;
//                }
//            }
//
//            // טיפול ברצף האחרון
//            if (rleIndex + 2 <= maxCompSize) {
//                rleCompressed[rleIndex++] = currentByte;
//                rleCompressed[rleIndex++] = (byte) runLength;
//            }
//        }
//
//        // שלב 3: כתיבת גודל המידע המקורי (לצורך פיענוח)
//        out.write((totalCells >> 24) & 0xFF);
//        out.write((totalCells >> 16) & 0xFF);
//        out.write((totalCells >> 8) & 0xFF);
//        out.write(totalCells & 0xFF);
//
//        // שלב 4: כתיבת גודל הדחיסה הראשונה (דחיסת ביטים)
//        out.write((compressedSize >> 8) & 0xFF);
//        out.write(compressedSize & 0xFF);
//
//        // שלב 5: כתיבת גודל הדחיסה השניה (RLE)
//        out.write((rleIndex >> 8) & 0xFF);
//        out.write(rleIndex & 0xFF);
//
//        // שלב 6: כתיבת המידע הדחוס
//        for (int i = 0; i < rleIndex; i++) {
//            out.write(rleCompressed[i]);
//        }
//    }
//}

package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        if (b == null || b.length == 0) {
            return;
        }

        // שמירת המטא-דאטה (12 בתים ראשונים) כמו שהוא ללא דחיסה
        for (int i = 0; i < 12; i++) {
            out.write(b[i]);
        }

        // אחסון גודל המערך המקורי
        int totalSize = b.length;
        out.write((totalSize >> 24) & 0xFF);
        out.write((totalSize >> 16) & 0xFF);
        out.write((totalSize >> 8) & 0xFF);
        out.write(totalSize & 0xFF);

        // דחיסת ביטים - 8 תאים בכל בייט
        int compressedSize = (b.length - 12 + 7) / 8; // מעוגל כלפי מעלה
        byte[] bitCompressed = new byte[compressedSize];

        // דחיסת כל תא במבוך לביט יחיד
        for (int i = 12; i < b.length; i++) {
            if (b[i] != 0) { // אם התא אינו 0, מדליקים את הביט המתאים
                int bitIndex = i - 12;
                int byteIndex = bitIndex / 8;
                int bitPosition = 7 - (bitIndex % 8);

                if (byteIndex < bitCompressed.length) {
                    bitCompressed[byteIndex] |= (1 << bitPosition);
                }
            }
        }

        // כתיבת הנתונים הדחוסים
        out.write(bitCompressed);
    }
}