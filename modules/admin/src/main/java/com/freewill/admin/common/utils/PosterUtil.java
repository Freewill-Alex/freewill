package com.freewill.admin.common.utils;

import com.freewill.common.qrcode.QRCodeUtil;
import com.freewill.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * 海报工具类
 *
 * @author pengqi
 * @date 2018年6月13日 上午11:51:59
 */
public class PosterUtil {

    private final static Logger logger = LoggerFactory.getLogger(PosterUtil.class);

    public static void main(String[] args) {
        //writePicture("http://ejauto-test.oss-cn-shenzhen.aliyuncs.com/mingpian.png", "http://baidu.com/s?wd=我的小可爱啊", null, null, null);

        writePicture("http://ejauto-test.oss-cn-shenzhen.aliyuncs.com/mingpian.jpg", "http://baidu.com/s?wd=我的小可爱啊",
                "http://ejauto-test.oss-cn-shenzhen.aliyuncs.com/store/20180711/0a9b68b6-c2ea-46d8-a279-3dbe007b6f06.png?x-oss-process=image/resize,h_80",
                "苏宝杰", "销售顾问", "13811889988", "广东易捷好车信息科技有限公司", "广州市天河区汇苑街23号广东铁路投资大厦副楼7楼");
    }

    /**
     * 写图片到本地
     *
     * @param templetUrl 模板链接
     * @param qrContent  二维码内容
     * @param name
     * @param phone
     * @param address
     */
    private static void writePicture(String templetUrl, String qrContent, String logoUrl,
                                     String name, String job, String phone, String storeName, String address) {
        try {
            BufferedImage tag = buildDriverPoster(templetUrl, qrContent);
            OutputStream out = new FileOutputStream(new File("D:/1.jpg"));
            ImageIO.write(tag, "jpg", out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage buildQrImage(String qrContent) {
        BufferedImage tag = null;
        try {
            Image src = QRCodeUtil.createQrCode(qrContent, 350, 350, "jpg");
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            logger.info("二维码宽度：" + wideth + ",高度：" + height);
            tag = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = tag.createGraphics();
            g.setBackground(new Color(26, 26, 26));
            g.clearRect(0, 0, wideth, height);
            g.drawImage(src, 0, 0, wideth, height, null);
            g.dispose();
            tag.flush();
            logger.info(" 成功  ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }

    public static BufferedImage buildDriverPoster(String templetUrl, String qrContent) {

        BufferedImage tag = null;
        try {

            Image src = ImageIO.read(new URL(templetUrl));
            Image src2 = QRCodeUtil.createQrCode(qrContent, 350, 350, "jpg");

            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            int wideth2 = src2.getWidth(null);
            int height2 = src2.getHeight(null);

            logger.info("二维码宽度：" + wideth2 + ",高度：" + height2);

            tag = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = tag.createGraphics();
            g.setBackground(new Color(26, 26, 26));
            g.clearRect(0, 0, wideth, height);

            g.drawImage(src, 0, 0, wideth, height, null);
            g.drawImage(src2, 170, 2200, wideth2, height2, null);

            g.dispose();
            tag.flush();

            logger.info(" 成功  ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }


    public static InputStream buildToInputStream(String templetUrl, String qrContent) {
        BufferedImage image;
        if (StringUtils.isEmpty(templetUrl)) {
            image = buildQrImage(qrContent);
        } else {
            image = buildDriverPoster(templetUrl, qrContent);
        }
        return buildToInputStream(image);
    }


    private static InputStream buildToInputStream(BufferedImage image) {


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(baos);

            ImageIO.write(image, "jpg", imageOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }

}
