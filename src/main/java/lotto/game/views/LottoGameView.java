package lotto.game.views;

import java.util.List;
import java.util.Map;
import lotto.collaboration.enums.Prize;
import lotto.collaboration.lottos.dto.PlayerLotto;

public class LottoGameView {

    public final LottosView lottosView;
    public final WinningLottoView winningLottoView;

    public LottoGameView(final LottosView lottosView, final WinningLottoView winningLottoView) {
        this.lottosView = lottosView;
        this.winningLottoView = winningLottoView;
    }

    public int askPurchaseAmount() {
        return lottosView.askPurchaseAmount();
    }

    public void announcePurchaseLottos(final List<PlayerLotto> purchaseLottos) {
        lottosView.announcePurchaseLottos(purchaseLottos);
    }

    public List<Integer> askWinningNumbers() {
        return winningLottoView.askWinningNumbers();
    }

    public int askBonusNumber() {
        return winningLottoView.askBonusNumber();
    }

    public void announceWinningStatistics(final int purchaseAmount, final Map<Prize, List<PlayerLotto>> prizeListMap) {
        winningLottoView.announceWinningStatistics(purchaseAmount, prizeListMap);
    }

}