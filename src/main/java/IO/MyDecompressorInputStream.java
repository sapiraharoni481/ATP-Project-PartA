//
//package IO;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//public class MyDecompressorInputStream extends InputStream {
//    private InputStream in;
//
//    public MyDecompressorInputStream(InputStream in) {
//        this.in = in;
//    }
//
//    @Override
//    public int read() throws IOException {
//        return in.read();
//    }
//
//    @Override
//    public int read(byte[] b) throws IOException {
//        if (b == null || b.length == 0) {
//            return 0;
//        }
//
//        // קריאת המטא-דאטה (12 בתים ראשונים)
//        for (int i = 0; i < 12; i++) {
//            int value = in.read();
//            if (value == -1) return -1;
//            b[i] = (byte) value;
//        }
//
//        // קריאת גודל המידע המקורי (מספר התאים)
//        int totalCells = 0;
//        for (int i = 0; i < 4; i++) {
//            int value = in.read();
//            if (value == -1) return -1;
//            totalCells = (totalCells << 8) | (value & 0xFF);
//        }
//
//        // קריאת גודל הדחיסה הראשונה (דחיסת ביטים)
//        int bitCompressedSize = 0;
//        for (int i = 0; i < 2; i++) {
//            int value = in.read();
//            if (value == -1) return -1;
//            bitCompressedSize = (bitCompressedSize << 8) | (value & 0xFF);
//        }
//
//        // קריאת גודל הדחיסה השניה (RLE)
//        int rleSize = 0;
//        for (int i = 0; i < 2; i++) {
//            int value = in.read();
//            if (value == -1) return -1;
//            rleSize = (rleSize << 8) | (value & 0xFF);
//        }
//
//        // קריאת המידע הדחוס (RLE)
//        byte[] rleCompressed = new byte[rleSize];
//        for (int i = 0; i < rleSize; i++) {
//            int value = in.read();
//            if (value == -1) break;
//            rleCompressed[i] = (byte) value;
//        }
//
//        // פענוח RLE לדחיסת ביטים
//        byte[] bitCompressed = new byte[bitCompressedSize];
//        int bitIndex = 0;
//
//        for (int i = 0; i < rleSize; i += 2) {
//            if (i + 1 < rleSize) {
//                byte value = rleCompressed[i];
//                int count = rleCompressed[i + 1] & 0xFF;
//
//                for (int j = 0; j < count && bitIndex < bitCompressedSize; j++) {
//                    bitCompressed[bitIndex++] = value;
//                }
//            }
//        }
//
//        // פענוח דחיסת ביטים למערך המקורי
//        int cellIndex = 12; // מתחיל אחרי המטא-דאטה
//
//        for (int i = 0; i < bitCompressedSize; i++) {
//            byte currentByte = bitCompressed[i];
//
//            // פענוח 8 ביטים (או פחות, אם בסוף המערך)
//            for (int j = 0; j < 8 && cellIndex < b.length; j++) {
//                if ((cellIndex - 12) < totalCells) {
//                    b[cellIndex++] = (byte) ((currentByte & (1 << (7 - j))) != 0 ? 1 : 0);
//                }
//            }
//        }
//
//        return b.length;
//    }
//}

package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

//    @Override
//    public int read(byte[] b) throws IOException {
//        if (b == null || b.length == 0) {
//            return 0;
//        }
//
//        // קריאת המטא-דאטה (12 בתים ראשונים)
//        for (int i = 0; i < 12; i++) {
//            int value = in.read();
//            if (value == -1) return -1;
//            b[i] = (byte) value;
//        }
//
//        // קריאת גודל המערך המקורי
//        int totalSize = 0;
//        for (int i = 0; i < 4; i++) {
//            int value = in.read();
//            if (value == -1) return -1;
//            totalSize = (totalSize << 8) | (value & 0xFF);
//        }
//
//        // וידוא שהמערך שקיבלנו גדול מספיק
//        if (b.length < totalSize) {
//            System.out.println("Warning: Output array too small. Expected " +
//                    totalSize + " bytes, but array is only " + b.length + " bytes.");
//        }
//
//        // חישוב גודל המידע הדחוס (ביטים)
//        int compressedSize = (totalSize - 12 + 7) / 8;
//        byte[] compressedData = new byte[compressedSize];
//
//        // קריאת המידע הדחוס
//        for (int i = 0; i < compressedSize; i++) {
//            int value = in.read();
//            if (value == -1) break;
//            compressedData[i] = (byte) value;
//        }
//
//        // פענוח המידע הדחוס
//        // איפוס כל הבתים המיועדים למידע המבוך
//        for (int i = 12; i < Math.min(b.length, totalSize); i++) {
//            b[i] = 0;
//        }
//
//        // פענוח כל ביט למידע המבוך
//        for (int i = 0; i < compressedSize; i++) {
//            byte currentByte = compressedData[i];
//            for (int j = 0; j < 8; j++) {
//                int originalIndex = i * 8 + j + 12;
//                if (originalIndex < totalSize) {
//                    if ((currentByte & (1 << (7 - j))) != 0) {
//                        b[originalIndex] = 1;
//                    }
//                }
//            }
//        }
//
//        return Math.min(b.length, totalSize);
//    }

    @Override
    public int read(byte[] b) throws IOException {
        if (b == null || b.length == 0) {
            return 0;
        }

        // קריאת המטא-דאטה (12 בתים ראשונים)
        for (int i = 0; i < 12; i++) {
            int value = in.read();
            if (value == -1) return -1;
            b[i] = (byte) value;
        }

        // קריאת גודל המערך המקורי
        int totalSize = 0;
        for (int i = 0; i < 4; i++) {
            int value = in.read();
            if (value == -1) return -1;
            totalSize = (totalSize << 8) | (value & 0xFF);
        }

        // וידוא שהמערך שקיבלנו גדול מספיק
        if (b.length < totalSize) {
            System.out.println("Warning: Output array too small. Expected " +
                    totalSize + " bytes, but array is only " + b.length + " bytes.");
        }

        // חישוב גודל המידע הדחוס (ביטים)
        int compressedSize = (totalSize - 12 + 7) / 8;
        byte[] compressedData = new byte[compressedSize];

        // קריאת המידע הדחוס
        for (int i = 0; i < compressedSize; i++) {
            int value = in.read();
            if (value == -1) break;
            compressedData[i] = (byte) value;
        }

        // פענוח המידע הדחוס
        // איפוס כל הבתים המיועדים למידע המבוך
        for (int i = 12; i < b.length; i++) {
            b[i] = 0;
        }

        // פענוח כל ביט למידע המבוך - עם בדיקת גבולות
        for (int i = 0; i < compressedSize; i++) {
            byte currentByte = compressedData[i];
            for (int j = 0; j < 8; j++) {
                int originalIndex = i * 8 + j + 12;
                // וודא שלא חורגים מגבולות המערך
                if (originalIndex < Math.min(b.length, totalSize)) {
                    if ((currentByte & (1 << (7 - j))) != 0) {
                        b[originalIndex] = 1;
                    }
                }
            }
        }

        return b.length;
    }
}