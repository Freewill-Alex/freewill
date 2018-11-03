package com.freewill.common.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {

	public static void main(String[] args) throws Exception {
		
		/** ZXingCode 二维码生成测试 */
		QRCodeUtil.writeQrCode("http://blog.csdn.net/eileen_crystal", 150, 150, "E://zxingcode.jpg", "jpg");
//
//		/** ZxingCode 二维码解析 */
//		String zxingAnalyze = QRCodeUtil.analyzeQrCode("E://zxingcode.jpg").toString();
//		System.out.println("zxingAnalyze----->" + zxingAnalyze);

		System.out.println("success");
	}

	// 二维码颜色
	private static final int BLACK = 0xFF000000;

	// 二维码颜色
	private static final int WHITE = 0xFFFFFFFF;

	private static void writeQrCode(String text, int width, int height, String outPutPath, String imageType) {
		try {
			BufferedImage image = createQrCode(text, width, height, imageType);
			File outPutImage = new File(outPutPath);
			// 如果图片不存在创建图片
			if (!outPutImage.exists())
				outPutImage.createNewFile();
			// 5、将二维码写入图片
			ImageIO.write(image, imageType, outPutImage);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("生成二维码图片失败");
		}
	}

	/**
	 * <span style="font-size:18px;font-weight:blod;">ZXing 方式生成二维码</span>
	 *
	 * @param text
	 *            <a href="javascript:void();">二维码内容</a>
	 * @param width
	 *            二维码宽
	 * @param height
	 *            二维码高
	 * @param outPutPath
	 *            二维码生成保存路径
	 * @param imageType
	 *            二维码生成格式
	 */
	public static BufferedImage createQrCode(String text, int width, int height, String imageType) {
		BufferedImage image = null;

		Map<EncodeHintType, Object> his = new HashMap<EncodeHintType, Object>();
		// 设置编码字符集
		his.put(EncodeHintType.CHARACTER_SET, "utf-8");
		his.put(EncodeHintType.MARGIN, 0);
		try {
			// 1、生成二维码
			BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, his);

//			BitMatrix encode = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height, his);
//			encode = deleteWhite(encode);// 删除白边

			// 2、获取二维码宽高
			int codeWidth = encode.getWidth();
			int codeHeight = encode.getHeight();

			// 3、将二维码放入缓冲流
			image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < codeWidth; i++) {
				for (int j = 0; j < codeHeight; j++) {
					// 4、循环将二维码内容定入图片
					image.setRGB(i, j, encode.get(i, j) ? BLACK : WHITE);
				}
			}
		} catch (WriterException e) {
			e.printStackTrace();
			System.out.println("二维码生成失败");
		}
		return image;
	}

	/**
	 * 手动去除二维码白边
	 * 
	 * @param matrix
	 * @return
	 */
//	private static BitMatrix deleteWhite(BitMatrix matrix) {
//		int[] rec = matrix.getEnclosingRectangle();
//		int resWidth = rec[2] + 1;
//		int resHeight = rec[3] + 1;
//
//		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
//		resMatrix.clear();
//		for (int i = 0; i < resWidth; i++) {
//			for (int j = 0; j < resHeight; j++) {
//				if (matrix.get(i + rec[0], j + rec[1]))
//					resMatrix.set(i, j);
//			}
//		}
//		return resMatrix;
//	}

	/**
	 * <span style="font-size:18px;font-weight:blod;">二维码解析</span>
	 *
	 * @param analyzePath
	 *            二维码路径
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object analyzeQrCode(String analyzePath) throws Exception {
		MultiFormatReader formatReader = new MultiFormatReader();
		Object result = null;
		try {
			File file = new File(analyzePath);
			if (!file.exists()) {
				return "二维码不存在";
			}
			BufferedImage image = ImageIO.read(file);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			result = formatReader.decode(binaryBitmap, hints);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

}
