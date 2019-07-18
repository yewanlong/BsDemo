package simcpux.sourceforge.net.muzilibrary.model;

public class OrderData extends ModelBase {
    private Order data;

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }

    public class Order {
        private Order2 order;
        private String out_trade_no;
        private String payOrder;
        private String money;

        public Order2 getOrder() {
            return order;
        }

        public void setOrder(Order2 order) {
            this.order = order;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getPayOrder() {
            return payOrder;
        }

        public void setPayOrder(String payOrder) {
            this.payOrder = payOrder;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }


}
