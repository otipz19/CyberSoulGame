package com.mygdx.game.ui.upgrades;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.resources.HeroResourcesManager;
import com.mygdx.game.utils.Assets;

public abstract class UpgradeBar {
    private float defaultValue;
    protected float currentValue;
    protected Label valueLabel;

    protected float valueStep;
    protected TextButton stepButton;

    protected int price;
    private int priceStep;
    protected Label priceLabel;

    protected final HeroResourcesManager resourcesManager;

    private final Array<UpgradeTransaction> upgradeTransactions = new Array<>();

    public UpgradeBar(float defaultValue, float currentValue, float valueStep, int priceStep, HeroResourcesManager resourcesManager) {
        this.defaultValue = defaultValue;
        this.currentValue = currentValue;
        this.valueStep = valueStep;
        calcPrice();
        this.priceStep = priceStep;
        this.resourcesManager = resourcesManager;
    }

    private void calcPrice() {
        this.price = (int)((currentValue - defaultValue + valueStep) / valueStep);
    }

    public void createRow(Table table, String style) {
        if (valueLabel != null)
            return;

        Skin skin = MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN, Skin.class);

        table.setSkin(skin);
        table.setFillParent(true);

        Image icon = new Image(skin, style + "-icon");

        valueLabel = new Label(String.format("%.1f", currentValue), skin);
        valueLabel.setAlignment(Align.center);

        stepButton = new TextButton(String.format("+%.1f", valueStep), skin);
        stepButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (resourcesManager.getSouls() >= price) {
                    UpgradeTransaction transaction = new UpgradeTransaction(price) {
                        @Override
                        public void commit() {
                            currentValue += valueStep;
                            resourcesManager.changeSouls(-this.price);
                            upgrade();
                            updateValue();
                            updatePrice();
                        }

                        @Override
                        public void undo() {
                            currentValue -= valueStep;
                            resourcesManager.changeSouls(this.price);
                            revert();
                            updateValue();
                            updatePrice();
                        }
                    };
                    upgradeTransactions.add(transaction);
                    transaction.commit();
                }
            }
        });

        priceLabel = new Label(String.format(" %d ", price), skin );
        priceLabel.setAlignment(Align.center);

        Image soulIcon = new Image(skin,  "soul-icon");

        table.add(icon)
                .size(50)
                .pad(10, 10, 10, 10);
        table.add(valueLabel)
                .pad(0, 0, 0, 25)
                .growX();
        table.add(soulIcon)
                .size(50)
                .pad(10, 10, 10, 0);
        table.add(priceLabel);
        table.add().expandX();
        table.add(stepButton)
                .height(40)
                .growX()
                .pad(0, 0, 0, 10);;
        table.row();
    }

    protected abstract void upgrade();
    protected abstract void revert();

    public void updateValue() {
        valueLabel.setText(String.format("%.1f", currentValue));
    }

    private void updatePrice() {
        calcPrice();
        priceLabel.setText(String.format(" %d ", price));
    }

    public void reset() {
        upgradeTransactions.forEach(UpgradeTransaction::undo);
        upgradeTransactions.clear();
    }
}
