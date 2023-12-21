package com.xiaoyai.common.utils.barcode;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.pdf417.PDF417Writer;

/**
 * @author junyang
 * @date 2023/9/11
 */
public class BarCodeUtils {



        /**
         * 默认图片宽度
         */
        private static final int DEFAULT_PICTURE_WIDTH = 300;

        /**
         * 默认图片高度
         */
        private static final int DEFAULT_PICTURE_HEIGHT = 200;

        /**
         * 默认条形码宽度
         */
        private static final int DEFAULT_BAR_CODE_WIDTH = 250;

        /**
         * 默认条形码高度
         */
        private static final int DEFAULT_BAR_CODE_HEIGHT = 70;

        /**
         * 默认字体大小
         */
        private static final int DEFAULT_FONT_SIZE = 15;

        /**
         * 设置 条形码参数
         */
        private static final Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();

        static {
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        }

        /**
         * 获取条形码图片
         *
         * @param codeValue 条形码内容
         * @return 条形码图片
         */
        public static BufferedImage getBarCodeImage(String codeValue) {
            return getBarCodeImage(codeValue, DEFAULT_BAR_CODE_WIDTH, DEFAULT_BAR_CODE_HEIGHT);
        }

        /**
         * 获取条形码图片
         *
         * @param codeValue 条形码内容
         * @param width 宽度
         * @param height 高度
         * @return 条形码图片
         */
        public static BufferedImage getBarCodeImage(String codeValue, int width, int height) {
            // CODE_128是最常用的条形码格式
            return getBarCodeImage(codeValue, width, height, BarcodeFormat.CODE_128);
        }

        /**
         * 获取条形码图片
         *
         * @param codeValue 条形码内容
         * @param width 宽度
         * @param height 高度
         * @param barcodeFormat 条形码编码格式
         * @return 条形码图片
         */
        public static BufferedImage getBarCodeImage(String codeValue, int width, int height, BarcodeFormat barcodeFormat) {
            Writer writer;
            switch (barcodeFormat) {
                case CODE_128:
                    // 最常见的条形码，但是不支持中文
                    writer = new Code128Writer();
                    break;
                case PDF_417:
                    // 支持中文的条形码格式
                    writer = new PDF417Writer();
                    break;
                // 如果使用到其他格式，可以在这里添加
                default:
                    writer = new Code128Writer();
            }

            // 编码内容, 编码类型, 宽度, 高度, 设置参数
            BitMatrix bitMatrix;
            try {
                bitMatrix = writer.encode(codeValue, barcodeFormat, width, height, hints);
            } catch (WriterException e) {
                throw new RuntimeException("条形码内容写入失败");
            }
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        }

        /**
         * 获取条形码
         *
         * @param codeValue 条形码内容
         * @param bottomStr 底部文字
         * @return
         */
        public static BufferedImage getBarCodeWithWords(String codeValue, String bottomStr) {
            return getBarCodeWithWords(codeValue, bottomStr, "", "");
        }

        /**
         * 获取条形码
         *
         * @param codeValue 条形码内容
         * @param bottomStr 底部文字
         * @param topLeftStr 左上角文字
         * @param topRightStr 右上角文字
         * @return
         */
        public static BufferedImage getBarCodeWithWords(String codeValue, String bottomStr, String topLeftStr,
                                                        String topRightStr) {
            return getCodeWithWords(getBarCodeImage(codeValue), bottomStr, topLeftStr, topRightStr, DEFAULT_PICTURE_WIDTH,
                    DEFAULT_PICTURE_HEIGHT, 0, 0, 50, 0, 0, 0, DEFAULT_FONT_SIZE);
        }

        /**
         * 获取条形码
         *
         * @param codeImage 条形码图片
         * @param bottomStr 底部文字
         * @param topLeftStr 左上角文字
         * @param topRightStr 右上角文字
         * @param pictureWidth 图片宽度
         * @param pictureHeight 图片高度
         * @param codeOffsetX 条形码宽度
         * @param codeOffsetY 条形码高度
         * @param topLeftOffsetX 左上角文字X轴偏移量
         * @param topLeftOffsetY 左上角文字Y轴偏移量
         * @param topRightOffsetX 右上角文字X轴偏移量
         * @param topRightOffsetY 右上角文字Y轴偏移量
         * @param fontSize 字体大小
         * @return 条形码图片
         */
        public static BufferedImage getCodeWithWords(BufferedImage codeImage, String bottomStr, String topLeftStr,
                                                     String topRightStr, int pictureWidth, int pictureHeight, int codeOffsetX, int codeOffsetY,
                                                     int topLeftOffsetX, int topLeftOffsetY, int topRightOffsetX, int topRightOffsetY, int fontSize) {
            BufferedImage picImage = new BufferedImage(pictureWidth, pictureHeight, BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = picImage.createGraphics();
            // 抗锯齿
            setGraphics2D(g2d);
            // 设置白色
            setColorWhite(g2d, picImage.getWidth(), picImage.getHeight());

            // 条形码默认居中显示
            int codeStartX = (pictureWidth - codeImage.getWidth()) / 2 + codeOffsetX;
            int codeStartY = (pictureHeight - codeImage.getHeight()) / 2 + codeOffsetY;
            // 画条形码到新的面板
            g2d.drawImage(codeImage, codeStartX, codeStartY, codeImage.getWidth(), codeImage.getHeight(), null);

            // 画文字到新的面板
            g2d.setColor(Color.BLACK);
            // 字体、字型、字号
            g2d.setFont(new Font("宋体", Font.PLAIN, fontSize));
            // 文字与条形码之间的间隔
            int wordAndCodeSpacing = 3;

            if (StringUtils.isNotEmpty(bottomStr)) {
                // 文字长度
                int strWidth = g2d.getFontMetrics().stringWidth(bottomStr);
                // 文字X轴开始坐标，这里是居中
                int strStartX = codeStartX + (codeImage.getWidth() - strWidth) / 2;
                // 文字Y轴开始坐标
                int strStartY = codeStartY + codeImage.getHeight() + fontSize + wordAndCodeSpacing;
                // 画文字
                g2d.drawString(bottomStr, strStartX, strStartY);
            }

            if (StringUtils.isNotEmpty(topLeftStr)) {
                // 文字长度
                int strWidth = g2d.getFontMetrics().stringWidth(topLeftStr);
                // 文字X轴开始坐标
                int strStartX = codeStartX + topLeftOffsetX;
                // 文字Y轴开始坐标
                int strStartY = codeStartY + topLeftOffsetY - wordAndCodeSpacing;
                // 画文字
                g2d.drawString(topLeftStr, strStartX, strStartY);
            }

            if (StringUtils.isNotEmpty(topRightStr)) {
                // 文字长度
                int strWidth = g2d.getFontMetrics().stringWidth(topRightStr);
                // 文字X轴开始坐标，这里是居中
                int strStartX = codeStartX + codeImage.getWidth() - strWidth + topRightOffsetX;
                // 文字Y轴开始坐标
                int strStartY = codeStartY + topRightOffsetY - wordAndCodeSpacing;
                // 画文字
                g2d.drawString(topRightStr, strStartX, strStartY);
            }

            g2d.dispose();
            picImage.flush();

            return picImage;
        }

        /**
         * 设置 Graphics2D 属性 （抗锯齿）
         *
         * @param g2d Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
         */
        private static void setGraphics2D(Graphics2D g2d) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
            Stroke s = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
            g2d.setStroke(s);
        }

        /**
         * 设置背景为白色
         *
         * @param g2d Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
         */
        private static void setColorWhite(Graphics2D g2d, int width, int height) {
            g2d.setColor(Color.WHITE);
            // 填充整个屏幕
            g2d.fillRect(0, 0, width, height);
            // 设置笔刷
            g2d.setColor(Color.BLACK);
        }
    }

