package com.cincc.o2o.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author li
 * Date: 2018/05/05
 */
@Configuration
public class PathUtil {
    private static String separator=System.getProperty("file.separator");

    private static String winPath;
    @Value("${win.base.path}")
    public  void setWinPath(String winPath) {
        PathUtil.winPath = winPath;
    }

    private static String linuxPath;
    @Value("${linux.base.path}")
    public  void setLinuxPath(String linuxPath) {
        PathUtil.linuxPath = linuxPath;
    }


    private static String shopPath;
    @Value("${shop.relevant.path}")
    public  void setShopPath(String shopPath) {
        PathUtil.shopPath = shopPath;
    }

    /**
     * 提供项目图片所要存储的根路径
     * 工具类，不需要实例化，所以用静态方法，调用如下
     * String realFileParentPath=PathUtil.getImgBasePath();
     * @return
     */
    public static String getImgBasePath(){
        String os=System.getProperty("os.name");
        String basePath="";
        if (os.toLowerCase().startsWith("win")){
            basePath=winPath;
        }else {
            basePath=linuxPath;
        }
        basePath=basePath.replace("/",separator);
        return basePath;
    }

    /**
     * 根据shopID获取商铺图片路径
     * @param shopId
     * @return
     */
    public static String getShopImagePath(long shopId){
        String imagePath=shopPath + shopId +separator;
        return imagePath.replace("/",separator);
    }

    /**
     * 根据userID获取用户图片路径
     * @return
     */
    public static String getPersonImagePath(){
        String imagePath="/upload/item/personinfo"+"/";
        return imagePath.replace("/",separator);
    }
}
