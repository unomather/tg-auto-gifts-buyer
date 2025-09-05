package di

import di.api.moduleApi
import di.core.moduleCore
import di.navigation.moduleNavigation
import di.repository.moduleRepository
import di.screens.moduleScreens
import di.usecase.moduleUseCase

val appModules = listOf(
    moduleCore,
    moduleApi,
    moduleRepository,
    moduleUseCase,
    moduleScreens,
    moduleNavigation
)