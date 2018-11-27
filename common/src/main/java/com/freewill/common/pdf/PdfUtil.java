package com.freewill.common.pdf;

import com.hg.xdoc.XDocService;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PdfUtil {

    /**
     * 经过测试,dpi为96,100,105,120,150,200中,105显示效果较为清晰,体积稳定,dpi越高图片体积越大
     */
    public static final int DEFAULT_DPI = 105;
    private static final Logger log = Logger.getLogger(PdfUtil.class);

    public static void main(String[] args) {
        String pdfFilePath = "D:/000000000/pdftoimg.pdf";
        String dstImgFolder = "D:/000000000";
        pdf2Img(pdfFilePath, dstImgFolder, DEFAULT_DPI);
    }

    /**
     * PDF文件转PNG图片，全部页数
     *
     * @param dstImgFolder 图片存放的文件夹
     * @param dpi          dpi越大转换后越清晰，相对转换速度越慢
     */
    public static void pdf2Img(String pdfFilePath, String dstImgFolder, int dpi) {
        PDDocument document = null;
        try {
            File file = new File(pdfFilePath);

            document = PDDocument.load(file);
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            int size = document.getNumberOfPages();
            for (int i = 0; i < size; i++) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(i, dpi, ImageType.RGB);

                File outFile = new File(dstImgFolder + i + ".png");
                ImageIO.write(image, "png", outFile);
            }
        } catch (Exception e) {
            log.warn("pdf2Img has a error", e);
        } finally {
            if (null != document) {
                try {
                    document.close();
                } catch (IOException e) {
                    log.warn("pdf2Img has a error", e);
                }
            }
        }
    }

    /**
     * PDF文件转PNG图片，全部页数
     *
     * @param dpi dpi越大转换后越清晰，相对转换速度越慢
     */
    public static List<BufferedImage> pdf2Img(InputStream pdfFile, int dpi) {
        List<BufferedImage> piclist = new ArrayList<BufferedImage>();
        PDDocument document = null;
        try {
            document = PDDocument.load(pdfFile);
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            int size = document.getNumberOfPages();
            for (int i = 0; i < size; i++) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(i, dpi, ImageType.RGB);
                piclist.add(image);
            }
        } catch (Exception e) {
            log.warn("pdf2Img has a error", e);
        } finally {
            if (null != document) {
                try {
                    document.close();
                } catch (IOException e) {
                    log.warn("pdf2Img has a error", e);
                }
            }
        }
        return piclist;
    }

    public static List<InputStream> pdfToImg(InputStream pdfFile) {
        List<InputStream> list = new ArrayList<InputStream>();
        List<BufferedImage> imgs = pdf2Img(pdfFile, DEFAULT_DPI);
        try {
            for (BufferedImage bi : imgs) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();

                ImageIO.write(bi, "png", os);
                InputStream is = new ByteArrayInputStream(os.toByteArray());

                list.add(is);
            }
        } catch (IOException e) {
            log.warn("pdfToImg has a error", e);
        }
        return list;
    }

    /**
     * word模板转PDF文件
     *
     * @param wordFileUrl
     * @param data
     * @param projectPath
     * @return
     */
    public static synchronized InputStream wordToPdf(String wordFileUrl, Map<String, Object> data, String projectPath) {

        XDocService service = new XDocService();
        try {
			/*Map<String, Object>  param  =  new  HashMap<String, Object>();
			param .put( "name" ,  "广东易捷好车信息科技有限公司" );
			param .put( "phone" ,  "13878894561" );*/

            String path = projectPath + "/temp.pdf";

            //service .to( "D:/000000000/aaa.docx" ,  new  File( "D:/000000000/a.pdf" ));
            //service.run("D:/000000000/aaa.docx" ,data,  new  File(path));
            service.run(wordFileUrl, data, new File(path));

            return new FileInputStream(path);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static InputStream pdfTempToFile(InputStream pdfTemp, Map<String, Object> data, String ttfPath) {

        InputStream pdfFileTemp = pdfTemp;

        ByteArrayOutputStream os = null;
        PdfStamper ps = null;
        PdfReader reader = null;
        try {
            // 1创建输出
            os = new ByteArrayOutputStream();

            // 2 读入pdf表单
            reader = new PdfReader(pdfFileTemp);

            // 3 根据表单生成一个新的pdf
            ps = new PdfStamper(reader, os);

            // 4 获取pdf表单
            AcroFields form = ps.getAcroFields();

            //String ttfPath = PdfUtil.class.getResource("/simfang.ttf").getPath();

            log.info("path:" + ttfPath);
            // 5给表单添加中文字体 这里采用系统字体。不设置的话，中文可能无法显示
            BaseFont baseFont = BaseFont.createFont(ttfPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            form.addSubstitutionFont(baseFont);

            // 6查询数据==============
			/*Map<String, Object> data = new HashMap<String, Object>();
			data.put("name", "广东易捷好车信息科技有限公司");
			data.put("phone", "广东易捷好车信息科技有限公司");*/

            // 7遍历data 给pdf表单表格赋值
            for (String key : data.keySet()) {
                form.setField(key, data.get(key) == null ? "" : data.get(key).toString());
            }
            ps.setFormFlattening(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                reader.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ByteArrayInputStream(os.toByteArray());
    }
}
