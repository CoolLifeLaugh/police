package com.lhsj.police.dingding.request;

import java.util.List;

import static com.lhsj.police.dingding.enums.MessageType.MULTI_ACTION_CARD;
import static java.util.Objects.isNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

public class DingMultiActionCardRequest extends AbstractDingRequest {

    private MultiActionCard actionCard;

    public DingMultiActionCardRequest() {
        super(MULTI_ACTION_CARD.code());
    }

    public DingMultiActionCardRequest(MultiActionCard actionCard) {
        super(MULTI_ACTION_CARD.code());
        this.actionCard = actionCard;
    }

    public MultiActionCard getActionCard() {
        return actionCard;
    }

    public void setActionCard(MultiActionCard actionCard) {
        this.actionCard = actionCard;
    }

    // --------- flow api ----------

    public static DingMultiActionCardRequest of() {
        return new DingMultiActionCardRequest();
    }

    public DingMultiActionCardRequest title(String title) {
        if (isNull(actionCard)) {
            actionCard = MultiActionCard.of().title(title);
        } else {
            actionCard.title(title);
        }
        return this;
    }

    public DingMultiActionCardRequest text(String text) {
        if (isNull(actionCard)) {
            actionCard = MultiActionCard.of().text(text);
        } else {
            actionCard.text(text);
        }
        return this;
    }

    public DingMultiActionCardRequest btnOrientation(String btnOrientation) {
        if (isNull(actionCard)) {
            actionCard = MultiActionCard.of().btnOrientation(btnOrientation);
        } else {
            actionCard.btnOrientation(btnOrientation);
        }
        return this;
    }

    public DingMultiActionCardRequest btn(Btn btn) {
        if (isNull(btn)) {
            return this;
        }

        if (isNull(actionCard)) {
            actionCard = MultiActionCard.of().btn(btn);
        } else {
            actionCard.btn(btn);
        }

        return this;
    }

    public DingMultiActionCardRequest btns(List<Btn> btns) {
        if (isEmpty(btns)) {
            return this;
        }

        if (isNull(actionCard)) {
            actionCard = MultiActionCard.of().btns(btns);
        } else {
            actionCard.btns(btns);
        }

        return this;
    }
}
