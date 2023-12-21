package com.xiaoyai.common.utils;

import cn.hutool.core.util.StrUtil;
import com.xiaoyai.common.constant.PrintConst;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.boot.system.ApplicationHome;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author junyang
 * @date 2023/9/11
 */
public class PrintUtils {
    /**
     * 获取当前jar包所在路径，如果不是jar包，是idea环境，则在 target/
     *
     * @return 路径
     */
    private static String getJarPath() {
        ApplicationHome applicationHome = new ApplicationHome(PrintUtils.class);
        File jar = applicationHome.getSource();
        return String.format("%s%s", jar.getParentFile().toString(), File.separator);
    }

    /**
     * 根据报表模板文件名称获取编译后的文件流
     * 只编译一次，如果编译过了，直接用编译过的文件
     *
     * @param tplName 模板文件名称
     * @return 编译后的文件流
     */
    private static InputStream getCompiledStream(String tplName) {
        String path = getJarPath();
        try {
            String jasperPath = StrUtil.format("{}{}{}", path, tplName, PrintConst.JASPER_EXT);
            File file = new File(jasperPath);
            if (!file.exists()) {
                // 如果 jasper 不存在，则读取 jrxml 文件，编译生成，然后放在目录下
                String tplPath = StrUtil.format("/{}/{}{}", PrintConst.TPL_FOLDER_NAME, tplName, PrintConst.JRXML_EXT);
                InputStream is = PrintUtils.class.getResourceAsStream(tplPath);
                OutputStream os = new FileOutputStream(jasperPath);
                JasperCompileManager.compileReportToStream(is, os);
                os.close();
            }
            File jasperFile = new File(jasperPath);
            return new FileInputStream(jasperFile);
        } catch (Exception e) {
            throw new IllegalArgumentException("获取报表模板文件失败！");
        }
    }


    public static String getCompiledPath(String tplName) {
        String path = getJarPath();
        try {
            String jasperPath = StrUtil.format("{}{}{}", path, tplName, PrintConst.JASPER_EXT);
            File file = new File(jasperPath);
            if (!file.exists()) {
                // 如果 jasper 不存在，则读取 jrxml 文件，编译生成，然后放在目录下
                String tplPath = StrUtil.format("/{}/{}{}", PrintConst.TPL_FOLDER_NAME, tplName, PrintConst.JRXML_EXT);
                InputStream is = PrintUtils.class.getResourceAsStream(tplPath);
                OutputStream os = new FileOutputStream(jasperPath);
                JasperCompileManager.compileReportToStream(is, os);
                os.close();
            }
            return jasperPath;
        } catch (Exception e) {
            throw new IllegalArgumentException("获取报表模板文件失败！");
        }
    }

    /**
     * 导出单个jasperPrint 的 pdf文件
     *
     * @param jasperPrint print 对象
     * @return 返回流
     */
    private static ByteArrayOutputStream exportSinglePagePdf(JasperPrint jasperPrint) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, os);
            os.flush();
            return os;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("导出文件失败！");
        }
    }

    /**
     * 多个jasperPrint 导出一个 pdf文件
     *
     * @param jasperPrintList print列表
     * @return 返回流
     */
    private static ByteArrayOutputStream exportMultiPagePdf(List<JasperPrint> jasperPrintList) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
            exporter.exportReport();
            os.flush();
            return os;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("导出文件失败！");
        }
    }

    /**
     * 根据参数获取对应的打印信息
     *
     * @param tplName 模板名称
     * @param params  属性参数，没有就传null
     * @param list    列表参数，没有就传null
     */
    public static JasperPrint getExportPrint(String tplName, Map params, List list) {
        try {
            InputStream jasperStream = getCompiledStream(tplName);

            // 获取列表信息的数据
            JRDataSource dataSource = new JRBeanCollectionDataSource(list);
            // 获取print对象
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
            jasperStream.close();
            return jasperPrint;

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("导出文件失败！");
        }
    }

    /**
     * 导出pdf文件为输出流
     *
     * @param printArr 打印的页面，如果传入单个则导出单页文件，多个则导出多页合并后的pdf
     * @return 返回流
     */
    public static ByteArrayOutputStream exportPdf(JasperPrint... printArr) {
        if (printArr.length == 1) {
            return exportSinglePagePdf(printArr[0]);
        } else if (printArr.length > 1) {
            return exportMultiPagePdf(Arrays.asList(printArr));
        } else {
            throw new IllegalArgumentException("导出文件失败！");
        }
    }
}
