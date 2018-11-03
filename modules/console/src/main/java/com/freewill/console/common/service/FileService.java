package com.freewill.console.common.service;

import org.springframework.stereotype.Service;

@Service
public class FileService {

    /**
     * 生成PDF快照图片(多张)
     *
     * @param fileName
     * @return
     */
//    public String pdfToSnapshot(String fileName) {
//        InputStream pdfFile = OSSUtils.getPrivateFileAsStream(fileName);
//
//        List<InputStream> list = PdfUtil.pdfToImg(pdfFile);
//
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            InputStream imgFile = list.get(i);
//            String imgUrl = OSSUtils.savePrivateFile(imgFile, "bms/loan/snapshot", ".png");
//            sb.append(imgUrl);
//            if ((i + 1) < list.size()) {
//                sb.append(",");
//            }
//        }
//        return sb.toString();
//    }
}
