package com.freewill.console.common.controller;

import com.freewill.common.web.annotation.IPLimit;
import com.freewill.common.web.annotation.ResponseResult;
import com.freewill.console.common.exception.BussinessException;
import com.freewill.console.common.utils.OSSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 * <p>
 * 私有文件上传 数据库存储的格式：/module/date/filename OSS提交格式：/userid/module/date/filename
 *
 * @author dengfuyuan
 */
@ResponseResult
@Controller
@RequestMapping("/file")
public class FileController {

    // 公有文件目录
    private static final String[] PUBLIC_MODULE = {"sdp_driver", "sdp_driver_activity"};
    // 私有文件目录
    private static final String[] PRIVATE_MODULE = {"user"};
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 文件上传的类型->后缀名映射
     */
    private Map<String, String> typemap = new HashMap<>();

    public FileController() {
        typemap.put(".jpg", ".jpg");
        typemap.put(".jpeg", ".jpg");
        typemap.put(".png", ".png");
        typemap.put(".bmp", ".bmp");
        typemap.put(".doc", ".doc");
        typemap.put(".docx", ".docx");
        typemap.put(".pdf", ".pdf");
        typemap.put(".rar", ".rar");
        typemap.put(".zip", ".zip");

        typemap.put(".xls", ".xls");
        typemap.put(".xlsx", ".xlsx");
        typemap.put(".ppt", ".ppt");
        typemap.put(".pptx", ".pptx");
        typemap.put(".txt", ".txt");
    }

    /**
     * 上传公有文件接口
     *
     * @param image  图片文件
     * @param module 目录
     * @return
     */
    @IPLimit(count = 200)
    @RequestMapping("/upload/public")
    public String uploadPublicFile(HttpServletRequest request, HttpServletResponse response, @RequestParam() MultipartFile image,
                                 String module) {
        logger.info("公有文件上传module=" + module);
        if (!validPublicModule(module)) {
            throw new BussinessException("上传目录不正确");
        }

        // 找不到类型则默认jpg
        String type = getImgType(image);
        if (type == null) {
            type = ".jpg";
        }

        String name = OSSUtils.savePublicFile(image, module, type);

      return OSSUtils.addPrefix(name);
    }


    /**
     * 上传私有文件
     *
     * @param request
     * @param response
     * @param image
     * @param module
     */
    @IPLimit(count = 200)
    @RequestMapping("/upload/private/{module}")
    public String uploadPrivateFile(HttpServletRequest request, HttpServletResponse response, @RequestParam() MultipartFile image,
                                  @PathVariable("module") String module) {

        if (!validPrivateModule(module)) {
            throw new BussinessException("上传目录不正确");
        }
        // 找不到类型则默认jpg
        String type = getImgType(image);
        if (type == null) {
            type = ".jpg";
        }
        String url = OSSUtils.savePrivateFile(image, module, type);
        return OSSUtils.filePrivateUrl(url);
    }

    /**
     * 下载私有文件
     *
     * @param request
     * @param response
     */
//	@RequiresPermissions("file:download:private")
    @RequestMapping("/download/private")
    public void getPrivateFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        String preview = request.getParameter("preview");
        if (StringUtils.isEmpty(fileName)) {
            throw new BussinessException("空文件");
        }

        OSSUtils.downPrivateFile(fileName, response, preview);
    }


    @RequestMapping("/download/public")
    public void getPublicFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        String preview = request.getParameter("preview");
        if (StringUtils.isEmpty(fileName)) {
            throw new BussinessException("空文件");
        }

        OSSUtils.downPublishFile(fileName, response, preview);
    }


    @RequestMapping("/list/filename")
    public String getFileOriginName(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        if (StringUtils.isEmpty(fileName)) {
            throw new BussinessException("空文件");
        }

        return OSSUtils.getFileOriginName(fileName);
    }

    /**
     * 校验公共目录
     *
     * @param module 目录
     * @return
     */
    private boolean validPublicModule(String module) {
        if (StringUtils.isEmpty(module)) {
            return false;
        }

        for (String m : PUBLIC_MODULE) {
            if (m.equals(module)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验私有目录
     *
     * @param module 目录
     * @return
     */
    private boolean validPrivateModule(String module) {
        if (StringUtils.isEmpty(module)) {
            return false;
        }

        for (String m : PRIVATE_MODULE) {
            if (m.equals(module)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件类型后缀名
     *
     * @param image
     * @return null 类型不是图片类型
     */
    private String getImgType(MultipartFile image) {
        if (image.isEmpty()) {
            return null;
        }
        String filename = image.getOriginalFilename();
        if (filename.lastIndexOf(".") == -1) {
            return null;
        }

        return typemap.get(filename.substring(filename.lastIndexOf(".")).toLowerCase());
    }
}
