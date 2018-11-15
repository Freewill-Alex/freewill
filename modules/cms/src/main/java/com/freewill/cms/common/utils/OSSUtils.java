package com.freewill.cms.common.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.freewill.common.config.CommonConfig;
import com.freewill.cms.common.config.ApiConfig;
import com.freewill.cms.common.exception.BussinessException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件工具类 上传到OSS
 *
 * @author dengfuyuan
 */
public class OSSUtils {

    private static final Logger logger = LoggerFactory.getLogger(OSSUtils.class);

    private static final int IMG_URL_PREFIX_INT = ApiConfig.OSS_IMG_URL.length();
    private static final String IMG_PRIVATE_PREFIX = ApiConfig.DOMAIN + "/file/download/private?fileName=";

    private static OSSClient ossClient;

    static {
        // 使用内网地址操作OSS
        ossClient = new OSSClient(ApiConfig.OSS_ENDPOINT, CommonConfig.getAliyunRamAccesskey(), CommonConfig.getAliyunRamAccesssecret());
    }

    OSSUtils() {

    }

    /**
     * 读取私有文件接口
     *
     * @param filename
     * @return
     */
    public static void downPrivateFile(String filename, HttpServletResponse response, String preview) {
        if (StringUtils.isEmpty(filename)) {
            return;
        }

        OSSObject ossObject = ossClient.getObject(ApiConfig.OSS_PRIVATE_BUCKETNAME, filename);

        if (StringUtils.isEmpty(preview)) {
            String originName = ossObject.getObjectMetadata().getUserMetadata().get("originname");
            response.setHeader("Content-Disposition", "attachement;filename=" + originName);
        }
        response.setContentType(ossObject.getObjectMetadata().getContentType());
        OutputStream outputStream = null;
        InputStream inputStream = ossObject.getObjectContent();
        try {
            outputStream = response.getOutputStream();

            byte[] buffer = new byte[8192];
            int len = inputStream.read(buffer);
            while (len != -1) {
                outputStream.write(buffer, 0, len);
                len = inputStream.read(buffer);
            }
        } catch (IOException e) {
            logger.debug("", e);
        } finally {
            try {
                inputStream.close();
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.debug("", e);
            }
        }
    }


    /**
     * 读取公有文件接口
     *
     * @param filename
     * @return
     */
    public static void downPublishFile(String filename, HttpServletResponse response, String preview) {
        if (StringUtils.isEmpty(filename)) {
            return;
        }

        OSSObject ossObject = ossClient.getObject(ApiConfig.OSS_PUBLIC_BUCKETNAME, filename);

        if (StringUtils.isEmpty(preview)) {
            String originName = ossObject.getObjectMetadata().getUserMetadata().get("originname");
            response.setHeader("Content-Disposition", "attachement;filename=" + originName);
        }
        response.setContentType(ossObject.getObjectMetadata().getContentType());
        OutputStream outputStream = null;
        InputStream inputStream = ossObject.getObjectContent();
        try {
            outputStream = response.getOutputStream();

            byte[] buffer = new byte[8192];
            int len = inputStream.read(buffer);
            while (len != -1) {
                outputStream.write(buffer, 0, len);
                len = inputStream.read(buffer);
            }
        } catch (IOException e) {
            logger.debug("", e);
        } finally {
            try {
                inputStream.close();
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.debug("", e);
            }
        }
    }


    /**
     * 读取私有文件
     *
     * @param filename
     * @return 返回文件流
     */
    public static InputStream getPrivateFileAsStream(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return null;
        }

        OSSObject ossObject = ossClient.getObject(ApiConfig.OSS_PRIVATE_BUCKETNAME, filename);

        return ossObject.getObjectContent();
    }

    /**
     * 获取原文件名
     *
     * @param filename
     * @return
     */
    public static String getFileOriginName(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return "";
        }

        String[] filenames = filename.split(",");
        StringBuilder sf = new StringBuilder();
        for (int i = 0; i < filenames.length; i++) {
            String tempName = filenames[i];
            OSSObject ossObject = ossClient.getObject(ApiConfig.OSS_PRIVATE_BUCKETNAME, tempName);
            String originName = ossObject.getObjectMetadata().getUserMetadata().get("originname");
            if (i > 0) {
                sf.append(",");
            }

            sf.append(originName);
        }
        return sf.toString();

    }

    /**
     * 上传公有文件
     *
     * @param image
     * @param module
     * @param type
     */
    public static String savePublicFile(MultipartFile image, String module, String type) {
        String filename = getFileName(module, type);

        // 获取指定文件的输入流
        InputStream content;
        try {
            content = image.getInputStream();

            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(image.getSize());
            String cache = "public";
            meta.setCacheControl(cache);
            meta.setContentType(image.getContentType());
            meta.setLastModified(new Date(System.currentTimeMillis()));

            // 原文件名
            String originName = image.getOriginalFilename();
            Map<String, String> map = new HashMap<>();
            map.put("originname", URLEncoder.encode(originName, "UTF-8"));
            meta.setUserMetadata(map);

            // 上传Object
            PutObjectResult result = ossClient.putObject(ApiConfig.OSS_PUBLIC_BUCKETNAME, filename, content, meta);

            // 打印ETag
            logger.debug(result.getETag());
        } catch (IOException e) {
            logger.warn("文件上传OSS失败", e);
            throw new BussinessException(image.getOriginalFilename() + "文件上传失败");
        }

        return filename;
    }

    /**
     * 上传私有文件
     *
     * @param image
     * @param module
     * @param type
     * @return
     */
    public static String savePrivateFile(MultipartFile image, String module, String type) {
        // 原文件名
        String originName = image.getOriginalFilename();

        String uploadFileName = getFileName(module, type);
        // 获取指定文件的输入流
        InputStream content;
        try {
            content = image.getInputStream();

            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(image.getSize());
            meta.setCacheControl("no-cache");
            meta.setContentType(image.getContentType());
            meta.setLastModified(new Date(System.currentTimeMillis()));
            Map<String, String> map = new HashMap<>();
            map.put("originname", URLEncoder.encode(originName, "UTF-8"));
            meta.setUserMetadata(map);

            // 上传Object
            PutObjectResult result = ossClient.putObject(ApiConfig.OSS_PRIVATE_BUCKETNAME, uploadFileName, content, meta);

            // 打印ETag
            logger.debug(result.getETag());
        } catch (IOException e) {
            logger.warn("文件上传OSS失败", e);
            throw new BussinessException(image.getOriginalFilename() + "文件上传失败");
        }
        return uploadFileName;
    }

    /**
     * 上传公有文件
     *
     * @param is
     * @param module
     * @param type
     * @return
     */
    public static String savePublishFile(InputStream is, String module, String type) {
        String filename = getFileName(module, type);

        String uploadFileName = filename;
        // 获取指定文件的输入流
        InputStream content;
        try {
            content = is;

            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();

            meta.setContentLength(is.available());
            meta.setCacheControl("no-cache");
            meta.setContentType("application/octet-stream");
            meta.setLastModified(new Date(System.currentTimeMillis()));

            Map<String, String> map = new HashMap<>();
            map.put("originname", URLEncoder.encode(filename.substring(filename.lastIndexOf("/") + 1, filename.length()), "UTF-8"));
            meta.setUserMetadata(map);

            // 上传Object
            PutObjectResult result = ossClient.putObject(ApiConfig.OSS_PUBLIC_BUCKETNAME, uploadFileName, content, meta);

            // 打印ETag
            logger.debug(result.getETag());
        } catch (IOException e) {
            logger.warn("文件上传OSS失败", e);
            throw new BussinessException("文件上传失败");
        }
        return uploadFileName;
    }

    /**
     * 上传私有文件
     *
     * @param is
     * @param module
     * @param type
     * @return
     */
    public static String savePrivateFile(InputStream is, String module, String type) {
        String filename = getFileName(module, type);

        String uploadFileName = filename;
        // 获取指定文件的输入流
        InputStream content;
        try {
            content = is;

            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();

            meta.setContentLength(is.available());
            meta.setCacheControl("no-cache");
            meta.setContentType("application/octet-stream");
            meta.setLastModified(new Date(System.currentTimeMillis()));

            Map<String, String> map = new HashMap<>();
            map.put("originname", URLEncoder.encode(filename.substring(filename.lastIndexOf("/") + 1, filename.length()), "UTF-8"));
            meta.setUserMetadata(map);

            // 上传Object
            PutObjectResult result = ossClient.putObject(ApiConfig.OSS_PRIVATE_BUCKETNAME, uploadFileName, content, meta);

            // 打印ETag
            logger.debug(result.getETag());
        } catch (IOException e) {
            logger.warn("文件上传OSS失败", e);
            throw new BussinessException("文件上传失败");
        }
        return uploadFileName;
    }

    /**
     * 相对路径添加 域名前缀
     * <p>
     * 逻辑： 1，地址为相对地址时，拼接前缀域名 2，地址为绝对地址时，直接返回
     *
     * @param url
     * @return
     */
    public static String addPrefix(String url) {
        // /2015227/topic/08f1a60b-2f55-4911-963f-fd07dd868871.jpg
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        String[] urls = url.split(",");
        StringBuilder urlTemp = new StringBuilder();
        for (int i = 0; i < urls.length; i++) {
            if (i > 0) {
                urlTemp.append(",");
            }
            String s = urls[i];
            if (!s.startsWith(ApiConfig.OSS_IMG_URL)) {
                s = ApiConfig.OSS_IMG_URL + s;
            }
            urlTemp.append(s);
        }
        return urlTemp.toString();
    }

    /**
     * 去掉OSS原始路径的前缀，获得相对路径名
     * <p>
     * 逻辑： 1，地址为相对地址时，直接返回(用于Mybatis取值) 2，地址为绝对地址时，截取域名前缀后返回(用于绝对地址存储)
     *
     * @param url OSS的原始绝对路径
     * @return
     */
    public static String removePrefix(String url) {
        // http://img.autovclub.com.com/2015227/topic/08f1a60b-2f55-4911-963f-fd07dd868871.jpg
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        String[] urls = url.split(",");
        StringBuilder urlTemp = new StringBuilder();
        for (int i = 0; i < urls.length; i++) {
            if (i > 0) {
                urlTemp.append(",");
            }
            String s = urls[i];
            if (s.startsWith(ApiConfig.OSS_IMG_URL)) {
                s = s.substring(IMG_URL_PREFIX_INT);
            }
            urlTemp.append(s);
        }
        return urlTemp.toString();

    }

    /**
     * 私有文件添加下载地址
     *
     * @param url 私有文件的原始绝对路径
     * @return String
     */
    public static String removePrivatePrefix(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        String[] urls = url.split(",");
        StringBuilder urlTemp = new StringBuilder();
        for (int i = 0; i < urls.length; i++) {
            if (i > 0) {
                urlTemp.append(urlTemp).append(",");
            }
            String s = urls[i];
            if (s.startsWith(ApiConfig.DOMAIN)) {
                s = s.substring(IMG_PRIVATE_PREFIX.length());
            }
            urlTemp.append(urlTemp).append(s);
        }
        return urlTemp.toString();
    }

    /**
     * 私有文件添加下载地址
     *
     * @param url
     * @return
     */
    public static String filePrivateUrl(String url) {

        if (StringUtils.isEmpty(url)) {
            return null;
        }
        String[] urls = url.split(",");
        StringBuilder urlTemp = new StringBuilder();
        for (int i = 0; i < urls.length; i++) {
            if (i > 0) {
                urlTemp.append(",");
            }
            String s = IMG_PRIVATE_PREFIX + urls[i];
            urlTemp.append(s);
        }
        return urlTemp.toString();
    }


    /**
     * APP端提交文件保存文件到OSS
     *
     * @param image
     * @param module
     * @param type
     * @param isPrivate
     */
    public static String saveFile(MultipartFile image, String module, String type, boolean isPrivate) {

        String filename = getFileName(module, type);

        // 获取指定文件的输入流
        InputStream content;
        try {
            content = image.getInputStream();

            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(image.getSize());
            String cache = isPrivate ? "no-cache" : "public";
            meta.setCacheControl(cache);
            meta.setContentType(image.getContentType());
            meta.setLastModified(new Date(System.currentTimeMillis()));

            // 上传Object
            PutObjectResult result;
            if (isPrivate) {
                result = ossClient.putObject(ApiConfig.OSS_PRIVATE_BUCKETNAME, filename, content, meta);
            } else {
                result = ossClient.putObject(ApiConfig.OSS_PUBLIC_BUCKETNAME, filename, content, meta);
            }

            // 打印ETag
            logger.debug(result.getETag());
        } catch (IOException e) {
            logger.warn("文件上传OSS失败", e);
            throw new BussinessException(image.getOriginalFilename() + "文件上传失败");
        }

        return filename;
    }

    /**
     * 从url转存到OSS
     *
     * @param imgUrl
     * @param module
     * @return
     */
    public static String uploadFromUrl(String imgUrl, String module) {

        String filename = getFileName(module, ".jpg");

        CloseableHttpClient client = null;
        try {
            client = HttpClients.createDefault();
            HttpGet get = new HttpGet(imgUrl);

            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();

            InputStream inputStream = entity.getContent();

            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(entity.getContentLength());
            meta.setCacheControl("public");
            meta.setContentType(entity.getContentType().getValue());
            meta.setLastModified(new Date(System.currentTimeMillis()));

            // 上传Object. PutObjectResult result =
            ossClient.putObject(ApiConfig.OSS_PUBLIC_BUCKETNAME, filename, inputStream, meta);

        } catch (Exception e) {
            return null;
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    logger.debug("", e);
                }
            }
        }

        return filename;

    }

    /**
     * 获取一个保存文件名
     *
     * @return
     */
    private static String getFileName(String module, String type) {
        StringBuilder filename = new StringBuilder();

        Calendar calender = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        filename.append(module);
        filename.append("/");
        filename.append(format.format(calender.getTime()));
        filename.append("/");

        UUID uuid = UUID.randomUUID();
        filename.append(uuid.toString());
        filename.append(type);

        return filename.toString();
    }
}
