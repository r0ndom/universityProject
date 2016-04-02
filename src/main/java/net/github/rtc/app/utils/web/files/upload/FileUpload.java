package net.github.rtc.app.utils.web.files.upload;


import net.github.rtc.app.exception.ServiceProcessingException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * Class that is responsible for uploading files to server
 */
@Component
public class FileUpload implements java.io.Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(FileUpload.class.getName());


    private static final String EXTENTION = ".jpg";

    @Value("${img.save.folder}")
    private String imgfold;

    /**
     * Save image to server
     * @param filename with what name upload file to server
     * @param image actually image file that needs to be uploaded
     * @return string that represents filename with extension at server
     */
    public String saveImage(String filename, MultipartFile image) {
        final String adr = filename + EXTENTION;
        try {
            final File file = new File(imgfold + adr);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println(file.getName() + " is deleted");
                } else {
                    System.out.println("Delete operation is failed");
                }
            }
            FileUtils.writeByteArrayToFile(file, image.getBytes());
        } catch (Exception e) {
            throw new ServiceProcessingException("Unable to save image: " + e.getMessage());
        }
        return adr;
    }

    /**
     * Create folder that must contain images on server if it's not exist
     */
    @PostConstruct
    public void folderPhoto() {
        final File f = new File(imgfold);
        if (!f.exists()) {
            if (f.mkdir()) {
                LOG.info("Directory is created");
            } else {
                LOG.info("Directory is not created");
            }
        }
    }
}
