package mobile.sistema.com.foodlist.Model;

public class ListFoodModel {

    String idFood, titleFood, imgFood;

    public ListFoodModel(){

    }

    public ListFoodModel(String idFood, String titleFood, String imgFood) {
        this.idFood = idFood;
        this.titleFood = titleFood;
        this.imgFood = imgFood;
    }

    public String getIdFood() {
        return idFood;
    }

    public String getTitleFood() {
        return titleFood;
    }

    public String getImgFood() {
        return imgFood;
    }
}
