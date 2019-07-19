package com.amcjt.jbhim.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class XmlHelper {

    private static String wxBaseXml(String toUser, String fromUser, String msgType, String content) {
        return "<xml>\n"
                + "  <ToUserName><![CDATA[" + toUser + "]]></ToUserName>\n"
                + "  <FromUserName><![CDATA[" + fromUser + "]]></FromUserName>\n"
                + "  <CreateTime>" + System.currentTimeMillis() + "</CreateTime>\n"
                + "  <MsgType><![CDATA[" + msgType + "]]></MsgType>\n"
                + "  " + content + "\n"
                + "</xml>";
    }

    public static String wxTextXml(String toUser, String fromUser, String content) {
        return wxBaseXml(toUser, fromUser, "text", "<Content><![CDATA[" + content + "]]></Content>");
    }

    public static String wxImageXml(String toUser, String fromUser, String mediaId) {
        String content = "<Image>\n"
                + "<MediaId><![CDATA[" + mediaId + "]]></MediaId>\n"
                + "</Image>\n";
        return wxBaseXml(toUser, fromUser, "image", content);
    }

    public static String wxVoiceXml(String toUser, String fromUser, String mediaId) {
        String content = "<Voice>\n"
                + "<MediaId><![CDATA[" + mediaId + "]]></MediaId>\n"
                + "</Voice>";
        return wxBaseXml(toUser, fromUser, "voice", content);
    }

    public static String wxVideoXml(String toUser, String fromUser, String mediaId, String title, String desc) {
        String content = "<Video>\n"
                + "<MediaId><![CDATA[" + mediaId + "]]></MediaId>\n"
                + "<Title><![CDATA[" + title + "]]></Title>\n"
                + "<Description><![CDATA[" + desc + "]]></Description>\n"
                + "</Video>";
        return wxBaseXml(toUser, fromUser, "video", content);
    }

    public static String wxMusicXml(String toUser, String fromUser, String mediaId, String title,
                                    String desc, String url, String hqUrl) {
        String content = "<Music>\n"
                + "<Title><![CDATA[" + title + "]]></Title>\n"
                + "<Description><![CDATA[" + desc + "]]></Description>\n"
                + "<MusicUrl><![CDATA[" + url + "]]></MusicUrl>\n"
                + "<HQMusicUrl><![CDATA[" + hqUrl + "]]></HQMusicUrl>\n"
                + "<ThumbMediaId><![CDATA[" + mediaId + "]]></ThumbMediaId>\n"
                + "</Music>";
        return wxBaseXml(toUser, fromUser, "music", content);
    }

    public static String wxNewsXml(String toUser, String fromUser, List<News> data) {
        StringBuilder stringBuilder = new StringBuilder("<ArticleCount>" + data.size() + "</ArticleCount>");
        stringBuilder.append("<Articles>");
        data.forEach(news -> stringBuilder.append("<item>\n")
                .append("<Title><![CDATA[" + news.getTitle() + "]]></Title>\n")
                .append("<Description><![CDATA[" + news.getDescription() + "]]></Description>\n")
                .append("<PicUrl><![CDATA[" + news.getPicUrl() + "]]></PicUrl>\n")
                .append("<Url><![CDATA[" + news.getUrl() + "]]></Url>\n")
                .append("</item>"));
        stringBuilder.append("</Articles>");
        return wxBaseXml(toUser, fromUser, "news", stringBuilder.toString());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class News {
        private String title;
        private String description;
        private String picUrl;
        private String url;
    }
}
