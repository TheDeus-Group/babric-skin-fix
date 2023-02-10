package me.allinkdev.skinfix;

public class AbstractClass {
    private static final String INSTANTIATION_EXCEPTION = "%s is an abstract class; it is unable to be instantiated!";

    public AbstractClass() {
        final Class<? extends AbstractClass> me = this.getClass();
        final String simpleName = me.getSimpleName();
        final String exceptionMessage = String.format(INSTANTIATION_EXCEPTION, simpleName);

        throw new UnsupportedOperationException(exceptionMessage);
    }
}
