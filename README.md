# Quy tắc 
- Các thành viên khi clone về hãy tạo 1 branch mới từ nhánh main
> VD: git checkout -b feature/order_list_item
- Sau khi xong thì push code lên nhánh đó. Tuyệt đối không được push trực tiêp lên nhánh main
> VD: git push origin feature/order_list_item
- Tạo pull request vào nhánh main, tuyệt đối KHÔNG được MERGE

# Mô tả Entity
## Item Class
> Chứa thông tin về mặt hàng trong hệ thống
> itemId: Mã mặt hàng
> itemName: Tên mặt hàng
> unit: Đơn vị của mặt hàng. VD: giày: đôi

## ItemSite Class
> Sản phẩm site kinh doanh trong hệ thống
> Kế thừa 3 thuộc tính phía trên của Item
> quantity: số lượng mặt hàng MH001 mà site S001 đang kinh doanh

## OrderListItem
> Danh sách mặt hàng cần đặt: được tạo ra bởi bộ phận sales

## OrderItem
> Là 1 sản phẩm trong danh sách mặt hàng cần đặt 
> Kế thừa 3 thuộc tính của Item
> quantityOrdered: số lượng mặt hàng cần đặt
> desiredDeliveryDate: ngày nhận mong muốn

## Order
> Đơn hàng được tạo ra khi Bộ Phận đặt hàng quốc tế đặt hàng với Site

## OrderItemSite
> 1 sản phẩm nằm trong đơn hàng Order phía trên
> ... Item
> quantityOrdered: số lượng MH001 đã đặt ở site S001
> desiredDeliveryDate: ngày giao hàng dự kiến cho mặt hàng MH001


//        selectedItemColumn.setCellValueFactory(param -> {
//            CheckBox checkBox = new CheckBox();
//
//            checkBox.setOnAction(event -> {
//
//            });
//
//            return new SimpleObjectProperty<>(checkBox);
//        });

//        deliveryTypeColumn.setCellValueFactory(param -> {
//            MenuButton menuButton = new MenuButton("Ship");
//            menuButton.getItems().addAll(new MenuItem("Air"));
//
//            menuButton.getStyleClass().add("findsite__main__box__bottom__action__btn__filter");
//
//            menuButton.setDisable(true);
//
//            return new SimpleObjectProperty<>(menuButton);
//        });



//        quantityOrderedColumn.setCellValueFactory(param -> {
//            TextField textField = new TextField();
//            textField.setDisable(true);
//
//            return new SimpleObjectProperty<>(textField);
//        });

