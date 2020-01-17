package cn.knowimage.service;

import cn.knowimage.utils.ClincialResult;


public interface ImageService {
    ClincialResult updateImage(String image_details, String image_type, String image_Date, String imageId);
    void deleteImage(String imageId);
}
