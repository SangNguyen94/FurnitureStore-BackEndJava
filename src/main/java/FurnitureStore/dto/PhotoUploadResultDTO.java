package FurnitureStore.dto;

public class PhotoUploadResultDTO {
    private String publicId;
    private String url;

    public PhotoUploadResultDTO(String publicId, String url) {
        this.publicId = publicId;
        this.url = url;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getUrl() {
        return url;
    }

    public PhotoUploadResultDTO() {
		super();
	}

	public void setUrl(String url) {
        this.url = url;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
}
