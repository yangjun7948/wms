package com.xiaoyai.common.core.controller;

import com.xiaoyai.common.utils.StringUtils;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author junyang
 * @date 2023/7/19
 */
public class BaseApiController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private int[] key = new int[]{17, 47, 66, 118, 132, 60, 194, 141, 220, 86, 12, 146, 134, 85, 201, 185, 80, 217, 82, 233, 203, 171, 181,
            173, 218, 100, 14, 223, 29, 2, 204, 21, 228, 13, 190, 80, 151, 212, 156, 209, 38, 28, 170, 158, 48, 98, 136,
            41, 163, 228, 84, 61, 127, 109, 110, 100, 120, 148, 155, 32, 46, 225, 113, 218, 136, 246, 172, 122, 150, 122,
            34, 123, 62, 127, 195, 176, 2, 24, 125, 121, 219, 228, 61, 55, 173, 144, 148, 120, 157, 90, 214, 4, 57, 124,
            163, 68, 169, 207, 22, 57, 180, 15, 145, 41, 195, 215, 254, 254, 168, 188, 99, 97, 23, 34, 68, 73, 164, 58,
            45, 247, 98, 53, 219, 60, 163, 31, 66, 192, 218, 128, 7, 50, 2, 69, 196, 205, 83, 20, 115, 213, 144, 149,
            206, 224, 246, 117, 235, 24, 57, 91, 158, 111, 162, 29, 214, 127, 50, 86, 133, 164, 149, 62, 37, 40, 182, 50,
            96, 209, 93, 117, 245, 144, 198, 86, 103, 204, 102, 250, 69, 46, 50, 43, 135, 128, 224, 150, 142, 228, 109,
            70, 155, 252, 142, 205, 64, 83, 112, 166, 43, 197, 22, 27, 186, 194, 100, 14, 168, 76, 180, 11, 26, 55, 17,
            87, 115, 107, 181, 163, 70, 32, 162, 122, 100, 24, 217, 248, 2, 248, 23, 21, 92, 190, 207, 127, 25, 24, 112,
            196, 28, 211, 237, 66, 48, 99, 44, 100, 235, 56, 91, 212, 157, 204, 96, 11, 35, 154, 181, 163, 232, 107, 12,
            174, 54, 160, 174, 91, 102, 145, 62, 91, 250, 240, 135, 114, 49, 97, 74, 2, 234, 117, 14, 201, 158, 65, 119,
            143, 94, 197, 92, 125, 97, 203, 218, 176, 199, 63, 12, 143, 180, 208, 253, 91, 248, 254, 217, 171, 69, 237,
            217, 193, 80, 180, 184, 116, 94, 66, 17, 244, 41, 32, 251, 160, 53, 192, 248, 47, 22, 214, 170, 240, 85, 186,
            76, 26, 118, 177, 207, 158, 102, 202, 74, 112, 234, 154, 45, 172, 20, 103, 83, 133, 47, 23, 102, 149, 212,
            93, 38, 57, 6, 171, 150, 109, 67, 231, 108, 149, 104, 158, 252, 171, 113, 114, 142, 94, 212, 140, 180, 153,
            147, 77, 27, 174, 132, 37, 194, 62, 84, 182, 69, 26, 140, 151, 153, 186, 53, 219, 45, 4, 47, 150, 68, 117,
            105, 166, 21, 187, 136, 9, 192, 72, 168, 139, 164, 184, 145, 230, 232, 31, 37, 46, 66, 177, 90, 227, 32, 216,
            216, 211, 42, 247, 95, 177, 107, 252, 9, 165, 70, 220, 16, 7, 181, 75, 245, 251, 113, 1, 242, 224, 42, 78,
            242, 15, 221, 1, 85, 146, 27, 133, 135, 59, 226, 180, 222, 199, 231, 245, 32, 167, 175, 235, 161, 13, 15, 31,
            120, 245, 187, 94, 115, 31, 229, 247, 170, 218, 6, 81, 210, 107, 168, 118, 138, 191, 86, 212, 35, 2, 108, 46,
            20, 122, 223, 76, 49, 62, 171, 230, 1, 191, 136, 94, 213, 188};

    /**
     * 读取字节流，解码成map类型
     *
     * @param byteArray
     * @return
     */
    @SneakyThrows
    protected Map<String, String> readMapFromByteArray(byte[] byteArray) {
        Map<String, String> dictionary = new HashMap<>();
        //解码
        byte[] dataSrc = decodeStream(byteArray);

        int bufferLength = dataSrc[3] & 0xFF;
        bufferLength <<= 8;
        bufferLength += dataSrc[2] & 0xFF;
        bufferLength <<= 8;
        bufferLength += dataSrc[1] & 0xFF;
        bufferLength <<= 8;
        bufferLength += dataSrc[0] & 0xFF;

        if (bufferLength > dataSrc.length - 4) {
            return dictionary;
        }
        String dicStr = new String(Arrays.copyOfRange(byteArray, 4, bufferLength + 4), StandardCharsets.UTF_8);
        String picContent = StringUtils.byteArrayToHexString(Arrays.copyOfRange(byteArray, bufferLength + 4, dataSrc.length));
        String[] contentArr = dicStr.split("\\|");
        for (int i = 0; i < contentArr.length; i++) {
            String[] arrList = contentArr[i].split("\\=");
            String key = arrList.length > 0 ? arrList[0] : "";
            String value = arrList.length > 1 ? URLDecoder.decode(arrList[1], "utf-8") : "";
            dictionary.put(key, value);
        }
        if (picContent.length() > 0)
            dictionary.put("pic", picContent);
        return dictionary;
    }

    /**
     * 读取字节流，解码成map类型
     *
     * @param byteArray
     * @return
     */
    @SneakyThrows
    protected List<Map<String, String>> readMapListFromByteArray(byte[] byteArray) {
        List<Map<String, String>> mapList = new ArrayList<>();
        //解码
        byte[] dataBuffer = decodeStream(byteArray);
        String dataStr = new String(dataBuffer, StandardCharsets.UTF_8);
        //先通过;分割成每一条
        String[] dataArray = dataStr.split(";");


        for (int i = 0; i < dataArray.length; i++) {
            String[] tempArray = dataArray[i].split("\\|");
            Map<String, String> dictionary = new HashMap<>();
            for (int j = 0; j < tempArray.length; j++) {
                String[] arrList = tempArray[j].split("\\=");
                String key = arrList.length > 0 ? arrList[0] : "";
                String value = arrList.length > 1 ? URLDecoder.decode(arrList[1], "utf-8") : "";
                dictionary.put(key, value);
            }
            mapList.add(dictionary);
        }
        return mapList;
    }

    /**
     * 解码
     *
     * @param encodedData
     * @return
     */
    private byte[] decodeStream(byte[] encodedData) {

        int count = encodedData.length;

        for (int i = 0; i < count; i++) {
            int num = i % key.length;
            encodedData[i] = (byte) (encodedData[i] ^ key[num]);
        }
        return encodedData;
    }
}
