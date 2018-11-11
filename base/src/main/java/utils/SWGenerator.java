package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class SWGenerator {

    private static final String XML_PROTOCOL = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    private static final String TAG_START = "<resources>";
    private static final String TAG_END = "</resources>";

    private static final String root = "/Users/amoryan/Documents/develop/web_projects";

    public static void main(String[] args) {
        generate();

        String[] swDps = {
                "300", "310", "320", "330", "340",
                "350", "360", "370", "380", "390",
                "400", "411", "430", "440", "450",
                "460", "470", "480"
        };
        for (String swDp : swDps) {
            generate(swDp);
        }
    }

    private static void generate() {
        StringBuilder builder = new StringBuilder(XML_PROTOCOL);
        builder.append("\n");
        builder.append(TAG_START);
        builder.append("\n\t<dimen name=\"base_dpi\">375dp</dimen>\n");
        for (int i = 0; i <= 375; i++) {
            builder.append("\t<dimen name=\"");
            String name = String.format("dp_%s", i);
            builder.append(name);
            builder.append("\">");
            builder.append(String.format("%sdp", i));
            builder.append("</dimen>\n");
        }
        builder.append(TAG_END);
        String content = builder.toString();

        File file = new File(root, "values");
        if (!file.exists()) {
            boolean b = file.mkdirs();
            System.out.println("" + b);
        }
        writeToFile(file, content);
    }

    private static void generate(String baseDPI) {
        BigDecimal bigDecimal = new BigDecimal(baseDPI).divide(new BigDecimal("375"), 10, BigDecimal.ROUND_HALF_UP);
        DecimalFormat format = new DecimalFormat("#0.00");

        StringBuilder builder = new StringBuilder(XML_PROTOCOL);
        builder.append("\n");
        builder.append(TAG_START);
        builder.append("\n\t<dimen name=\"base_dpi\">");
        builder.append(String.format("%sdp", baseDPI));
        builder.append("</dimen>\n");
        for (int i = 0; i <= 375; i++) {
            builder.append("\t<dimen name=\"");
            String name = String.format("dp_%s", i);
            builder.append(name);
            builder.append("\">");
            BigDecimal multiply = bigDecimal.multiply(new BigDecimal(i));
            String value = format.format(multiply);
            builder.append(String.format("%sdp", value));
            builder.append("</dimen>\n");
        }
        builder.append(TAG_END);
        String content = builder.toString();

        File file = new File(root, String.format("values-sw%sdp", baseDPI));
        writeToFile(file, content);
    }

    private static void writeToFile(File file, String content) {
        if (!file.exists()) {
            boolean b = file.mkdirs();
            System.out.println("" + b);
        }
        file = new File(file, "dimens.xml");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
