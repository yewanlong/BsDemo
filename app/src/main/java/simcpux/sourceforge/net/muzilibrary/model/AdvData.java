package simcpux.sourceforge.net.muzilibrary.model;

import java.io.Serializable;
import java.util.List;

public class AdvData extends ModelBase implements Serializable {
    private Adv data;

    public Adv getData() {
        return data;
    }

    public void setData(Adv data) {
        this.data = data;
    }

    public class Adv {
        private String page;
        private String count;

        private List<AdvDataList> list;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<AdvDataList> getList() {
            return list;
        }

        public void setList(List<AdvDataList> list) {
            this.list = list;
        }

        public class AdvDataList {
            private String id;
            private String title;
            private String cover;
            private String url;
            private String create_time;
            private String update_time;
            private String sort;
            private String status;
            private String type;
            private String article_id;
            private String img;
            private String name;

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

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getArticle_id() {
                return article_id;
            }

            public void setArticle_id(String article_id) {
                this.article_id = article_id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

    }
}
