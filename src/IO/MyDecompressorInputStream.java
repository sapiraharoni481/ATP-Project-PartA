package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * Decompressor input stream that decompresses maze data compressed by MyCompressorOutputStream.
 * Reads metadata, original size, and bit-compressed data to reconstruct the original maze.
 *
 * @author Sapir Aharoni
 * @version 1.0
 */
public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    /**
     * Creates a new decompressor input stream.
     *
     * @param in the underlying input stream to read compressed data from
     */
    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * Reads a single byte from the underlying stream.
     *
     * @return the byte read, or -1 if end of stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Decompresses and reads data into the provided byte array.
     * The decompression process:
     * 1. Reads first 12 bytes as metadata
     * 2. Reads original array size (4 bytes)
     * 3. Reads and decompresses bit-compressed data
     * 4. Reconstructs original maze data
     *
     * @param b the byte array to fill with decompressed data
     * @return the number of bytes read
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read(byte[] b) throws IOException {
        if (b == null || b.length == 0) {
            return 0;
        }

        // Read metadata (first 12 bytes)
        for (int i = 0; i < 12; i++) {
            int value = in.read();
            if (value == -1) return -1;
            b[i] = (byte) value;
        }

        // Read original array size
        int totalSize = 0;
        for (int i = 0; i < 4; i++) {
            int value = in.read();
            if (value == -1) return -1;
            totalSize = (totalSize << 8) | (value & 0xFF);
        }

        // Ensure the array we received is large enough
        if (b.length < totalSize) {
            System.out.println("Warning: Output array too small. Expected " +
                    totalSize + " bytes, but array is only " + b.length + " bytes.");
        }

        // Calculate compressed data size (bits)
        int compressedSize = (totalSize - 12 + 7) / 8;
        byte[] compressedData = new byte[compressedSize];

        // Read compressed data
        for (int i = 0; i < compressedSize; i++) {
            int value = in.read();
            if (value == -1) break;
            compressedData[i] = (byte) value;
        }

        // Decompress the data
        // Initialize all bytes designated for maze data
        for (int i = 12; i < b.length; i++) {
            b[i] = 0;
        }

        // Decode each bit to maze data - with bounds checking
        for (int i = 0; i < compressedSize; i++) {
            byte currentByte = compressedData[i];
            for (int j = 0; j < 8; j++) {
                int originalIndex = i * 8 + j + 12;
                // Ensure we don't exceed array bounds
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