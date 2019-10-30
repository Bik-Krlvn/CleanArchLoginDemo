package com.cheise_proj.data.mapper.base

interface Mapper<T, E> {
    fun from(e: E): T
    fun to(t: T): E
}