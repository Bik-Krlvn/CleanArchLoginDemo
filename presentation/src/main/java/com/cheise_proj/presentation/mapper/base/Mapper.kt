package com.cheise_proj.presentation.mapper.base

/**
 * Base Mapper for presentation module
 *
 * @property Mapper
 */
interface Mapper<T, E> {
    /**
     * Maps type E to type T
     *
     * @param e type E
     * @return T return the map type of E
     */
    fun from(e: E): T

    /**
     * Maps type t to type E
     *
     * @param t type T
     * @return E return the map type of T
     */
    fun to(t: T): E
}