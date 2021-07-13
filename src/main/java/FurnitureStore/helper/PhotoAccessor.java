package FurnitureStore.helper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import FurnitureStore.dto.PhotoUploadResultDTO;

public class PhotoAccessor implements IPhotoAccessor {
    private static Map config = new HashMap();
    private static Cloudinary cloudinary;

    public PhotoAccessor() {
    }

    protected static Cloudinary getCloudinary() {
        if (cloudinary == null) {
            config.clear();
            config.put("cloud_name", "sang-nguyen-uit");
            config.put("api_key", "421283825294697");
            config.put("api_secret", "XjraJvCwJrSRqsLFSoyWBtt0Pks");
            cloudinary = new Cloudinary(config);
        }
        return cloudinary;
    }

    @Override
    public PhotoUploadResultDTO AddPhoto(File file) throws Exception {
        Map uploadResult = new HashMap();
        if ((file.length() > 0)) {
            try {
                Map params = ObjectUtils.asMap("eager",
                        Arrays.asList(new Transformation().width(500).height(500).crop("fill").gravity("face")));
                uploadResult = getCloudinary().uploader().upload(file, params);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (uploadResult.get("public_id") == null || (uploadResult.get("public_id").equals("")))
            throw new Exception("Error Uploading");
        return new PhotoUploadResultDTO(uploadResult.get("public_id").toString(), uploadResult.get("url").toString());
    }

    @Override
    public String DeletePhoto(String publicId) throws Exception {
        Map deleteResult = new HashMap();
        try {
            deleteResult = getCloudinary().uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new Exception("Error Deleting");
        }
        return deleteResult.get("result") != null ? deleteResult.get("result").toString() : "fail";
    }

}