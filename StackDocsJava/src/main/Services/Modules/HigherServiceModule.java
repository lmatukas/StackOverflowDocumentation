package Services.Modules;

import Services.ICache;
import Services.ICrud;
import Services.IDataBase;
import Services.IHigherService;
import Services.Impl.Cache;
import Services.Impl.Crud;
import Services.Impl.DataBase;
import Services.Impl.HigherService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class HigherServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IDataBase.class).to(DataBase.class);
        bind(ICrud.class).to(Crud.class);
        bind(ICache.class).to(Cache.class).in(Singleton.class);
        bind(IHigherService.class).to(HigherService.class);
    }
}
