package My_Project.integration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.function.Function;

public class Pagination<T> extends PageImpl<T> {

    private final long customize_total;

    public Pagination(List<T> content,long N) {
        super(content);
        customize_total = N;
    }

    @Override
    public long getTotalElements() {
        return customize_total;
    }

    @Override
    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) customize_total / (double) getSize());
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new PageImpl<>(getConvertedContent(converter), getPageable(), customize_total);
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PageImpl<?>)) {
            return false;
        }

        Pagination<?> that = (Pagination<?>) obj;

        return this.customize_total == that.customize_total && super.equals(obj);
    }

    @Override
    public int hashCode() {

        int result = 17;

        result += 31 * (int) (customize_total ^ customize_total >>> 32);
        result += 31 * super.hashCode();

        return result;
    }
}
