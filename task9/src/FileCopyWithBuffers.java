import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

public class FileCopyWithBuffers {
    public static void main(String[] args) {
        try {
            String sourceFile = "src/source.txt";
            String appendedFile = "appended.txt";
            String finalFile = "final.txt";
            String nameAndSurname = "Anna Kutova";

            // Copy content of the source file three times into a new file
            try (FileChannel srcChannel = FileChannel.open(Paths.get(sourceFile), StandardOpenOption.READ);
                 FileChannel destChannel = FileChannel.open(Paths.get(appendedFile),
                         StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {

                for (int i = 0; i < 3; i++) {
                    srcChannel.position(0); // Reset source channel position
                    destChannel.transferFrom(srcChannel, destChannel.size(), srcChannel.size());
                }
            }

            // Copy content of the new file with name and surname
            try (FileChannel srcChannel = FileChannel.open(Paths.get(appendedFile), StandardOpenOption.READ);
                 FileChannel destChannel = FileChannel.open(Paths.get(finalFile),
                         StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {

                // Check if source file size is sufficient for writing at position 200
                if (srcChannel.size() < 200) {
                    throw new IOException("Source file size is less than 200 bytes. Cannot proceed :(");
                }

                // Copy initial part up to position 200
                srcChannel.transferTo(0, Math.min(200, srcChannel.size()), destChannel);

                // Write my name and surname starting at position 200
                ByteBuffer nameBuffer = ByteBuffer.wrap(nameAndSurname.getBytes());
                destChannel.position(200);
                destChannel.write(nameBuffer);

                // Append the rest of the file
                srcChannel.transferTo(200, srcChannel.size() - 200, destChannel);
            }

            System.out.println("File operations completed successfully!");
        } catch (IOException e) {
            System.err.println("Error during file operations: " + e.getMessage());
        }
    }
}
