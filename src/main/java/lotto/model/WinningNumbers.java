package lotto.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WinningNumbers {

    private final MainNumbers mainNumbers;

    private final Integer bonusNumber;

    private WinningNumbers(MainNumbers mainNumbers, Integer bonusNumber) {
        this.mainNumbers = mainNumbers;
        this.bonusNumber = bonusNumber;
        validateBonusNumber();
    }

    public WinningNumbers(List<Integer> mainNumbers, Integer bonusNumber) {
        this(new MainNumbers(mainNumbers), bonusNumber);
    }

    private void validateBonusNumber() {
        if (mainNumbers.numbers.contains(bonusNumber)) {
            throw new IllegalArgumentException();
        }
    }

    public MatchedResult check(Lotto lotto) {
        return new MatchedResult(checkMainNumbers(lotto), checkBonusNumber(lotto));
    }

    private Integer checkMainNumbers(Lotto lotto) {
        Set<Integer> standard = new HashSet<>(mainNumbers.numbers);
        standard.retainAll(new HashSet<>(lotto.getNumbers()));
        return standard.size();
    }

    private Boolean checkBonusNumber(Lotto lotto) {
        return lotto.getNumbers().contains(bonusNumber);
    }

    private static class MainNumbers {

        private final List<Integer> numbers;

        public MainNumbers(List<Integer> numbers) {
            this.numbers = numbers;
            validateNumbers();
        }

        private void validateNumbers() {
            boolean hasInvalidNumber = numbers.stream()
                    .filter(number -> hasValidLength())
                    .collect(Collectors.toSet())
                    .size() != numbers.size();

            if (hasInvalidNumber) {
                throw new IllegalArgumentException();
            }
        }

        private Boolean hasValidLength() {
            return numbers.size() == Lotto.NUMBER_LENGTH;
        }
    }
}
