package gameCards;

public enum PlantCardEnum {

    CARD_PEASHOOTER("/images/cards/card_peashooter_hd.png"),
    CARD_FREEZE_PEASHOOTER("/images/cards/card_freezepeashooter.png"),
    CARD_SUNFLOWER("/images/cards/card_sunflower_hd.png"),
    CARD_WALLNUT("/images/cards/card_wallnut.png"),
    CARD_CHERRYBOMB("/images/cards/card_cherrybomb.png");

    private String imagePath;
    PlantCardEnum(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
