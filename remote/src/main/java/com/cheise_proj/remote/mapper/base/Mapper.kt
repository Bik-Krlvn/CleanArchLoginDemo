package com.cheise_proj.remote.mapper.base

/**
 * Base mapper for remote
 *
 * @param T type any
 * @param E type any
 */
interface Mapper<T, E> {
    /**
     * Maps type E to type T
     *
     * @param e type E
     * @return maps type E to type T
     */
    fun from(e: E): T

    /**
     * Maps type T to type E
     *
     * @param t type T
     * @return maps type T to type E
     */
    fun to(t: T): E
}