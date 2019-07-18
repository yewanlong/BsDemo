package simcpux.sourceforge.net.muzilibrary.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

public class ListData extends ModelBase {
    private String data;

    public ListData.Adv getData() {
        return JSON.parseObject(data, ListData.Adv.class);
    }

    public void setData(String data) {
        this.data = data;
    }

    public static class Adv {
        private String page;
        private int count;
        private List<HomeListData> list;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<HomeListData> getList() {
            return list;
        }

        public void setList(List<HomeListData> list) {
            this.list = list;
        }

        public class HomeListData implements Serializable {
            private String id;
            private String title;
            private String img;
            private String source;
            private String create_time;
            private String info_type;
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getInfo_type() {
                int type = 0;
                if ("1".equals(info_type)) {
                    type = 1;
                }
                return type + "";
            }

            public void setInfo_type(String info_type) {
                this.info_type = info_type;
            }
        }

    }


}
