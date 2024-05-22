package com.cfs.obd2logger.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ThumbnailService {

  @Autowired
  private S3Service s3Service;

  /**
   * 동영상에서 썸네일 추출
   */
  public File generateVideoThumbnail(MultipartFile videoMultiFile, String fileName)
      throws IOException {
    // Video 변환 (Multipartfile --> File)
    File videoFile = new File(fileName);
    videoMultiFile.transferTo(videoFile);

    // 썸네일 파일 생성 (파일명.png)
    String thumbExtension = "png";
    File thumbnail = File.createTempFile(fileName,
        "." + thumbExtension);

    try {
      System.out.println("START FRAMEGRAB");
      FrameGrab frameGrab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(videoFile));
      frameGrab.seekToSecondPrecise(0);       // 0초의 프레임
      System.out.println("START PICTURE");
      Picture thumbnailPic = frameGrab.getNativeFrame();

      BufferedImage bi = AWTUtil.toBufferedImage(thumbnailPic);
      ImageIO.write(bi, thumbExtension, thumbnail);
      System.out.println("THUMB FILE: " + thumbnail);
      return thumbnail;
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  /**
   * 썸네일 업로드 후 썸네일 URL 반환
   */
  public String uploadThumbnail(File thumbnail, String thumbnailName, String deviceId)
      throws IOException {
    return s3Service.uploadFile(thumbnail, deviceId);
  }

}
