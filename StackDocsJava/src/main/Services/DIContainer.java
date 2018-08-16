package Services;

import Services.Modules.HigherServiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public final class DIContainer {
    private static Injector injector;
    static {
        injector = Guice.createInjector(new HigherServiceModule());
    }

    public static Injector getInjector(){
        return injector;
    }

    private DIContainer(){}
}
