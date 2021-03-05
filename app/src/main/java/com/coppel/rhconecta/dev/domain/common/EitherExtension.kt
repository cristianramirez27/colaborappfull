package com.coppel.rhconecta.dev.domain.common

/**
 * Executes the received as param function if the Either value is Left. Do nothing in other case.
 * Returns the current Either value after these.
 */
suspend fun <L, R> Either<L, R>.onLeft(fn: suspend (L) -> Unit): Either<L, R> =
        when (this) {
            is Either.Left -> fn(left)
            else -> {
                /* PASS */
            }
        }.let { this }

/**
 * Executes the received as param function if the Either value is Right. Do nothing in other case.
 * Returns the current Either value after these.
 */
suspend fun <L, R> Either<L, R>.onRight(fn: suspend (R) -> Unit): Either<L, R> =
        when (this) {
            is Either.Right -> fn(right)
            else -> {
                /* PASS */
            }
        }.let { this }