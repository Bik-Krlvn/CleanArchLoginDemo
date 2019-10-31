package com.cheise_proj.presentation.mapper.base

/**
 * @property Mapper generic mapper
 */
interface Mapper<T, E> {
    /**
     * @param e type E
     * @return T return the convert type of E
     */
    fun from(e: E): T

    /**
     * @param t type T
     * @return E return the convert type of T
     */
    fun to(t: T): E
}