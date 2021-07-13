package FurnitureStore.helper;

import java.io.File;

import FurnitureStore.dto.PhotoUploadResultDTO;

public interface IPhotoAccessor {
    PhotoUploadResultDTO AddPhoto(File file) throws Exception;

    String DeletePhoto(String publicId) throws Exception;
}