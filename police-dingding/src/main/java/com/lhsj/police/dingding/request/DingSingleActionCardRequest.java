package com.lhsj.police.dingding.request;

import static com.lhsj.police.dingding.enums.MessageType.SINGLE_ACTION_CARD;
import static java.util.Objects.isNull;

public class DingSingleActionCardRequest extends AbstractDingRequest {

    private SingleActionCard actionCard;

    public DingSingleActionCardRequest() {
        super(SINGLE_ACTION_CARD.code());
    }

    public DingSingleActionCardRequest(SingleActionCard actionCard) {
        super(SINGLE_ACTION_CARD.code());
        this.actionCard = actionCard;
    }

    public SingleActionCard getActionCard() {
        return actionCard;
    }

    public void setActionCard(SingleActionCard actionCard) {
        this.actionCard = actionCard;
    }

    // --------- flow api ----------

    public static DingSingleActionCardRequest of() {
        return new DingSingleActionCardRequest();
    }

    public DingSingleActionCardRequest title(String title) {
        if (isNull(actionCard)) {
            actionCard = SingleActionCard.of().title(title);
        } else {
            actionCard.title(title);
        }
        return this;
    }

    public DingSingleActionCardRequest text(String text) {
        if (isNull(actionCard)) {
            actionCard = SingleActionCard.of().text(text);
        } else {
            actionCard.text(text);
        }
        return this;
    }

    public DingSingleActionCardRequest btnOrientation(String btnOrientation) {
        if (isNull(actionCard)) {
            actionCard = SingleActionCard.of().btnOrientation(btnOrientation);
        } else {
            actionCard.btnOrientation(btnOrientation);
        }
        return this;
    }

    public DingSingleActionCardRequest singleTitle(String singleTitle) {
        if (isNull(actionCard)) {
            actionCard = SingleActionCard.of().singleTitle(singleTitle);
        } else {
            actionCard.singleTitle(singleTitle);
        }
        return this;
    }

    public DingSingleActionCardRequest singleURL(String singleURL) {
        if (isNull(actionCard)) {
            actionCard = SingleActionCard.of().singleURL(singleURL);
        } else {
            actionCard.singleURL(singleURL);
        }
        return this;
    }
}
