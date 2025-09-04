package core.extensions

import kotlin.coroutines.cancellation.CancellationException

inline fun <T> runCatchingApp(callback: () -> T): Result<T> {
    return try {
        Result.success(callback())
    } catch (error: CancellationException) {
        throw error
    } catch (error: Throwable) {
        Result.failure(error)
    }
}