package com.chendong.app.shesfaraway.bean;

import java.util.List;

/**
 *
 * 作者：陈东
 * Github.com/chendongde310
 * 日期：2017/4/25 - 2:15
 * 语音识别返回结果
 */

public class VoiceResults {

    /**
     * content : {"item":["你好"]}
     * result : {"corpus_no":6412634940214290819,"err_no":0,"idx":-6,"res_type":3,"sn":"8ef45f6c-d565-485c-8930-f11812e4b011_s-1"}
     */

    private ContentBean content;
    private ResultBean result;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ContentBean {
        private List<String> item;

        public List<String> getItem() {
            return item;
        }

        public void setItem(List<String> item) {
            this.item = item;
        }
    }

    public static class ResultBean {
        /**
         * corpus_no : 6412634940214290819
         * err_no : 0
         * idx : -6
         * res_type : 3
         * sn : 8ef45f6c-d565-485c-8930-f11812e4b011_s-1
         */

        private long corpus_no;
        private int err_no;
        private int idx;
        private int res_type;
        private String sn;

        public long getCorpus_no() {
            return corpus_no;
        }

        public void setCorpus_no(long corpus_no) {
            this.corpus_no = corpus_no;
        }

        public int getErr_no() {
            return err_no;
        }

        public void setErr_no(int err_no) {
            this.err_no = err_no;
        }

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public int getRes_type() {
            return res_type;
        }

        public void setRes_type(int res_type) {
            this.res_type = res_type;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }
    }

    /**
     * 返回识别结果的文字
     * @return
     */
    public List<String> toContentString() {
        return getContent().getItem();
    }
}
