import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SimpleDeletedItemsRecovery2 {

    public static void main(String[] args) {
        String recoveryFolderPath = "recoveredFiles2/";
        String destinationFolderPath = "deletedFiles/";

        recoverDeletedItems(recoveryFolderPath, destinationFolderPath);
    }

    public static void recoverDeletedItems(String recoveryFolderPath, String destinationFolderPath) {
        Path recoveryFolder = Paths.get(recoveryFolderPath);
        Path destinationFolder = Paths.get(destinationFolderPath);

        if (!Files.exists(recoveryFolder) || !Files.isDirectory(recoveryFolder)) {
            System.err.println("Recovery folder does not exist or is not a directory.");
            return;
        }

        if (!Files.exists(destinationFolder) || !Files.isDirectory(destinationFolder)) {
            System.err.println("Destination folder does not exist or is not a directory.");
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(recoveryFolder)) {
            for (Path deletedItem : directoryStream) {
                BasicFileAttributes attrs = Files.readAttributes(deletedItem, BasicFileAttributes.class);
                LocalDateTime deletionDate = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(attrs.creationTime().toMillis()),
                        ZoneId.systemDefault());

                if (deletionDate.isAfter(now.minusDays(30))) {
                    Path destinationFile = destinationFolder.resolve(deletedItem.getFileName());
                    Files.copy(deletedItem, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Recovered: " + deletedItem.getFileName());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading recovery folder: " + e.getMessage());
        }
    }
}

