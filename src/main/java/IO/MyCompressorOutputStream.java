package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Compressor output stream that compresses maze data using bit compression.
 * Preserves the first 12 bytes of metadata and compresses the rest by
 * storing 8 maze cells in each byte using bit representation.
 *
 * @author Sapir Aharoni
 * @version 1.0
 */
public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    /**
     * Creates a new compressor output stream.
     *
     * @param out the underlying output stream to write compressed data to
     */
    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * Writes a single byte to the underlying stream.
     *
     * @param b the byte to write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * Compresses and writes a byte array to the underlying stream.
     * The compression process:
     * 1. Preserves first 12 bytes as metadata without compression
     * 2. Stores the original array size (4 bytes)
     * 3. Compresses remaining data using bit compression (8 cells per byte)
     *
     * @param b the byte array to compress and write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(byte[] b) throws IOException {
        if (b == null || b.length == 0) {
            return;
        }

        // Save metadata (first 12 bytes) without compression
        for (int i = 0; i < 12; i++) {
            out.write(b[i]);
        }

        // Store original array size
        int totalSize = b.length;
        out.write((totalSize >> 24) & 0xFF);
        out.write((totalSize >> 16) & 0xFF);
        out.write((totalSize >> 8) & 0xFF);
        out.write(totalSize & 0xFF);

        // Bit compression - 8 cells in each byte
        int compressedSize = (b.length - 12 + 7) / 8; // Round up
        byte[] bitCompressed = new byte[compressedSize];

        // Compress each maze cell to a single bit
        for (int i = 12; i < b.length; i++) {
            if (b[i] != 0) { // If cell is not 0, turn on the corresponding bit
                int bitIndex = i - 12;
                int byteIndex = bitIndex / 8;
                int bitPosition = 7 - (bitIndex % 8);

                if (byteIndex < bitCompressed.length) {
                    bitCompressed[byteIndex] |= (1 << bitPosition);
                }
            }
        }

        // Write compressed data
        out.write(bitCompressed);
    }
}