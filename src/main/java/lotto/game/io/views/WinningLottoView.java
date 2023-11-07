package lotto.game.io.views;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import lotto.collaboration.enums.Prize;
import lotto.collaboration.lottos.dto.PlayerLotto;
import lotto.game.io.Input;
import lotto.game.io.InteractionRepeatable;
import lotto.game.io.Output;

public class WinningLottoView implements InteractionRepeatable {

    public final Input input;
    public final Output output;

    public WinningLottoView(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public List<Integer> askWinningNumbers() {
        return supplyInteraction(() -> {
            output.println();
            output.println("당첨 번호를 입력해 주세요");
            List<Integer> winningNumbers = input.numbers(",");
            validate(winningNumbers);
            return winningNumbers;
        });
    }

    private void validate(List<Integer> winningNumbers) {
        winningNumbers.forEach(this::occurExceptionIfOutOfRange);
        occurExceptionIfNotSix(winningNumbers);
        occurExceptionIfDuplicated(winningNumbers);
    }

    private void occurExceptionIfOutOfRange(int bonusNumber) {
        if (bonusNumber < 1 || 45 < bonusNumber) {
            throw new IllegalArgumentException("번호는 1부터 45까지의 숫자 중에서 선택할 수 있습니다.");
        }
    }

    private void occurExceptionIfNotSix(List<Integer> winningNumbers) {
        if (winningNumbers.size() != 6) {
            throw new IllegalArgumentException("당첨 번호는 여섯자리 숫자입니다.");
        }
    }

    private void occurExceptionIfDuplicated(List<Integer> winningNumbers) {
        if (winningNumbers.size() != winningNumbers.stream().distinct().count()) {
            throw new IllegalArgumentException("당첨 번호는 중복될 수 없습니다.");
        }
    }

    public int askBonusNumber() {
        return supplyInteraction(() -> {
            output.println();
            output.println("보너스 번호를 입력해 주세요.");
            int bonusNumber = input.number();
            occurExceptionIfOutOfRange(bonusNumber);
            return bonusNumber;
        });
    }

    public void announceWinningStatistics(int purchaseAmount, Map<Prize, List<PlayerLotto>> prizeListMap) {
        output.println();
        output.println("당첨 통계");
        output.println("---");
        long totalPrizeMoney = 0L;
        for (Prize prize : Prize.values()) {
            if (prize == Prize.LOST) {
                continue;
            }
            List<PlayerLotto> prizeLottos = prizeListMap.getOrDefault(prize, List.of());
            output.println(prize.message() + " - " + prizeLottos.size() + "개");
            totalPrizeMoney += prize.money() * prizeLottos.size();
        }

        double result = (double) Math.round(((double) totalPrizeMoney / purchaseAmount) * 1_000) / 10;
        output.println("총 수익률은 " + new DecimalFormat("#,###.0").format(result) + "%입니다.");
    }

}
