package pl.agasior.interviewprep.services.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.agasior.interviewprep.dto.ImageDto;

import java.io.File;
import java.io.IOException;

@Service
public class ImageUploader {
    private static final String IMAGE_EXTENSION = ".jpg";
    private static final String TEMP_IMAGE = "temp";
    private static final String TEMP_DIRECTORY = "java.io.tmpdir";
    private static final String URL_KEY = "url";

    private final Cloudinary cloudinary;

    ImageUploader(final Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public ImageDto uploadImage(MultipartFile multipartFile) throws IOException {
        final var imageFile = multipartToFile(multipartFile);
        final var uploadResult = cloudinary.uploader().upload(imageFile, ObjectUtils.emptyMap());
        return new ImageDto((String) uploadResult.get(URL_KEY));
    }

    private File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        final var file = new File(System.getProperty(TEMP_DIRECTORY) + File.separator + TEMP_IMAGE + IMAGE_EXTENSION);
        FileUtils.writeByteArrayToFile(file, multipart.getBytes());
        return file;
    }
}
