package com.yongjia.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.yongjia.utils.UpYun.PARAMS;

public class UpYunUtil {

    // 运行前先设置好以下三个参数
    private static final String BUCKET_NAME = "qding-record";
    private static final String OPERATOR_NAME = "qiandingre";
    private static final String OPERATOR_PWD = "123qweasd";

    private static final String ThumbsName = "qdThumbs";
    private static final String Separator = "!";

    /** 绑定的域名 */
    private static final String URL = "http://" + BUCKET_NAME + ".b0.upaiyun.com";

    /** 根目录 */
    private static final String DIR_ROOT = "/";

    /** app图片目录 */
    // private static final String IMG_DIR = "images/";

    private static UpYun upyun = null;

    public static String uploadPic(File file, String upyunName) throws IOException {
        return uploadPic(file, upyunName, false);
    }

    public static String uploadPic(File file, String upyunName, boolean isSocialPush) throws IOException {
        // 初始化空间
        upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);

        // 要传到upyun后的文件路径
        String upyunPath = DIR_ROOT + upyunName;

        // 本地待上传的图片文件
        // 设置待上传文件的 Content-MD5 值
        // 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
        upyun.setContentMD5(UpYun.md5(file));

        // 设置待上传文件的"访问密钥"
        // 注意：
        // 仅支持图片空！，设置密钥后，无法根据原文件URL直接访问，需带URL后面加上（缩略图间隔标志符+密钥）进行访问
        // 举例：
        // 如果缩略图间隔标志符为"!"，密钥为"bac"，上传文件路径为"/folder/test.jpg"，
        // 那么该图片的对外访问地址为：http://空间域名 /folder/test.jpg!bac
        // upyun.setFileSecret("bac");

        // 设置缩略图的参数
        Map<String, String> params = new HashMap<String, String>();
        // 设置缩略图类型，必须搭配缩略图参数值（KEY_VALUE）使用，否则无效
        params.put(PARAMS.KEY_X_GMKERL_TYPE.getValue(), PARAMS.VALUE_FIX_MIN.getValue());
        if (!isSocialPush) {
            // 设置缩略图参数值，必须搭配缩略图类型（KEY_TYPE）使用，否则无效
            params.put(PARAMS.KEY_X_GMKERL_VALUE.getValue(), "640");

            // 设置缩略图的质量，默认 95
            params.put(PARAMS.KEY_X_GMKERL_QUALITY.getValue(), "70");

            // 设置缩略图的锐化，默认锐化（true）
            params.put(PARAMS.KEY_X_GMKERL_UNSHARP.getValue(), "true");
        } else {
            // 设置缩略图参数值，必须搭配缩略图类型（KEY_TYPE）使用，否则无效
            params.put(PARAMS.KEY_X_GMKERL_VALUE.getValue(), "1024");

            // 设置缩略图的质量，默认 95
            params.put(PARAMS.KEY_X_GMKERL_QUALITY.getValue(), "95");

            // 设置缩略图的锐化，默认锐化（true）
            params.put(PARAMS.KEY_X_GMKERL_UNSHARP.getValue(), "true");
        }

        // 若在 upyun 后台配置过缩略图版本号，则可以设置缩略图的版本名称
        // 注意：只有存在缩略图版本名称，才会按照配置参数制作缩略图，否则无效
        // params.put(PARAMS.KEY_X_GMKERL_THUMBNAIL.getValue(), ThumbsName);
        // 上传文件，并自动创建父级目录（最多10级）
        boolean success = upyun.writeFile(upyunPath, file, true, params);

        if (success) {
            return URL + upyunPath;
        } else {
            return null;
        }

    }

    public static String uploadFile(String filePath, String upyunName) throws IOException {
        // 初始化空间
        upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);

        // 要传到upyun后的文件路径
        String upyunPath = DIR_ROOT + upyunName;

        // 本地待上传的图片文件
        File file = new File(filePath);
        // 设置待上传文件的 Content-MD5 值
        // 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
        upyun.setContentMD5(UpYun.md5(file));

        // 设置待上传文件的"访问密钥"
        // 注意：
        // 仅支持图片空！，设置密钥后，无法根据原文件URL直接访问，需带URL后面加上（缩略图间隔标志符+密钥）进行访问
        // 举例：
        // 如果缩略图间隔标志符为"!"，密钥为"bac"，上传文件路径为"/folder/test.jpg"，
        // 那么该图片的对外访问地址为：http://空间域名 /folder/test.jpg!bac
        // upyun.setFileSecret("bac");

        // 上传文件，并自动创建父级目录（最多10级）
        boolean success = upyun.writeFile(upyunPath, file, true);

        if (success) {
            return URL + upyunPath;
        } else {
            return null;
        }

    }

    public static String getUpYunPath(String upyunName) {
        if (upyunName == null) {
            return null;
        }
        String upyunPath = DIR_ROOT + upyunName;
        return URL + upyunPath;
    }

}
