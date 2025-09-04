package di.core

import core.app_scope.AppScope
import core.app_scope.AppScopeImpl
import core.dispatchers.AppDispatchers
import core.dispatchers.AppDispatchersImpl
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val moduleCore = module {
    /**
     * JSON
     */
    factory {
        Json {
            isLenient = true
            prettyPrint = true
            ignoreUnknownKeys = true
        }
    }
    /**
     * DISPATCHERS
     */
    single<AppDispatchers> {
        AppDispatchersImpl()
    }
    /**
     * APP SCOPE
     */
    singleOf(::AppScopeImpl) { bind<AppScope>() }
}