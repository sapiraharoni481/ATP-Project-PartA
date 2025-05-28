package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Simple compressor output stream that uses run-length encoding (RLE) compression.
 * Compresses data by counting consecutive identical bytes and writing the count followed by the byte value.
 * Preserves the first 12 bytes of metadata without compression.
 *
 * @author Sapir Aharoni
 * @version 1.0
 */
public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;
    private byte lastByte;
    private int counter;

    /**
     * Creates a new simple compressor output stream.
     *
     * @param out the underlying output stream to write compressed data to
     */
    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
        this.lastByte = 0; // Start with 0
        this.counter = 0;
    }

    /**
     * Writes a single byte using run-length encoding compression.
     *
     * @param b the byte to write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(int b) throws IOException {
        if (b == lastByte) {
            counter++;
            if (counter == 255) { // If we reached the maximum a byte can represent
                out.write(counter);
                counter = 0;
                out.write(0); // Zero occurrences of the opposite value (to maintain order)
            }
        } else {
            out.write(counter);
            lastByte = (byte) b;
            counter = 1;
        }
    }

    /**
     * Compresses and writes a byte array using run-length encoding.
     * The compression process:
     * 1. Writes first 12 bytes as metadata without compression
     * 2. Applies RLE compression to the remaining maze content
     * 3. Handles maximum count overflow (255) by writing intermediate counts
     *
     * @param b the byte array to compress and write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(byte[] b) throws IOException {
        // Write metadata (dimensions and start/end positions)
        for (int i = 0; i < 12; i++) {
            out.write(b[i]);
        }

        // Compress maze content using the method described on pages 19-20
        lastByte = 0; // Start with 0
        counter = 0;

        for (int i = 12; i < b.length; i++) {
            byte currentBit = b[i];

            if (currentBit == lastByte) {
                counter++;
                // If we reached the maximum value a byte can represent (255)
                if (counter == 255) {
                    out.write(counter);
                    counter = 0;
                    // Continue with same value, need to output 0 occurrences of opposite value
                    out.write(0);
                }
            } else {
                // Write the count of the previous value
                out.write(counter);
                // Switch value and reset counter
                lastByte = currentBit;
                counter = 1;
            }
        }

        // Write the final count
        if (counter > 0) {
            out.write(counter);
        }
    }
}